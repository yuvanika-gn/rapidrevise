package com.simats.rapidrevise.retrofit;

import com.simats.rapidrevise.model.ActivityModel;
import com.simats.rapidrevise.model.ProgressModel;
import com.simats.rapidrevise.model.SearchResponse;
import com.simats.rapidrevise.model.Subject;
import com.simats.rapidrevise.request.AddSubjectRequest;
import com.simats.rapidrevise.request.DeleteDocumentRequest;
import com.simats.rapidrevise.request.DeleteImageRequest;
import com.simats.rapidrevise.request.DeleteNoteRequest;
import com.simats.rapidrevise.request.DeleteSubjectRequest;
import com.simats.rapidrevise.request.LoginRequest;
import com.simats.rapidrevise.request.NoteRequest;
import com.simats.rapidrevise.request.RenameDocumentRequest;
import com.simats.rapidrevise.request.SignupRequest;
import com.simats.rapidrevise.request.SubjectRequest;
import com.simats.rapidrevise.response.AiResponse;
import com.simats.rapidrevise.response.CommonResponse;
import com.simats.rapidrevise.response.DocumentCountResponse;
import com.simats.rapidrevise.response.DocumentListResponse;
import com.simats.rapidrevise.response.FlashcardResponse;
import com.simats.rapidrevise.response.FormulaResponse;
import com.simats.rapidrevise.response.ImageCountResponse;
import com.simats.rapidrevise.response.ImageListResponse;
import com.simats.rapidrevise.response.KeyPointsResponse;
import com.simats.rapidrevise.response.LoginResponse;
import com.simats.rapidrevise.response.NoteResponse;
import com.simats.rapidrevise.response.NotesListResponse;
import com.simats.rapidrevise.response.ProfileResponse;
import com.simats.rapidrevise.response.QuizResponse;
import com.simats.rapidrevise.response.SignupResponse;
import com.simats.rapidrevise.response.SubjectResponse;
import com.simats.rapidrevise.response.AddSubjectResponse;
import com.simats.rapidrevise.response.DeleteSubjectResponse;
import com.simats.rapidrevise.response.UrlListResponse;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @POST("signup.php")
    Call<SignupResponse> signupUser(@Body SignupRequest signupRequest);

    //Add login
    @POST("login.php")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("add_subject.php")
    Call<AddSubjectResponse> addSubject(@Body AddSubjectRequest request);

    @GET("get_subjects.php")
    Call<List<SubjectResponse>> getSubjects(@Query("user_id") int userId);

    @POST("delete_subject.php")
    Call<DeleteSubjectResponse> deleteSubject(@Body DeleteSubjectRequest request);

    @POST("add_note.php")
    Call<CommonResponse> addNote(@Body NoteRequest request);

    @POST("add_subject.php")
    Call<CommonResponse> addSubject(@Body SubjectRequest request);

    @GET("get_notes.php")
    Call<NotesListResponse> getNotes(
            @Query("user_id") int userId,
            @Query("subject_name") String subjectName
    );

    @POST("delete_note.php")
    Call<CommonResponse> deleteNote(@Body DeleteNoteRequest request);

    @Multipart
    @POST("upload_image.php")
    Call<CommonResponse> uploadImage(
            @Part MultipartBody.Part image,
            @Part("user_id") RequestBody userId,
            @Part("subject_name") RequestBody subjectName
    );

    @GET("get_images.php")
    Call<ImageListResponse> getImages(
            @Query("user_id") int userId,
            @Query("subject_name") String subjectName
    );

    @POST("delete_image.php")
    Call<CommonResponse> deleteImage(@Body DeleteImageRequest request);

    @Multipart
    @POST("upload_document.php")
    Call<CommonResponse> uploadDocument(
            @Part MultipartBody.Part document,
            @Part("user_id") RequestBody userId,
            @Part("subject_name") RequestBody subjectName
    );

    @GET("get_documents.php")
    Call<DocumentListResponse> getDocuments(
            @Query("user_id") int userId,
            @Query("subject_name") String subjectName
    );

    @POST("delete_document.php")
    Call<CommonResponse> deleteDocument(@Body DeleteDocumentRequest request);

    @POST("rename_document.php")
    Call<CommonResponse> renameDocument(@Body RenameDocumentRequest request);

    @GET("get_documents_count.php")
    Call<DocumentCountResponse> getDocumentsCount(@Query("user_id") int userId);

    @GET("get_images_count.php")
    Call<ImageCountResponse> getImagesCount(@Query("user_id") int userId);

    @Multipart
    @POST("upload_url.php")
    Call<CommonResponse> uploadUrl(
            @Part("user_id") RequestBody userId,
            @Part("subject_name") RequestBody subject,
            @Part("url_name") RequestBody urlName,
            @Part("url_link") RequestBody urlLink
    );

    @GET("get_urls.php")
    Call<UrlListResponse> getUrls(
            @Query("user_id") int userId,
            @Query("subject_name") String subjectName
    );

    @GET("search_materials.php")
    Call<SearchResponse> searchMaterials(
            @Query("user_id") int userId,
            @Query("keyword") String keyword
    );


    @POST("profile.php")
    Call<ProfileResponse> getProfile(@Body Map<String, Integer> body);

    @POST("ai_generate.php")
    Call<AiResponse> generateSummary(@Body Map<String, String> body);

    @Multipart
    @POST("save_notes.php")
    Call<CommonResponse> saveNotes(
            @Part("user_id") RequestBody user,
            @Part("subject_name") RequestBody subject,
            @Part("title") RequestBody title,
            @Part("content") RequestBody content
    );

    @POST("ai_generate.php")
    Call<KeyPointsResponse> getKeyPoints(@Body Map<String, String> body);

    @POST("ai_flashcards.php")
    Call<FlashcardResponse> getFlashcards(@Body Map<String, String> body);

    @POST("extract_formulas.php")
    Call<FormulaResponse> getFormulas(@Body Map<String, String> body);

    @POST("generate_quiz.php")
    Call<QuizResponse> generateQuiz(@Body Map<String, String> body);

    @GET("get_progress.php")
    Call<List<ProgressModel>> getProgress(@Query("user_id") int userId);


    @POST("update_note.php")
    Call<Map<String, String>> updateNote(@Body Map<String, String> body);

    @GET("get_note_by_id.php")
    Call<NoteResponse> getNoteById(@Query("note_id") int noteId);

    @POST("track_activity.php")
    Call<CommonResponse> trackActivity(@Body Map<String, Object> map);

}
