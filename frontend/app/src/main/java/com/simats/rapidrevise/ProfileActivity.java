package com.simats.rapidrevise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.simats.rapidrevise.response.ProfileResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends BaseActivity {

    TextView tvProfileName, tvProfileEmail,tvInitials;
    TextView subjectCount, documentCount, imageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));

        setupBottomNavigation(R.id.nav_profile);

        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);

        subjectCount = findViewById(R.id.subjectcount);
        documentCount = findViewById(R.id.documentscount);
        imageCount = findViewById(R.id.imagescount);

        tvInitials = findViewById(R.id.tvProfileInitials);
        View historyMenu = findViewById(R.id.menuHistory);

        historyMenu.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, HistoryActivity.class));
        });

        loadProfile();

        findViewById(R.id.btnLogout).setOnClickListener(v -> {

            new AlertDialog.Builder(ProfileActivity.this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> logoutUser())
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        findViewById(R.id.menuSubjects).setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, SubjectsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.menuSubjects).setOnClickListener(v -> {

            v.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(100)
                    .withEndAction(() -> {
                        v.animate().scaleX(1f).scaleY(1f).setDuration(100);

                        startActivity(new Intent(ProfileActivity.this, SubjectsActivity.class));
                    });
        });

        findViewById(R.id.menuProgress).setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, ProgressActivity.class));
        });
    }



    private void logoutUser() {

        // 🔥 Clear user session
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        // 🔥 Go to Login screen
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // 🔥 Finish current activity
        finish();
    }

    private String getInitials(String name) {

        if (name == null || name.isEmpty()) return "";

        String[] words = name.trim().split("\\s+");

        StringBuilder initials = new StringBuilder();

        for (int i = 0; i < words.length && i < 2; i++) {
            initials.append(words[i].charAt(0));
        }

        return initials.toString().toUpperCase();
    }

    private void loadProfile() {

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        if(userId == -1) return;

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        Map<String, Integer> body = new HashMap<>();
        body.put("user_id", userId);

        apiService.getProfile(body)
                .enqueue(new Callback<ProfileResponse>() {

                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                        if(response.isSuccessful() && response.body()!=null
                                && response.body().getStatus().equals("success")){

                            ProfileResponse data = response.body();

                            tvProfileName.setText(data.getName());
                            tvProfileEmail.setText(data.getEmail());

                            subjectCount.setText(String.valueOf(data.getSubjects()));
                            documentCount.setText(String.valueOf(data.getDocuments()));
                            imageCount.setText(String.valueOf(data.getImages()));

                            String name = data.getName();

                            tvProfileName.setText(name);
                            tvProfileEmail.setText(data.getEmail());

// Set initials
                            tvInitials.setText(getInitials(name));
                        }
                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {

                        Toast.makeText(ProfileActivity.this,
                                "Failed to load profile",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
