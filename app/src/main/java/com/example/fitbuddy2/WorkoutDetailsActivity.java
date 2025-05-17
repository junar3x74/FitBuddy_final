package com.example.fitbuddy2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout_details);

        String workoutName = getIntent().getStringExtra("workout_name");

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());


        // Set title
        TextView tvTitle = findViewById(R.id.tvWorkoutTitle);
        tvTitle.setText(workoutName);


        List<Exercise> exercises = new ArrayList<>();
        if ("Chest Workout".equals(workoutName)) {
            exercises.add(new Exercise("Bench Press", "3 sets × 8–10 reps"));
            exercises.add(new Exercise("Incline Dumbbell Press", "3 sets × 8–10 reps"));
            exercises.add(new Exercise("Chest Fly", "3 sets × 12–15 reps"));
            exercises.add(new Exercise("Push‑Ups", "3 sets to failure"));
        } else if ("Leg Workout".equals(workoutName)) {
            exercises.add(new Exercise("Squats", "4 sets × 8–12 reps"));
            exercises.add(new Exercise("Lunges", "3 sets × 10 reps per leg"));
            exercises.add(new Exercise("Leg Press", "3 sets × 10–12 reps"));
        } else if ("Back & Biceps".equals(workoutName)) {
            exercises.add(new Exercise("Pull‑Ups", "3 sets × 6–8 reps"));
            exercises.add(new Exercise("Bent‑Over Row", "3 sets × 8–10 reps"));
            exercises.add(new Exercise("Bicep Curls", "3 sets × 12–15 reps"));
        } else if ("Shoulders & Triceps".equals(workoutName)) {
            exercises.add(new Exercise("Overhead Press", "3 sets × 8–10 reps"));
            exercises.add(new Exercise("Lateral Raises", "3 sets × 12–15 reps"));
            exercises.add(new Exercise("Triceps Dips", "3 sets × 10–12 reps"));
        } else {
            exercises.add(new Exercise("General Warm‑Up", "5–10 mins"));
        }

        RecyclerView rv = findViewById(R.id.rvExercises);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ExerciseAdapter(exercises));


        FloatingActionButton fab = findViewById(R.id.fabAddExercise);
        fab.setOnClickListener(v -> {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_excercise, null);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Add New Exercise")
                    .setView(dialogView)
                    .create();
            dialog.show();

            dialogView.findViewById(R.id.btnSaveExercise).setOnClickListener(btn -> {
                String name = ((TextInputEditText)dialogView.findViewById(R.id.etExerciseName)).getText().toString();
                String detail = ((TextInputEditText)dialogView.findViewById(R.id.etExerciseDetail)).getText().toString();
                dialog.dismiss();
            });
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}