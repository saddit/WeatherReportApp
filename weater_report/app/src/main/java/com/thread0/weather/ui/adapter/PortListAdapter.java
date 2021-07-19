package com.thread0.weather.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thread0.weather.data.model.Port;
import com.thread0.weather.databinding.RvItemPortBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PortListAdapter extends RecyclerView.Adapter<PortListAdapter.ViewHolder> {

    private List<Port> ports;

    public PortListAdapter(List<Port> ports) {
        this.ports = ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RvItemPortBinding binding = RvItemPortBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Port port = ports.get(position);
        holder.binding.setPort(port);
    }

    @Override
    public int getItemCount() {
        return ports.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RvItemPortBinding binding;

        public ViewHolder(RvItemPortBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
