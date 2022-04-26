package com.bugcompany.dictionaryappactivity.database

import android.content.ContentValues
import com.bugcompany.dictionaryappactivity.model.WordModel

class DatabaseDao {

    fun wordDelete(databaseHelper: DatabaseHelper, wordId:Int){
        val db=databaseHelper.writableDatabase
        db.delete("words","word_id=?", arrayOf(wordId.toString()))
        db.close()
    }

    fun wordInsert(databaseHelper: DatabaseHelper, englishWord:String, turkishWord:String){
        val db=databaseHelper.writableDatabase
        val values=ContentValues()
        values.put("english_word",englishWord)
        values.put("turkish_word",turkishWord)
        db.insertOrThrow("words",null,values)
        db.close()
    }



    fun wordUpdate(databaseHelper: DatabaseHelper, wordId:Int, englishWord:String, turkishWord:String){
        val db=databaseHelper.writableDatabase
        val values=ContentValues()
        values.put("english_word",englishWord)
        values.put("turkish_word",turkishWord)
        db.update("words",values,"word_id=?", arrayOf(wordId.toString()))
        db.close()
    }


    fun getAllWord(databaseHelper: DatabaseHelper):ArrayList<WordModel>{
        val db=databaseHelper.writableDatabase
        val wordList=ArrayList<WordModel>()
        val cursor=db.rawQuery("SELECT *FROM words",null)

        val idIx = cursor.getColumnIndex("word_id")
        val englishIx = cursor.getColumnIndex("english_word")
        val turkishIx = cursor.getColumnIndex("turkish_word")

        while (cursor.moveToNext()){
            val word= WordModel(
                cursor.getInt(idIx),
                cursor.getString(englishIx),
                cursor.getString(turkishIx)
            )
            wordList.add(word)
        }
        return wordList
    }

    fun searchWord(databaseHelper: DatabaseHelper, searchedWord:String): ArrayList<WordModel> {
        val wordList = ArrayList<WordModel>()
        val db = databaseHelper.writableDatabase
        val cursor = db.rawQuery("SELECT *FROM words WHERE english_word LIKE '%$searchedWord%' ", null)

        val idIx = cursor.getColumnIndex("word_id")
        val englishIx = cursor.getColumnIndex("english_word")
        val turkishIx = cursor.getColumnIndex("turkish_word")

        while (cursor.moveToNext()) {
            val word = WordModel(
                cursor.getInt(idIx),
                cursor.getString(englishIx),
                cursor.getString(turkishIx)
            )
            wordList.add(word)
        }
        return wordList

    }
}