package com.bugcompany.dictionaryappactivity.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "words.sqlite", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE words (word_id INTEGER PRIMARY KEY AUTOINCREMENT,english_word TEXT,turkish_word TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS words")
        onCreate(db)
    }
}