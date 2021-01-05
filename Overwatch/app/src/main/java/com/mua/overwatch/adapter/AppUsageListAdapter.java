package com.mua.overwatch.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mua.overwatch.R;
import com.mua.overwatch.entity.AppUsage;

import java.util.List;

public class AppUsageListAdapter extends RecyclerView.Adapter<AppUsageListAdapter.AppUsageListViewHolder> {
    private List<AppUsage> appUsageList;
    private Context context;

    public AppUsageListAdapter(Context context) {
        this.context = context;
    }

    protected class AppUsageListViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView duration;
        private ImageView icon;

        AppUsageListViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_item_app_usage_name);
            duration = view.findViewById(R.id.tv_item_app_usage_duration);
            icon = view.findViewById(R.id.iv_item_app_usage_icon);
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
            holder.name.setText(appUsageList.get(position).getAppName());
            holder.duration.setText(appUsageList.get(position).getSeconds().toString());
            Drawable icon = new BitmapDrawable(context.getResources(),
                    BitmapFactory
                            .decodeByteArray(appUsageList.get(position).getIcon(),
                                    0,
                                    appUsageList.get(position).getIcon().length)
            );
            holder.icon.setImageDrawable(icon);
        }
        catch(Exception ignored) {

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