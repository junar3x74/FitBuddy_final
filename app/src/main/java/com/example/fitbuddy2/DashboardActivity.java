package com.example.fitbuddy2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbuddy2.adapters.SummaryAdapter;
import com.example.fitbuddy2.adapters.WorkoutItemAdapter;
import com.example.fitbuddy2.models.Summary;
import com.example.fitbuddy2.models.WorkoutItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements WorkoutItemAdapter.OnWorkoutClickListener {

    @Override
    public void onWorkoutClick(String workoutName) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        // Initialize RecyclerViews
        setupSummaryRecyclerView();


        // Hide static summary layout since we're using RecyclerView instead
        LinearLayout staticSummaryLayout = findViewById(R.id.llSummaryStatic);
        if (staticSummaryLayout != null) {
            staticSummaryLayout.setVisibility(View.GONE);
        }

        setupBottomNavigation();
        setupRecentRecyclerView();

        // Apply edge-to-edge insets to the root view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupSummaryRecyclerView() {
        // Add a RecyclerView for summaries to the layout XML
        // You need to add this RecyclerView to your activity_dashboard.xml
        RecyclerView summaryRecyclerView = findViewById(R.id.rvSummary);

        // If the RecyclerView doesn't exist in the layout yet, we need to return
        if (summaryRecyclerView == null) {
            return;
        }

        // Set horizontal layout manager
        summaryRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        // Supply data
        List<Summary> summaryData = Arrays.asList(
                new Summary("Workouts", "5"),
                new Summary("Calories", "1.2k"),
                new Summary("Duration", "3h 15m")
        );

        // Set adapter
        summaryRecyclerView.setAdapter(new SummaryAdapter(summaryData));
    }


    private void setupRecentRecyclerView() {
        RecyclerView rvRecent = findViewById(R.id.rvRecent);
        if (rvRecent == null) return;

        rvRecent.setLayoutManager(new LinearLayoutManager(this));

        List<WorkoutItem> items = Arrays.asList(
                new WorkoutItem("Chest Day", "May 16, 2025", "45m", R.drawable.ic_fitness, Color.parseColor("#D81B60")),
                new WorkoutItem("Leg Day", "May 15, 2025", "60m", R.drawable.ic_fitness, Color.parseColor("#43A047")),
                new WorkoutItem("Back and Biceps", "May 14, 2025", "50m", R.drawable.ic_fitness, Color.parseColor("#8E24AA")),
                new WorkoutItem("Shoulders and Triceps", "May 13, 2025", "55m", R.drawable.ic_fitness, Color.parseColor("#F4511E"))
        );

        rvRecent.setAdapter(new WorkoutItemAdapter(items, this));
    }





    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Intent intent = null;
            int itemId = item.getItemId();

            // Don't create new intent if we're already on the selected tab
            if (itemId == R.id.nav_dashboard) {
                // Already on Dashboard, no need to navigate
                return true;
            } else if (itemId == R.id.nav_log) {
                intent = new Intent(this, LogWorkoutActivity.class);
            } else if (itemId == R.id.nav_history) {
                intent = new Intent(this, HistoryActivity.class);
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
        bottomNav.setSelectedItemId(R.id.nav_dashboard);
    }
}