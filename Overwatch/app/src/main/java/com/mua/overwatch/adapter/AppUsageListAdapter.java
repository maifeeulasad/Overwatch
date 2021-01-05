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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppUsageListAdapter extends RecyclerView.Adapter<AppUsageListAdapter.AppUsageListViewHolder> {
    private List<AppUsage> appUsageList = new ArrayList<>();
    private final Context context;

    public AppUsageListAdapter(Context context) {
        this.context = context;
    }

    protected class AppUsageListViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView duration;
        private final ImageView icon;

        AppUsageListViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_item_app_usage_name);
            duration = view.findViewById(R.id.tv_item_app_usage_duration);
            icon = view.findViewById(R.id.iv_item_app_usage_icon);
        }
    }

    @NotNull
    @Override
    public AppUsageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppUsageListViewHolder((LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.item_app_usage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull AppUsageListViewHolder holder, int position) {
        holder.name.setText(appUsageList.get(position).getAppName());
        long millis = appUsageList.get(position).getDuration();
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(millis)
        );
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millis)
        );
        String duration = (hours != 0L ? hours + " Hours" : "")
                + (minutes != 0L ? minutes + " Minutes" : "")
                + seconds + " Seconds";
        holder.duration.setText(duration);
        Drawable icon = new BitmapDrawable(context.getResources(),
                BitmapFactory
                        .decodeByteArray(appUsageList.get(position).getIcon(),
                                0,
                                appUsageList.get(position).getIcon().length)
        );
        holder.icon.setImageDrawable(icon);
    }

    @Override
    public int getItemCount() {
        return appUsageList.size();
    }

    public void setAppUsages(List<AppUsage> appUsageList) {
        this.appUsageList = appUsageList;
        notifyDataSetChanged();
    }
}