package com.anroid.bottommenu

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ForumActivity : AppCompatActivity() {
    private lateinit var title: String
    private lateinit var category: String

    lateinit var Title_TextView: TextView
    lateinit var Description_TextView: TextView
    lateinit var imageView: ImageView
    lateinit var forumList: List<Forum>
    lateinit var adapter: ForumExpandableAdapter
    lateinit var btn_back: FloatingActionButton

    lateinit var myHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)

        Title_TextView = findViewById<TextView>(R.id.Title_TextView)
        Description_TextView = findViewById<TextView>(R.id.Description_TextView)
        imageView = findViewById<ImageView>(R.id.image)
        btn_back = findViewById<FloatingActionButton>(R.id.btn_back)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        myHelper = DBHelper(this, "GURU", null, 1)
        if(intent.hasExtra("title")) {
            title = intent.getStringExtra("title").toString()
        }

        Title_TextView.setText(title)

        sqlDB = myHelper.readableDatabase
        var cursor: Cursor = sqlDB.rawQuery("SELECT image, description, category FROM CONTENT WHERE title = '$title';", null)

        while (cursor.moveToNext()){
            var image = cursor.getBlob(cursor.getColumnIndex("image")) // image
            var Description = cursor.getString(cursor.getColumnIndex("description")) // description
            category = cursor.getString(cursor.getColumnIndex("category"))

            imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.size))
            Description_TextView.setText(Description)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.forum_recyclerView)
        val contentArr = myHelper.WIKI_Select(title)

        forumList = ArrayList()
        forumList = loadData(contentArr, category)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ForumExpandableAdapter(forumList, title)
        recyclerView.adapter = adapter

        myHelper.close()
        sqlDB.close()
        cursor.close()
    }

    private fun loadData(contentArray: ArrayList<String>, flag: String): List<Forum> {
        val forumList = ArrayList<Forum>()
        var categories: Array<String>
        when(flag){
            "MOVIE" -> {
                categories = resources.getStringArray(R.array.category_movie)
                val contents = contentArray
                for (i in categories.indices) {
                    val forum = Forum().apply {
                        category = categories[i]
                        content = contents[i]
                    }
                    forumList.add(forum)
                }
            }
            "MUSIC" -> {
                categories = resources.getStringArray(R.array.category_music)
                val contents = contentArray
                for (i in categories.indices) {
                    val forum = Forum().apply {
                        category = categories[i]
                        content = contents[i]
                    }
                    forumList.add(forum)
                }
            }
            "BOOK" -> {
                categories = resources.getStringArray(R.array.category_book)
                val contents = contentArray
                for (i in categories.indices) {
                    val forum = Forum().apply {
                        category = categories[i]
                        content = contents[i]
                    }
                    forumList.add(forum)
                }
            }
        }
        return forumList
    }
}