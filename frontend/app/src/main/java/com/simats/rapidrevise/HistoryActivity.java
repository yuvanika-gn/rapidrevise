package com.simats.rapidrevise;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.simats.rapidrevise.model.ProgressModel;
import com.simats.rapidrevise.retrofit.ApiService;
import com.simats.rapidrevise.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    PieChart pieChart;
    View loader;
    int userId;
    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(getResources().getColor(R.color.primaryGreen));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        pieChart = findViewById(R.id.pieChart);
        loader = findViewById(R.id.progressLoader);
        barChart = findViewById(R.id.barChart);

        userId = getSharedPreferences("user", MODE_PRIVATE)
                .getInt("user_id", -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchProgress();
    }

    private void fetchProgress() {

        loader.setVisibility(View.VISIBLE);

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);

        api.getProgress(userId).enqueue(new Callback<List<ProgressModel>>() {
            @Override
            public void onResponse(Call<List<ProgressModel>> call, Response<List<ProgressModel>> response) {

                loader.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {

                    List<ProgressModel> data = response.body();

                    showPieChart(data);      // ✅ correct place
                    showBarChart(data);      // ✅ correct place
                }
            }

            @Override
            public void onFailure(Call<List<ProgressModel>> call, Throwable t) {
                loader.setVisibility(View.GONE);
            }
        });

    }

    private void showPieChart(List<ProgressModel> list) {

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        int[] palette = {
                Color.parseColor("#4CAF50"),
                Color.parseColor("#2196F3"),
                Color.parseColor("#FF9800"),
                Color.parseColor("#9C27B0"),
                Color.parseColor("#E91E63"),
                Color.parseColor("#009688")
        };

        int index = 0;

        for (ProgressModel model : list) {

            int activity = model.getTotal_activity();

            if (activity > 0) {
                entries.add(new PieEntry(activity, model.getSubject_name()));
                colors.add(palette[index % palette.length]);
                index++;
            }
        }

        if (entries.isEmpty()) {
            pieChart.setNoDataText("No activity yet");
            pieChart.invalidate();
            return;
        }

        PieDataSet dataSet = new PieDataSet(entries, "Subject Interaction");
        dataSet.setColors(colors);
        dataSet.setSliceSpace(3f);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);

        // 🔥 PREMIUM SETTINGS
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(60f);
        pieChart.setTransparentCircleRadius(65f);
        pieChart.setCenterText("Subjects");
        pieChart.setCenterTextSize(16f);

        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(true);

        pieChart.animateY(1000);

        pieChart.invalidate();
    }

    private void showBarChart(List<ProgressModel> list) {

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        int[] palette = {
                Color.parseColor("#4CAF50"),
                Color.parseColor("#2196F3"),
                Color.parseColor("#FF9800"),
                Color.parseColor("#9C27B0"),
                Color.parseColor("#E91E63"),
                Color.parseColor("#009688")
        };

        int index = 0;

        for (ProgressModel model : list) {

            int activity = model.getTotal_activity();

            if (activity > 0) {
                entries.add(new BarEntry(index, activity));
                labels.add(model.getSubject_name());

                // 🎨 Assign unique color per subject
                colors.add(palette[index % palette.length]);

                index++;
            }
        }

        if (entries.isEmpty()) {
            barChart.setNoDataText("No activity yet");
            return;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Subjects");

        // 🔥 IMPORTANT CHANGE
        dataSet.setColors(colors);  // ✅ multiple colors

        dataSet.setValueTextSize(12f);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.5f);

        barChart.setData(data);

        // X Axis labels
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        // Y Axis
        barChart.getAxisRight().setEnabled(false);

        // Clean UI
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        barChart.animateY(1000);
        barChart.invalidate();
        dataSet.setHighLightAlpha(255);
        dataSet.setBarShadowColor(Color.LTGRAY);
    }
}