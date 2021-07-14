package com.thread0.weather.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.thread0.weather.R;
import com.thread0.weather.data.constant.UIDataKt;
import com.thread0.weather.data.model.Weather;
import com.thread0.weather.databinding.RvItemHourlyWeatherBinding;

import org.jetbrains.annotations.NotNull;

import java.time.temporal.ChronoField;
import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {

    private final List<Weather> hourlyWeathers;

    public HourlyWeatherAdapter(List<Weather> hourlyWeathers) {
        this.hourlyWeathers = hourlyWeathers;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        RvItemHourlyWeatherBinding binding = RvItemHourlyWeatherBinding.inflate(inf, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather weather = hourlyWeathers.get(position);
        holder.binding.setWeather(weather);
        holder.binding.weatherIcon.setImageResource(UIDataKt.getSky(weather.getCode().toString()).getIcon());
    }

    @Override
    public int getItemCount() {
        return hourlyWeathers.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RvItemHourlyWeatherBinding binding;

        public ViewHolder(RvItemHourlyWeatherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
