package com.example.chatopia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity extends AppCompatActivity {
    EditText userEmail, userPassword;
    TextView signinBtn, signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        userEmail = findViewById(R.id.emailtext);
        userPassword = findViewById(R.id.passwordtext);
        signinBtn = findViewById(R.id.login);
        signupBtn = findViewById(R.id.signup);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    userEmail.setError("Please enter your email");
                    userEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    userPassword.setError("Please enter your password");
                    userPassword.requestFocus();
                    return;
                }
                SignIn(email, password);
            }
        });

        signupBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private void SignIn(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null && user.isEmailVerified()) {
                            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SigninActivity.this, "Please verify your email first", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(SigninActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SigninActivity.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
