package com.simats.rapidrevise;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.simats.rapidrevise.adapter.ProgressAdapter;
import com.simats.rapidrevise.model.ProgressModel;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgressActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressAdapter adapter;
    View loader;

    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));

        recyclerView = findViewById(R.id.recyclerProgress);
        loader = findViewById(R.id.progressLoader);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userId = getSharedPreferences("user", MODE_PRIVATE)
                .getInt("user_id", -1);

        PieChart pieChart = findViewById(R.id.pieChart);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchProgress();  // 🔥 refresh every time
    }

    private void fetchProgress() {

        loader.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);

        api.getProgress(userId).enqueue(new Callback<List<ProgressModel>>() {
            @Override
            public void onResponse(Call<List<ProgressModel>> call, Response<List<ProgressModel>> response) {

                loader.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {

                    List<ProgressModel> data = response.body();

                    adapter = new ProgressAdapter(data);
                    recyclerView.setAdapter(adapter);

                    // 🔥 ADD THIS
                    showPieChart(data);
                }
            }

            @Override
            public void onFailure(Call<List<ProgressModel>> call, Throwable t) {
                loader.setVisibility(View.GONE);
            }
        });
    }

    private void showPieChart(List<ProgressModel> list) {

        PieChart pieChart = findViewById(R.id.pieChart);

        int totalConcepts = 0;
        int totalFlashcards = 0;
        int totalFormulas = 0;

        for (ProgressModel model : list) {
            totalConcepts += model.getConcepts();
            totalFlashcards += model.getFlashcards();
            totalFormulas += model.getFormulas();
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(totalConcepts, "Concepts"));
        entries.add(new PieEntry(totalFlashcards, "Flashcards"));
        entries.add(new PieEntry(totalFormulas, "Formulas"));

        if (totalConcepts == 0 && totalFlashcards == 0 && totalFormulas == 0) {
            pieChart.setNoDataText("No activity yet");
            return;
        }

        PieDataSet dataSet = new PieDataSet(entries, "Study Distribution");

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#4CAF50")); // green
        colors.add(Color.parseColor("#2196F3")); // blue
        colors.add(Color.parseColor("#FF9800")); // orange

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(14f);

        pieChart.setData(data);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate(); // refresh
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleRadius(55f);
    }
}