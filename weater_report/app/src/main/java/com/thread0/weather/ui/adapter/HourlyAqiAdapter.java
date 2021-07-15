package com.thread0.weather.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thread0.weather.R;
import com.thread0.weather.data.constant.UIDataKt;
import com.thread0.weather.data.model.AirQuality;
import com.thread0.weather.databinding.RvItemHourlyAqiBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HourlyAqiAdapter extends RecyclerView.Adapter<HourlyAqiAdapter.ViewHolder>{
    private final List<AirQuality> qualities;

    public HourlyAqiAdapter(List<AirQuality> qualities) {
        this.qualities = qualities;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RvItemHourlyAqiBinding binding = RvItemHourlyAqiBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        AirQuality airQuality = qualities.get(position);
        holder.binding.setQuality(airQuality);
    }

    @Override
    public int getItemCount() {
        return qualities.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RvItemHourlyAqiBinding binding;

        public ViewHolder(RvItemHourlyAqiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
