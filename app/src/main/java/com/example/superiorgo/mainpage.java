package com.example.superiorgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class mainpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
    }
    public void logout(View view){

        FirebaseAuth.getInstance().signOut();
        try {
            GoogleSignIn.getClient(getApplicationContext(),
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            ).signOut();
        } catch (Exception ex) {
            Toast.makeText(mainpage.this, "Logging Out Error", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }
    public void schedule(View view){
        Intent intent = new Intent(getApplicationContext(), schedule.class);
        startActivity(intent);}
    public void route(View view){
        Intent intent = new Intent(getApplicationContext(), route.class);
        startActivity(intent);}
}
