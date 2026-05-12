package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCreate, btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreate = findViewById(R.id.btnCreate);
        btnShow = findViewById(R.id.btnShow);

        btnCreate.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddItemActivity.class)));

        btnShow.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ViewitemsActivity.class)));

        View btnMap = findViewById(R.id.btnMap);

        btnMap.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MapActivity.class)));
    }
}