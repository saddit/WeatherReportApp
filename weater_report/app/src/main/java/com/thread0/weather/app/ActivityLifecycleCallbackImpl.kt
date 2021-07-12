/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.thread0.weather.R
import top.xuqingquan.utils.StatusBarUtils

private const val TAG="ActivityLifecycle"

class ActivityLifecycleCallbackImpl : Application.ActivityLifecycleCallbacks{

    private var activityList: ArrayList<Activity> = ArrayList()

    override fun onActivityPaused(activity: Activity) {
        Log.d(TAG,"onActivityPaused----${activity.javaClass.name}")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d(TAG,"onActivityResumed----${activity.javaClass.name}")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d(TAG,"onActivityStarted----${activity.javaClass.name}")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d(TAG,"onActivityDestroyed----${activity.javaClass.name}")
        activityList.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.d(TAG,"onActivitySaveInstanceState----${activity.javaClass.name}")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.d(TAG,"onActivityStopped----${activity.javaClass.name}")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.d(TAG,"onActivityCreated----${activity.javaClass.name}")
        activityList.add(activity)
        StatusBarUtils.setStatusBarBackgroundColor(activity, activity.resources.getColor(R.color.blue_00DEFF))
    }

}