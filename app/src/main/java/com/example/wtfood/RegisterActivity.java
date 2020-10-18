package com.example.wtfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText rEmail, rPassword, rPassword2;
    Button goToLogin;
    ImageButton registerButton;
    ProgressBar progress;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rEmail = findViewById(R.id.rEmail);
        rPassword = findViewById(R.id.rPassword);
        rPassword2 = findViewById(R.id.rPassword2);
        goToLogin = findViewById(R.id.goToLoginButton);
        registerButton = findViewById(R.id.registerButton);

        fAuth = FirebaseAuth.getInstance();
        progress = findViewById(R.id.progressBar);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = rEmail.getText().toString().trim();
                String password = rPassword.getText().toString();
                String password2 = rPassword2.getText().toString();

                if (TextUtils.isEmpty(email)){
                    rEmail.setError("");
                }
            }
        });
    }
}