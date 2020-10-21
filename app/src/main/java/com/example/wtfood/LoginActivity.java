package com.example.wtfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText lEmail, lPassword;
    Button goToSignUp;
    ImageButton loginButton;
    ProgressBar progress;
    FirebaseAuth fAuth;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lEmail = findViewById(R.id.lEmail);
        lPassword = findViewById(R.id.lPassword);
        goToSignUp = findViewById(R.id.goToSignUpButton);
        loginButton = findViewById(R.id.loginButton);
        navigationView = findViewById(R.id.nav_view);

        fAuth = FirebaseAuth.getInstance();
        progress = findViewById(R.id.progressBar);

        //If the user is already logged in, direct the user back to the main activity.
        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        loginButton.setOnClickListener(v -> {
            String email = lEmail.getText().toString().trim();
            String password = lPassword.getText().toString();

            if (TextUtils.isEmpty(email)){
                lEmail.setError("Email cannot be empty.");
                return;
            }

            else if (TextUtils.isEmpty(password)) {
                lPassword.setError("Password cannot be empty.");
                return;
            }

            progress.setVisibility(View.VISIBLE);

            //Login the user.
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    progress.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Logged in successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    progress.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Please check your email and password again.", Toast.LENGTH_SHORT).show();
                }
            });
        });



        goToSignUp = findViewById(R.id.goToSignUpButton);
        goToSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    public void loginBackButton(View view){
        finish();
    }
}