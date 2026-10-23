package com.simats.rapidrevise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.simats.rapidrevise.request.SignupRequest;
import com.simats.rapidrevise.response.SignupResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.TextView;


public class CreateAccountActivity extends AppCompatActivity {

    EditText etFullName, etEmail, etPassword;
    Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bind views
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        btnCreateAccount.setOnClickListener(v -> signupUser());

        TextView tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // optional: prevents coming back to signup
        });

    }

    private void signupUser() {

        String fullname = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Basic validation
        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient
                .getInstance()
                .create(ApiService.class);

        SignupRequest request = new SignupRequest(fullname, email, password);

        apiService.signupUser(request).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    SignupResponse res = response.body();

                    Toast.makeText(CreateAccountActivity.this,
                            res.getMessage(),
                            Toast.LENGTH_SHORT).show();

                    if (res.getStatus().equalsIgnoreCase("success")) {

                        // ✅ SAVE USER ID HERE
                        SharedPreferences.Editor editor =
                                getSharedPreferences("user", MODE_PRIVATE).edit();

                        editor.putInt("user_id", res.getUserId());
                        editor.apply();

                        // ✅ Navigate after saving
                        Intent intent = new Intent(CreateAccountActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }




            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(CreateAccountActivity.this,
                        "Network error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
