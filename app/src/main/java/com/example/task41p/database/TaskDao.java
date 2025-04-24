package com.example.task41p.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task_table ORDER BY dueDate ASC, dueTime ASC")
    List<Task> getAllTasks();

    @Query("SELECT * FROM task_table WHERE id = :taskId LIMIT 1")
    Task getTaskById(int taskId);
}
