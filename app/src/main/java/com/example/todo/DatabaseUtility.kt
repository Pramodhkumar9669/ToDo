package com.example.todo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseUtility (context: Context,
                       factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME,
            factory, DATABASE_VERSION) {

    /**
     * Creates table in database
     * **/
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TODO_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                NAME
                + " TEXT," +
                PRIORITY
                + " TEXT,"+
                DESCRIPTION
                + " TEXT,"+
                STATUS
                + " TEXT,"+
                DATE
                + " TEXT)")
        db.execSQL(CREATE_TODO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    /**
     * Inserts a task in database
     * **/
    fun addTask(task: Task) {
        val values = ContentValues()
        values.put(NAME, task.name)
        values.put(DESCRIPTION, task.description)
        values.put(PRIORITY, task.priority)
        values.put(STATUS, task.status)
        values.put(DATE, task.date)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    /**
     * Updates a task in database
     * **/
    fun updateTask(task: Task): Cursor? {

        val db = this.readableDatabase
        return db.rawQuery("UPDATE $TABLE_NAME SET name="
                +task.name+", description="
                +task.description+", priority="
                +task.priority+", date="
                +task.date+", status="
                +task.status, null)
    }

    /**
     * Deletes a task in database
     * **/
    fun deleteTask(id : String) {
        val db = this.readableDatabase
        db.delete(TABLE_NAME, "_ID="+id, null)
    }

    /**
     * Retruns all tasks in database
     * **/
    fun getAllTask(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    /**
     * Retruns a task  by id in database
     * **/
    fun getTaskById(id:String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE _ID="+id, null)
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "tasks.db"
        val TABLE_NAME = "todo"
        val COLUMN_ID = "_id"
        val NAME = "name"
        val PRIORITY = "priority"
        val STATUS = "status"
        val DATE = "date"
        val DESCRIPTION = "description"

    }}