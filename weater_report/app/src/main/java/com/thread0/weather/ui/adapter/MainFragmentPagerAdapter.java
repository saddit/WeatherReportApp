package com.thread0.weather.ui.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Transformations;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.thread0.weather.data.model.Location;
import com.thread0.weather.data.model.Weather;
import com.thread0.weather.ui.fragment.WeatherFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class MainFragmentPagerAdapter extends FragmentStateAdapter {

    private final LinkedList<Fragment> fragments;

    public MainFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = new LinkedList<>(fragments);
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
        notifyItemInserted(fragments.size()-1);
        Log.i("sjh_fg_adapter", "add fragment " + fragment.toString());
    }

    public void removeFragment(int position) {
        fragments.remove(position);
        notifyItemRemoved(position);
    }

    public void addFragmentRange(Collection<Fragment> data) {
        int oldPos = fragments.size()-1;
        fragments.addAll(data);
        notifyItemRangeInserted(oldPos, fragments.size());
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
