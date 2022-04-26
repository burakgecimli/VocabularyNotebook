package com.bugcompany.dictionaryappactivity.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bugcompany.dictionaryappactivity.R
import com.bugcompany.dictionaryappactivity.database.DatabaseDao
import com.bugcompany.dictionaryappactivity.database.DatabaseHelper
import com.bugcompany.dictionaryappactivity.databinding.WordcardDesignBinding
import com.bugcompany.dictionaryappactivity.model.WordModel
import com.bugcompany.dictionaryappactivity.view.DetailActivity
import com.google.android.material.snackbar.Snackbar


class WordAdapter(private val mcontext: Context, private var wordArrayList: List<WordModel>, private val db: DatabaseHelper)
    : RecyclerView.Adapter<WordAdapter.WordCardDesign>() {

    inner class WordCardDesign(val binding: WordcardDesignBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordCardDesign {
        val layoutInflater = LayoutInflater.from(mcontext)
        val binding = WordcardDesignBinding.inflate(layoutInflater, parent, false)
        return WordCardDesign(binding)
    }

    override fun onBindViewHolder(holder: WordCardDesign, position: Int) {
        val word = wordArrayList[position]
        holder.binding.textViewEnglish.text = word.englishWord
        holder.binding.textViewTurkish.text = word.turkishWord

        holder.binding.imageViewAdd.setOnClickListener {

            val popUpMenu = PopupMenu(mcontext, holder.binding.imageViewAdd)
            popUpMenu.menuInflater.inflate(R.menu.pop_menu, popUpMenu.menu)

            popUpMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_delete -> {
                        Snackbar.make(holder.binding.imageViewAdd, "${word.englishWord} silinsin mi?", Snackbar.LENGTH_SHORT).setAction("EVET") {
                            DatabaseDao().wordDelete(db, word.wordId)
                            wordArrayList = DatabaseDao().getAllWord(db)
                            notifyDataSetChanged()
                        }.show()
                        true
                    }
                    R.id.action_update -> {
                        alertShow(word)
                        true
                    }
                    else -> false
                }
            }
            popUpMenu.show()
        }

        holder.binding.cardview.setOnClickListener {
            val intent= Intent(mcontext, DetailActivity::class.java)
            intent.putExtra("object",word)
            mcontext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return wordArrayList.size
    }

    fun alertShow(word: WordModel) {
        val design = LayoutInflater.from(mcontext).inflate(R.layout.alert_design, null)
        val editTextEnglish = design.findViewById<EditText>(R.id.editTextEnglish)
        val editTextTurkish = design.findViewById<EditText>(R.id.editTextTurkish)

        editTextEnglish.setText(word.englishWord)
        editTextTurkish.setText(word.turkishWord)

        val alertDialog = AlertDialog.Builder(mcontext)
        alertDialog.setTitle("Kelime Düzenle")
        alertDialog.setView(design)
        alertDialog.setPositiveButton("Düzenle") { dialogInterface, i ->
            val englishWord = editTextEnglish.text.toString().trim()
            val turkishWord = editTextTurkish.text.toString().trim()

            DatabaseDao().wordUpdate(db, word.wordId, englishWord, turkishWord)
            wordArrayList = DatabaseDao().getAllWord(db)
            notifyDataSetChanged()
        }
        alertDialog.setNegativeButton("İptal") { dialogInterface, i ->
        }
        alertDialog.create().show()
    }
}