package com.example.fitbuddy2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbuddy2.R;
import com.example.fitbuddy2.models.WorkoutItem;

import java.util.List;

public class WorkoutItemAdapter extends RecyclerView.Adapter<WorkoutItemAdapter.ViewHolder> {

    public interface OnWorkoutClickListener {
        void onWorkoutClick(String workoutName);
    }

    private final List<WorkoutItem> workoutList;
    private final OnWorkoutClickListener listener;

    public WorkoutItemAdapter(List<WorkoutItem> workoutList, OnWorkoutClickListener listener) {
        this.workoutList = workoutList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WorkoutItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutItemAdapter.ViewHolder holder, int position) {
        WorkoutItem item = workoutList.get(position);
        holder.tvWorkoutName.setText(item.name);
        holder.tvWorkoutDate.setText(item.date);
        holder.tvWorkoutDuration.setText(item.duration);
        holder.imgWorkout.setImageResource(item.iconRes);
        holder.imgWorkout.setColorFilter(item.tintColor);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onWorkoutClick(item.name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWorkoutName, tvWorkoutDate, tvWorkoutDuration;
        ImageView imgWorkout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWorkoutName = itemView.findViewById(R.id.tvWorkoutName);
            tvWorkoutDate = itemView.findViewById(R.id.tvWorkoutDate);
            tvWorkoutDuration = itemView.findViewById(R.id.tvWorkoutDuration);
            imgWorkout = itemView.findViewById(R.id.imgWorkout);
        }
    }
}
