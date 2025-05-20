package com.example.fitbuddy2;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbuddy2.adapters.ExerciseAdapter;
import com.example.fitbuddy2.models.Exercise;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSessionActivity extends AppCompatActivity {

    private TextView tvWorkoutTitle, tvTimer, tvReps, tvSets;
    private final int MAX_STOPWATCH_TIME = 3600; // Maximum time in seconds (1 hour)
    private CircularProgressIndicator progressTimer;
    private RecyclerView rvWorkoutList;
    private ExerciseAdapter adapter;
    private List<Exercise> exerciseList;
    private MaterialButton btnStartPause, btnFinish, btnAddExercise, btnRemoveExercise;

    // Stopwatch variables
    private boolean isTimerRunning = false;
    private long startTimeMillis = 0L;
    private long timeInMillis = 0L;
    private long pausedTimeMillis = 0L;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout_session);

        // Initialize all UI elements
        initializeUI();

        // Set up button listeners
        setupButtonListeners();

        // Set up toolbar
        setupToolbar();

        // Set up initial data
        setupInitialData();
    }

    private void initializeUI() {
        tvWorkoutTitle = findViewById(R.id.tvWorkoutTitle);
        tvTimer = findViewById(R.id.tvTimer);
        tvReps = findViewById(R.id.tvReps);
        tvSets = findViewById(R.id.tvSets);
        progressTimer = findViewById(R.id.progressTimer);

        // Set max for progress indicator (1 hour in seconds)
        progressTimer.setMax(MAX_STOPWATCH_TIME);
        // Initialize progress to zero
        progressTimer.setProgress(0);

        rvWorkoutList = findViewById(R.id.rvWorkoutList);
        rvWorkoutList.setLayoutManager(new LinearLayoutManager(this));

        btnStartPause = findViewById(R.id.btnStartPause);
        btnFinish = findViewById(R.id.btnFinish);
        btnAddExercise = findViewById(R.id.btnAddExercise);
        btnRemoveExercise = findViewById(R.id.btnRemoveExercise);

        // Initialize timer display
        tvTimer.setText("00:00");
    }

    private void setupButtonListeners() {
        btnAddExercise.setOnClickListener(v -> showAddExerciseDialog());

        btnRemoveExercise.setOnClickListener(v -> {
            if (!exerciseList.isEmpty()) {
                int lastIndex = exerciseList.size() - 1;
                exerciseList.remove(lastIndex);
                adapter.notifyItemRemoved(lastIndex);
            }
        });

        btnStartPause.setOnClickListener(v -> {
            if (!isTimerRunning) {
                startStopwatch();
                btnStartPause.setText("Pause");
            } else {
                pauseStopwatch();
                btnStartPause.setText("Resume");
            }
        });

        btnFinish.setOnClickListener(v -> {
            // Stop the stopwatch when finishing
            if (isTimerRunning) {
                pauseStopwatch();
            }
            // Handle finish session logic
            showWorkoutSummary();
        });
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.sessionAppBar);
        // Set the navigation click listener
        toolbar.setNavigationOnClickListener(v -> {
            // This will navigate back (same as pressing the device back button)
            onBackPressed();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupInitialData() {
        // Initialize exercise list with sample data
        exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("Bench Press", "4 sets of 8 reps"));
        exerciseList.add(new Exercise("Incline Dumbbell Press", "3 sets of 12 reps"));
        exerciseList.add(new Exercise("Triceps Pushdowns", "3 sets of 12 reps"));

        // Set up adapter for RecyclerView
        adapter = new ExerciseAdapter(exerciseList);
        rvWorkoutList.setAdapter(adapter);

        // Reset all timer values
        pausedTimeMillis = 0L; // Reset paused time
        timeInMillis = 0L; // Reset current time
        // Reset progress indicator
        progressTimer.setProgress(0);
    }

    private void showAddExerciseDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_excercise, null);

        EditText etName = dialogView.findViewById(R.id.etExerciseName);
        EditText etDetail = dialogView.findViewById(R.id.etExerciseDetail);
        Button btnSave = dialogView.findViewById(R.id.btnSaveExercise);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String detail = etDetail.getText().toString().trim();
            if (!name.isEmpty() && !detail.isEmpty()) {
                exerciseList.add(new Exercise(name, detail));
                adapter.notifyItemInserted(exerciseList.size() - 1);
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Please fill out both fields", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void startStopwatch() {
        isTimerRunning = true;

        if (pausedTimeMillis > 0) {
            // If resuming from pause
            startTimeMillis = SystemClock.uptimeMillis() - pausedTimeMillis;
        } else {
            // If starting fresh
            startTimeMillis = SystemClock.uptimeMillis();
        }

        // Update the timer every 100ms for smooth UI
        handler.postDelayed(updateStopwatchRunnable, 100);
    }

    private final Runnable updateStopwatchRunnable = new Runnable() {
        @Override
        public void run() {
            if (isTimerRunning) {
                // Calculate elapsed time
                timeInMillis = SystemClock.uptimeMillis() - startTimeMillis;

                // Convert to minutes and seconds
                int seconds = (int) (timeInMillis / 1000);
                int minutes = seconds / 60;
                seconds %= 60;

                // Format time as MM:SS
                String time = String.format("%02d:%02d", minutes, seconds);
                tvTimer.setText(time);

                // Update progress indicator (capped at MAX_STOPWATCH_TIME)
                int totalSeconds = minutes * 60 + seconds;
                int progress = Math.min(totalSeconds, MAX_STOPWATCH_TIME);
                progressTimer.setProgress(progress);

                // Schedule the next update
                handler.postDelayed(this, 100);
            }
        }
    };

    private void resetStopwatch() {
        // Stop any running timer
        if (isTimerRunning) {
            pauseStopwatch();
        }

        // Reset all timer values
        pausedTimeMillis = 0L;
        timeInMillis = 0L;

        // Reset UI
        tvTimer.setText("00:00");
        progressTimer.setProgress(0);
        btnStartPause.setText("Start");
    }

    private void pauseStopwatch() {
        isTimerRunning = false;
        pausedTimeMillis = timeInMillis;
        handler.removeCallbacks(updateStopwatchRunnable);
    }

    private void showWorkoutSummary() {
        // Format the final time for display
        int seconds = (int) (timeInMillis / 1000);
        int minutes = seconds / 60;
        seconds %= 60;
        String finalTime = String.format("%02d:%02d", minutes, seconds);

        // Create a dialog to show workout summary
        new AlertDialog.Builder(this)
                .setTitle("Workout Complete")
                .setMessage("Total workout time: " + finalTime)
                .setPositiveButton("Finish", (dialog, which) -> {
                    dialog.dismiss();
                    finish(); // Close the activity
                })
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Make sure to remove any callbacks to prevent memory leaks
        handler.removeCallbacks(updateStopwatchRunnable);
    }
}