package com.outsource.danding.qingdaoic.net

import android.annotation.SuppressLint
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*

class DataHandler {

    companion object {
        fun encryptParams(map: TreeMap<String, String>): Map<String, String> {
            val builder: StringBuilder = StringBuilder()
            map.forEach {
                builder.append(it.key)
                builder.append(it.value)
            }
            Log.i("test", builder.toString())
            //map.put("sign", string2MD5(builder.toString()))
            return map
        }

        //字符串转32位MD5
        fun string2MD5(text: String): String {
            var result = ""
            try {
                val md = MessageDigest.getInstance("MD5")
                md.update(text.toByteArray())
                val b = md.digest()
                var i: Int
                val buf = StringBuffer("")
                for (offset in b.indices) {
                    i = b[offset].toInt()
                    if (i < 0)
                        i += 256
                    if (i < 16)
                        buf.append("0")
                    buf.append(Integer.toHexString(i))
                }
                result = buf.toString()
            } catch (e: NoSuchAlgorithmException) {
                System.out.println(e)
            }
            return result
        }


        /*
  * 将时间转换为时间戳
  */
        @SuppressLint("SimpleDateFormat")
        fun dateToStamp(s: String): String {
            val res: String
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = simpleDateFormat.parse(s)
            val ts = date.getTime()
            res = ts.toString()
            return res
        }
    }


}