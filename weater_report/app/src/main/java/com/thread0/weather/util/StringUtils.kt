/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.util

import android.util.Base64
import java.net.URLEncoder
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * StringUtils 字符串工具类
 */
object StringUtils {

    /**
     * getEncryptSign 获取加密的签名
     * @param timestamp 时间戳
     * @param key 公钥
     * @param ttl 失效时间
     * @return 验证签名的字符串
     */
    fun getEncryptSign(timestamp: String, key: String = "PV6Uj2FnKRzwKMxR0", ttl: String = "1800"): String{
        val splicing = "ts=$timestamp&ttl=$ttl&uid=$key"
        val type = "HmacSHA1"
        val secret = SecretKeySpec("S5cg0Hkjkt8tDfMGs".toByteArray(), type)
        val mac = Mac.getInstance(type)
        mac.init(secret)
        val digest = mac.doFinal(splicing.toByteArray())
        val base64 = Base64.encodeToString(digest, Base64.DEFAULT)
        val encryptSign = URLEncoder.encode(base64)
        return "ts=$timestamp&ttl=$ttl&uid=$key&sig=$encryptSign"
    }

}