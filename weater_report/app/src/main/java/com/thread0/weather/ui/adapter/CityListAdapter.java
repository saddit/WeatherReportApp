package com.thread0.weather.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.thread0.weather.R;
import com.thread0.weather.data.model.City;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private List<City> cities;
    private OnCityItemClickListener onClickCityListener;

    public CityListAdapter(List<City> cities) {
        this.cities = cities;
    }

    public void setData(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public void setOnClickCityListener(OnCityItemClickListener listener) {
        this.onClickCityListener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_city, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        City city = cities.get(position);
        holder.name.setText(city.getName());
        if (onClickCityListener != null) {
            holder.itemView.setOnClickListener(v -> onClickCityListener.onCityItemClick(v,city,position));
        }
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView name;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.rv_city_item);
        }
    }
}
