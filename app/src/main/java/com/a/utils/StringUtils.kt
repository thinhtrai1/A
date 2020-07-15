package com.example.baseproject.utils

import com.a.utils.Constants
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat

object StringUtils {
    fun getParamsRequest(params: Map<String, String?>): String {
        val builder = StringBuilder()
        if (params.isNotEmpty()) {
            for (key in params.keys) {
                if (builder.isNotEmpty()) {
                    builder.append("&")
                }
                builder.append(key).append("=")
                try {
                    builder.append(URLEncoder.encode(params[key], Constants.CHARSET))
                } catch (e: UnsupportedEncodingException) {
                    builder.append(params[key])
                }

            }
        }
        return builder.toString()
    }

    fun toMd5(s: String): String {
        try {
            val digest = java.security.MessageDigest.getInstance("MD5")
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    fun convertDateFormatApi(date: String?): String? {
        if (date == null) return null
        return try {
            SimpleDateFormat("dd/MM/yyyy").format(
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date)!!
            )
        } catch (e: ParseException) {
            date
        }
    }
}