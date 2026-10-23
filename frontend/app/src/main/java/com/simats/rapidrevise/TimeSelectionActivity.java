package com.simats.rapidrevise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.adapter.SubjectAdapter;
import com.simats.rapidrevise.response.SubjectResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeSelectionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));

        setContentView(R.layout.activity_time_selection);

        setupBottomNavigation(R.id.nav_revise);

        View option5 = findViewById(R.id.option5);

        option5.setOnClickListener(v -> {
            showSubjectPopup(5);
        });

        View option30 = findViewById(R.id.option30);

        option30.setOnClickListener(v -> {
            showSubjectPopup(30);
        });

        View option1hr = findViewById(R.id.option1hr);

        option30.setOnClickListener(v -> {
            showSubjectPopup(60);
        });

        View option3hr = findViewById(R.id.option3hr);

        option30.setOnClickListener(v -> {
            showSubjectPopup(180);
        });

        View optionFull = findViewById(R.id.optionFull);

        option30.setOnClickListener(v -> {
            showSubjectPopup(1440);
        });


    }

    private void showSubjectPopup(int selectedTime) {

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_subject_list, null);

        RecyclerView recyclerSubjects = dialogView.findViewById(R.id.recyclerSubjects);

        ArrayList<SubjectResponse> subjectList = new ArrayList<>();

        SubjectAdapter adapter = new SubjectAdapter(
                subjectList,

                // ✅ PASS BOTH subject_id + subject_name
                (subjectId, subjectName) -> {
                    Intent intent = new Intent(TimeSelectionActivity.this, RevisionSessionActivity.class);
                    intent.putExtra("subject_id", subjectId);
                    intent.putExtra("subject_name", subjectName);
                    intent.putExtra("time", selectedTime);
                    startActivity(intent);
                },

                null
        );

        recyclerSubjects.setLayoutManager(new LinearLayoutManager(this));
        recyclerSubjects.setAdapter(adapter);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        dialog.show();

        loadSubjectsForPopup(subjectList, adapter);
    }

    private void loadSubjectsForPopup(ArrayList<SubjectResponse> subjectList,
                                      SubjectAdapter adapter) {

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", 0);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getSubjects(userId).enqueue(new Callback<List<SubjectResponse>>() {
            @Override
            public void onResponse(Call<List<SubjectResponse>> call,
                                   Response<List<SubjectResponse>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    subjectList.clear();
                    subjectList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<SubjectResponse>> call, Throwable t) {
                Toast.makeText(TimeSelectionActivity.this,
                        "Failed to load subjects", Toast.LENGTH_SHORT).show();
            }
        });
    }
}