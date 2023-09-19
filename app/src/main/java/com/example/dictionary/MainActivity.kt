package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dictionary.api.ApiDictionaryClient
import com.example.dictionary.api.DictionaryEntry
import com.example.dictionary.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.text = "No definition yet"

        binding.button.setOnClickListener {
            val word = binding.editTextWord.text.toString()
            if (word.isNotEmpty()) {
                // coroutine for API call
                Log.d("WORD", word)
                GlobalScope.launch(Dispatchers.IO) {
                    val result = ApiDictionaryClient().makeApiCall(word)
                    Log.d("RESULT", result.toString())
                    // Switching to the main thread
                    withContext(Dispatchers.Main) {

                        updateUI(result)
                    }
                }
            }
        }

    }

    private fun updateUI(data: List<DictionaryEntry>) {
        // Update your UI components
        Log.d("DATAGUSNO", data.toString())
        if (data.isNotEmpty()) {
            Log.d("DATAGUS", data.toString())
            val firstEntry = data[0]
            val updatedText = "Word: ${firstEntry.word}\nDefinition: ${firstEntry.meanings[0].definitions[0].definition}"
            binding.textView.text = updatedText
        } else {
            binding.textView.text = "Oh No ... No definition found."
        }
    }
}

