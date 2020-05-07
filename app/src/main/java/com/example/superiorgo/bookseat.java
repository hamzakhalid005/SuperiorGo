package com.example.superiorgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class bookseat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookseat);
    }
    public void SCHEDULE(View view){
        Intent intent = new Intent(getApplicationContext(), schedule.class);
        startActivity(intent);}
}
