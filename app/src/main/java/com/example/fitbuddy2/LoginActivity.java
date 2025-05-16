package com.example.fitbuddy2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    Button loginbtn;
    TextView linktosignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        loginbtn = findViewById(R.id.btnLogin);
        linktosignup = findViewById(R.id.tvSignUpLink);


        linktosignup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this , SignupActivity.class);
            startActivity(intent);
            finish();
        });


        loginbtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this , DashboardActivity.class);
            startActivity(intent);
            finish();
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}