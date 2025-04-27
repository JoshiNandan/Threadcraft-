package com.example.mylogin;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etFullName = findViewById(R.id.etFullName);
        Button btnSignup = findViewById(R.id.btnSignup);
        FirebaseAuth Fauth = FirebaseAuth.getInstance();
        ProgressBar progressBar2 = findViewById(R.id.progressBar2);


        if(Fauth.getCurrentUser() != null){
            Intent intent = new Intent(SignUpActivity.this, MainProductActivity.class);
                startActivity(intent);
                finish();
        }


        btnSignup.setOnClickListener(v -> {

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String fullName = etFullName.getText().toString().trim();

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


//
//            if (email.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
//                Toast.makeText(SignupActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(SignupActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(SignupActivity.this, MainProductActivity.class);
//                startActivity(intent);
//                finish();
//            }
            progressBar2.setVisibility(View.VISIBLE);

          // register the user in firebase
           Fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(SignUpActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(SignUpActivity.this, MainProductActivity.class);
                       startActivity(intent);
                       finish();
                   }else {
                       Toast.makeText(SignUpActivity.this , "Error !" + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                   }
               }
           });
        });
    }
}
