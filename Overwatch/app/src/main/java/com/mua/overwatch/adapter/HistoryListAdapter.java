package com.mua.overwatch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mua.overwatch.R;
import com.mua.overwatch.entity.DayUsage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.AppUsageListViewHolder> {
    private List<DayUsage> dayUsageList = new ArrayList<>();


    protected class AppUsageListViewHolder extends RecyclerView.ViewHolder {
        private final TextView date;

        AppUsageListViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.tv_item_day_usage_date);
        }
    }

    @NotNull
    @Override
    public AppUsageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppUsageListViewHolder((LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.item_package_usage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull AppUsageListViewHolder holder, int position) {
        holder.date.setText(dayUsageList.get(position).getDate().toGMTString());
    }

    @Override
    public int getItemCount() {
        return dayUsageList.size();
    }

    public void setAppUsages(List<DayUsage> dayUsageList) {
        this.dayUsageList = dayUsageList;
        notifyDataSetChanged();
    }
}