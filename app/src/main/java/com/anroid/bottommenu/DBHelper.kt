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
                        "NAME TEXT, PASSWORD TEXT, PASSWORD_CK TEXT);"
            )

            db!!.execSQL("CREATE TABLE REVIEW(alias INTEGER," + " title TEXT," + " image BLOB," + "category TEXT," + " review TEXT," + " description TEXT," + " rating REAL," + "emotion TEXT," + " recommend TEXT," + " PRIMARY KEY('alias' AUTOINCREMENT));")
            db!!.execSQL("CREATE TABLE CONTENT(title TEXT, " + "image BLOB, " + "category TEXT," + "description TEXT, " + "date TEXT, " + "reviewNum INTEGER, " + "rating REAL);")
            db!!.execSQL("CREATE TABLE WIKI(title TEXT, " + "content_1 TEXT, " + "content_2 TEXT, " + "content_3 TEXT, " + "content_4 TEXT);")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insert(
        email: String, name: String, password: String, password_ck: String
    ) {
        var db: SQLiteDatabase = writableDatabase

        db.execSQL(
            "INSERT INTO MEMBER VALUES('" + email + "'" + ", '" + name + "'" + ", '" + password + "'" + ", '" + password_ck + "');"

        )
        db.close()
    }

    fun update(
        name: String, password: String, password_ck: String, email: String
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

    fun NEW_Select(category: String): ArrayList<Content> {
        var db: SQLiteDatabase = readableDatabase
        val contentList: ArrayList<Content> = ArrayList<Content>()
        try {
            val cursor: Cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = '$category' ORDER BY date DESC", null)
            var count = 0
            while (cursor.moveToNext() && count <= 10) {
                count += 1
                val image = cursor.getBlob(1)
                val title = cursor.getString(0)
                val content = Content(image, title)
                contentList.add(content)
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return contentList
    }

    /* REVIEW */
    fun REVIEW_Select(): ArrayList<Review> {
        var db: SQLiteDatabase = readableDatabase
        val reviewList: ArrayList<Review> = ArrayList<Review>()
        try {
            val cursor: Cursor = db!!.rawQuery("SELECT * FROM REVIEW;", null)
            while (cursor.moveToNext()) {
                val alias = cursor.getInt(cursor.getColumnIndex("alias"))
                val title = cursor.getString(cursor.getColumnIndex("title"))
                val review = cursor.getString(cursor.getColumnIndex("review"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val rating = cursor.getFloat(cursor.getColumnIndex("rating"))
                val emotion = cursor.getString(cursor.getColumnIndex("emotion"))
                val recommend = cursor.getString(cursor.getColumnIndex("recommend"))
                val review_content =
                    Review(alias, title, review, description, rating, emotion, recommend)
                reviewList.add(review_content)
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return reviewList
    }

    fun REVIEW_Insert(title: String, review: String, description: String, rating: Float, emotion: String, recommend: String) {
        var db: SQLiteDatabase = writableDatabase
        db!!.execSQL("INSERT INTO REVIEW(title, review, description, rating, emotion, recommend) VALUES('$title', '$review', '$description', '$rating', '$emotion', '$recommend');")
        // CONTENT - CATEGORY에 해당 TITLE이 있는지 검색해 추가하는 과정 필요 & WIKI CONTENT 추가
        // CONTENT_Insert(title, image, category, description, rating)
        db.close()
    }

    fun REVIEW_Update(alias: Int, title: String, review: String, description: String, rating: Float, emotion: String, recommend: String) {
        var db: SQLiteDatabase = writableDatabase
        db!!.execSQL("UPDATE REVIEW SET title = '$title' WHERE alias = '$alias';")
        db!!.execSQL("UPDATE REVIEW SET review = '$review' WHERE alias = '$alias';")
        db!!.execSQL("UPDATE REVIEW SET description = '$description' WHERE alias = '$alias';")
        db!!.execSQL("UPDATE REVIEW SET rating = '$rating' WHERE alias = '$alias';")
        db!!.execSQL("UPDATE REVIEW SET emotion = '$emotion' WHERE alias = '$alias';")
        db!!.execSQL("UPDATE REVIEW SET recommend = '$recommend' WHERE alias = '$alias';")
        db.close()
    }

    fun REVIEW_Delete(alias: Int) {
        var db: SQLiteDatabase = writableDatabase
        db!!.execSQL("DELETE FROM REVIEW WHERE alias = '$alias';")
        db.close()
    }


    /* CONTENT */
    fun CONTENT_Select(): ArrayList<Content> {
        var db: SQLiteDatabase = readableDatabase
        val contentList: ArrayList<Content> = ArrayList<Content>()
        try {
            val cursor: Cursor = db!!.rawQuery("SELECT * FROM CONTENT;", null)
            var count = 0
            while (cursor.moveToNext() && count <= 10) {
                count += 1
                val image = cursor.getBlob(1)
                val title = cursor.getString(0)
                val content = Content(image, title)
                contentList.add(content)
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return contentList
    }

    fun CONTENT_Select_NEW(category: String): ArrayList<Content> {
        var db: SQLiteDatabase = readableDatabase
        val contentList: ArrayList<Content> = ArrayList<Content>()
        try {
            val cursor: Cursor = db!!.rawQuery(
                "SELECT * FROM CONTENT WHERE category = '$category' ORDER BY date DESC",
                null
            )
            var count = 0
            while (cursor.moveToNext() && count <= 10) {
                count += 1
                val image = cursor.getBlob(1)
                val title = cursor.getString(0)
                val content = Content(image, title)
                contentList.add(content)
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return contentList
    }

    fun CONTENT_Select_EMOTION(category: String, emotion: String): ArrayList<Content> {
        var db: SQLiteDatabase = readableDatabase
        val contentList: ArrayList<Content> = ArrayList<Content>()
        try {
            when(emotion){
                "HAPPY" -> {
                    val cursor: Cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = '$category' ORDER BY date DESC",null)
                    var count = 0
                    while (cursor.moveToNext() && count <= 10) {
                        count += 1
                        val image = cursor.getBlob(1)
                        val title = cursor.getString(0)
                        val content = Content(image, title)
                        contentList.add(content)
                    }
                }
                "SAD" -> {

                }
                "BORED" -> {

                }
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return contentList
    }

    fun CONTENT_Insert(title: String, image: ByteArray, category: String, description: String, rating: Float){
        var db: SQLiteDatabase = writableDatabase

        val date =  db!!.execSQL("SELECT strftime('%Y-%m-%d %H-%M-%f', 'now');")
        //val reviewNum = 1 추가
        // db!!.execSQL("INSERT INTO CONTENT VALUES('$title', '$image', '$category', '$description', '$date', '$reviewNum', '$rating');")
        db.close()
    }

    fun CONTENT_Update(flag: Int, title: String, category: String, rating: Float){
        var db: SQLiteDatabase = writableDatabase
        var cursor: Cursor = db!!.rawQuery("SELECT rating FROM CONTENT WHERE title = '$title' AND category = '$category';", null)

        var ratingScore = 0.0f
        var reviewNum = 0
        var value = reviewNum * rating

        while (cursor.moveToNext()){
            ratingScore = cursor.getFloat(cursor.getColumnIndex("rating"))
            reviewNum = cursor.getInt(cursor.getColumnIndex("reviewNum"))
        }

        when(flag){
            0 -> {
                // 후기 추가
                reviewNum = reviewNum + 1
                ratingScore = (ratingScore * (reviewNum - 1) + value) / reviewNum
            }
            1 -> {
                // 후기 삭제
                reviewNum = reviewNum - 1
                ratingScore = (ratingScore * (reviewNum + 1) + value) / reviewNum
            }
        }

        db!!.execSQL("UPDATE CONTENT SET rating = '$ratingScore' WHERE title = '$title'AND category = '$category';")
        db!!.execSQL("UPDATE CONTENT SET reviewNum = '$reviewNum' WHERE title = '$title'AND category = '$category';")

        db.close()
    }


    /* WIKI */
    fun WIKI_Select(title: String): ArrayList<String> {
        var db: SQLiteDatabase = readableDatabase
        var content: String
        val forumList: ArrayList<String> = ArrayList<String>()
        try {
            val cursor: Cursor = db!!.rawQuery("SELECT * FROM WIKI WHERE title = '$title'", null)
            //var position = 1
            while (cursor.moveToNext()) {
                //content = cursor.getString(position)
                forumList.add(cursor.getString(1))
                forumList.add(cursor.getString(2))
                forumList.add(cursor.getString(3))
                forumList.add(cursor.getString(4))
                //position = position + 1
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return forumList
    }

    fun WIKI_Insert(title: String) {
        var db: SQLiteDatabase = writableDatabase
        db!!.execSQL("INSERT INTO WIKI VALUES('$title', '', '', '', '');")
        db.close()
    }

    fun WIKI_Update(update_text: String, title: String, itemPosition: Int) {
        var db: SQLiteDatabase = writableDatabase
        when (itemPosition) {
            0 -> db!!.execSQL("UPDATE WIKI SET content_1 = '$update_text' WHERE title = '$title';")
            1 -> db!!.execSQL("UPDATE WIKI SET content_2 = '$update_text' WHERE title = '$title';")
            2 -> db!!.execSQL("UPDATE WIKI SET content_3 = '$update_text' WHERE title = '$title';")
            3 -> db!!.execSQL("UPDATE WIKI SET content_4 = '$update_text' WHERE title = '$title';")
            else -> false
        }
        db.close()
    }


    /* RANK */
    fun RANK_Select(flag: String, category: String): ArrayList<rankContent> {
        var db: SQLiteDatabase = readableDatabase
        val contentList: ArrayList<rankContent> = ArrayList<rankContent>()
        val cursor: Cursor
        try {
            // Book category only
            when (flag) {
                "random" -> {
                    cursor = db!!.rawQuery(
                        "SELECT * FROM CONTENT WHERE category = '$category' ORDER BY random();",
                        null
                    )

                    var rank = 0
                    while (cursor.moveToNext()) {
                        rank = rank + 1
                        val title = cursor.getString(cursor.getColumnIndex("title"))
                        val image = cursor.getBlob(cursor.getColumnIndex("image"))
                        val description = cursor.getString(cursor.getColumnIndex("description"))
                        val content = rankContent(rank, title, image, description)
                        contentList.add(content)
                    }
                }
                "popularity" -> {
                    cursor = db!!.rawQuery(
                        "SELECT * FROM CONTENT WHERE category = '$category' ORDER BY reviewNum DESC;",
                        null
                    )

                    var rank = 0
                    while (cursor.moveToNext()) {
                        rank = rank + 1
                        val title = cursor.getString(0)
                        val image = cursor.getBlob(1)
                        val description = cursor.getString(4)
                        val content = rankContent(rank, title, image, description)
                        contentList.add(content)
                    }
                }
                "rating" -> {
                    cursor = db!!.rawQuery(
                        "SELECT * FROM CONTENT WHERE category = '$category' ORDER BY rating DESC;",
                        null
                    )

                    var rank = 0
                    while (cursor.moveToNext()) {
                        rank = rank + 1
                        val title = cursor.getString(0)
                        val image = cursor.getBlob(1)
                        val description = cursor.getString(4)
                        val content = rankContent(rank, title, image, description)
                        contentList.add(content)
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return contentList
    }
}