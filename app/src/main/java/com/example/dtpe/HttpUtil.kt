package com.example.dtpe

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL

object HttpUtil {
    fun sendGetRequest(urlString: String): String {
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        val result = StringBuilder()

        try {
            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            // 设置连接超时时间为10秒
            connection.readTimeout = 10000
            // 设置读取超时时间为10秒
            connection.connectTimeout = 10000
            println(connection.responseCode)
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                reader = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    result.append(line)
                }
            } else {
                // 处理错误流
                reader = BufferedReader(InputStreamReader(connection.errorStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    result.append(line)
                }
            }
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            // 连接超时，打印连接超时信息
            println("连接超时")
        } catch (e: Exception) {
            e.printStackTrace()
            println(e)
        } finally {
            connection?.disconnect()
            try {
                reader?.close()
            } catch (e: Exception) {
                e.printStackTrace()
                println(e)
            }
        }

        return result.toString()
    }
}

