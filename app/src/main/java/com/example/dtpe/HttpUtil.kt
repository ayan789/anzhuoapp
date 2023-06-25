package com.example.dtpe

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL

object HttpUtil {
    fun sendGetRequest(urlString: String, callback: (String) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            val result = StringBuilder()

            try {
                val url = URL(urlString)
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.readTimeout = 10000
                connection.connectTimeout = 10000

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    reader = BufferedReader(InputStreamReader(connection.inputStream))
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        result.append(line)
                    }
                } else {
                    reader = BufferedReader(InputStreamReader(connection.errorStream))
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        result.append(line)
                    }
                }

                withContext(Dispatchers.Main) {
                    callback(result.toString())
                }
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                // 连接超时处理逻辑...
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()

                try {
                    reader?.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                    // 异常处理逻辑...
                }
            }
        }
    }
}

