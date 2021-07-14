package com.thread0.weather.ui.fragment

import android.annotation.SuppressLint
import com.thread0.weather.data.model.Weather
import com.thread0.weather.net.service.WeatherService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.data.constant.getSky
import com.thread0.weather.data.model.Location
import com.thread0.weather.ui.adapter.HourlyWeatherAdapter
import com.thread0.weather.data.model.WeatherResult
import com.thread0.weather.databinding.FragmentWeatherBinding
import kotlinx.coroutines.*
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.extension.launch
import kotlin.coroutines.coroutineContext

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {
    private var binding: FragmentWeatherBinding? = null
    private var weather: Weather? = null
    private var location: Location? = null
    private var hourlyWeathers: List<Weather>? = null
    private var dailyWeathers: List<Weather>? = null

    private val weatherService: WeatherService = ScaffoldConfig.getRepositoryManager().obtainRetrofitService(
        WeatherService::class.java
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            weather = requireArguments().getSerializable(ARG_W) as Weather?
            location = requireArguments().getSerializable(ARG_L) as Location?
        }
    }

    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        binding!!.weather = weather
        binding!!.weatherLayout.setBackgroundResource(getSky(weather?.code.toString()).bg);
        binding!!.hourlyRv.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        launch(Dispatchers.IO,{
            initHourly()
            initDaily()
        },{
            it.printStackTrace()
        })
        return binding!!.root
    }

    private suspend fun initHourly() {
        hourlyWeathers =
            location?.let { weatherService.getHourlyWeather(location = it.name, start = 0, hours = 18)?.results?.get(0)?.multi };
        withContext(Dispatchers.Main) {
            binding!!.hourlyRv.adapter = HourlyWeatherAdapter(hourlyWeathers)
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun initDaily() {
        dailyWeathers = location?.let { weatherService.getDailyWeather(location = it.name, start = 0, days = 14)?.results?.get(0)?.multi }
        val l = dailyWeathers?.get(0)?.low
        val h = dailyWeathers?.get(0)?.high
        withContext(Dispatchers.Main) {
            binding!!.temperatureHighLow.text = "$h°/$l°"
        }
    }

    companion object {
        var ARG_W = "arg_weather"
        var ARG_L = "arg_location"

        fun newInstance(weather: WeatherResult): WeatherFragment {
            val fragment = WeatherFragment()
            val args = Bundle()
            args.putSerializable(ARG_W, weather.now)
            args.putSerializable(ARG_L, weather.location)
            fragment.arguments = args
            return fragment
        }
    }
}