package com.example.chatopia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
=======

>>>>>>> origin/master
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
<<<<<<< HEAD
    private EditText userName, userEmail, userPassword;
    private TextView signinBtn, signupBtn;

=======
    EditText userName, userEmail, userPassword;
    TextView signinBtn, signupBtn;
    String name, email, password;
    DatabaseReference databaseReference;
>>>>>>> origin/master
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
<<<<<<< HEAD

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
=======
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
>>>>>>> origin/master
        userName = findViewById(R.id.usernametext);
        userEmail = findViewById(R.id.emailtext);
        userPassword = findViewById(R.id.passwordtext);
        signinBtn = findViewById(R.id.login);
        signupBtn = findViewById(R.id.signup);

<<<<<<< HEAD
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
=======

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = userName.getText().toString().trim();
                email = userEmail.getText().toString().trim();
                password = userPassword.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    userName.setError("Please Enter your name");
                    userName.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    userEmail.setError("Please Enter your email");
                    userEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    userPassword.setError("Please Enter your password");
                    userPassword.requestFocus();
                    return;
                }
                Signup();

>>>>>>> origin/master
            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                startActivity(new Intent(SignupActivity.this, SigninActivity.class));
            }
        });
    }
}
=======
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            startActivity(new Intent(SignupActivity.this, MainActivity.class));
            finish();
        }
    }


    private void Signup() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.trim(), password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        UserProfileChangeRequest userProfileChangeRequest= new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        firebaseUser.updateProfile(userProfileChangeRequest);

                        UserModel userModel= new UserModel(FirebaseAuth.getInstance().getUid(), name, email, password);
                        databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(userModel);
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this, "Sign up Failed", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
>>>>>>> origin/master
