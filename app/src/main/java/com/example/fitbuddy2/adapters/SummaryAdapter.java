package com.example.fitbuddy2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbuddy2.R;
import com.example.fitbuddy2.models.Summary;

import java.util.List;


public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {

    private final List<Summary> items;

    public SummaryAdapter(List<Summary> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_summary, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        Summary s = items.get(pos);
        holder.title.setText(s.title);
        holder.value.setText(s.value);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, value;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvSummaryTitle);
            value = itemView.findViewById(R.id.tvSummaryValue);
        }
    }
}