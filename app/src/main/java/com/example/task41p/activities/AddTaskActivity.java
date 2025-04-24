package com.example.task41p.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task41p.R;
import com.example.task41p.database.Task;
import com.example.task41p.database.TaskDatabase;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    int taskId = -1;
    boolean isEdit = false;
    EditText editTextTitle, editTextDescription;
    TextView textViewDate, textViewTime;
    Button buttonPickDate, buttonPickTime, buttonSaveTask;

    String selectedDate = "", selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);
        buttonPickDate = findViewById(R.id.buttonPickDate);
        buttonPickTime = findViewById(R.id.buttonPickTime);
        buttonSaveTask = findViewById(R.id.buttonSaveTask);

        buttonPickDate.setOnClickListener(v -> showDatePicker());
        buttonPickTime.setOnClickListener(v -> showTimePicker());

        buttonSaveTask.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();

            if (title.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEdit) {
                Task task = TaskDatabase.getInstance(this).taskDao().getTaskById(taskId);
                if (task != null) {
                    task.title = title;
                    task.description = description;
                    task.dueDate = selectedDate;
                    task.dueTime = selectedTime;
                    TaskDatabase.getInstance(this).taskDao().update(task);
                    Toast.makeText(this, "Task updated!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Task newTask = new Task(title, description, selectedDate, selectedTime, false);
                TaskDatabase.getInstance(this).taskDao().insert(newTask);
                Toast.makeText(this, "Task saved!", Toast.LENGTH_SHORT).show();
            }

            finish();
        });


        taskId = getIntent().getIntExtra("taskId", -1);
        if (taskId != -1) {
            isEdit = true;
            Task task = TaskDatabase.getInstance(this).taskDao().getTaskById(taskId);
            if (task != null) {
                editTextTitle.setText(task.title);
                editTextDescription.setText(task.description);
                selectedDate = task.dueDate;
                selectedTime = task.dueTime;
                textViewDate.setText("Due Date: " + selectedDate);
                textViewTime.setText("Due Time: " + selectedTime);
            }
        }


    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
            (view, year, month, dayOfMonth) -> {
                selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                textViewDate.setText("Due Date: " + selectedDate);
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
            (view, hourOfDay, minute) -> {
                selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                textViewTime.setText("Due Time: " + selectedTime);
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        );
        timePickerDialog.show();
    }
}
