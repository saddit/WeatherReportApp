package com.thread0.weather.util

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jxl.Sheet
import jxl.Workbook
import java.io.File
import java.io.InputStream

object ExcelUtils {
    fun getWorkBook(path: String): Workbook {
        val file = File(path)
        return Workbook.getWorkbook(file)
    }

    fun getWorkBook(inputStream: InputStream): Workbook {
        return Workbook.getWorkbook(inputStream);
    }

    fun <T> getListFromSheet(sheet: Sheet, ofType: Class<T>): List<T> {
        val gson = GsonBuilder().enableComplexMapKeySerialization().create()
        val rows = sheet.rows
        val resList = ArrayList<T>(rows-1)
        val titleRow = sheet.getRow(0)
        for (i in 1 until rows) {
            val row = sheet.getRow(i)
            val jsonObject = JsonObject()
            for (cell in row) {
                jsonObject.addProperty(titleRow[cell.column].contents, cell.contents)
            }
            val resObject = gson.fromJson(jsonObject.toString(), ofType)
            resList.add(resObject)
        }
        return resList
    }
}