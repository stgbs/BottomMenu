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
    private lateinit var contentArr: ArrayList<String>

    lateinit var Title_TextView: TextView
    lateinit var Description_TextView: TextView
    lateinit var imageView: ImageView
    lateinit var btn_back: FloatingActionButton

    lateinit var recyclerView: RecyclerView
    lateinit var forumList: ArrayList<Forum>
    lateinit var adapter: ForumExpandableAdapter

    lateinit var myHelper: DBHelper
    lateinit var sqlDB: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)

        Title_TextView = findViewById<TextView>(R.id.Title_TextView)
        Description_TextView = findViewById<TextView>(R.id.Description_TextView)
        imageView = findViewById<ImageView>(R.id.image)
        btn_back = findViewById<FloatingActionButton>(R.id.btn_back)
        recyclerView = findViewById<RecyclerView>(R.id.forum_recyclerView)

        myHelper = DBHelper(this, "GURU", null, 1)

        // 백 버튼 클릭 이벤트
        btn_back.setOnClickListener {
            onBackPressed()
        }

        // HOME Fragment 또는 RANK Fragment에서 가져온 title 데이터를 변수에 저장 후 setText
        if(intent.hasExtra("title")) {
            title = intent.getStringExtra("title").toString()
        }
        Title_TextView.setText(title)

        // CONTENT 테이블에서 title로 검색하여 이미지, 설명, 카테고리 데이터 변수에 저장 후 세팅
        sqlDB = myHelper.readableDatabase
        var cursor: Cursor = sqlDB.rawQuery("SELECT image, description, category FROM CONTENT WHERE title = '$title';", null)

        while (cursor.moveToNext()){
            var image = cursor.getBlob(cursor.getColumnIndex("image")) // image
            var Description = cursor.getString(cursor.getColumnIndex("description")) // description
            category = cursor.getString(cursor.getColumnIndex("category"))

            imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.size))
            Description_TextView.setText(Description)
        }

        // 해당 타이틀의 WIKI 데이터를 가져와 리사이클러뷰에서 출력
        contentArr = myHelper.WIKI_Select(title)
        forumList = loadData(contentArr, category)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ForumExpandableAdapter(forumList, title)
        recyclerView.adapter = adapter

        myHelper.close()
        sqlDB.close()
        cursor.close()
    }

    // 인자로 받은 카테고리에 따라 strings.xml에 저장된 문자열 배열(strings.xml)의 목차 텍스트, WIKI_Select()의 결과 값 리스트 반환
    private fun loadData(contentArray: ArrayList<String>, flag: String): ArrayList<Forum> {
        val forumList = ArrayList<Forum>()
        var categories: Array<String>
        when(flag){
            "MOVIE" -> {
                categories = resources.getStringArray(R.array.category_movie)        // strings.xml의 category_movie를 목차 텍스트로 사용
                val contents = contentArray
                for (i in categories.indices) {
                    val forum = Forum().apply {
                        category = categories[i]                                     // i행의 목차 텍스트와 i행 내부 컨텐츠(위키 내용)
                        content = contents[i]
                    }
                    forumList.add(forum)
                }
            }
            "MUSIC" -> {
                categories = resources.getStringArray(R.array.category_music)        // strings.xml의 category_music을 목차 텍스트로 사용
                val contents = contentArray
                for (i in categories.indices) {
                    val forum = Forum().apply {
                        category = categories[i]                                     // i행의 목차 텍스트와 i행 내부 컨텐츠(위키 내용)
                        content = contents[i]
                    }
                    forumList.add(forum)
                }
            }
            "BOOK" -> {
                categories = resources.getStringArray(R.array.category_book)        // strings.xml의 category_book을 목차 텍스트로 사용
                val contents = contentArray
                for (i in categories.indices) {
                    val forum = Forum().apply {
                        category = categories[i]                                     // i행의 목차 텍스트와 i행 내부 컨텐츠(위키 내용)
                        content = contents[i]
                    }
                    forumList.add(forum)
                }
            }
        }
        return forumList
    }
}