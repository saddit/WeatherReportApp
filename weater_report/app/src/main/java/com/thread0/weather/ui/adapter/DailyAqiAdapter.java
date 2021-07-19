package com.thread0.weather.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thread0.weather.data.model.AirQuality;
import com.thread0.weather.databinding.RvItemDailyAqiBinding;
import com.thread0.weather.databinding.RvItemHourlyAqiBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DailyAqiAdapter extends RecyclerView.Adapter<DailyAqiAdapter.ViewHolder> {
    private final List<AirQuality> qualities;

    public DailyAqiAdapter(List<AirQuality> qualities) {
        this.qualities = qualities;
    }

    @NotNull
    @Override
    public DailyAqiAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RvItemDailyAqiBinding binding = RvItemDailyAqiBinding.inflate(inflater, parent, false);
        return new DailyAqiAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DailyAqiAdapter.ViewHolder holder, int position) {
        holder.binding.setQuality(qualities.get(position));
    }

    @Override
    public int getItemCount() {
        return qualities.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RvItemDailyAqiBinding binding;

        public ViewHolder(RvItemDailyAqiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
