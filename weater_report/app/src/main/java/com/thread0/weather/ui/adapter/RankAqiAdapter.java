package com.thread0.weather.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thread0.weather.data.model.AirQuality;
import com.thread0.weather.data.model.AirQualityRank;
import com.thread0.weather.databinding.RvItemCityRankBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RankAqiAdapter extends RecyclerView.Adapter<RankAqiAdapter.ViewHolder> {

    private final List<AirQualityRank> ranks;

    public RankAqiAdapter(List<AirQualityRank> ranks) {
        this.ranks = ranks;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RvItemCityRankBinding binding = RvItemCityRankBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        AirQualityRank rank = ranks.get(position);
        rank.setNo(String.valueOf(position));
        holder.binding.setRank(rank);
    }

    @Override
    public int getItemCount() {
        return ranks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RvItemCityRankBinding binding;

        public ViewHolder(RvItemCityRankBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
