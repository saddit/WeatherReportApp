package com.thread0.weather.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thread0.weather.data.constant.UIDataKt;
import com.thread0.weather.data.model.Weather;
import com.thread0.weather.databinding.RvItemDailyForecastBinding;
import com.thread0.weather.databinding.RvItemHourlyWeatherBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {

    private final List<Weather> dailyWeathers;
    public DailyWeatherAdapter(List<Weather> weathers) {
        this.dailyWeathers = weathers;
    }

    @NotNull
    @Override
    public DailyWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        RvItemDailyForecastBinding binding = RvItemDailyForecastBinding.inflate(inf, parent, false);
        return new DailyWeatherAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DailyWeatherAdapter.ViewHolder holder, int position) {
        Weather weather = dailyWeathers.get(position);
        holder.binding.setWeather(weather);
    }

    @Override
    public int getItemCount() {
        return dailyWeathers.size();
    }
    static class ViewHolder  extends RecyclerView.ViewHolder {
        RvItemDailyForecastBinding binding;

        public ViewHolder(RvItemDailyForecastBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
