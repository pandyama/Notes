package com.mp.notes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbManager {

    val dbName = "Notes"
    val dbTable = "NotesTable"
    val colID = "ID"
    val colName = "Note Name"
    val colDesc = "Note Description"
    val dbVersion = 1
    val sqlCreateTable = "CREATE TABLE IF NOT EXIST $dbTable ($colID INTEGER PRIMARY KEY," +
            "$colName TEXT, $colDesc TEXT);"

    val sqlDB:SQLiteDatabase?=null

    constructor(){

    }

    inner class DatabaseHelperNotes: SQLiteOpenHelper {
        //SQLiteOpenHelper is responsible for creating db and making connection

        constructor(context: Context):super(context,dbName,null,dbVersion){

        }
        override fun onCreate(db: SQLiteDatabase?) {
            TODO("Not yet implemented")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            TODO("Not yet implemented")
        }

    }
}