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

        binding.word.text = "No definition yet"

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
            val word = "${firstEntry.word}"
            val defintion = "${firstEntry.meanings[0].definitions[0].definition}"
            val pos = "${firstEntry.meanings[0].partOfSpeech}"
            binding.word.text = word
            binding.pos.text = pos
            binding.definition.text = defintion
        } else {
            binding.word.text = "Oh No ... No definition found."
        }
    }
}

