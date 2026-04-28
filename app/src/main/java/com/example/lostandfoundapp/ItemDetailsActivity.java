package com.example.lostandfoundapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

public class ItemDetailsActivity extends AppCompatActivity {

    TextView detailTitle, detailDate, detailCategory, detailLocation, detailPhone, detailDescription;
    Button btnRemove;

    DatabaseHelper databaseHelper;
    int itemId;
    Item item;

    ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        databaseHelper = new DatabaseHelper(this);

        detailTitle = findViewById(R.id.detailTitle);
        detailDate = findViewById(R.id.detailDate);
        detailCategory = findViewById(R.id.detailCategory);
        detailLocation = findViewById(R.id.detailLocation);
        detailPhone = findViewById(R.id.detailPhone);
        detailDescription = findViewById(R.id.detailDescription);
        btnRemove = findViewById(R.id.btnRemove);

        detailImage = findViewById(R.id.detailImage);

        itemId = getIntent().getIntExtra("id", -1);

        if (itemId == -1) {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        item = databaseHelper.getItemById(itemId);

        if (item == null) {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        detailTitle.setText(item.postType + ": " + item.name);
        detailDate.setText("Date posted: " + item.date);
        detailCategory.setText("Category: " + item.category);
        detailLocation.setText("Location: " + item.location);
        detailPhone.setText("Phone: " + item.phone);
        detailDescription.setText("Description: " + item.description);

        if (item.imageUri != null && !item.imageUri.isEmpty()) {
            detailImage.setImageURI(Uri.parse(item.imageUri));
            detailImage.setVisibility(View.VISIBLE);
        }

        btnRemove.setOnClickListener(v -> {
            boolean deleted = databaseHelper.deleteItem(itemId);

            if (deleted) {
                Toast.makeText(this, "Advert removed", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to remove advert", Toast.LENGTH_SHORT).show();
            }
        });
    }
}