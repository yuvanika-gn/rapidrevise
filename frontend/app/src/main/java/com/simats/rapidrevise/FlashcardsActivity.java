package com.simats.rapidrevise;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.simats.rapidrevise.adapter.FlashcardAdapter;
import com.simats.rapidrevise.model.Flashcard;
import com.simats.rapidrevise.response.CommonResponse;
import com.simats.rapidrevise.response.FlashcardResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlashcardsActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    ImageView btnNext, btnPrevious, btnShuffle, btnRestart;
    FlashcardAdapter adapter;
    TextView tvCardCount;
    LinearLayout indicatorLayout;
    ProgressBar progressBar;

    int userId, subjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flashcards);

        userId = getSharedPreferences("user", MODE_PRIVATE)
                .getInt("user_id", -1);

        subjectId = getIntent().getIntExtra("subject_id", -1);
        String subjectName = getIntent().getStringExtra("subject_name");

        viewPager = findViewById(R.id.viewPagerFlashcards);
        tvCardCount = findViewById(R.id.tvCardCount);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnShuffle = findViewById(R.id.btnShuffle);
        btnRestart = findViewById(R.id.btnRestart);
        indicatorLayout = findViewById(R.id.indicatorLayout);
        progressBar = findViewById(R.id.progressBar);

        // ✅ TRACK OPEN
        trackActivity(userId, subjectId, "flashcard", "open");

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (adapter != null) {
                    tvCardCount.setText((position + 1) + "/" + adapter.getItemCount());
                    updateIndicators(position);

                    trackActivity(userId, subjectId, "flashcard", "view");
                }
            }
        });

        btnNext.setOnClickListener(v -> {
            if (adapter == null) return;

            int next = viewPager.getCurrentItem() + 1;
            if (next < adapter.getItemCount()) {
                viewPager.setCurrentItem(next, true);
                trackActivity(userId, subjectId, "flashcard", "next");
            }
        });

        btnPrevious.setOnClickListener(v -> {
            if (adapter == null) return;

            int prev = viewPager.getCurrentItem() - 1;
            if (prev >= 0) {
                viewPager.setCurrentItem(prev, true);
                trackActivity(userId, subjectId, "flashcard", "previous");
            }
        });

        btnRestart.setOnClickListener(v -> {
            if (adapter == null) return;

            int position = viewPager.getCurrentItem();
            RecyclerView recyclerView = (RecyclerView) viewPager.getChildAt(0);

            if (recyclerView != null) {
                RecyclerView.ViewHolder vh =
                        recyclerView.findViewHolderForAdapterPosition(position);

                if (vh instanceof FlashcardAdapter.ViewHolder) {
                    vh.itemView.performClick();
                    trackActivity(userId, subjectId, "flashcard", "restart");
                }
            }
        });

        btnShuffle.setOnClickListener(v -> {
            if (adapter == null) return;

            adapter.shuffleCards();
            int random = (int) (Math.random() * adapter.getItemCount());
            viewPager.setCurrentItem(random, true);

            tvCardCount.setText((random + 1) + "/" + adapter.getItemCount());
            updateIndicators(random);

            trackActivity(userId, subjectId, "flashcard", "shuffle");
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        loadFlashcards(subjectName);
    }

    // ✅ SINGLE TRACK METHOD
    private void trackActivity(int userId, int subjectId, String feature, String action) {

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("subject_id", subjectId);
        map.put("feature_type", feature);
        map.put("action_type", action);
        map.put("time_spent", 5);

        api.trackActivity(map).enqueue(new Callback<CommonResponse>() {
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {}
            public void onFailure(Call<CommonResponse> call, Throwable t) {}
        });
    }

    private void setupIndicators(int count) {
        indicatorLayout.removeAllViews();

        for (int i = 0; i < count; i++) {
            View dot = new View(this);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(i == 0 ? 40 : 12, 12);
            params.setMargins(6, 0, 6, 0);
            dot.setLayoutParams(params);

            dot.setBackgroundResource(
                    i == 0 ? R.drawable.bg_indicator_active : R.drawable.bg_indicator_inactive
            );

            indicatorLayout.addView(dot);
        }
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicatorLayout.getChildCount(); i++) {
            View dot = indicatorLayout.getChildAt(i);
            LinearLayout.LayoutParams params =
                    (LinearLayout.LayoutParams) dot.getLayoutParams();

            int width = (i == position) ? 40 : 12;

            ValueAnimator anim = ValueAnimator.ofInt(params.width, width);
            anim.setDuration(180);
            anim.addUpdateListener(a -> {
                params.width = (int) a.getAnimatedValue();
                dot.setLayoutParams(params);
            });
            anim.start();

            dot.setBackgroundResource(
                    (i == position)
                            ? R.drawable.bg_indicator_active
                            : R.drawable.bg_indicator_inactive
            );
        }
    }

    private void loadFlashcards(String subjectName) {

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);

        Map<String, String> body = new HashMap<>();
        body.put("subject_name", subjectName);

        api.getFlashcards(body).enqueue(new Callback<FlashcardResponse>() {

            public void onResponse(Call<FlashcardResponse> call, Response<FlashcardResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Flashcard> list = response.body().getFlashcards();

                    int time = getIntent().getIntExtra("time", 10);

                    int limit;

                    if (time == 5) limit = 5;
                    else if (time == 30) limit = 15;   // 🔥 MORE CARDS
                    else limit = list.size();

                    list = list.subList(0, Math.min(limit, list.size()));

                    adapter = new FlashcardAdapter(list);
                    viewPager.setAdapter(adapter);

                    tvCardCount.setText("1/" + adapter.getItemCount());
                    setupIndicators(list.size());
                }

                progressBar.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
            }

            public void onFailure(Call<FlashcardResponse> call, Throwable t) {
                Toast.makeText(FlashcardsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);


    }
}