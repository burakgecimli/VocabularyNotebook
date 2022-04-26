package com.bugcompany.dictionaryappactivity.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bugcompany.dictionaryappactivity.R
import com.bugcompany.dictionaryappactivity.model.WordModel
import com.bugcompany.dictionaryappactivity.adapter.WordAdapter
import com.bugcompany.dictionaryappactivity.database.DatabaseDao
import com.bugcompany.dictionaryappactivity.database.DatabaseHelper
import com.bugcompany.dictionaryappactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var wordList: ArrayList<WordModel>
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            toolbar.title = "Sözlük Uygulaması"
            setSupportActionBar(toolbar)

            rv.setHasFixedSize(true)
            rv.layoutManager = LinearLayoutManager(this@MainActivity)
            getWord()

            fab.setOnClickListener {
                alertShow()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val item = menu?.findItem(R.id.action_search)
        val searchview = item?.actionView as SearchView
        searchview.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    fun alertShow() {
        val design = LayoutInflater.from(this).inflate(R.layout.alert_design, null)
        val editTextEnglish = design.findViewById<EditText>(R.id.editTextEnglish)
        val editTextTurkish = design.findViewById<EditText>(R.id.editTextTurkish)

        val word = AlertDialog.Builder(this)
        word.setTitle("Kelime Ekle")
        word.setView(design)
        word.setPositiveButton("Ekle") { dialogInterface, i ->
            val englishWord = editTextEnglish.text.toString().trim()
            val turkishWord = editTextTurkish.text.toString().trim()

            if (englishWord != "" && turkishWord != "") {
                DatabaseDao().wordInsert(dbHelper, englishWord, turkishWord)
                getWord()
            }

        }
        word.setNegativeButton("İptal") { dialogInterface, i ->
        }
        word.create().show()

    }

    override fun onQueryTextSubmit(query: String): Boolean {
        searchWord(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        searchWord(newText)
        return true
    }


    fun getWord() {
        dbHelper = DatabaseHelper(this@MainActivity)
        wordList = DatabaseDao().getAllWord(dbHelper)
        binding.rv.adapter = WordAdapter(this@MainActivity, wordList, dbHelper)
    }

    fun searchWord(searchWord: String) {
        dbHelper = DatabaseHelper(this@MainActivity)
        wordList = DatabaseDao().searchWord(dbHelper, searchWord)
        binding.rv.adapter = WordAdapter(this@MainActivity, wordList, dbHelper)
    }


}