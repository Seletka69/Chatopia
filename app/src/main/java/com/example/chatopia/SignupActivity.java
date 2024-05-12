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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private EditText userName, userEmail, userPassword;
    private TextView signinBtn, signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userName = findViewById(R.id.usernametext);
        userEmail = findViewById(R.id.emailtext);
        userPassword = findViewById(R.id.passwordtext);
        signinBtn = findViewById(R.id.login);
        signupBtn = findViewById(R.id.signup);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = userName.getText().toString().trim();
                final String email = userEmail.getText().toString().trim();
                final String password = userPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    userName.setError("Please enter your name");
                    userName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    userEmail.setError("Please enter your email");
                    userEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    userPassword.setError("Please enter your password");
                    userPassword.requestFocus();
                    return;
                }

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();
                                firebaseUser.updateProfile(profileUpdates);

                                UserModel userModel = new UserModel(firebaseUser.getUid(), name, email, password);
                                databaseReference.child(firebaseUser.getUid()).setValue(userModel);

                                firebaseUser.sendEmailVerification()
                                        .addOnSuccessListener(aVoid -> Toast.makeText(SignupActivity.this, "Verification email sent", Toast.LENGTH_SHORT).show())
                                        .addOnFailureListener(e -> Toast.makeText(SignupActivity.this, "Failed to send verification email", Toast.LENGTH_SHORT).show());

                                startActivity(new Intent(SignupActivity.this, SigninActivity.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(e -> Toast.makeText(SignupActivity.this, "Sign up failed: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, SigninActivity.class));
            }
        });
    }
}
