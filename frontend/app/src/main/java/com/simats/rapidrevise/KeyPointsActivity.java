package com.simats.rapidrevise;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.adapter.KeyPointsAdapter;
import com.simats.rapidrevise.model.ActivityModel;
import com.simats.rapidrevise.model.KeyPoint;
import com.simats.rapidrevise.response.CommonResponse;
import com.simats.rapidrevise.response.KeyPointsResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeyPointsActivity extends BaseActivity {

    RecyclerView recyclerKeyPoints;
    KeyPointsAdapter adapter;
    List<KeyPoint> keyPointList;
    ProgressBar progressLoader;

    int userId, subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));

        setContentView(R.layout.activity_key_points);

        setupBottomNavigation(R.id.nav_revise);

        // ✅ GET USER + SUBJECT
        userId = getSharedPreferences("user", MODE_PRIVATE)
                .getInt("user_id", -1);

        subjectId = getIntent().getIntExtra("subject_id", -1);
        String subjectName = getIntent().getStringExtra("subject_name");

        // ✅ INIT VIEWS
        recyclerKeyPoints = findViewById(R.id.recyclerKeyPoints);
        recyclerKeyPoints.setLayoutManager(new LinearLayoutManager(this));

        progressLoader = findViewById(R.id.progressLoader);

        keyPointList = new ArrayList<>();
        adapter = new KeyPointsAdapter(keyPointList);
        recyclerKeyPoints.setAdapter(adapter);

        // ✅ FETCH DATA
        fetchKeyPoints(subjectName);
        trackActivity(userId, subjectId, "key_concept");
    }

    private void trackActivity(int userId, int subjectId, String feature) {

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("subject_id", subjectId);
        map.put("feature_type", feature);
        map.put("action_type", "open");
        map.put("time_spent", 5); // simple fixed value

        api.trackActivity(map).enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {}

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {}
        });
    }

    private void fetchKeyPoints(String subjectName) {

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        Map<String, String> body = new HashMap<>();
        body.put("subject_name", subjectName);

        apiService.getKeyPoints(body).enqueue(new Callback<KeyPointsResponse>() {

            @Override
            public void onResponse(Call<KeyPointsResponse> call, Response<KeyPointsResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    keyPointList.clear();

                    if (response.body().getPoints() != null) {

                        int time = getIntent().getIntExtra("time", 10);

                        List<KeyPoint> allPoints = response.body().getPoints();

                        int limit;

                        if (time == 5) limit = 3;
                        else if (time == 30) limit = 12;   // 🔥 MORE CONCEPTS
                        else limit = allPoints.size();

                        allPoints = allPoints.subList(0, Math.min(limit, allPoints.size()));

                        for (KeyPoint point : allPoints) {

                            String title = point.getTitle();
                            String text = point.getText();

                            if (title == null) title = "Key Concept";
                            if (text == null) text = "";

// 🔥 MAKE CONTENT RICHER FOR 30 MINS

                            if (time == 30) {
                                text = text + "\n\n👉 Example: " + generateExample(text)
                                        + "\n\n👉 Tip: Remember this in exams!";
                            }

                            boolean highlight = text.length() > 60;

                            keyPointList.add(new KeyPoint(title, text, highlight));
                        }

                    } else {
                        Toast.makeText(KeyPointsActivity.this,
                                "No key points found",
                                Toast.LENGTH_SHORT).show();
                    }

                    adapter.notifyDataSetChanged();
                }

                progressLoader.setVisibility(View.GONE);
                recyclerKeyPoints.setVisibility(View.VISIBLE);
            }

            private String generateExample(String text) {
                if (text.toLowerCase().contains("law")) {
                    return "Used in real-world physics problems";
                } else if (text.toLowerCase().contains("formula")) {
                    return "Apply this in numerical problems";
                }
                return "Important for understanding concepts";
            }

            @Override
            public void onFailure(Call<KeyPointsResponse> call, Throwable t) {
                Toast.makeText(KeyPointsActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                progressLoader.setVisibility(View.GONE);
                recyclerKeyPoints.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.loaderContainer).setVisibility(View.VISIBLE);
        recyclerKeyPoints.setVisibility(View.GONE);
    }

    // 🔥 TRACK FUNCTION
    private void trackActivity(int userId, int subjectId, String feature, String action) {

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("subject_id", subjectId);
        map.put("feature_type", feature);
        map.put("action_type", action);
        map.put("time_spent", 5);

        api.trackActivity(map).enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {}

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {}
        });
    }
}