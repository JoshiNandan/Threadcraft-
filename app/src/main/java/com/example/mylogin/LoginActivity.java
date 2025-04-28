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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseAuth Fauth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button tvSignup = findViewById(R.id.tvSignup);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

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

            progressBar.setVisibility(View.VISIBLE);

            Fauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // User logged in successfully
                        Toast.makeText(LoginActivity.this, "Logged In Successfully!", Toast.LENGTH_SHORT).show();

                        // Get the current user ID (UID)
                        String userId = Fauth.getCurrentUser().getUid();

                        // Check the user role in Firebase database
                        mDatabase.child(userId).child("role").get().addOnCompleteListener(roleTask -> {
                            if (roleTask.isSuccessful()) {
                                DataSnapshot dataSnapshot = roleTask.getResult();
                                String role = dataSnapshot.getValue(String.class);

                                // Redirect to different page based on the role
                                if ("admin".equals(role)) {
                                    // If admin, go to the admin orders page
                                    Intent intent = new Intent(LoginActivity.this, AdminOrderActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If user, go to the main product page
                                    Intent intent = new Intent(LoginActivity.this, MainProductActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                // Handle error in retrieving role
                                Toast.makeText(LoginActivity.this, "Failed to retrieve user role.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // If login fails
                        Toast.makeText(LoginActivity.this, "Incorrect Email or Password: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        });

        tvSignup.setOnClickListener(v -> {
            // Redirect to signup page
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
