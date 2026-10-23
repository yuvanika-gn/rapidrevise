package com.simats.rapidrevise;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    protected void setupBottomNavigation(int selectedItemId) {

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        if (bottomNav == null) return;

        bottomNav.setSelectedItemId(selectedItemId);

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {

                startActivity(new Intent(this, HomeActivity.class));
                return true;

            } else if (id == R.id.nav_subjects) {

                startActivity(new Intent(this, SubjectsActivity.class));
                return true;

            } else if (id == R.id.nav_search) {

                startActivity(new Intent(this, LibraryActivity.class));
                return true;

            } else if (id == R.id.nav_revise) {

                startActivity(new Intent(this, RevisionSessionActivity.class));
                return true;

            } else if (id == R.id.nav_profile) {

                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }

            return false;
        });
    }
}