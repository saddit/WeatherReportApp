package com.thread0.weather.ui.fragment

import android.annotation.SuppressLint
import android.opengl.Visibility
import com.thread0.weather.data.model.Weather
import com.thread0.weather.net.service.WeatherService
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thread0.weather.app.AppDatabase
import com.thread0.weather.data.constant.PRAM_LOCATION
import com.thread0.weather.data.constant.getSky
import com.thread0.weather.data.model.Location
import com.thread0.weather.ui.adapter.HourlyWeatherAdapter
import com.thread0.weather.data.model.WeatherResult
import com.thread0.weather.databinding.FragmentWeatherBinding
import com.thread0.weather.ui.activity.*
import com.thread0.weather.ui.adapter.DailyWeatherAdapter
import kotlinx.coroutines.*
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.extension.launch
import top.xuqingquan.utils.startActivity
import java.lang.IllegalStateException

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : SwipeRefreshLayout.OnRefreshListener, Fragment() {
    private var binding: FragmentWeatherBinding? = null
    private var weather: Weather? = null
    var location: Location? = null
    private var hourlyWeathers: List<Weather>? = null
    private var dailyWeathers: List<Weather>? = null



    private val weatherService: WeatherService = ScaffoldConfig.getRepositoryManager().obtainRetrofitService(
        WeatherService::class.java
    )

    private val portCityDao = AppDatabase.instance!!.getPortCityDao()


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
        initUI()
        launch(Dispatchers.IO,{
            setPortBtnVisibility()
            initHourly()
            initDaily()
        },{
            it.printStackTrace()
        })
        return binding!!.root
    }

    override fun onRefresh() {
        launch(Dispatchers.IO,{
            freshWeather()
        },{
            it.printStackTrace()
        })
    }

    private suspend fun freshWeather() {
        val result =
            weatherService.getLocationCurrentWeather(location = location!!.name)!!.results[0]
        withContext(Dispatchers.Main) {
            weather = result.now
            binding!!.weather = weather
            binding!!.swiperLayout.isRefreshing = false
            binding!!.notifyChange()
        }
    }

    private suspend fun setPortBtnVisibility() {
        val res = portCityDao.isPortCity(location!!.id)
        withContext(Dispatchers.Main) {
            binding!!.portBtn.visibility = if (res) View.VISIBLE else View.GONE
        }
    }

    private suspend fun initHourly() {
        hourlyWeathers =
            location?.let { weatherService.getHourlyWeather(location = it.name, start = 0, hours = 18)?.results?.get(0)?.multi }
        withContext(Dispatchers.Main) {
            binding!!.hourlyRv.adapter = HourlyWeatherAdapter(hourlyWeathers)
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun initDaily() {
        dailyWeathers = location?.let { weatherService.getDailyWeather(location = it.name, start = 1, days = 15)?.results?.get(0)?.multi }
        val l = dailyWeathers?.get(0)?.low
        val h = dailyWeathers?.get(0)?.high
        withContext(Dispatchers.Main) {
            binding!!.temperatureHighLow.text = "$h°/$l°"
            binding!!.rvDaily.adapter = DailyWeatherAdapter(dailyWeathers)
        }
    }

    private fun initUI() {
        binding!!.weather = weather
        binding!!.weatherLayout.setBackgroundResource(getSky(weather?.code.toString()).bg);
        binding!!.swiperLayout.setOnRefreshListener(this)
        binding!!.zodiacBtn.setOnClickListener {
            startActivity<ZodiacActivity>()
        }
        binding!!.carResrictBtn.setOnClickListener {
            startActivity<CarRestrictedCityActivity>()
        }
        binding!!.alarmBtn.setOnClickListener {
            startActivity<AlarmActivity>(
                PRAM_LOCATION to location?.id
            )
        }
        binding!!.airBtn.setOnClickListener {
            startActivity<AirQualityActivity>(
                PRAM_LOCATION to location?.id
            )
        }
        binding!!.yestodayWeatherBtn.setOnClickListener {
            startActivity<YesterdayActivity>(
                PRAM_LOCATION to location?.id
            )
        }
        binding!!.portBtn.setOnClickListener { 
            startActivity<PortActivity>(
                PRAM_LOCATION to location?.id
            )
        }
    }

    override fun toString(): String {
        return "WeatherFragment(location=$location)"
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