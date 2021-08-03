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

            db!!.execSQL("CREATE TABLE REVIEW(alias INTEGER," + " title TEXT," + " genre TEXT," + "category TEXT," + " review TEXT," + " description TEXT," + " rating REAL," + "emotion TEXT," + " recommend TEXT," + " PRIMARY KEY('alias' AUTOINCREMENT));")
            db!!.execSQL("CREATE TABLE CONTENT(title TEXT, " + "image BLOB, " + "category TEXT," + "description TEXT, " + "date TEXT, " + "reviewNum INTEGER, " + "rating REAL, "  + "recommend INT, " + "HAPPY INT, " + "SAD INT, " + "BORED INT);")
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

        var cursor: Cursor = db.rawQuery("SELECT EMAIL, PASSWORD FROM MEMBER", null)
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


    /* REVIEW */
    fun REVIEW_Select(): ArrayList<Review> {
        var db: SQLiteDatabase = readableDatabase
        val reviewList: ArrayList<Review> = ArrayList<Review>()
        try {
            val cursor: Cursor = db!!.rawQuery("SELECT * FROM REVIEW;", null)
            while (cursor.moveToNext()) {
                val alias = cursor.getInt(cursor.getColumnIndex("alias"))
                val title = cursor.getString(cursor.getColumnIndex("title"))
                val genre = cursor.getString(cursor.getColumnIndex("genre"))
                val category = cursor.getString(cursor.getColumnIndex("category"))
                val review = cursor.getString(cursor.getColumnIndex("review"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val rating = cursor.getFloat(cursor.getColumnIndex("rating"))
                val emotion = cursor.getString(cursor.getColumnIndex("emotion"))
                val recommend = cursor.getString(cursor.getColumnIndex("recommend"))
                val review_content = Review(alias, title, genre, category, review, description, rating, emotion, recommend)
                reviewList.add(review_content)
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return reviewList
    }

    fun REVIEW_Insert(title: String, review: String, genre: String, category: String, description: String, rating: Float, emotion: String, recommend: String) {
        var db: SQLiteDatabase = writableDatabase
        db!!.execSQL("INSERT INTO REVIEW(title, review, genre, category, description, rating, emotion, recommend) VALUES('$title', '$review', '$genre', '$category', '$description', '$rating', '$emotion', '$recommend');")

        CONTENT_Update_ADD(title, category, rating, recommend, emotion)
        db.close()
    }

    fun REVIEW_Update(alias: Int, title: String, review: String, genre: String, category: String, description: String, rating: Float, emotion: String, recommend: String) {
        var db: SQLiteDatabase = writableDatabase
        CONTENT_Update_DEL(title, category, rating, recommend, emotion)

        db!!.execSQL("UPDATE REVIEW SET title = '$title' WHERE alias = '$alias';")
        db!!.execSQL("UPDATE REVIEW SET review = '$review' WHERE alias = '$alias';")
        db!!.execSQL("UPDATE REVIEW SET category = '$category' WHERE alias = '$alias';")
        db!!.execSQL("UPDATE REVIEW SET genre = '$genre' WHERE alias = '$alias';")
        db!!.execSQL("UPDATE REVIEW SET description = '$description' WHERE alias = '$alias';")
        db!!.execSQL("UPDATE REVIEW SET rating = '$rating' WHERE alias = '$alias';")
        db!!.execSQL("UPDATE REVIEW SET emotion = '$emotion' WHERE alias = '$alias';")
        db!!.execSQL("UPDATE REVIEW SET recommend = '$recommend' WHERE alias = '$alias';")

        CONTENT_Update_ADD(title, category, rating, recommend, emotion)
        db.close()
    }

    fun REVIEW_Delete(alias: Int) {
        var db: SQLiteDatabase = writableDatabase
        var cursor: Cursor = db!!.rawQuery("SELECT * FROM REVIEW WHERE alias = '$alias';", null)
        var title = ""
        var category = ""
        var rating = 0.0f
        var recommend = ""
        var emotion = ""

        while (cursor.moveToNext()) {
            title = cursor.getString(cursor.getColumnIndex("title"))
            category = cursor.getString(cursor.getColumnIndex("category"))
            rating = cursor.getFloat(cursor.getColumnIndex("rating"))
            recommend = cursor.getString(cursor.getColumnIndex("recommend"))
            emotion = cursor.getString(cursor.getColumnIndex("emotion"))
        }

        CONTENT_Update_DEL(title, category, rating, recommend, emotion)
        db!!.execSQL("DELETE FROM REVIEW WHERE alias = '$alias';")

        cursor.close()
        db.close()
    }


    /* CONTENT */
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

    fun CONTENT_Select_EMOTION(emotion: String): ArrayList<Content> {
        var db: SQLiteDatabase = readableDatabase
        val contentList: ArrayList<Content> = ArrayList<Content>()
        try {
            when(emotion){
                "HAPPY" -> {
                    val cursor: Cursor = db!!.rawQuery("SELECT * FROM CONTENT ORDER BY HAPPY DESC, recommend DESC",null)
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
                    val cursor: Cursor = db!!.rawQuery("SELECT * FROM CONTENT ORDER BY SAD DESC, recommend DESC",null)
                    var count = 0
                    while (cursor.moveToNext() && count <= 10) {
                        count += 1
                        val image = cursor.getBlob(1)
                        val title = cursor.getString(0)
                        val content = Content(image, title)
                        contentList.add(content)
                    }
                }
                "BORED" -> {
                    val cursor: Cursor = db!!.rawQuery("SELECT * FROM CONTENT ORDER BY BORED DESC, recommend DESC",null)
                    var count = 0
                    while (cursor.moveToNext() && count <= 10) {
                        count += 1
                        val image = cursor.getBlob(1)
                        val title = cursor.getString(0)
                        val content = Content(image, title)
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

    fun CONTENT_Update_ADD(title: String, category: String, rating: Float, recommend: String, emotion: String){
        var db: SQLiteDatabase = writableDatabase
        var cursor: Cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE title = '$title' AND category = '$category';", null)

        var ratingScore = 0.0f
        var reviewNum = 0

        var recommendScore = 0
        var happyScore = 0
        var sadScore = 0
        var boredScore = 0

        while (cursor.moveToNext()){
            ratingScore = cursor.getFloat(cursor.getColumnIndex("rating"))
            reviewNum = cursor.getInt(cursor.getColumnIndex("reviewNum"))
            recommendScore = cursor.getInt(cursor.getColumnIndex("recommend"))
            happyScore = cursor.getInt(cursor.getColumnIndex("HAPPY"))
            sadScore = cursor.getInt(cursor.getColumnIndex("SAD"))
            boredScore = cursor.getInt(cursor.getColumnIndex("BORED"))
        }

        when(recommend){
            "YES" -> {
                recommendScore = recommendScore + 1
            }
            else -> false
        }

        when(emotion){
            "HAPPY" -> {
                happyScore += 1
            }
            "SAD" -> {
                sadScore += 1
            }
            "BORED" -> {
                boredScore += 1
            }
            else -> false
        }

        reviewNum += 1
        ratingScore = ((ratingScore * reviewNum-1) + rating) / reviewNum

        db!!.execSQL("UPDATE CONTENT SET HAPPY = '$happyScore' WHERE title = '$title' AND category = '$category';")
        db!!.execSQL("UPDATE CONTENT SET SAD = '$sadScore' WHERE title = '$title' AND category = '$category';")
        db!!.execSQL("UPDATE CONTENT SET BORED = '$boredScore' WHERE title = '$title' AND category = '$category';")
        db!!.execSQL("UPDATE CONTENT SET recommend = '$recommendScore' WHERE title = '$title' AND category = '$category';")
        db!!.execSQL("UPDATE CONTENT SET rating = '$ratingScore' WHERE title = '$title' AND category = '$category';")
        db!!.execSQL("UPDATE CONTENT SET reviewNum = '$reviewNum' WHERE title = '$title' AND category = '$category';")

        db.close()
    }

    fun CONTENT_Update_DEL(title: String, category: String, rating: Float, recommend: String, emotion: String){
        var db: SQLiteDatabase = writableDatabase
        var cursor: Cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE title = '$title' AND category = '$category';", null)

        var ratingScore = 0.0f
        var reviewNum = 0

        var recommendScore = 0
        var happyScore = 0
        var sadScore = 0
        var boredScore = 0

        while (cursor.moveToNext()){
            ratingScore = cursor.getFloat(cursor.getColumnIndex("rating"))
            reviewNum = cursor.getInt(cursor.getColumnIndex("reviewNum"))
            recommendScore = cursor.getInt(cursor.getColumnIndex("recommend"))
            happyScore = cursor.getInt(cursor.getColumnIndex("HAPPY"))
            sadScore = cursor.getInt(cursor.getColumnIndex("SAD"))
            boredScore = cursor.getInt(cursor.getColumnIndex("BORED"))
        }

        when(recommend){
            "YES" -> {
                recommendScore = recommendScore + 1
            }
            else -> false
        }

        when(emotion){
            "HAPPY" -> {
                happyScore -= 1
            }
            "SAD" -> {
                sadScore -= 1
            }
            "BORED" -> {
                boredScore -= 1
            }
            else -> false
        }

        reviewNum = reviewNum - 1
        ratingScore = ((ratingScore * reviewNum + 1) - rating) / reviewNum

        db!!.execSQL("UPDATE CONTENT SET HAPPY = '$happyScore' WHERE title = '$title' AND category = '$category';")
        db!!.execSQL("UPDATE CONTENT SET SAD = '$sadScore' WHERE title = '$title' AND category = '$category';")
        db!!.execSQL("UPDATE CONTENT SET BORED = '$boredScore' WHERE title = '$title' AND category = '$category';")
        db!!.execSQL("UPDATE CONTENT SET recommend = '$recommendScore' WHERE title = '$title' AND category = '$category';")
        db!!.execSQL("UPDATE CONTENT SET rating = '$ratingScore' WHERE title = '$title' AND category = '$category';")
        db!!.execSQL("UPDATE CONTENT SET reviewNum = '$reviewNum' WHERE title = '$title' AND category = '$category';")
    }

    /* WIKI */
    fun WIKI_Select(title: String): ArrayList<String> {
        var db: SQLiteDatabase = readableDatabase
        var content: String
        val forumList: ArrayList<String> = ArrayList<String>()
        try {
            val cursor: Cursor = db!!.rawQuery("SELECT * FROM WIKI WHERE title = '$title'", null)
            while (cursor.moveToNext()) {
                forumList.add(cursor.getString(1))
                forumList.add(cursor.getString(2))
                forumList.add(cursor.getString(3))
                forumList.add(cursor.getString(4))
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return forumList
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
                        val title = cursor.getString(cursor.getColumnIndex("title"))
                        val image = cursor.getBlob(cursor.getColumnIndex("image"))
                        val description = cursor.getString(cursor.getColumnIndex("description"))
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
                        val title = cursor.getString(cursor.getColumnIndex("title"))
                        val image = cursor.getBlob(cursor.getColumnIndex("image"))
                        val description = cursor.getString(cursor.getColumnIndex("description"))
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