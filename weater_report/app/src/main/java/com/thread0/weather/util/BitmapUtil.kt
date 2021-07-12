/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import android.os.Build


class BitmapUtil {

    companion object{

        private fun createBitmapByColor(color: Int?, width: Int, height: Int): Bitmap? {
            val bitmap = Bitmap.createBitmap(
                width, height,
                Bitmap.Config.RGB_565
            )
            if (color != null) {
                bitmap.eraseColor(color)
            }
            return bitmap
        }

        fun changeBitmapSize(originBitmap : Bitmap, targetWidth : Int, targetHeight : Int): Bitmap {
            val width = originBitmap.width
            val height = originBitmap.height
            //计算压缩的比率
            val scaleWidth = targetWidth.toFloat() / width
            val scaleHeight = targetHeight.toFloat() / height

            //获取想要缩放的matrix
            val matrix = Matrix()
            matrix.postScale(scaleWidth, scaleHeight)

            return Bitmap.createBitmap(originBitmap, 0, 0, width, height, matrix, true)
        }

        fun combineBitmapByColor(context: Context,bgColor: Int, targetWidth: Int, targetHeight: Int, foreground: Bitmap): Bitmap? {
            val newMap : Bitmap
            try {
                newMap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.RGB_565)
            }catch (e : Throwable){
                e.printStackTrace()
                return null
            }
            val canvas = Canvas(newMap)
            val bgBitmap = createBitmapByColor(bgColor,targetWidth,targetHeight)
            if (bgBitmap != null){
                canvas.drawBitmap(bgBitmap, 0f, 0f, null)
                if (!bgBitmap.isRecycled){
                    bgBitmap.recycle()
                }
            }
            canvas.drawBitmap(
                foreground, 0f,
                0f, null
            )
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
                System.gc()
            }
            canvas.save()
            canvas.restore()
            return newMap
        }

        // 以 frontBitmap 大小为例
        fun mergeBitmap(backBitmap: Bitmap?, frontBitmap: Bitmap?): Bitmap? {
            if (backBitmap == null || backBitmap.isRecycled
                || frontBitmap == null || frontBitmap.isRecycled) {
                return null
            }
            // 人像大小的背景画布
            val bitmap = Bitmap.createBitmap(frontBitmap.width, frontBitmap.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            // 第一个 Rect 代表要绘制的 bitmap 区域
            // 第二个 Rect 代表的是要将 bitmap 绘制在屏幕的什么地方
            val baseRect = Rect(0, 0, frontBitmap.width, frontBitmap.height)
            val frontRect = Rect(0, 0, frontBitmap.width, frontBitmap.height)

            val bgBitmap = changeBitmapSize(backBitmap,frontBitmap.width,frontBitmap.height)
            canvas.drawBitmap(bgBitmap, frontRect, baseRect, null)
            canvas.drawBitmap(frontBitmap, frontRect, baseRect, null)
            return bitmap
        }
    }

}