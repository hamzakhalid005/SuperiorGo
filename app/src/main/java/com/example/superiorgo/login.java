package com.example.superiorgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    EditText emailET,passwordET;
    Button createbtn,loginpage;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailET=findViewById(R.id.emailET);
        passwordET=findViewById(R.id.passwordET);
        fAuth=FirebaseAuth.getInstance();

        createbtn=findViewById(R.id.createbtn);
        loginpage=findViewById(R.id.loginpage);

        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailET.setError("email is required");
                    return;

                }
                if (TextUtils.isEmpty(password)) {
                    passwordET.setError("password is required");
                    return;

                }
                //authenticate user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "login successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), mainpage.class));
                        } else {
                            Toast.makeText(login.this, "error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    public void create_account_activity(View view){
        Intent intent = new Intent(getApplicationContext(), create_account.class);
        startActivity(intent);}
    public void login(View view){
        Intent intent = new Intent(getApplicationContext(), mainpage.class);
        startActivity(intent);}
}
