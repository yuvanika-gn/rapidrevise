package com.simats.rapidrevise;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.rapidrevise.adapter.LibraryAdapter;
import com.simats.rapidrevise.model.MaterialItem;
import com.simats.rapidrevise.model.SearchResponse;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryActivity extends BaseActivity {

    EditText etSearch;
    RecyclerView recyclerLibrary;
    LibraryAdapter adapter;
    List<MaterialItem> materialList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_library);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        setupBottomNavigation(R.id.nav_search);

        etSearch = findViewById(R.id.etSearch);
        recyclerLibrary = findViewById(R.id.recyclerLibrary);

        materialList = new ArrayList<>();

        adapter = new LibraryAdapter(this, materialList);

        recyclerLibrary.setLayoutManager(new LinearLayoutManager(this));
        recyclerLibrary.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s,int start,int before,int count) {

                searchMaterials(s.toString());
            }

        });
    }

    private void searchMaterials(String keyword){

        if(keyword.length() < 2){
            materialList.clear();
            adapter.notifyDataSetChanged();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("user",MODE_PRIVATE);
        int userId = prefs.getInt("user_id",0);

        ApiService apiService = RetrofitClient
                .getInstance()
                .create(ApiService.class);

        apiService.searchMaterials(userId,keyword)
                .enqueue(new Callback<SearchResponse>(){

                    @Override
                    public void onResponse(Call<SearchResponse> call,
                                           Response<SearchResponse> response){

                        if(response.isSuccessful()
                                && response.body()!=null
                                && response.body().getStatus().equals("success")){

                            materialList.clear();
                            materialList.addAll(response.body().getData());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t){

                        Toast.makeText(LibraryActivity.this,
                                "Search failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }




}