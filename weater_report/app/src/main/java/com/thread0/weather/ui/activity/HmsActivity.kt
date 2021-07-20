/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.huawei.hms.mlsdk.MLAnalyzerFactory
import com.huawei.hms.mlsdk.common.MLFrame
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationScene
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting
import com.thread0.weather.data.constant.PICS_REQ_CODE
import com.thread0.weather.databinding.ActivityHmsBinding
import com.thread0.weather.ui.widget.CircleDot
import com.thread0.weather.util.BitmapUtil
import kotlinx.coroutines.Dispatchers
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
import top.xuqingquan.utils.startActivityForResult
import kotlin.math.log


/**
 * Hms界面
 *      人像分割模型包
 *      文档：https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides-V5/image-segmentation-0000001050040109-V5#ZH-CN_TOPIC_0000001050990259__section1658976113112
 */
class HmsActivity : SimpleActivity() {

    private var originBitmap: Bitmap? = null
    private var analyzer: MLImageSegmentationAnalyzer? = null
    // view binding
    private lateinit var binding: ActivityHmsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHmsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        launch(Dispatchers.IO,{
            createAnalyzer()
        },{
            Log.e("sjh_hms_ml",it.stackTraceToString())
        })
        // 设置点击事件
        setClickEvent()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICS_REQ_CODE) {
            val uri = data?.data ?: return
            originBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
            binding.photoImgIV.setImageBitmap(originBitmap)
        }
    }

    private suspend fun createAnalyzer() {
        val setting =
            MLImageSegmentationSetting.Factory() // 设置分割精细模式，true为精细分割模式，false为速度优先分割模式。
                .setExact(true) // 设置分割模式为人像分割。
                .setAnalyzerType(MLImageSegmentationSetting.BODY_SEG) // 设置返回结果种类。
                // MLImageSegmentationScene.FOREGROUND_ONLY: 只返回背景透明的人像图和被分割的原图。
                .setScene(MLImageSegmentationScene.FOREGROUND_ONLY)
                .create()
        analyzer = MLAnalyzerFactory.getInstance().getImageSegmentationAnalyzer(setting)
    }

    override fun onDestroy() {
        super.onDestroy()
        analyzer?.stop()
    }

    private fun setClickEvent() {

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.photoImgIV.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, PICS_REQ_CODE)
        }

        binding.redCircleDot.setOnClickListener(object : CircleDot.OnClickListener {
            override fun onClick() {
                binding.blueCircleDot.isSelected = false;
                binding.whiteCircleDot.isSelected=false;
                if(originBitmap == null) return
                val frame = MLFrame.fromBitmap(originBitmap)
                // 创建一个task，处理图像分割检测器返回的结果。
                analyzer?.asyncAnalyseFrame(frame)?.addOnSuccessListener {
                    // 检测成功处理。
                    val foreground = it.foreground
                    val resBitmap = BitmapUtil.combineBitmapByColor(
                        Color.parseColor("#ff0000"), foreground.width,
                        foreground.height, foreground
                    )
                    binding.photoImgIV.setImageBitmap(resBitmap);
                }?.addOnFailureListener {
                    // 检测失败处理。
                    Log.e(TAG, "analyse -> asyncAnalyseFrame: ", it)
                }
            }
        })
        binding.blueCircleDot.setOnClickListener(object : CircleDot.OnClickListener {
            override fun onClick() {
                binding.redCircleDot.isSelected = false;
                binding.whiteCircleDot.isSelected = false;
                if(originBitmap == null) return
                val frame = MLFrame.fromBitmap(originBitmap)
                // 创建一个task，处理图像分割检测器返回的结果。
                analyzer?.asyncAnalyseFrame(frame)?.addOnSuccessListener {
                        // 检测成功处理。
                        val foreground = it.foreground
                        val resBitmap = BitmapUtil.combineBitmapByColor(
                            Color.parseColor("#007Aff"), foreground.width,
                            foreground.height, foreground
                        )
                        binding.photoImgIV.setImageBitmap(resBitmap);
                    }?.addOnFailureListener {
                        // 检测失败处理。
                        Log.e(TAG, "analyse -> asyncAnalyseFrame: ", it)
                    }
            }
        })
        binding.whiteCircleDot.setOnClickListener(object : CircleDot.OnClickListener {
            override fun onClick() {
                binding.blueCircleDot.isSelected = false;
                binding.redCircleDot.isSelected=false;
                if(originBitmap == null) return
                val frame = MLFrame.fromBitmap(originBitmap)
                // 创建一个task，处理图像分割检测器返回的结果。
                analyzer?.asyncAnalyseFrame(frame)?.addOnSuccessListener {
                        // 检测成功处理。
                        val foreground = it.foreground
                        val resBitmap = BitmapUtil.combineBitmapByColor(
                            Color.parseColor("#FFFFFF"), foreground.width,
                            foreground.height, foreground
                        )
                        binding.photoImgIV.setImageBitmap(resBitmap);
                    }?.addOnFailureListener {
                        // 检测失败处理。
                        Log.e(TAG, "analyse -> asyncAnalyseFrame: ", it)
                    }
            }
        })
    }

}