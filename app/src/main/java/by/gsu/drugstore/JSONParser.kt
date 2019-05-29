/*
package by.gsu.drugstore

import android.util.Log
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.utils.URLEncodedUtils
import org.apache.http.impl.client.DefaultHttpClient
import org.json.JSONException
import org.json.JSONObject

import java.io.*
import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine



class JSONParser {

    var `is`: InputStream? = null
    var jObj: JSONObject? = null
    var json = ""

    // constructor
    fun JSONParser() {

    }

    // метод получение json объекта по url
    // используя HTTP запрос и методы POST или GET
    fun makeHttpRequest(url: String, method: String, params: List<NameValuePair>): JSONObject? {
        var url = url

        // Создаем HTTP запрос
        try {

            // проверяем метод HTTP запроса
            if (method === "POST") {
                val httpClient = DefaultHttpClient()
                val httpPost = HttpPost(url)
                httpPost.setEntity(UrlEncodedFormEntity(params))

                val httpResponse = httpClient.execute(httpPost)
                val httpEntity = httpResponse.getEntity()
                `is` = httpEntity.getContent()

            } else if (method === "GET") {
                val httpClient = DefaultHttpClient()
                val paramString = URLEncodedUtils.format(params, "utf-8")
                url += "?$paramString"
                val httpGet = HttpGet(url)

                val httpResponse = httpClient.execute(httpGet)
                val httpEntity = httpResponse.getEntity()
                `is` = httpEntity.getContent()
            }

        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: ClientProtocolException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            val reader = BufferedReader(InputStreamReader(`is`, "iso-8859-1"), 8)
            val sb = StringBuilder()
            var line: String? = null
            while ((line = reader.readLine()) != null) {
                sb.append(line!! + "\n")
            }
            `is`!!.close()
            json = sb.toString()
        } catch (e: Exception) {
            Log.e("Buffer Error", "Error converting result $e")
        }

        // пытаемся распарсить строку в JSON объект
        try {
            jObj = JSONObject(json)
        } catch (e: JSONException) {
            Log.e("JSON Parser", "Error parsing data $e")
        }

        // возвращаем JSON строку
        return jObj

    }

}*/
