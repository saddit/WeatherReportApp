/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import com.thread0.weather.databinding.ActivityMainBinding
import top.xuqingquan.base.view.activity.SimpleActivity

/**
 *@ClassName: MainActivity
 *@Description: TODO: 1、进入界面首先获取已经保存的城市列表
 *                    2、未获取到列表需通过定位获取用户当前位置
 *                    3、获取用户定位前需判断用户是否授予定位权限
 *                    4、未授予定位权限需申请，申请后被拒绝需添加默认城市进入关注列表，并弹窗提示用户未未授予权限
 *                    5、获取到城市列表或刷新城市列表后，根据当前选择的城市展示天气数据
 *                    6、需要有按钮可以进入SearchActivity，添加城市进城市列表,添加完毕后需要刷新城市列表，并选择新添加的城市为当前选中的城市
 *                    7、通过左右滑动去切换当前选择城市，使用viewPager（viewPager2也可以）+Fragment（展示天气数据）实现
 *                    8、Fragment展示的天气数据：
 *                        8.1、当前气温
 *                        8.2、当前天气
 *                        8.3、月升月落时间
 *                        8.4、日升日落时间
 *                        8.5、展示未来7天气天气预报，7日天气预报尾部添加一个按钮进入FutureWeatherActivity界面
 *                    9、Fragment内一些必须的按钮：
 *                        9.1、进入AirQualityActivity的按钮,按钮文字——空气质量
 *                        9.2、进入YesterdayActivity的按钮，按钮文字——昨日天气
 *                        9.3、进入AlarmActivity的按钮，按钮文字——气象预警
 *                        9.4、进入ZodiacActivity的按钮，按钮文字——农历、节气与生肖
 *                        9.5、进入PortActivity的按钮，按钮文字——港口查询,判断当前fragment城市为港口城市则此按钮显示，不为港口城市则不显示
 *                        9.6、进入CarRestrictedCityActivity的按钮，按钮文字——机动车尾号限行
 *                        9.7、点击当前温度以及天气进入HoursActivity界面
 *                    10、需要有按钮点击进入HmsActivity
 *@Date: 2021/5/25 11:36 下午 Created
 */
class MainActivity : SimpleActivity() {
    // view binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}