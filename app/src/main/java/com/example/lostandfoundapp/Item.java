package com.example.lostandfoundapp;

public class Item {
    int id;
    String postType, name, phone, description, date, location, category, imageUri;

    public Item(int id, String postType, String name, String phone,
                String description, String date, String location,
                String category, String imageUri) {
        this.id = id;
        this.postType = postType;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
        this.category = category;
        this.imageUri = imageUri;
    }
}