package com.thread0.weather.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {

    public static String translate(String str) {
        StringBuilder sb = new StringBuilder();
        String tempPinyin = null;
        for (int i = 0; i < str.length(); ++i) {
            tempPinyin = getCharacterPinYin(str.charAt(i));
            if (tempPinyin == null) {
                // 如果str.charAt(i)非汉字，则保持原样
                sb.append(str.charAt(i));
            } else {
                sb.append(tempPinyin);
            }
        }
        return sb.toString();
    }

    public static String getCharacterPinYin(char c) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // WITH_V：用v表示ü  (nv)
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String[] pinyin = null;
        try {
            pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
        }catch(BadHanyuPinyinOutputFormatCombination e){
            e.printStackTrace();
        }
        // 如果c不是汉字
        if(pinyin == null) return null;
        // 只取一个发音
        return pinyin[0];
    }
}
