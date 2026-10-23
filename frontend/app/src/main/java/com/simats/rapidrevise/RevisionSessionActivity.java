package com.simats.rapidrevise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.simats.rapidrevise.response.AiResponse;
import com.simats.rapidrevise.response.SubjectResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevisionSessionActivity extends BaseActivity {

    TextView tvAiSummary;

    int subjectId;
    String subjectName;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_session);
        setupBottomNavigation(R.id.nav_revise);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));

        // ✅ GET DATA
        subjectId = getIntent().getIntExtra("subject_id", 0);
        subjectName = getIntent().getStringExtra("subject_name");
        time = getIntent().getIntExtra("time", 5);

        View flashcardsSection = findViewById(R.id.flashcardsSection);
        View keyconceptsSection = findViewById(R.id.keyconceptsSection);
        View formulasSection = findViewById(R.id.formulasSection);
        View quizSection = findViewById(R.id.quizSection);

        // ✅ NORMAL FEATURES (same logic)
        flashcardsSection.setOnClickListener(v -> handleNavigation("flashcards"));
        keyconceptsSection.setOnClickListener(v -> handleNavigation("keypoints"));
        formulasSection.setOnClickListener(v -> handleNavigation("formulas"));

        // ✅ QUIZ → ALWAYS ASK SUBJECT
        quizSection.setOnClickListener(v -> showSubjectDialog("quiz"));

        tvAiSummary = findViewById(R.id.tvAiSummary);
        generateSummary("Newton's laws describe motion...");

        // BACK BUTTON
        View backArrow = findViewById(R.id.btnBack);
        backArrow.setOnClickListener(v -> {
            Intent intent = new Intent(RevisionSessionActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    // ✅ COMMON NAVIGATION (FLASHCARDS, KEYPOINTS, FORMULAS)
    private void handleNavigation(String type) {

        if (subjectId != 0 && subjectName != null) {

            Intent intent;

            switch (type) {
                case "flashcards":
                    intent = new Intent(this, FlashcardsActivity.class);
                    break;

                case "keypoints":
                    intent = new Intent(this, KeyPointsActivity.class);
                    break;

                case "formulas":
                    intent = new Intent(this, FormulasActivity.class);
                    break;

                default:
                    return;
            }

            intent.putExtra("subject_id", subjectId);
            intent.putExtra("subject_name", subjectName);
            intent.putExtra("time", time);

            startActivity(intent);

        } else {
            showSubjectDialog(type);
        }
    }

    // ✅ SUBJECT POPUP (NOW SUPPORTS QUIZ ALSO)
    private void showSubjectDialog(String type) {

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getSubjects(userId).enqueue(new Callback<List<SubjectResponse>>() {
            @Override
            public void onResponse(Call<List<SubjectResponse>> call, Response<List<SubjectResponse>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<SubjectResponse> subjects = response.body();

                    String[] subjectNames = new String[subjects.size()];
                    for (int i = 0; i < subjects.size(); i++) {
                        subjectNames[i] = subjects.get(i).getSubject_name();
                    }

                    new AlertDialog.Builder(RevisionSessionActivity.this)
                            .setTitle("Select Subject")
                            .setItems(subjectNames, (dialog, which) -> {

                                SubjectResponse selected = subjects.get(which);

                                Intent intent;

                                switch (type) {
                                    case "flashcards":
                                        intent = new Intent(RevisionSessionActivity.this, FlashcardsActivity.class);
                                        break;

                                    case "keypoints":
                                        intent = new Intent(RevisionSessionActivity.this, KeyPointsActivity.class);
                                        break;

                                    case "formulas":
                                        intent = new Intent(RevisionSessionActivity.this, FormulasActivity.class);
                                        break;

                                    case "quiz":   // ✅ THIS WAS MISSING
                                        intent = new Intent(RevisionSessionActivity.this, QuizActivity.class);
                                        break;

                                    default:
                                        return;
                                }

                                intent.putExtra("subject_id", selected.getId());
                                intent.putExtra("subject_name", selected.getSubject_name());

                                startActivity(intent);
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<SubjectResponse>> call, Throwable t) {
                tvAiSummary.setText("Failed to load subjects");
            }
        });
    }

    // ✅ AI SUMMARY
    private void generateSummary(String content) {

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        Map<String, String> body = new HashMap<>();
        body.put("content", content);

        apiService.generateSummary(body)
                .enqueue(new Callback<AiResponse>() {
                    @Override
                    public void onResponse(Call<AiResponse> call, Response<AiResponse> response) {

                        if (response.isSuccessful() && response.body() != null
                                && response.body().getStatus().equals("success")) {

                            tvAiSummary.setText(response.body().getSummary());
                        }
                    }

                    @Override
                    public void onFailure(Call<AiResponse> call, Throwable t) {
                        tvAiSummary.setText("Failed to generate summary");
                    }
                });
    }
}