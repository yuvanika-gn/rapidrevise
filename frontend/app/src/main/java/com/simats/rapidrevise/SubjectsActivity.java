package com.simats.rapidrevise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simats.rapidrevise.adapter.SubjectAdapter;
import com.simats.rapidrevise.request.AddSubjectRequest;
import com.simats.rapidrevise.response.AddSubjectResponse;
import com.simats.rapidrevise.response.SubjectResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectsActivity extends BaseActivity {

    RecyclerView recyclerSubjects;
    ArrayList<SubjectResponse> subjectList;
    SubjectAdapter subjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));


        recyclerSubjects = findViewById(R.id.recyclerSubjects);

        subjectList = new ArrayList<>();

        subjectAdapter = new SubjectAdapter(
                subjectList,

                // Click → open subject
                (subjectId, subjectName) -> {
                    Intent intent = new Intent(SubjectsActivity.this, SubjectDetailActivity.class);
                    intent.putExtra("subject_id", subjectId);
                    intent.putExtra("subject_name", subjectName);
                    startActivity(intent);
                },

                // Long click → delete
                (subjectId, subjectName) -> {
                    // optional: delete logic same as Home
                }
        );

        recyclerSubjects.setLayoutManager(new LinearLayoutManager(this));
        recyclerSubjects.setAdapter(subjectAdapter);

        loadSubjects();

        setupBottomNavigation(R.id.nav_subjects);

        FloatingActionButton fabAddSubject = findViewById(R.id.fabAddSubject);

        fabAddSubject.setOnClickListener(v -> showAddSubjectDialog());

    }

    private void showAddSubjectDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_subject, null);

        EditText etSubjectName = dialogView.findViewById(R.id.etSubjectName);
        TextView btnCancel = dialogView.findViewById(R.id.btnCancel);
        TextView btnAdd = dialogView.findViewById(R.id.btnAdd);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnAdd.setOnClickListener(v -> {
            String subject = etSubjectName.getText().toString().trim();

            if (subject.isEmpty()) {
                Toast.makeText(this, "Subject cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
            int userId = prefs.getInt("user_id", 0);

            ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
            AddSubjectRequest request = new AddSubjectRequest(userId, subject);

            apiService.addSubject(request).enqueue(new Callback<AddSubjectResponse>() {
                @Override
                public void onResponse(Call<AddSubjectResponse> call,
                                       Response<AddSubjectResponse> response) {

                    if (response.isSuccessful()
                            && response.body() != null
                            && response.body().getStatus().equals("success")) {

                        loadSubjects(); // 🔥 refresh THIS page
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<AddSubjectResponse> call, Throwable t) {
                    Toast.makeText(SubjectsActivity.this,
                            "Failed to add subject", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }
    private void loadSubjects() {

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
                    subjectAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<SubjectResponse>> call, Throwable t) {
                Toast.makeText(SubjectsActivity.this,
                        "Failed to load subjects", Toast.LENGTH_SHORT).show();
            }
        });
    }
}