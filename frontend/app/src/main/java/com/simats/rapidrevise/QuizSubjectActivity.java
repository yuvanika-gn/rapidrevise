package com.simats.rapidrevise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

public class QuizSubjectActivity extends AppCompatActivity {

    RecyclerView recyclerSubjects;
    ArrayList<SubjectResponse> subjectList;
    SubjectAdapter subjectAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_subject);

        recyclerSubjects = findViewById(R.id.recyclerSubjects);

        subjectList = new ArrayList<>();

        subjectAdapter = new SubjectAdapter(
                subjectList,

                (subjectId, subjectName) -> {
                    Intent intent = new Intent(this, QuizActivity.class);
                    intent.putExtra("subject_id", subjectId);
                    intent.putExtra("subject_name", subjectName);
                    startActivity(intent);
                },

                (id, name) -> {}
        );

        recyclerSubjects.setLayoutManager(new LinearLayoutManager(this));
        recyclerSubjects.setAdapter(subjectAdapter);

        loadSubjects();

    }

    private void loadSubjects() {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", 0);

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);

        api.getSubjects(userId).enqueue(new Callback<List<SubjectResponse>>() {
            @Override
            public void onResponse(Call<List<SubjectResponse>> call, Response<List<SubjectResponse>> response) {
                subjectList.clear();
                subjectList.addAll(response.body());
                subjectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SubjectResponse>> call, Throwable t) {
                Toast.makeText(QuizSubjectActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}