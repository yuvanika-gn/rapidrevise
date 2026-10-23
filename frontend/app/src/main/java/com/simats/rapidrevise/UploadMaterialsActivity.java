package com.simats.rapidrevise;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.simats.rapidrevise.adapter.SelectedFilesAdapter;
import com.simats.rapidrevise.model.SelectedFile;
import com.simats.rapidrevise.response.CommonResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadMaterialsActivity extends AppCompatActivity {

    private String subjectName;
    private Uri selectedImageUri;

    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private Uri selectedDocumentUri;
    private ActivityResultLauncher<Intent> documentLauncher;

    List<SelectedFile> selectedFiles = new ArrayList<>();
    SelectedFilesAdapter adapter;
    RecyclerView rvSelectedFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_materials);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });



        subjectName = getIntent().getStringExtra("subject_name");

        if (subjectName == null) {
            finish();
            return;
        }

        setupLaunchers();
        View documentLayout = findViewById(R.id.layoutUploadDocument);

        documentLayout.setOnClickListener(v -> openDocumentPicker());


        View imageLayout = findViewById(R.id.layoutUploadImage);
        imageLayout.setOnClickListener(v -> showImageOptions());

        View notesLayout = findViewById(R.id.layoutUploadNotes);
        notesLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, NotesActivity.class);
            intent.putExtra("subject_name", subjectName);
            startActivity(intent);
        });

        findViewById(R.id.btnChooseFiles).setOnClickListener(v -> openDocumentPicker());

        rvSelectedFiles=findViewById(R.id.rvSelectedFiles);

        adapter=new SelectedFilesAdapter(selectedFiles);

        rvSelectedFiles.setLayoutManager(new LinearLayoutManager(this));

        rvSelectedFiles.setAdapter(adapter);

        findViewById(R.id.btnUpload).setOnClickListener(v -> {

            for (SelectedFile file : selectedFiles) {

                uploadDocumentToServer(file.getUri());
            }

        });

        View urlLayout = findViewById(R.id.layoutUploadUrl);
        urlLayout.setOnClickListener(v -> showUrlDialog());


    }

    private void showUrlDialog() {

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_url, null);

        EditText etUrlName = dialogView.findViewById(R.id.etUrlName);
        EditText etUrlLink = dialogView.findViewById(R.id.etUrlLink);

        new AlertDialog.Builder(this)
                .setTitle("Add URL")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {

                    String name = etUrlName.getText().toString().trim();
                    String url = etUrlLink.getText().toString().trim();

                    if(name.isEmpty() || url.isEmpty()){
                        Toast.makeText(this,"Enter all fields",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    uploadUrlToServer(name,url);

                })
                .setNegativeButton("Cancel",null)
                .show();
    }

    private void uploadUrlToServer(String name,String url){

        SharedPreferences prefs = getSharedPreferences("user",MODE_PRIVATE);
        int userId = prefs.getInt("user_id",0);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        RequestBody user =
                RequestBody.create(String.valueOf(userId),MediaType.parse("text/plain"));

        RequestBody subject =
                RequestBody.create(subjectName,MediaType.parse("text/plain"));

        RequestBody urlName =
                RequestBody.create(name,MediaType.parse("text/plain"));

        RequestBody urlLink =
                RequestBody.create(url,MediaType.parse("text/plain"));

        apiService.uploadUrl(user,subject,urlName,urlLink)
                .enqueue(new Callback<CommonResponse>() {

                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                        if(response.isSuccessful() && response.body()!=null
                                && response.body().getStatus().equals("success")){

                            Toast.makeText(UploadMaterialsActivity.this,
                                    "URL saved successfully",
                                    Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(UploadMaterialsActivity.this,
                                    "Failed to save URL",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {

                        Toast.makeText(UploadMaterialsActivity.this,
                                "Network Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openDocumentPicker() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");

        String[] mimeTypes = {
                "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "text/plain"
        };

        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        documentLauncher.launch(intent);
    }

    // ---------------- SHOW OPTIONS ----------------
    private void showImageOptions() {

        String[] options = {"Take Photo", "Choose from Gallery"};

        new AlertDialog.Builder(this)
                .setTitle("Upload Image")
                .setItems(options, (dialog, which) -> {

                    if (which == 0) {
                        openCamera();
                    } else {
                        openGallery();
                    }
                })
                .show();
    }

    // ---------------- CAMERA ----------------
    private void openCamera() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    101);
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }

    // ---------------- GALLERY ----------------
    private void openGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        galleryLauncher.launch(intent);
    }



    // ---------------- SETUP LAUNCHERS ----------------
    private void setupLaunchers() {

        documentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == RESULT_OK &&
                            result.getData() != null) {

                        selectedDocumentUri = result.getData().getData();

                        String fileName = "Document";

                        selectedFiles.add(new SelectedFile(selectedDocumentUri, fileName));

                        adapter.notifyDataSetChanged();

                        Toast.makeText(this,
                                "Added to upload queue",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK &&
                            result.getData() != null) {

                        selectedImageUri = result.getData().getData();

                        Toast.makeText(this,
                                "Image Selected",
                                Toast.LENGTH_SHORT).show();

                        uploadImageToServer(selectedImageUri);
                    }
                });

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK &&
                            result.getData() != null) {

                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");

                        if (imageBitmap != null) {

                            try {
                                File file = new File(getCacheDir(), "camera_image.jpg");
                                FileOutputStream fos = new FileOutputStream(file);

                                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                fos.flush();
                                fos.close();

                                Uri imageUri = Uri.fromFile(file);

                                uploadImageToServer(imageUri);

                                Toast.makeText(this,
                                        "Photo Captured",
                                        Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(this,
                                        "Camera processing error",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    private void uploadDocumentToServer(Uri fileUri) {

        try {

            SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
            int userIdValue = prefs.getInt("user_id", -1);

            if (userIdValue == -1) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
                return;
            }

            InputStream inputStream = getContentResolver().openInputStream(fileUri);

            String fileName = "document_" + System.currentTimeMillis() + ".pdf";

            File file = new File(getCacheDir(), fileName);

            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"),
                            file
                    );

            MultipartBody.Part documentPart =
                    MultipartBody.Part.createFormData(
                            "document",
                            file.getName(),
                            requestFile
                    );

            RequestBody userId =
                    RequestBody.create(
                            MediaType.parse("text/plain"),
                            String.valueOf(userIdValue)
                    );

            RequestBody subject =
                    RequestBody.create(
                            MediaType.parse("text/plain"),
                            subjectName
                    );

            ApiService apiService =
                    RetrofitClient.getInstance().create(ApiService.class);

            Call<CommonResponse> call =
                    apiService.uploadDocument(documentPart, userId, subject);

            call.enqueue(new Callback<CommonResponse>() {

                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {

                        if (response.body().getStatus().equals("success")) {

                            Toast.makeText(
                                    UploadMaterialsActivity.this,
                                    "Document uploaded successfully",
                                    Toast.LENGTH_SHORT
                            ).show();

                        } else {

                            Toast.makeText(
                                    UploadMaterialsActivity.this,
                                    response.body().getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }

                    } else {

                        Toast.makeText(
                                UploadMaterialsActivity.this,
                                "Server response error",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {

                    Toast.makeText(
                            UploadMaterialsActivity.this,
                            "Network error: " + t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });

        } catch (Exception e) {

            e.printStackTrace();

            Toast.makeText(
                    this,
                    "File processing error",
                    Toast.LENGTH_LONG
            ).show();
        }
    }



    private void uploadImageToServer(Uri imageUri) {

        try {

            SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
            int userIdValue = prefs.getInt("user_id", -1);

            if (userIdValue == -1) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
                return;
            }

            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            File file = new File(getCacheDir(), "upload_image.jpg");

            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            RequestBody requestFile =
                    RequestBody.create(file, MediaType.parse("image/*"));

            MultipartBody.Part imagePart =
                    MultipartBody.Part.createFormData(
                            "image",
                            file.getName(),
                            requestFile);

            RequestBody userId =
                    RequestBody.create(
                            String.valueOf(userIdValue),
                            MediaType.parse("text/plain"));

            RequestBody subject =
                    RequestBody.create(
                            subjectName,
                            MediaType.parse("text/plain"));

            ApiService apiService =
                    RetrofitClient.getInstance().create(ApiService.class);

            apiService.uploadImage(imagePart, userId, subject)
                    .enqueue(new Callback<CommonResponse>() {

                        @Override
                        public void onResponse(Call<CommonResponse> call,
                                               Response<CommonResponse> response) {

                            if (response.isSuccessful()
                                    && response.body() != null
                                    && response.body().getStatus().equals("success")) {

                                Toast.makeText(UploadMaterialsActivity.this,
                                        "Image uploaded successfully",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UploadMaterialsActivity.this,
                                        "Upload failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {
                            Toast.makeText(UploadMaterialsActivity.this,
                                    "Network error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error processing image", Toast.LENGTH_SHORT).show();
        }
    }
}
