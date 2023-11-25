package com.example.dictionary

import DefinitionAdapter
import MeaningCard
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.api.ApiDictionaryClient
import com.example.dictionary.api.DictionaryEntry
import com.example.dictionary.api.ItemSpacingDecoration
import com.example.dictionary.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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

        val spacingDp = 8
        val itemSpacingDecoration = ItemSpacingDecoration(this, spacingDp)
        recyclerView.addItemDecoration(itemSpacingDecoration)

        binding.button.setOnClickListener {
            binding.errorMsg.visibility = View.GONE
            binding.searchCta.visibility = View.GONE
            binding.ctaImage.visibility = View.GONE
            val word = binding.editTextWord.text.toString()
            Log.d("WORD", word)
            if (word.isNotEmpty()) {
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
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.editTextWord.windowToken, 0)
            binding.editTextWord.clearFocus()
        }

        binding.clearButton.setOnClickListener{
            binding.editTextWord.setText("")
            adapter.clearData()
            binding.searchCta.visibility = View.VISIBLE
            binding.ctaImage.visibility = View.VISIBLE
            binding.errorMsg.visibility = View.GONE
        }
    }

    private fun updateUI(data: List<DictionaryEntry>) {
        // Update your UI components
        Log.d("DATAGUSNO", data.toString())
        if (data.isNotEmpty()) {
            Log.d("DATAGUS", data.toString())
            var defs : MutableList<MeaningCard> = mutableListOf()
            var result = data[0]
            for (i in 0 .. result.meanings.size-1){
                    val currDef = data[0]
                    val def = MeaningCard(
                        currDef.word,
                        currDef.meanings[i].partOfSpeech,
                        currDef.meanings[i].definitions.map { it.definition }
                    )
                    defs.add(def)
            }
            adapter.setData(defs)
        } else {
            adapter.clearData()
            binding.errorMsg.visibility = View.VISIBLE
        }
    }
}

