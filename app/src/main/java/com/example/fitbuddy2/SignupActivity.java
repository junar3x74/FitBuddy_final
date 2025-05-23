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

public class SignupActivity extends AppCompatActivity {

    Button btnsignup;
    TextView loginlink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        btnsignup = findViewById(R.id.btnSignUp);

        loginlink = findViewById(R.id.tvLoginLink);


        loginlink.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this , LoginActivity.class);
            startActivity(intent);
            finish();
        });






        btnsignup.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this , LoginActivity.class);
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