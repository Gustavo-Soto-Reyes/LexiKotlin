package com.example.dictionary.api;

import android.util.Log
import android.widget.TextView
import com.example.dictionary.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ApiDictionaryClient {
    private val client = OkHttpClient()
    private val baseUrl = "https://api.dictionaryapi.dev/"

    private val headers = Headers.Builder()
        .build()

    // Make the API call using a coroutine
    fun makeApiCall(word: String): List<DictionaryEntry> {
        val request = Request.Builder()
            .url("${baseUrl}api/v2/entries/en/$word")
            .headers(headers)
            .build()
        Log.d("URL", request.toString())

        return try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseData = response.body?.string()
                Log.d("NOTEMPTY", "not returning empty list")
                parseApiResponse(responseData)
            } else {
                // Handle non-successful response (e.g., API error)
                Log.d("EMPTY", "returning empty list")
                emptyList()
            }
        } catch (e: Exception) {
            // Handle exceptions (e.g., network error)
            Log.e("API_ERROR", "API call failed", e)
            emptyList()
        }
    }

    // Parse the API response

    private fun parseApiResponse(responseData: String?): List<DictionaryEntry> {
        if (responseData.isNullOrEmpty()) {
            return emptyList()
        }

        val gson = Gson()
        return gson.fromJson(responseData, object : TypeToken<List<DictionaryEntry>>() {}.type)
    }

}


