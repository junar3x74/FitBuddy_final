package com.example.fitbuddy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvWeight, tvHeight, tvAge, tvFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Reference to stat TextViews
        tvWeight = findViewById(R.id.tvWeight);
        tvHeight = findViewById(R.id.tvHeight);
        tvAge = findViewById(R.id.tvAge);
        tvFavorite = findViewById(R.id.tvFavoriteWorkout);

        MaterialButton btnEdit = findViewById(R.id.btnEditStats);
        btnEdit.setOnClickListener(v -> showEditDialog());

        setupBottomNavigation();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showEditDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_stats, null);

        TextInputEditText etWeight = dialogView.findViewById(R.id.etWeight);
        TextInputEditText etHeight = dialogView.findViewById(R.id.etHeight);
        TextInputEditText etAge = dialogView.findViewById(R.id.etAge);
        TextInputEditText etFavorite = dialogView.findViewById(R.id.etFavoriteWorkout);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Edit Body Stats")
                .setView(dialogView)
                .create();
        dialog.show();

        dialogView.findViewById(R.id.btnSaveStats).setOnClickListener(saveBtn -> {
            String newWeight = etWeight.getText().toString().trim();
            String newHeight = etHeight.getText().toString().trim();
            String newAge = etAge.getText().toString().trim();
            String newFav = etFavorite.getText().toString().trim();

            if (!newWeight.isEmpty()) tvWeight.setText("Weight: " + newWeight + " kg");
            if (!newHeight.isEmpty()) tvHeight.setText("Height: " + newHeight + " cm");
            if (!newAge.isEmpty()) tvAge.setText("Age: " + newAge);
            if (!newFav.isEmpty()) tvFavorite.setText("Favorite Workout: " + newFav);

            dialog.dismiss();
        });
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Intent intent = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_profile) {
                return true;
            } else if (itemId == R.id.nav_log) {
                intent = new Intent(this, LogWorkoutActivity.class);
            } else if (itemId == R.id.nav_history) {
                intent = new Intent(this, HistoryActivity.class);
            } else if (itemId == R.id.nav_dashboard) {
                intent = new Intent(this, DashboardActivity.class);
            }

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            return true;
        });

        bottomNav.setSelectedItemId(R.id.nav_profile);
    }
}