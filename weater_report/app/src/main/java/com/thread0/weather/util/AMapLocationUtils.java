/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.util;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.thread0.weather.BuildConfig;

import top.xuqingquan.app.ScaffoldConfig;

/**
 * TODO:高德定位帮助类
 * 示例：
 * 开始定位：AMapLocationUtils.getInstance().startLocation()
 * 停止定位：AMapLocationUtils.getInstance().stopLocation()
 * 释放相关资源：AMapLocationUtils.getInstance().destroyLocation()
 * 监听获取返回结果：AMapLocationUtils.getInstance().liveData.observe(this, Observer {
 * })
 */
public class AMapLocationUtils {

    private static final String TAG = "AMapLocationUtils";
    private final MutableLiveData<AMapLocation> liveData = new MutableLiveData<>();

    private static AMapLocationUtils instance;

    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;

    private AMapLocation location = null;


    public static AMapLocationUtils getInstance() {
        if (instance == null) {
            instance = new AMapLocationUtils();
        }
        return instance;
    }

    private AMapLocationUtils() {
        if (null == locationOption) {
            locationOption = new AMapLocationClientOption();
        }
        initLocation();
    }


    /**
     * 定位监听
     */
    private AMapLocationListener locationListener = location -> {
        if (null != location) {
            Log.d(TAG, "定位返回: " + location.getErrorCode() + location.getErrorInfo());
            StringBuffer sb = new StringBuffer();
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.getErrorCode() == 0) {
                liveData.postValue(location);
                this.location = location;
                sb.append("定位成功" + "\n");
                sb.append("定位类型: " + location.getLocationType() + "\n");
                sb.append("经    度    : " + location.getLongitude() + "\n");
                sb.append("纬    度    : " + location.getLatitude() + "\n");
                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                sb.append("提供者    : " + location.getProvider() + "\n");

                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + location.getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : " + location.getSatellites() + "\n");
                sb.append("国    家    : " + location.getCountry() + "\n");
                sb.append("省            : " + location.getProvince() + "\n");
                sb.append("市            : " + location.getCity() + "\n");
                sb.append("城市编码 : " + location.getCityCode() + "\n");
                sb.append("区            : " + location.getDistrict() + "\n");
                sb.append("区域 码   : " + location.getAdCode() + "\n");
                sb.append("地    址    : " + location.getAddress() + "\n");
                sb.append("兴趣点    : " + location.getPoiName() + "\n");
                //定位完成的时间
                sb.append("定位时间: " + TimeUtils.INSTANCE.time2String(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
            } else {
                //定位失败
                sb.append("定位失败" + "\n");
                sb.append("错误码:" + location.getErrorCode() + "\n");
                sb.append("错误信息:" + location.getErrorInfo() + "\n");
                sb.append("错误描述:" + location.getLocationDetail() + "\n");
            }
            sb.append("***定位质量报告***").append("\n");
            sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
            sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
            sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
            sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
            sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
            sb.append("****************").append("\n");
            //定位之后的回调时间
            sb.append("定位时间: " + TimeUtils.INSTANCE.time2String(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "AMapLocationListener: " + sb.toString());
            }
        }
    };

    /**
     * @return 直接获取当前定位结果, 定位结果可能为空
     */
    @Nullable
    public AMapLocation getLocation() {
        return location;
    }

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(ScaffoldConfig.getApplication());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 使用默认监听
     *
     * @return 定位信息
     */
    public MutableLiveData<AMapLocation> getLiveData() {
        return liveData;
    }

    /**
     * @return 获取定位配置
     */
    public AMapLocationClientOption getLocationOption() {
        return locationOption;
    }

    /**
     * @param locationOption 重置定位配置
     */
    public void setLocationOption(AMapLocationClientOption locationOption) {
        this.locationOption = locationOption;
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        restartLocation();
    }

    public boolean isStarted() {
        return locationClient.isStarted();
    }

    /**
     * 自定义定位监听
     *
     * @param locationListener 监听类
     */
    public void setLocationListener(AMapLocationListener locationListener) {
        this.locationListener = locationListener;
        locationClient.setLocationListener(locationListener);
        startLocation();
    }

    /**
     * 开始定位
     *
     * @since 2.8.0
     */
    public void startLocation() {
        Log.d(TAG, "startLocation: ");
        // 启动定位
        if (!locationClient.isStarted()) {
            locationClient.startLocation();
            Log.d(TAG, "startLocation: " + locationClient.isStarted());
        }
    }

    /**
     * 重新开始定位
     *
     * @since 2.8.0
     */
    public void restartLocation() {
        Log.d(TAG, "restartLocation: ");
        // 启动定位
        if (locationClient.isStarted()) {
            stopLocation();
        }
        startLocation();
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     */
    public void stopLocation() {
        Log.d(TAG, "stopLocation: ");
        // 启动定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void destroyLocation() {
        Log.d(TAG, "destroyLocation: ");
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
        }
        locationClient = null;
        locationOption = null;
        instance = null;
    }

}
