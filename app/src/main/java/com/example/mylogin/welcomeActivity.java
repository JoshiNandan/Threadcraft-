package com.example.mylogin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class welcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        Button button = findViewById(R.id.button);
        TextView tvLogin = findViewById(R.id.tvLogin);

        button.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut(); // Force logout
            Intent intent = new Intent(welcomeActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });


        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(welcomeActivity.this, LoginActivity.class);
            startActivity(intent);

        });
    }
}
