package com.simats.rapidrevise.model;


import android.content.Context;
import android.content.SharedPreferences;

public class UsageTracker {

    private static final String PREF = "usage_data";

    public static void increment(Context context, String subject, String feature) {
        String key = subject + "_" + feature;

        SharedPreferences prefs = context.getSharedPreferences("usage_data", Context.MODE_PRIVATE);
        int count = prefs.getInt(key, 0);

        prefs.edit().putInt(key, count + 1).apply();
    }

    public static int get(Context context, String subject, String feature) {
        String key = subject + "_" + feature;

        SharedPreferences prefs = context.getSharedPreferences("usage_data", Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }
}
