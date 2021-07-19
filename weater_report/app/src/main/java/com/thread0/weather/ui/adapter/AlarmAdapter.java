package com.thread0.weather.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thread0.weather.data.constant.UIDataKt;
import com.thread0.weather.data.model.Alarms;
import com.thread0.weather.databinding.ListItemLayoutBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.MyViewHolder> {

    private List<Alarms> mBeanList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public AlarmAdapter(List<Alarms> mBeanList){
        this.mBeanList=mBeanList;

    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        this.mContext=parent.getContext();
        mLayoutInflater=LayoutInflater.from(mContext);
        ListItemLayoutBinding binding = ListItemLayoutBinding.inflate(mLayoutInflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Alarms alarms = mBeanList.get(position);
        holder.binding.tvTitle.setText(alarms.getTitle());
        holder.binding.tvContent.setText(alarms.getDescription());
        holder.binding.tvImg.setImageResource(UIDataKt.getAlarm(alarms.getType(),alarms.getLevel()));
        holder.binding.tvTime.setText(alarms.getPub_date());
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ListItemLayoutBinding binding;

        public MyViewHolder(ListItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
