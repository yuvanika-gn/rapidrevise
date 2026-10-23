package com.simats.rapidrevise;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.simats.rapidrevise.model.ActivityModel;
import com.simats.rapidrevise.model.QuizQuestion;
import com.simats.rapidrevise.response.CommonResponse;
import com.simats.rapidrevise.response.QuizResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    TextView tvQuestion, tvProgress;
    ProgressBar progressBar;
    RelativeLayout loadingLayout;

    CardView card1, card2, card3, card4;
    TextView option1, option2, option3, option4;

    Button btnNext;

    List<QuizQuestion> questions;
    int currentIndex = 0;
    int score = 0;

    String selectedAnswer = "";

    int userId, subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));

        setContentView(R.layout.activity_quiz);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));

        // ✅ USER + SUBJECT
        userId = getSharedPreferences("user", MODE_PRIVATE)
                .getInt("user_id", -1);

        subjectId = getIntent().getIntExtra("subject_id", -1);
        String subjectName = getIntent().getStringExtra("subject_name");

        tvQuestion = findViewById(R.id.tvQuestion);
        tvProgress = findViewById(R.id.tvProgress);
        progressBar = findViewById(R.id.progressBar);
        loadingLayout = findViewById(R.id.loadingLayout);

        card1 = findViewById(R.id.cardOption1);
        card2 = findViewById(R.id.cardOption2);
        card3 = findViewById(R.id.cardOption3);
        card4 = findViewById(R.id.cardOption4);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        btnNext = findViewById(R.id.btnNext);

        // 🔥 TRACK QUIZ OPEN
        trackActivity(userId, subjectId, "quiz", "open");

        fetchQuiz(subjectName);

        // OPTION CLICK
        View.OnClickListener optionClick = v -> {

            resetOptions();

            CardView selectedCard = (CardView) v;
            selectedCard.setCardBackgroundColor(Color.parseColor("#E0F2F1"));

            if (v.getId() == R.id.cardOption1) selectedAnswer = option1.getText().toString();
            if (v.getId() == R.id.cardOption2) selectedAnswer = option2.getText().toString();
            if (v.getId() == R.id.cardOption3) selectedAnswer = option3.getText().toString();
            if (v.getId() == R.id.cardOption4) selectedAnswer = option4.getText().toString();
        };

        card1.setOnClickListener(optionClick);
        card2.setOnClickListener(optionClick);
        card3.setOnClickListener(optionClick);
        card4.setOnClickListener(optionClick);

        btnNext.setOnClickListener(v -> {

            if (selectedAnswer.isEmpty()) {
                Toast.makeText(this, "Select an option", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔥 CHECK ANSWER
            String correct = questions.get(currentIndex).getCorrectAnswer();

            QuizQuestion currentQuestion = questions.get(currentIndex);

// 🔥 SAVE USER ANSWER
            currentQuestion.setSelectedAnswer(selectedAnswer);

            if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
                score++;
            }

            else {
                trackActivity(userId, subjectId, "quiz", "correct");
            }

            // 🔥 TRACK NEXT
            trackActivity(userId, subjectId, "quiz", "wrong");
            currentIndex++;

            if (currentIndex < questions.size()) {
                showQuestion();
            } else {

                // 🔥 FINAL COMPLETE TRACK

                trackActivity(userId, subjectId, "quiz", "complete");

                Intent intent = new Intent(this, QuizResultActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("total", questions.size());
                intent.putExtra("questions", new Gson().toJson(questions));

                startActivity(intent);
                finish();
            }
        });
        trackActivity(userId, subjectId, "quiz");
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

    private void showQuestion() {

        QuizQuestion q = questions.get(currentIndex);

        tvQuestion.setText(q.getQuestion());

        option1.setText(q.getOptions().get(0));
        option2.setText(q.getOptions().get(1));
        option3.setText(q.getOptions().get(2));
        option4.setText(q.getOptions().get(3));

        selectedAnswer = "";
        resetOptions();

        tvProgress.setText("Question " + (currentIndex + 1) + "/" + questions.size());

        int progress = (int) (((currentIndex + 1) * 100f) / questions.size());

        ObjectAnimator.ofInt(progressBar, "progress", progress)
                .setDuration(400)
                .start();
    }

    private void resetOptions() {
        card1.setCardBackgroundColor(Color.WHITE);
        card2.setCardBackgroundColor(Color.WHITE);
        card3.setCardBackgroundColor(Color.WHITE);
        card4.setCardBackgroundColor(Color.WHITE);
    }

    private void fetchQuiz(String subjectName) {

        loadingLayout.setVisibility(View.VISIBLE);

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);

        Map<String, String> body = new HashMap<>();
        body.put("subject_name", subjectName);

        api.generateQuiz(body).enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {

                loadingLayout.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {

                    questions = response.body().getQuiz();

                    int time = getIntent().getIntExtra("time", 10);


                    int limit;

                    if (time == 5) limit = 5;
                    else if (time == 30) limit = 15;   // 🔥 MORE QUESTIONS
                    else limit = questions.size();

                    questions = questions.subList(0, Math.min(limit, questions.size()));

                    if (questions == null || questions.isEmpty()) {
                        Toast.makeText(QuizActivity.this, "No questions generated", Toast.LENGTH_SHORT).show();
                        return;
                    }



                    showQuestion();
                }
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {

                loadingLayout.setVisibility(View.GONE);
                Toast.makeText(QuizActivity.this, "Error loading quiz", Toast.LENGTH_SHORT).show();
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