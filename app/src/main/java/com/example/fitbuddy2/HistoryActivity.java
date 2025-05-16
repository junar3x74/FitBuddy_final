package com.example.fitbuddy2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbuddy2.adapters.WorkoutItemAdapter;
import com.example.fitbuddy2.models.WorkoutItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements WorkoutItemAdapter.OnWorkoutClickListener {

    @Override
    public void onWorkoutClick(String workoutName) {
        // Handle click, e.g. open details
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        setupBottomNavigation();
        setupRecentRecyclerView();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupRecentRecyclerView() {
        RecyclerView rvRecent = findViewById(R.id.rvHistory);
        if (rvRecent == null) return;

        rvRecent.setLayoutManager(new LinearLayoutManager(this));

        List<WorkoutItem> items = Arrays.asList(
                new WorkoutItem("Day 1: Chest + Triceps", "Week 1", "45m", R.drawable.ic_fitness, Color.parseColor("#D81B60")),
                new WorkoutItem("Day 2: Legs", "Week 1", "55m", R.drawable.ic_fitness, Color.parseColor("#43A047")),
                new WorkoutItem("Day 3: Back + Biceps", "Week 1", "50m", R.drawable.ic_fitness, Color.parseColor("#8E24AA")),
                new WorkoutItem("Day 4: Rest", "Week 1", "0m", R.drawable.ic_fitness, Color.parseColor("#BDBDBD")),
                new WorkoutItem("Day 5: Shoulders + Core", "Week 1", "40m", R.drawable.ic_fitness, Color.parseColor("#F4511E"))
        );

        rvRecent.setAdapter(new WorkoutItemAdapter(items, this));
    }
    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Intent intent = null;
            int itemId = item.getItemId();






            // Don't create new intent if we're already on the selected tab
            if (itemId == R.id.nav_history) {
                // Already on Dashboard, no need to navigate
                return true;
            } else if (itemId == R.id.nav_log) {
                intent = new Intent(this, LogWorkoutActivity.class);
            } else if (itemId == R.id.nav_dashboard) {
                intent = new Intent(this, DashboardActivity.class);
            } else if (itemId == R.id.nav_profile) {
                intent = new Intent(this, ProfileActivity.class);
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
        bottomNav.setSelectedItemId(R.id.nav_history);
    }
}