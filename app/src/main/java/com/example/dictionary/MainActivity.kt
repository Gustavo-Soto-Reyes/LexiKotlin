package com.example.dictionary

import Definition
import DefinitionAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.api.ApiDictionaryClient
import com.example.dictionary.api.DictionaryEntry
import com.example.dictionary.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.Dictionary

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DefinitionAdapter
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the coroutine scope when the activity is destroyed to avoid leaks
        coroutineScope.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = DefinitionAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        binding.button.setOnClickListener {
            val word = binding.editTextWord.text.toString()
            Log.d("WORD", word)
            if (word.isNotEmpty()) {
                // coroutine for API call
                Log.d("WORD", word)

                // Cancel any ongoing API calls before making a new one
                coroutineScope.coroutineContext.cancelChildren()

                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val result = ApiDictionaryClient().makeApiCall(word)
                        Log.d("RESULT", result.toString())
                        // Switching to the main thread
                        withContext(Dispatchers.Main) {
                            updateUI(result)
                        }
                    } catch (e: Exception) {
                        Log.e("API_ERROR", "API call failed", e)
                    } finally {
                        coroutineScope.cancel() // Cancel the coroutine scope when done
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

            var defs : MutableList<Definition> = mutableListOf()
            if (data[0].meanings.size > 1){
                for (i in 0..1) {
                    val currDef = data[0]
                    val def = Definition(
                        currDef.word,
                        currDef.meanings[i].partOfSpeech,
                        currDef.meanings[i].definitions[0].definition
                    )
                    defs.add(def)
                }
            }

            adapter.clearData()
            adapter.setData(defs)


        } else {
        }
    }
}

