package com.example.task41p.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task41p.R;
import com.example.task41p.activities.TaskDetailActivity;
import com.example.task41p.database.Task;
import com.example.task41p.database.TaskDatabase;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;

    public TaskAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDueDate;
        CheckBox checkboxTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDueDate = itemView.findViewById(R.id.textViewDueDate);
            checkboxTask = itemView.findViewById(R.id.checkboxTask);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        // Temporarily remove listener to prevent weird behavior during recycling
        holder.checkboxTask.setOnCheckedChangeListener(null);
        holder.checkboxTask.setChecked(task.isCompleted);

        // Set data
        holder.textViewTitle.setText(task.title);
        holder.textViewDueDate.setText("Due: " + task.dueDate + " " + task.dueTime);

        // Set background color based on completion status
        if (task.isCompleted) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.green_complete));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        // Handle checkbox toggle
        holder.checkboxTask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.isCompleted = isChecked;
            TaskDatabase.getInstance(context).taskDao().update(task);

            // Refresh this item to apply new background color immediately
            notifyItemChanged(holder.getAdapterPosition());
        });

        // Handle item click → show task details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TaskDetailActivity.class);
            intent.putExtra("taskId", task.id);
            context.startActivity(intent);
            Log.d("TaskAdapter", "Clicked task ID: " + task.id);
        });
    }


    @Override
    public int getItemCount() {
        return taskList.size();

    }
}
