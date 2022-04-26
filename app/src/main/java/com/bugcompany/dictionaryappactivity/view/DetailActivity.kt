package com.bugcompany.dictionaryappactivity.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bugcompany.dictionaryappactivity.databinding.ActivityDetailBinding
import com.bugcompany.dictionaryappactivity.model.WordModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val word = intent.getSerializableExtra("object") as WordModel

        binding.apply {
            textViewEnglish.text = word.englishWord
            textViewTurkish.text = word.turkishWord
        }
    }
}