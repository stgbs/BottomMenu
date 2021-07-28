package com.anroid.bottommenu

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*

@Suppress("DEPRECATION")
class DBHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) :
    SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(
                "CREATE TABLE MEMBER(EMAIL TEST," +
                        "ID TEXT, PASSWORD TEXT, PASSWORD_CK TEXT);"
            )

            // (임시)contents table > Home 화면과 연동
            db!!.execSQL("CREATE TABLE CONTENT(image BLOB," + "title CHAR(20));")
            db!!.execSQL("CREATE TABLE (image BLOB," + "title CHAR(20));")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insert(
        email: String, id: String, password: String, password_ck: String
    ) {
        var db: SQLiteDatabase = writableDatabase

        db.execSQL(
            "INSERT INTO MEMBER VALUES('" + email + "'" + ", '" + id + "'" + ", '" + password + "'" + ", '" + password_ck + "');"

        )
        db.close()
    }

    fun update(
        name: String, password: String, password_ck: String, phone: String, email: String,
        address: String, level: String
    ) {
        var db: SQLiteDatabase = writableDatabase

        db.execSQL(
            "UPDATE MEMBER SET PASSWORD = " + "'" + password + "'" + ", PASSWORD_OK = '" + password_ck + "'"
                    + ", EMAIL = '" + email + "'" +
                    "WHERE NAME = '" + name + "';"
        )

        db.close()
    }

    fun getResult(): String {
        var db: SQLiteDatabase = readableDatabase
        var result: String = ""

        var cursor: Cursor = db.rawQuery("SELECT * FROM MEMBER", null)
        while (cursor.moveToNext()) {
            result += (cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " : "
                    + cursor.getString(2)
                    + " : "
                    + cursor.getString(3)
                    + " : "
                    + cursor.getString(4)
                    + "\n")

        }

        return result
    }

    fun getResult1(ID: String, PASSWORD: String): Boolean {
        var db: SQLiteDatabase = readableDatabase
        var result: String = ""

        var cursor: Cursor = db.rawQuery("SELECT ID, PASSWORD FROM MEMBER", null)
        while (cursor.moveToNext()) {
            result = (cursor.getString(0))
            if (result.equals(ID)) {
                if (cursor.getString(1).equals(PASSWORD)) {
                    return true
                    break
                } else {
                    return false
                }
            }else {

            }
        }

        return false
    }

    // cartegory나 분류 성격에 따라 select 하도록 변경
    fun selectAll(): ArrayList<Content> {
        var db: SQLiteDatabase = readableDatabase
        val contentList: ArrayList<Content> = ArrayList<Content>()
        try {
            val cursor: Cursor = db!!.rawQuery("SELECT * FROM" + " CONTENT", null)
            while (cursor.moveToNext()) {
                val image = R.drawable.image
                val title = cursor.getString(1)
                val content = Content(image, title)
                contentList.add(content)
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        return contentList
    }

    fun Rank(): ArrayList<Movie> {
        var db: SQLiteDatabase = readableDatabase
        val movieList: ArrayList<Movie> = ArrayList<Movie>()
        try {
            val cursor: Cursor = db!!.rawQuery("SELECT * FROM" + " CONTENT", null)
            var rank = 0
            while (cursor.moveToNext()) {
                rank = rank + 1
                val image = R.drawable.image
                val title = cursor.getString(1)
                val description = ""
                val content = Movie(rank, image, title, description)
                movieList.add(content)
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }

        return movieList
    }

}