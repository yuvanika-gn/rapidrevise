package com.simats.rapidrevise;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.simats.rapidrevise.model.Note;
import com.simats.rapidrevise.response.NoteResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteDetailActivity extends AppCompatActivity {

    EditText etTitle, etContent;

    String title, content;
    int noteId;

    Handler handler = new Handler();
    Runnable saveRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        etTitle = findViewById(R.id.tvTitle);
        etContent = findViewById(R.id.tvContent);

        // 🔥 GET DATA
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        noteId = getIntent().getIntExtra("note_id", -1);

        fetchNote(noteId);
        if (title == null) title = "";
        if (content == null) content = "";

        etTitle.setText(title);
        etContent.setText(content);

        // 🔥 AUTO SAVE AFTER USER STOPS TYPING
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (saveRunnable != null) {
                    handler.removeCallbacks(saveRunnable);
                }

                saveRunnable = () -> saveNote();

                handler.postDelayed(saveRunnable, 10); // 1.5 sec delay
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        etTitle.addTextChangedListener(watcher);
        etContent.addTextChangedListener(watcher);
    }

    // 🔥 SAVE NOTE API CALL
    private void saveNote() {

        String newTitle = etTitle.getText().toString().trim();
        String newContent = etContent.getText().toString().trim();

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);

        Map<String, String> body = new HashMap<>();
        body.put("note_id", String.valueOf(noteId));
        body.put("title", newTitle);
        body.put("content", newContent);

        api.updateNote(body).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                // silent save
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(NoteDetailActivity.this, "Save failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchNote(int noteId){

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);

        api.getNoteById(noteId).enqueue(new Callback<NoteResponse>() {
            @Override
            public void onResponse(Call<NoteResponse> call, Response<NoteResponse> response) {

                if(response.isSuccessful() && response.body() != null){

                    Note note = response.body().getData();

                    if(note != null){
                        etTitle.setText(note.getTitle());
                        etContent.setText(note.getContent());
                    }
                }
            }

            @Override
            public void onFailure(Call<NoteResponse> call, Throwable t) {
                Toast.makeText(NoteDetailActivity.this,
                        "Failed to load note",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}