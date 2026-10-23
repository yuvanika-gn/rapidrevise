package com.simats.rapidrevise;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.simats.rapidrevise.model.QuizQuestion;

import java.lang.reflect.Type;
import java.util.List;

public class AnswerReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_review);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));

        LinearLayout container = findViewById(R.id.containerAnswers);
        Button btnBack = findViewById(R.id.btnBackBottom);

        // 🔙 BACK BUTTON (BOTTOM)
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, RevisionSessionActivity.class);
            startActivity(intent);
            finish();
        });

        String json = getIntent().getStringExtra("questions");

        Type type = new TypeToken<List<QuizQuestion>>() {}.getType();
        List<QuizQuestion> questions = new Gson().fromJson(json, type);

        if (questions == null) return;

        for (int i = 0; i < questions.size(); i++) {

            QuizQuestion q = questions.get(i);

            // 🔥 CARD
            CardView card = new CardView(this);
            card.setRadius(20f);
            card.setCardElevation(6f);
            card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            card.setCardElevation(8f);
            LinearLayout.LayoutParams cardParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            cardParams.setMargins(0, 0, 0, 50);
            card.setLayoutParams(cardParams);

            // INNER LAYOUT
            LinearLayout inner = new LinearLayout(this);
            inner.setOrientation(LinearLayout.VERTICAL);
            inner.setPadding(50, 50, 50, 50);


            // QUESTION
            TextView tvQ = new TextView(this);
            tvQ.setText("Q" + (i + 1) + ": " + q.getQuestion());
            tvQ.setTextSize(16);
            tvQ.setTextColor(Color.BLACK);
            tvQ.setTypeface(null, android.graphics.Typeface.BOLD);

            // CORRECT ANSWER
            TextView tvCorrect = new TextView(this);
            tvCorrect.setText("✔ Correct: " + q.getCorrectAnswer());
            tvCorrect.setTextColor(Color.parseColor("#4CAF50"));
            tvCorrect.setTextSize(14);
            tvCorrect.setPadding(0, 16, 0, 0);

            inner.addView(tvQ);

// 🔥 USER ANSWER
            TextView tvUser = new TextView(this);
            String userAns = q.getSelectedAnswer();

            if (userAns != null && !userAns.equals(q.getCorrectAnswer())) {

                tvUser.setText("✘ Your Answer: " + userAns);
                tvUser.setTextColor(Color.parseColor("#F44336")); // red
                tvUser.setPadding(0, 12, 0, 0);

                inner.addView(tvUser);
            }

// 🔥 CORRECT ANSWER
            tvCorrect.setText("✔ Correct: " + q.getCorrectAnswer());
            tvCorrect.setTextColor(Color.parseColor("#4CAF50"));
            tvCorrect.setPadding(0, 12, 0, 0);

            inner.addView(tvCorrect);
            card.addView(inner);
            container.addView(card);
        }
    }
}