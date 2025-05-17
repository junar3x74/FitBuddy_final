package com.example.fitbuddy2;

import android.os.Bundle;
import android.os.CountDownTimer;
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
    private CircularProgressIndicator progressTimer;
    private RecyclerView rvWorkoutList;
    private ExerciseAdapter adapter;
    private List<Exercise> exerciseList;
    private MaterialButton btnStartPause, btnFinish, btnAddExercise, btnRemoveExercise;

    private boolean isTimerRunning = false;
    private long timeInMillis = 0;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout_session);


        tvWorkoutTitle = findViewById(R.id.tvWorkoutTitle);
        tvTimer = findViewById(R.id.tvTimer);
        tvReps = findViewById(R.id.tvReps);
        tvSets = findViewById(R.id.tvSets);
        progressTimer = findViewById(R.id.progressTimer);

        rvWorkoutList = findViewById(R.id.rvWorkoutList);
        rvWorkoutList.setLayoutManager(new LinearLayoutManager(this));

        btnStartPause = findViewById(R.id.btnStartPause);
        btnFinish = findViewById(R.id.btnFinish);
        btnAddExercise = findViewById(R.id.btnAddExercise);
        btnRemoveExercise = findViewById(R.id.btnRemoveExercise);


        exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("Bench Press", "4 sets of 8 reps"));
        exerciseList.add(new Exercise("Incline Dumbbell Press", "3 sets of 12 reps"));
        exerciseList.add(new Exercise("Triceps Pushdowns", "3 sets of 12 reps"));



        adapter = new ExerciseAdapter(exerciseList);
        rvWorkoutList.setAdapter(adapter);


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
                startTimer(600000); // 10 minutes (adjust as needed)
                btnStartPause.setText("Pause");
            } else {
                pauseTimer();
                btnStartPause.setText("Start");
            }
        });


        btnFinish.setOnClickListener(v -> {
            // Handle finish session logic
            finish();
        });



        MaterialToolbar toolbar = findViewById(R.id.sessionAppBar);
        // Set the navigation click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This will navigate back (same as pressing the device back button)
                onBackPressed();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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

    private void startTimer(long duration) {
        isTimerRunning = true;
        timeInMillis = duration;
        progressTimer.setMax((int) duration / 1000);

        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeInMillis = millisUntilFinished;
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds %= 60;
                String time = String.format("%02d:%02d", minutes, seconds);
                tvTimer.setText(time);

                int progress = (int) (millisUntilFinished / 1000);
                progressTimer.setProgress(progress);
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                tvTimer.setText("00:00");
            }
        }.start();
    }

    private void pauseTimer() {
        isTimerRunning = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


}