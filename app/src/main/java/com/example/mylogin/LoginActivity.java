package com.example.mylogin;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button tvSignup = findViewById(R.id.tvSignup);
        FirebaseAuth Fauth = FirebaseAuth.getInstance();
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();


            if(TextUtils.isEmpty(email)){
                etEmail.setError("Email is Required.");
                return;
            }
            if(TextUtils.isEmpty(password)){
                etPassword.setError("Password is Required.");
                return;
            }
            if(password.length() < 6){
                etPassword.setError("Password must be >= 6 characters.");
                return  ;
            }

            Fauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Logged In Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainProductActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this , "Incorrect Email or Password " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                    }

                }
            });
            progressBar.setVisibility(View.VISIBLE);
        });

        tvSignup.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Back to Signup", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

    }
}
