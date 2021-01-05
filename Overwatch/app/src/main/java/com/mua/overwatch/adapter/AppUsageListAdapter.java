package com.mua.overwatch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mua.overwatch.R;
import com.mua.overwatch.entity.AppUsage;

import java.util.List;

public class AppUsageListAdapter extends RecyclerView.Adapter<AppUsageListAdapter.AppUsageListViewHolder> {
    private List<AppUsage> appUsageList;

    protected class AppUsageListViewHolder extends RecyclerView.ViewHolder {
        private TextView rowAppUsageTxt0, rowAppUsageTxt1;

        AppUsageListViewHolder(View view) {
            super(view);
            rowAppUsageTxt0 = view.findViewById(R.id.tv_item_app_usage_name);
            rowAppUsageTxt1 = view.findViewById(R.id.tv_item_app_usage_duration);
        }
    }

    @Override
    public AppUsageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppUsageListViewHolder((LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.item_app_usage, parent, false));
    }

    @Override
    public void onBindViewHolder(AppUsageListViewHolder holder, int position) {
        try {
            holder.rowAppUsageTxt0.setText("Name: " + appUsageList.get(position).getApp_name());
            holder.rowAppUsageTxt1.setText("Address: " + appUsageList.get(position).getSeconds());
        }
        catch(NullPointerException n) {

        }
    }

    @Override
    public int getItemCount() {
        try {
            return appUsageList.size();
        }
        catch(NullPointerException n) {
            return 0;
        }
    }

    public void setAppUsages(List<AppUsage> appUsageList) {
        this.appUsageList = appUsageList;
        notifyDataSetChanged();
    }
}