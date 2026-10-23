package com.simats.rapidrevise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simats.rapidrevise.adapter.DocumentAdapter;
import com.simats.rapidrevise.adapter.ImageAdapter;
import com.simats.rapidrevise.adapter.NotesAdapter;
import com.simats.rapidrevise.adapter.UrlAdapter;
import com.simats.rapidrevise.model.DocumentMaterial;
import com.simats.rapidrevise.model.ImageMaterial;
import com.simats.rapidrevise.model.Note;
import com.simats.rapidrevise.model.UrlMaterial;
import com.simats.rapidrevise.request.DeleteDocumentRequest;
import com.simats.rapidrevise.request.DeleteImageRequest;
import com.simats.rapidrevise.request.DeleteNoteRequest;
import com.simats.rapidrevise.request.RenameDocumentRequest;
import com.simats.rapidrevise.response.CommonResponse;
import com.simats.rapidrevise.response.DocumentListResponse;
import com.simats.rapidrevise.response.ImageListResponse;
import com.simats.rapidrevise.response.NotesListResponse;
import com.simats.rapidrevise.response.UrlListResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectDetailActivity extends BaseActivity {

    private String subjectName;

    private List<Note> noteList;
    private NotesAdapter notesAdapter;

    private List<ImageMaterial> imageList;
    private ImageAdapter imageAdapter;

    private List<DocumentMaterial> documentList;
    private DocumentAdapter documentAdapter;
    private List<UrlMaterial> urlList;
    private UrlAdapter urlAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_detail);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));
        setupBottomNavigation(R.id.nav_subjects);


        subjectName = getIntent().getStringExtra("subject_name");

        TextView tvSubjectTitle = findViewById(R.id.tvSubjectTitle);

        if (subjectName == null || subjectName.isEmpty()) {
            Toast.makeText(this, "Subject missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvSubjectTitle.setText(subjectName);

        FloatingActionButton fabAddItem = findViewById(R.id.fabAddItem);
        fabAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(this, UploadMaterialsActivity.class);
            intent.putExtra("subject_name", subjectName);
            startActivity(intent);
        });

        setupNotes();
        setupImages();
        setupDocuments();

        loadNotes();
        loadImages();
        loadDocuments();
        setupUrls();
    }

    private void setupUrls(){

        RecyclerView recyclerUrls = findViewById(R.id.recyclerUrls);

        recyclerUrls.setLayoutManager(new LinearLayoutManager(this));

        urlList = new ArrayList<>();

        urlAdapter = new UrlAdapter(this,urlList);

        recyclerUrls.setAdapter(urlAdapter);
    }

    private void loadUrls(){

        int userId = getUserId();
        if(userId==-1) return;

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getUrls(userId,subjectName)
                .enqueue(new Callback<UrlListResponse>() {

                    @Override
                    public void onResponse(Call<UrlListResponse> call,
                                           Response<UrlListResponse> response) {

                        if(response.isSuccessful() &&
                                response.body()!=null &&
                                response.body().getStatus().equals("success")){

                            urlList.clear();
                            urlList.addAll(response.body().getData());

                            urlAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<UrlListResponse> call, Throwable t) {

                        Toast.makeText(SubjectDetailActivity.this,
                                "Failed to load URLs",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
        loadImages();
        loadDocuments();
        loadUrls();
    }

    // ================= NOTES SETUP =================

    private void setupNotes() {

        RecyclerView recyclerNotes = findViewById(R.id.recyclerTopics);
        recyclerNotes.setLayoutManager(new LinearLayoutManager(this));

        noteList = new ArrayList<>();

        notesAdapter = new NotesAdapter(
                noteList,
                (note, position) -> showDeleteDialog(note, position)
        );

        recyclerNotes.setAdapter(notesAdapter);
    }

    // ================= IMAGE SETUP =================

    private void setupImages() {

        RecyclerView recyclerImages = findViewById(R.id.recyclerImages);
        recyclerImages.setLayoutManager(new GridLayoutManager(this, 2));

        imageList = new ArrayList<>();

        imageAdapter = new ImageAdapter(
                this,
                imageList,
                (image, position) -> showImageDeleteDialog(image, position)
        );

        recyclerImages.setAdapter(imageAdapter);
    }

    // ================= DOCUMENT SETUP =================

    private void setupDocuments() {

        RecyclerView recyclerDocuments = findViewById(R.id.recyclerDocuments);
        recyclerDocuments.setLayoutManager(new LinearLayoutManager(this));

        documentList = new ArrayList<>();

        documentAdapter = new DocumentAdapter(
                this,
                documentList,
                (doc, position) -> showDocumentOptions(doc, position)
        );

        recyclerDocuments.setAdapter(documentAdapter);
    }

    // ================= LOAD NOTES =================

    private void loadNotes() {

        int userId = getUserId();
        if (userId == -1) return;

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getNotes(userId, subjectName)
                .enqueue(new Callback<NotesListResponse>() {

                    @Override
                    public void onResponse(Call<NotesListResponse> call, Response<NotesListResponse> response) {

                        if (response.isSuccessful() && response.body() != null &&
                                "success".equals(response.body().getStatus())) {

                            noteList.clear();
                            noteList.addAll(response.body().getData());
                            notesAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<NotesListResponse> call, Throwable t) {

                        Toast.makeText(SubjectDetailActivity.this,
                                "Failed to load notes", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // ================= LOAD IMAGES =================

    private void loadImages() {

        int userId = getUserId();
        if (userId == -1) return;

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getImages(userId, subjectName)
                .enqueue(new Callback<ImageListResponse>() {

                    @Override
                    public void onResponse(Call<ImageListResponse> call, Response<ImageListResponse> response) {

                        if (response.isSuccessful() && response.body() != null &&
                                "success".equals(response.body().getStatus())) {

                            imageList.clear();
                            imageList.addAll(response.body().getData());
                            imageAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageListResponse> call, Throwable t) {

                        Toast.makeText(SubjectDetailActivity.this,
                                "Failed to load images", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // ================= LOAD DOCUMENTS =================

    private void loadDocuments() {

        int userId = getUserId();
        if (userId == -1) return;

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.getDocuments(userId, subjectName)
                .enqueue(new Callback<DocumentListResponse>() {

                    @Override
                    public void onResponse(Call<DocumentListResponse> call, Response<DocumentListResponse> response) {

                        if (response.isSuccessful() && response.body() != null &&
                                "success".equals(response.body().getStatus())) {

                            documentList.clear();
                            documentList.addAll(response.body().getData());
                            documentAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<DocumentListResponse> call, Throwable t) {

                        Toast.makeText(SubjectDetailActivity.this,
                                "Failed to load documents", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // ================= USER ID =================

    private int getUserId() {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        return prefs.getInt("user_id", -1);
    }

    // ================= NOTE DELETE =================

    private void showDeleteDialog(Note note, int position) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Delete",
                        (dialog, which) -> deleteNote(note, position))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteNote(Note note, int position) {

        int userId = getUserId();

        DeleteNoteRequest request = new DeleteNoteRequest(note.getId(), userId);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        apiService.deleteNote(request)
                .enqueue(new Callback<CommonResponse>() {

                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                        if (response.isSuccessful() && response.body() != null &&
                                "success".equals(response.body().getStatus())) {

                            noteList.remove(position);
                            notesAdapter.notifyItemRemoved(position);

                            Toast.makeText(SubjectDetailActivity.this,
                                    "Note deleted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {

                        Toast.makeText(SubjectDetailActivity.this,
                                "Delete failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // ================= IMAGE DELETE =================

    private void showImageDeleteDialog(ImageMaterial image, int position) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Image")
                .setMessage("Are you sure you want to delete this image?")
                .setPositiveButton("Delete",
                        (dialog, which) -> deleteImage(image, position))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteImage(ImageMaterial image, int position) {

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        DeleteImageRequest request = new DeleteImageRequest(image.getId());

        apiService.deleteImage(request).enqueue(new Callback<CommonResponse>() {

            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful() && response.body() != null &&
                        response.body().getStatus().equals("success")) {

                    imageList.remove(position);
                    imageAdapter.notifyItemRemoved(position);

                    Toast.makeText(SubjectDetailActivity.this,
                            "Image deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                Toast.makeText(SubjectDetailActivity.this,
                        "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ================= DOCUMENT OPTIONS =================

    private void showDocumentOptions(DocumentMaterial doc, int position) {

        String[] options = {"Rename", "Delete"};

        new AlertDialog.Builder(this)
                .setTitle("Document Options")
                .setItems(options, (dialog, which) -> {

                    if (which == 0) {
                        showRenameDocumentDialog(doc, position);
                    } else {
                        deleteDocument(doc, position);
                    }

                })
                .show();
    }

    // ================= DOCUMENT RENAME =================

    private void showRenameDocumentDialog(DocumentMaterial doc, int position) {

        EditText input = new EditText(this);
        input.setText(doc.getFileName());   // show current file name

        new AlertDialog.Builder(this)
                .setTitle("Rename Document")
                .setView(input)
                .setPositiveButton("Rename", (dialog, which) -> {

                    String newName = input.getText().toString().trim();

                    if (newName.isEmpty()) {
                        Toast.makeText(this,
                                "Name cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    renameDocument(doc.getId(), newName);

                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void renameDocument(int id, String newName) {

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        RenameDocumentRequest request =
                new RenameDocumentRequest(id, newName);

        apiService.renameDocument(request)
                .enqueue(new Callback<CommonResponse>() {

                    @Override
                    public void onResponse(Call<CommonResponse> call,
                                           Response<CommonResponse> response) {

                        if (response.isSuccessful() && response.body() != null &&
                                "success".equals(response.body().getStatus())) {

                            loadDocuments();   // refresh list

                            Toast.makeText(SubjectDetailActivity.this,
                                    "Document renamed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {

                        Toast.makeText(SubjectDetailActivity.this,
                                "Rename failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // ================= DOCUMENT DELETE =================

    private void deleteDocument(DocumentMaterial doc, int position) {

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        DeleteDocumentRequest request = new DeleteDocumentRequest(doc.getId());

        apiService.deleteDocument(request)
                .enqueue(new Callback<CommonResponse>() {

                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                        if (response.isSuccessful() && response.body() != null &&
                                "success".equals(response.body().getStatus())) {

                            documentList.remove(position);
                            documentAdapter.notifyItemRemoved(position);

                            Toast.makeText(SubjectDetailActivity.this,
                                    "Document deleted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {

                        Toast.makeText(SubjectDetailActivity.this,
                                "Delete failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
