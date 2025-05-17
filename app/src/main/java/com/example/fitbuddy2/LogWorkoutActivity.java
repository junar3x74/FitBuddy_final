package com.example.fitbuddy2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbuddy2.adapters.WorkoutItemAdapter;
import com.example.fitbuddy2.models.WorkoutItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LogWorkoutActivity extends AppCompatActivity implements WorkoutItemAdapter.OnWorkoutClickListener {

    private List<WorkoutItem> items;
    private WorkoutItemAdapter workoutItemAdapter;
    private RecyclerView rvRecent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_workout);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view -> showAddWorkoutPopup());

        setupBottomNavigation();
        setupRecentRecyclerView();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupRecentRecyclerView() {
        rvRecent = findViewById(R.id.rvTodayWorkouts);
        if (rvRecent == null) return;

        rvRecent.setLayoutManager(new LinearLayoutManager(this));

        items = new ArrayList<>();
        items.add(new WorkoutItem("Chest Workout", "May 16, 2025", "click to view chest exercises", R.drawable.ic_fitness, Color.parseColor("#D81B60")));
        items.add(new WorkoutItem("Leg Workout", "May 15, 2025", "click to view leg exercises", R.drawable.ic_fitness, Color.parseColor("#43A047")));
        items.add(new WorkoutItem("Back & Biceps", "May 14, 2025", "click to view back & biceps", R.drawable.ic_fitness, Color.parseColor("#8E24AA")));
        items.add(new WorkoutItem("Shoulders & Triceps", "May 13, 2025", "click to view shoulders & triceps", R.drawable.ic_fitness, Color.parseColor("#F4511E")));

        workoutItemAdapter = new WorkoutItemAdapter(items, this);
        rvRecent.setAdapter(workoutItemAdapter);
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Intent intent = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_log) {
                return true;
            } else if (itemId == R.id.nav_dashboard) {
                intent = new Intent(this, DashboardActivity.class);
            } else if (itemId == R.id.nav_history) {
                intent = new Intent(this, HistoryActivity.class);
            } else if (itemId == R.id.nav_profile) {
                intent = new Intent(this, ProfileActivity.class);
            }

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            return true;
        });

        bottomNav.setSelectedItemId(R.id.nav_log);
    }

    private void showAddWorkoutPopup() {
        View popupView = LayoutInflater.from(this).inflate(R.layout.activity_addworkout, null);
        EditText etWorkoutName = popupView.findViewById(R.id.etWorkoutName);
        Button btnSaveWorkout = popupView.findViewById(R.id.btnSaveWorkout);

        AlertDialog dialog = new AlertDialog.Builder(this).setView(popupView).create();
        dialog.show();

        btnSaveWorkout.setOnClickListener(v -> {
            String name = etWorkoutName.getText().toString().trim();
            if (!name.isEmpty()) {
                WorkoutItem newItem = new WorkoutItem(name, "Today", "click to view " + name.toLowerCase(), R.drawable.ic_fitness, Color.parseColor("#3F51B5"));
                items.add(newItem);
                workoutItemAdapter.notifyItemInserted(items.size() - 1);
                rvRecent.scrollToPosition(items.size() - 1);
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Please enter a workout name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onWorkoutClick(String workoutName) {
        Intent intent = new Intent(this, WorkoutDetailsActivity.class);
        intent.putExtra("workout_name", workoutName);
        startActivity(intent);
    }
}
