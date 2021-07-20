package com.thread0.weather.ui.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.thread0.weather.data.constant.UIDataKt;
import com.thread0.weather.data.model.Calendar;
import com.thread0.weather.databinding.RvItemZodiacBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import top.xuqingquan.utils.ListUtilsKt;

public class ZodiacRvAdapter extends RecyclerView.Adapter<ZodiacRvAdapter.ViewHolder> {

    private final List<Calendar> calendars;

    public ZodiacRvAdapter(List<Calendar> calendars) {
        this.calendars = calendars;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RvItemZodiacBinding binding = RvItemZodiacBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Calendar calendar = calendars.get(position);
        calendar.setGanzhi(calendar.getGanzhiYear()+" "+calendar.getGanzhiMonth()+ " "+ calendar.getGanzhiDay());
        calendar.setLunar("("+calendar.getLunarYear()+"."+calendar.getLunarMonth()+ "."+ calendar.getLunarDay()+")");
        calendar.setLunarName(calendar.getLunarMonthName() + calendar.getLunarDayName());
        holder.binding.zodiacPic.setImageResource(UIDataKt.getZodiacMipmap(calendar.getZodiac()));
        holder.binding.setCalendar(calendar);
    }

    @Override
    public int getItemCount() {
        return calendars.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RvItemZodiacBinding binding;
        ViewHolder(RvItemZodiacBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
