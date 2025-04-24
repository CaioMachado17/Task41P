//package com.example.task41p.activities;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.widget.CheckBox;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.task41p.R;
//import com.example.task41p.database.Task;
//import com.example.task41p.database.TaskDatabase;
//
//public class TaskDetailActivity extends AppCompatActivity {
//
//    private int taskId;
//    private Task task;
//    private TextView textViewDetailTitle, textViewDetailDate, textViewDetailTime, textViewDetailDescription;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_task_detail);
//
//        textViewDetailTitle = findViewById(R.id.textViewDetailTitle);
//        textViewDetailDate = findViewById(R.id.textViewDetailDate);
//        textViewDetailTime = findViewById(R.id.textViewDetailTime);
//        textViewDetailDescription = findViewById(R.id.textViewDetailDescription);
//
//
//
//        taskId = getIntent().getIntExtra("taskId", -1);
//        if (taskId == -1) {
//            Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        task = TaskDatabase.getInstance(this).taskDao().getTaskById(taskId);
//        Log.d("TaskDetail", "Received task ID: " + taskId);
//        if (task != null) {
//            textViewDetailTitle.setText(task.title);
//            textViewDetailDate.setText("Due Date: " + task.dueDate);
//            textViewDetailTime.setText("Due Time: " + task.dueTime);
//            textViewDetailDescription.setText(task.description);
//            CheckBox checkBoxCompleted = findViewById(R.id.checkBoxCompleted);
//            checkBoxCompleted.setChecked(task.isCompleted);
//            Log.d("TaskDetail", "Loaded task title: " + task.title);
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.task_detail_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_edit) {
//            Intent intent = new Intent(this, AddTaskActivity.class);
//            intent.putExtra("taskId", task.id);
//            startActivity(intent);
//            return true;
//        } else if (item.getItemId() == R.id.menu_delete) {
//            TaskDatabase.getInstance(this).taskDao().delete(task);
//            Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//}


package com.example.task41p.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task41p.R;
import com.example.task41p.database.Task;
import com.example.task41p.database.TaskDatabase;

public class TaskDetailActivity extends AppCompatActivity {

    private int taskId;
    private Task task;
    private TextView textViewDetailTitle, textViewDetailDate, textViewDetailTime, textViewDetailDescription;
    private CheckBox checkBoxCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // Bind views
        textViewDetailTitle = findViewById(R.id.textViewDetailTitle);
        textViewDetailDate = findViewById(R.id.textViewDetailDate);
        textViewDetailTime = findViewById(R.id.textViewDetailTime);
        textViewDetailDescription = findViewById(R.id.textViewDetailDescription);
        checkBoxCompleted = findViewById(R.id.checkBoxCompleted);
        ImageButton buttonBack = findViewById(R.id.buttonBack);
        ImageButton buttonMenu = findViewById(R.id.buttonMenu);

        // Back button functionality
        buttonBack.setOnClickListener(v -> finish());

        // Popup menu for edit/delete
        buttonMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, buttonMenu);
            popupMenu.getMenuInflater().inflate(R.menu.task_detail_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_edit) {
                    Intent intent = new Intent(this, AddTaskActivity.class);
                    intent.putExtra("taskId", task.id);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.menu_delete) {
                    TaskDatabase.getInstance(this).taskDao().delete(task);
                    Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });

        // Load task
        taskId = getIntent().getIntExtra("taskId", -1);
        Log.d("TaskDetail", "Received task ID: " + taskId);

        if (taskId == -1) {
            Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        task = TaskDatabase.getInstance(this).taskDao().getTaskById(taskId);

        if (task != null) {
            textViewDetailTitle.setText(task.title);
            textViewDetailDate.setText("Due Date: " + task.dueDate);
            textViewDetailTime.setText("Due Time: " + task.dueTime);
            textViewDetailDescription.setText(task.description);
            checkBoxCompleted.setChecked(task.isCompleted);
            Log.d("TaskDetail", "Loaded task title: " + task.title);
        } else {
            Toast.makeText(this, "Could not load task details.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.task_detail_menu, menu);
//        return true;
//    }

    // Optional — only needed if using top-right 3-dot menu from app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
