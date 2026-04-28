package com.example.lostandfoundapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar;

public class ViewitemsActivity extends AppCompatActivity {

    RecyclerView recyclerItems;
    Spinner spinnerFilter;

    DatabaseHelper databaseHelper;
    ItemAdapter itemAdapter;
    ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewitems);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Lost & Found Items");
        }

        recyclerItems = findViewById(R.id.recyclerItems);
        spinnerFilter = findViewById(R.id.spinnerFilter);

        databaseHelper = new DatabaseHelper(this);

        recyclerItems.setLayoutManager(new LinearLayoutManager(this));

        String[] categories = {
                "All",
                "Electronics",
                "Pets",
                "Wallets",
                "Keys",
                "Clothing",
                "Other"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );

        spinnerFilter.setAdapter(adapter);

        loadItems();

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {

                String selected = spinnerFilter.getSelectedItem().toString();

                if (selected.equals("All")) {
                    itemList = databaseHelper.getAllItems();
                } else {
                    itemList = databaseHelper.getItemsByCategory(selected);
                }

                itemAdapter = new ItemAdapter(ViewitemsActivity.this, itemList);
                recyclerItems.setAdapter(itemAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void loadItems() {
        itemList = databaseHelper.getAllItems();
        itemAdapter = new ItemAdapter(this, itemList);
        recyclerItems.setAdapter(itemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }
}