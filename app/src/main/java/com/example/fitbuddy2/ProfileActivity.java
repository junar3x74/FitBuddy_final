package com.example.fitbuddy2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        setupBottomNavigation();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Intent intent = null;
            int itemId = item.getItemId();

            // Don't create new intent if we're already on the selected tab
            if (itemId == R.id.nav_profile) {
                // Already on Dashboard, no need to navigate
                return true;
            } else if (itemId == R.id.nav_log) {
                intent = new Intent(this, LogWorkoutActivity.class);
            } else if (itemId == R.id.nav_history) {
                intent = new Intent(this, HistoryActivity.class);
            } else if (itemId == R.id.nav_dashboard) {
                intent = new Intent(this, DashboardActivity.class);
            }

            if (intent != null) {
                // Clear the back stack so tapping repeatedly doesn't pile up Activities
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0); // optional: no animation
            }
            return true;
        });

        // Finally, highlight the current tab:
        bottomNav.setSelectedItemId(R.id.nav_profile);
    }
}