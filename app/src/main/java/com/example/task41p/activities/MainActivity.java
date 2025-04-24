package com.example.task41p.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task41p.R;
import com.example.task41p.adapters.TaskAdapter;
import com.example.task41p.database.Task;
import com.example.task41p.database.TaskDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;


    @Override
    protected void onResume() {
        super.onResume();
        // Refresh task list
        taskList.clear();
        taskList.addAll(TaskDatabase.getInstance(this).taskDao().getAllTasks());
        taskAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));

        taskList = TaskDatabase.getInstance(this).taskDao().getAllTasks();
        taskAdapter = new TaskAdapter(taskList, this);
        recyclerViewTasks.setAdapter(taskAdapter);

        FloatingActionButton fab = findViewById(R.id.fabAddTask);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });


//        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
//        bottomNav.setOnItemSelectedListener(item -> {
//            int id = item.getItemId();
//
//            if (id == R.id.nav_tasks) {
//                return true;
//            } else if (id == R.id.nav_calendar) {
//                startActivity(new Intent(this, CalendarActivity.class));
//                return true;
//            } else if (id == R.id.nav_settings) {
//                startActivity(new Intent(this, SettingsActivity.class));
//                return true;
//            }
//
//            return false;
//        });

    }
}
