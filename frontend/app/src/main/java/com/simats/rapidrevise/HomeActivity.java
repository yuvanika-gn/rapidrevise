package com.simats.rapidrevise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RelativeLayout;
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
import com.simats.rapidrevise.request.DeleteSubjectRequest;
import com.simats.rapidrevise.response.AddSubjectResponse;
import com.simats.rapidrevise.response.DeleteSubjectResponse;
import com.simats.rapidrevise.response.DocumentCountResponse;
import com.simats.rapidrevise.response.ImageCountResponse;
import com.simats.rapidrevise.response.SubjectResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {

    TextView tvGreeting, tvUsername;
    RecyclerView recyclerSubjects;
    FloatingActionButton fabAddSubject;

    ArrayList<SubjectResponse> subjectList;
    SubjectAdapter subjectAdapter;
    TextView tvSubjectsCount, tvDocumentsCount,tvImagesCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));
        tvGreeting = findViewById(R.id.tvGreeting);
        tvUsername = findViewById(R.id.tvUsername);
        recyclerSubjects = findViewById(R.id.recyclerSubjects);
        fabAddSubject = findViewById(R.id.fabAddSubject);

        TextView tvSeeAll = findViewById(R.id.tvSeeAll);

        tvSeeAll.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SubjectsActivity.class);
            startActivity(intent);
        });

        setGreeting();
        setUsername();

        subjectList = new ArrayList<>();

        subjectAdapter = new SubjectAdapter(
                subjectList,

                // ✅ SINGLE CLICK → OPEN SUBJECT
                (subjectId, subjectName) -> openSubjectDetail(subjectId, subjectName),

                // ✅ LONG CLICK → DELETE SUBJECT
                (subjectId, subjectName) -> showDeleteDialog(subjectId, subjectName)
        );

        recyclerSubjects.setLayoutManager(new LinearLayoutManager(this));
        recyclerSubjects.setAdapter(subjectAdapter);

        fabAddSubject.setOnClickListener(v -> showAddSubjectDialog());

        tvSubjectsCount = findViewById(R.id.tvSubjectsCount);
        tvDocumentsCount = findViewById(R.id.tvDocumentsCount);
        tvImagesCount = findViewById(R.id.tvHoursCount);
        loadSubjectsFromBackend();
        loadDocumentCount();
        loadImageCount();

        setupBottomNavigation(R.id.nav_home);

        RelativeLayout cardQuickRevision = findViewById(R.id.cardQuickRevision);

        cardQuickRevision.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TimeSelectionActivity.class);
            startActivity(intent);
        });
    }

    private void loadImageCount(){

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        int userId = prefs.getInt("user_id",0);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getImagesCount(userId).enqueue(new Callback<ImageCountResponse>() {
            @Override
            public void onResponse(Call<ImageCountResponse> call, Response<ImageCountResponse> response) {

                if(response.isSuccessful() && response.body()!=null){

                    int count = response.body().getTotal();
                    tvImagesCount.setText(String.valueOf(count));
                }
            }

            @Override
            public void onFailure(Call<ImageCountResponse> call, Throwable t) {

                Toast.makeText(HomeActivity.this,
                        "Failed to load images",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDocumentCount() {

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", 0);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getDocumentsCount(userId).enqueue(new Callback<DocumentCountResponse>() {
            @Override
            public void onResponse(Call<DocumentCountResponse> call, Response<DocumentCountResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    int count = response.body().getTotal();
                    tvDocumentsCount.setText(String.valueOf(count));
                }
            }

            @Override
            public void onFailure(Call<DocumentCountResponse> call, Throwable t) {

                Toast.makeText(HomeActivity.this,
                        "Failed to load documents", Toast.LENGTH_SHORT).show();
            }
        });
    }



    // ---------------- OPEN SUBJECT ----------------
    private void openSubjectDetail(int subjectId, String subjectName) {

        Intent intent = new Intent(HomeActivity.this, SubjectDetailActivity.class);
        intent.putExtra("subject_id", subjectId);
        intent.putExtra("subject_name", subjectName);
        startActivity(intent);
    }




    // ---------------- LOAD SUBJECTS ----------------
    private void loadSubjectsFromBackend() {
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

                    // Set subject count
                    tvSubjectsCount.setText(String.valueOf(subjectList.size()));
                }
            }

            @Override
            public void onFailure(Call<List<SubjectResponse>> call, Throwable t) {
                Toast.makeText(HomeActivity.this,
                        "Failed to load subjects", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ---------------- ADD SUBJECT ----------------
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

                        loadSubjectsFromBackend();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<AddSubjectResponse> call, Throwable t) {
                    Toast.makeText(HomeActivity.this,
                            "Failed to add subject", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }

    // ---------------- DELETE SUBJECT ----------------
    private void showDeleteDialog(int subjectId, String subjectName) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Subject")
                .setMessage("Delete \"" + subjectName + "\"?")
                .setPositiveButton("Delete",
                        (dialog, which) -> deleteSubjectFromBackend(subjectId))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteSubjectFromBackend(int subjectId) {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", 0);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        DeleteSubjectRequest request = new DeleteSubjectRequest(subjectId, userId);

        apiService.deleteSubject(request).enqueue(new Callback<DeleteSubjectResponse>() {
            @Override
            public void onResponse(Call<DeleteSubjectResponse> call,
                                   Response<DeleteSubjectResponse> response) {

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getStatus().equals("success")) {

                    loadSubjectsFromBackend();
                    Toast.makeText(HomeActivity.this,
                            "Subject deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteSubjectResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this,
                        "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ---------------- UI HELPERS ----------------
    private void setGreeting() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String greeting;

        if (hour >= 5 && hour < 12) greeting = "Good Morning";
        else if (hour < 17) greeting = "Good Afternoon";
        else greeting = "Good Evening";

        tvGreeting.setText(greeting);
    }

    private void setUsername() {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        tvUsername.setText(prefs.getString("fullname", "User"));
    }
}
