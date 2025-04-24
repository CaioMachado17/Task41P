package com.example.task41p.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String title;

    public String description;

    public String dueDate;  // Format: "yyyy-MM-dd"
    public String dueTime;  // Format: "HH:mm"

    public boolean isCompleted;

    // Constructor
    public Task(@NonNull String title, String description, String dueDate, String dueTime, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.isCompleted = isCompleted;
    }
}
