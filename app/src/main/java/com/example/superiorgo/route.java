package com.example.superiorgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class route extends AppCompatActivity {
    private static final String COLLECTION_INFO = "bussesinfo";
    EditText routenumber, timeA;
    Button submit;
    private ProgressDialog progressDialog;

    private FirebaseFirestore obj;
    private DocumentReference objectDocumentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        routenumber = findViewById(R.id.routenumber);
        timeA = findViewById(R.id.timeA);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCanceledOnTouchOutside(false);

        submit = findViewById(R.id.adddata);
        obj = FirebaseFirestore.getInstance();
    }
    public void adddata(View v) {

        try {
            if (!routenumber.getText().toString().isEmpty() && !timeA.getText().toString().isEmpty()) {
                progressDialog.show();
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("routenumber", routenumber.getText().toString());

                objectMap.put("campusname", timeA.getText().toString());
                obj.collection(COLLECTION_INFO)
                        .document(routenumber.getText().toString()).set(objectMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.cancel();
                                Toast.makeText(route.this, "DATA SUCCESSFULLY ADDED", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(route.this, "DATA NOT ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "PLEASE ENTER ALL DETAILS ", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

            Toast.makeText(this, "addValues:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
