package com.example.superiorgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class create_account extends AppCompatActivity {
EditText nameET,emailET,passwordET,phoneET;
Button createbtn,loginpage;
FirebaseAuth fAuth;
FirebaseFirestore fstore;
String userid;
String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        nameET=findViewById(R.id.nameET);
        emailET=findViewById(R.id.emailET);

        passwordET=findViewById(R.id.passwordET);
        phoneET=findViewById(R.id.phoneET);

        createbtn=findViewById(R.id.createbtn);
        loginpage=findViewById(R.id.loginpage);

        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
if (fAuth.getCurrentUser()!=null){
    startActivity(new Intent(getApplicationContext(),MainActivity.class));
    finish();

}
        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailET.getText().toString().trim();
                final String password = passwordET.getText().toString().trim();
                final String name = nameET.getText().toString().trim();
                final String phone = phoneET.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailET.setError("email is required");
                    return;

                }
                if (TextUtils.isEmpty(password)) {
                    passwordET.setError("password is required");
                    return;

                }

                Task<AuthResult> user_created_successfully = fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //sent verification link

                            Toast.makeText(create_account.this, "user created successfully", Toast.LENGTH_SHORT).show();
                            userid=fAuth.getCurrentUser().getUid();
                            DocumentReference objdocument=fstore.collection("users").document(userid);
                            Map<String,Object> user= new HashMap<>();
                            user.put("fname",name);
                            user.put("email",email);
                            user.put("password",password);
                            user.put("phone",phone);



                             objdocument.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for " + userid);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onfailure: failed " + e.toString());
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(create_account.this, "error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }
    public void login(View view){
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);}
}
