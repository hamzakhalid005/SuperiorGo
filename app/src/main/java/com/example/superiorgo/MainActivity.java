package com.example.superiorgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
 private ImageView app_logo;
 private Button create,main_login_btn,gmail;
 private TextView line;
    private FirebaseAuth objectFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 222;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app_logo=findViewById(R.id.app_logo);
        create=findViewById(R.id.create);
        main_login_btn=findViewById(R.id.main_login_btn);
        gmail=findViewById(R.id.gmail);
        line=findViewById(R.id.line);
        mAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken
                (getString(R.string.default_web_client_id)).requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinmethod();
            }
        });
//////


    }
    public void create_account_activity(View view){
        Intent intent = new Intent(getApplicationContext(), create_account.class);
        startActivity(intent);

    }
    public void login_activity(View view) {
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);
    }
    private void signinmethod(){

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

                Toast.makeText(this, "Login Failed: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "signInWithCredential:success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), mainpage.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(MainActivity.this, "signInWithCredential:failure" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

