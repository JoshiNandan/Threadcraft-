package com.example.mylogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseAuth Fauth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etFullName = findViewById(R.id.etFullName);
        Button btnSignup = findViewById(R.id.btnSignup);
        ProgressBar progressBar2 = findViewById(R.id.progressBar2);

        btnSignup.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String fullName = etFullName.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email is Required.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                etPassword.setError("Password is Required.");
                return;
            }
            if (password.length() < 6) {
                etPassword.setError("Password must be >= 6 characters.");
                return;
            }

            progressBar2.setVisibility(View.VISIBLE);

            Fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Get the user ID after successful signup
                        String userId = Fauth.getCurrentUser().getUid();

                        // Set user role as "user" by default (you can manually set admin role if needed)
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
                        userRef.child("email").setValue(email);
                        userRef.child("role").setValue("user"); // You can change this to "admin" for admin users

                        Toast.makeText(SignUpActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, MainProductActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar2.setVisibility(View.INVISIBLE);
                }
            });
        });
    }
}
