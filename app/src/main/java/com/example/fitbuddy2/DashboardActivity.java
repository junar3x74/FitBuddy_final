package com.example.fitbuddy2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbuddy2.adapters.SummaryAdapter;
import com.example.fitbuddy2.models.Summary;

import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);




        // Apply edge-to-edge insets to the root view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView rv = findViewById(R.id.rvSummary);
        // 1) set horizontal layout manager
        rv.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        // 2) supply some dummy data
        List<Summary> dummy = Arrays.asList(
                new Summary("Workouts", "5"),
                new Summary("Calories", "1.2k"),
                new Summary("Duration", "3h 15m")
        );
        // 3) set adapter
        rv.setAdapter(new SummaryAdapter(dummy));
    }
}