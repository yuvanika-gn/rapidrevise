package com.simats.rapidrevise;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditNoteActivity extends AppCompatActivity {

    EditText etTitle, etContent;
    Button btnSave;

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);

        // 🔥 GET DATA
        noteId = getIntent().getIntExtra("note_id", -1);
        etTitle.setText(getIntent().getStringExtra("title"));
        etContent.setText(getIntent().getStringExtra("content"));

        btnSave.setOnClickListener(v -> {
            // 👉 call API (update_note.php)
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}