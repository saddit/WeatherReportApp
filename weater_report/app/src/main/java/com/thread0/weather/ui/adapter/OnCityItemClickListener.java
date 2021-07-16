package com.thread0.weather.ui.adapter;

import android.view.View;

import com.thread0.weather.data.model.City;

public interface OnCityItemClickListener {
    void onCityItemClick(View v, City city, int position);
}
