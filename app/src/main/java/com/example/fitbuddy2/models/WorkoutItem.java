package com.example.fitbuddy2.models;

public class WorkoutItem {
    public String name;
    public String date;
    public String duration;
    public int iconRes;
    public int tintColor;

    public WorkoutItem(String name, String date, String duration, int iconRes, int tintColor) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.iconRes = iconRes;
        this.tintColor = tintColor;
    }
}
