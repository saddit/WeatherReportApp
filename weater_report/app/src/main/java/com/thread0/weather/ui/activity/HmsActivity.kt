/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.huawei.hms.mlsdk.MLAnalyzerFactory
import com.huawei.hms.mlsdk.common.MLFrame
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationScene
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting
import com.thread0.weather.R
import com.thread0.weather.data.constant.PICS_REQ_CODE
import com.thread0.weather.databinding.ActivityHmsBinding
import com.thread0.weather.ui.widget.CircleDot
import com.thread0.weather.util.BitmapUtil
import com.thread0.weather.util.UiUtils
import com.zyao89.view.zloading.ZLoadingTextView
import kotlinx.coroutines.Dispatchers
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
import java.io.ByteArrayOutputStream


/**
 * Hms界面
 *      人像分割模型包
 *      文档：https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides-V5/image-segmentation-0000001050040109-V5#ZH-CN_TOPIC_0000001050990259__section1658976113112
 */
class HmsActivity : SimpleActivity() {

    private var originBitmap: Bitmap? = null
    private var foregroundBitmap:Bitmap? = null
    private var analyzer: MLImageSegmentationAnalyzer? = null
    private var isGenerating: Boolean = false
    private var alertDialog: AlertDialog? = null
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
            originBitmap = BitmapUtil.changeBitmapSize(BitmapFactory.decodeStream(contentResolver.openInputStream(uri)),900,1080)
            generateForegroundBitmap()
            binding.photoImgIV.setImageBitmap(originBitmap)
        }
    }

    private fun generateForegroundBitmap() {
        if(analyzer == null || originBitmap == null) return
        val frame = MLFrame.fromBitmap(originBitmap)
        // 创建一个task，处理图像分割检测器返回的结果。
        isGenerating = true
        showLoadingDialog()
        analyzer!!.asyncAnalyseFrame(frame).addOnSuccessListener {
            // 检测成功处理。
            foregroundBitmap = it.foreground
            binding.photoImgIV.setImageBitmap(it.foreground)
        }.addOnFailureListener {
            Log.e(TAG, "analyse -> asyncAnalyseFrame: ", it)
        }.addOnCompleteListener {
            isGenerating = false
            dismissLoadingDialog()
        }
    }

    private suspend fun createAnalyzer() {
        val setting =
            MLImageSegmentationSetting.Factory() // 设置分割精细模式，true为精细分割模式，false为速度优先分割模式。
                .setExact(true) // 设置分割模式为人像分割。
                .setAnalyzerType(MLImageSegmentationSetting.BODY_SEG) // 设置返回结果种类。
                .setScene(MLImageSegmentationScene.FOREGROUND_ONLY) //只分割人像
                .create()
        analyzer = MLAnalyzerFactory.getInstance().getImageSegmentationAnalyzer(setting)
    }

    override fun onDestroy() {
        super.onDestroy()
        analyzer?.stop()
    }

    private fun combineForeAndColor(hexStr: String) {
        if (analyzer==null) return
        if(foregroundBitmap == null) {
            if(!isGenerating) {
                generateForegroundBitmap()
            }
            return
        }
        val resBitmap = BitmapUtil.combineBitmapByColor(
            Color.parseColor(hexStr), foregroundBitmap!!.width,
            foregroundBitmap!!.height, foregroundBitmap!!
        )
        binding.photoImgIV.setImageBitmap(resBitmap)
    }

    private fun showLoadingDialog() {
        alertDialog = AlertDialog.Builder(this).create()
        alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable())
        alertDialog!!.setCancelable(false)
        alertDialog!!.setOnKeyListener { _, keyCode, _ -> keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK }
        alertDialog!!.show()
        alertDialog!!.setContentView(R.layout.z_loading_dialog)
        val textView = alertDialog!!.findViewById<ZLoadingTextView>(R.id.z_text_view)
        textView?.visibility = View.VISIBLE
        textView?.setText("全力处理中..")
        alertDialog!!.setCanceledOnTouchOutside(false)
    }

    private fun dismissLoadingDialog() {
        if (null != alertDialog && alertDialog!!.isShowing) {
            alertDialog!!.dismiss()
        }
    }

    private fun setClickEvent() {

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.photoImgIV.setOnClickListener {
            if(UiUtils.isFastClick()) return@setOnClickListener
            val intent = Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, PICS_REQ_CODE)
        }

        binding.redCircleDot.setOnClickListener(object : CircleDot.OnClickListener {
            override fun onClick() {
                binding.blueCircleDot.isSelected = false
                binding.whiteCircleDot.isSelected = false
                if (UiUtils.isFastClick()) {
                    binding.redCircleDot.isSelected = false
                    return
                }
                combineForeAndColor("#ff0000")
            }
        })
        binding.blueCircleDot.setOnClickListener(object : CircleDot.OnClickListener {
            override fun onClick() {
                binding.redCircleDot.isSelected = false
                binding.whiteCircleDot.isSelected = false
                if (UiUtils.isFastClick()) {
                    binding.blueCircleDot.isSelected = false
                    return
                }
                combineForeAndColor("#007Aff")
            }
        })
        binding.whiteCircleDot.setOnClickListener(object : CircleDot.OnClickListener {
            override fun onClick() {
                binding.blueCircleDot.isSelected = false
                binding.redCircleDot.isSelected = false
                if (UiUtils.isFastClick()) {
                    binding.whiteCircleDot.isSelected = false
                    return
                }
                combineForeAndColor("#FFFFFF")
            }
        })
    }

}