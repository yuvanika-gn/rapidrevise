package com.simats.rapidrevise;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.adapter.FormulaAdapter;
import com.simats.rapidrevise.model.ActivityModel;
import com.simats.rapidrevise.model.Formula;
import com.simats.rapidrevise.response.CommonResponse;
import com.simats.rapidrevise.response.FormulaResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormulasActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FormulaAdapter adapter;

    String subjectName;
    ProgressBar progressBar;

    int userId, subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulas);

        progressBar = findViewById(R.id.progressBar);

        // ✅ GET USER + SUBJECT
        userId = getSharedPreferences("user", MODE_PRIVATE)
                .getInt("user_id", -1);

        subjectId = getIntent().getIntExtra("subject_id", -1);
        subjectName = getIntent().getStringExtra("subject_name");

        if (subjectName == null || subjectName.isEmpty()) {
            Toast.makeText(this, "Invalid Subject", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // ✅ INIT RECYCLER
        recyclerView = findViewById(R.id.recyclerFormulas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 🔥 TRACK OPEN
        trackActivity(userId, subjectId, "formula", "open");
        // ✅ LOAD DATA
        loadFormulas();
        trackActivity(userId, subjectId, "formula");
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

    private void loadFormulas() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        Map<String, String> body = new HashMap<>();
        body.put("subject_name", subjectName);

        apiService.getFormulas(body).enqueue(new Callback<FormulaResponse>() {
            @Override
            public void onResponse(Call<FormulaResponse> call, Response<FormulaResponse> response) {

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {

                    List<Formula> list = response.body().getFormulas();

                    if (list == null || list.isEmpty()) {
                        Toast.makeText(FormulasActivity.this, "No formulas found", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int time = getIntent().getIntExtra("time", 10);

                    int limit;

                    if (time == 5) limit = 3;
                    else if (time == 30) limit = 10;   // 🔥 MORE FORMULAS
                    else limit = list.size();

                    list = list.subList(0, Math.min(limit, list.size()));

                    adapter = new FormulaAdapter(FormulasActivity.this, list);
                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(FormulasActivity.this, "Failed to load formulas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FormulaResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                Toast.makeText(FormulasActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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