package com.simats.rapidrevise;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class QuizResultActivity extends AppCompatActivity {

    TextView tvScore, tvMessage, tvTitle;
    Button btnFinish, btnViewAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));

        tvScore = findViewById(R.id.tvScore);
        tvMessage = findViewById(R.id.tvMessage);
        tvTitle = findViewById(R.id.tvResultTitle);
        btnFinish = findViewById(R.id.btnFinish);
        Button btnViewAnswers = findViewById(R.id.btnViewAnswers);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);

        // RESULT TEXT
        if (score >= total / 2) {
            tvTitle.setText("Great Job!");
            tvMessage.setText("You did really well!");
            tvScore.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            tvTitle.setText("Keep Trying!");
            tvMessage.setText("Practice makes perfect!");
            tvScore.setTextColor(Color.parseColor("#F44336"));
        }

        runPopAnimation(score, total);

        btnFinish.setOnClickListener(v -> {
            Intent intent = new Intent(this, RevisionSessionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        btnViewAnswers.setOnClickListener(v -> {

            Intent intent = new Intent(this, AnswerReviewActivity.class);

            // PASS QUESTIONS AGAIN
            intent.putExtra("questions",
                    getIntent().getStringExtra("questions"));

            startActivity(intent);
        });
    }

    private void runPopAnimation(int score, int total) {

        View card = findViewById(R.id.resultCard);
        View flash = findViewById(R.id.popFlash);
        FrameLayout container = findViewById(R.id.popperContainer);

        // 🎯 CARD POP
        card.setScaleX(0.7f);
        card.setScaleY(0.7f);

        card.animate()
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(200)
                .withEndAction(() ->
                        card.animate().scaleX(1f).scaleY(1f).setDuration(150)
                )
                .start();

        // 🎯 FLASH
        flash.animate().alpha(1f).setDuration(80)
                .withEndAction(() ->
                        flash.animate().alpha(0f).setDuration(200)
                );

        // 🎯 SCORE COUNT
        ValueAnimator animator = ValueAnimator.ofInt(0, score);
        animator.setDuration(800);
        animator.addUpdateListener(a -> {
            int val = (int) a.getAnimatedValue();
            tvScore.setText(val + "/" + total);
        });
        animator.start();

        // 🎯 VIBRATION
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) vibrator.vibrate(80);

        // 🎉 POPPERS
        for (int i = 0; i < 20; i++) {

            View dot = new View(this);

            FrameLayout.LayoutParams params =
                    new FrameLayout.LayoutParams(12, 12);

            dot.setLayoutParams(params);
            dot.setBackgroundColor(getRandomColor());

            container.addView(dot);

            float dx = (float) (Math.random() * 600 - 300);
            float dy = (float) (Math.random() * 600 - 300);

            dot.animate()
                    .translationX(dx)
                    .translationY(dy)
                    .alpha(0f)
                    .setDuration(800)
                    .withEndAction(() -> container.removeView(dot))
                    .start();
        }
    }

    private int getRandomColor() {
        int[] colors = {
                Color.RED, Color.BLUE, Color.GREEN,
                Color.YELLOW, Color.MAGENTA, Color.CYAN
        };
        return colors[(int) (Math.random() * colors.length)];
    }
}