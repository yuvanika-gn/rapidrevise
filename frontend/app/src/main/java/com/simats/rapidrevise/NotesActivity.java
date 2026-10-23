package com.simats.rapidrevise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.simats.rapidrevise.request.NoteRequest;
import com.simats.rapidrevise.response.CommonResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesActivity extends AppCompatActivity {

    private EditText etTitle, etNotes;
    private String subjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        etTitle = findViewById(R.id.etTitle);
        etNotes = findViewById(R.id.etNotes);

        // Get subject automatically
        subjectName = getIntent().getStringExtra("subject_name");

        if (subjectName == null || subjectName.isEmpty()) {
            Toast.makeText(this, "Subject missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        findViewById(R.id.btnSubmitNotes).setOnClickListener(v -> saveNote());
    }

    private void saveNote() {

        String title = etTitle.getText().toString().trim();
        String content = etNotes.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        NoteRequest request = new NoteRequest(userId, subjectName, title, content);

        ApiService apiService = RetrofitClient
                .getInstance()
                .create(ApiService.class);

        apiService.addNote(request).enqueue(new Callback<CommonResponse>() {

            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful() && response.body() != null &&
                        response.body().getStatus().equalsIgnoreCase("success")) {

                    Toast.makeText(NotesActivity.this,
                            "Note saved successfully",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(NotesActivity.this, SubjectDetailActivity.class);
                    intent.putExtra("subject_name", subjectName);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(NotesActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
