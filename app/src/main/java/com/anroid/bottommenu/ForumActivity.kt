package com.anroid.bottommenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ForumActivity : AppCompatActivity() {
    private lateinit var title: String
    lateinit var Title_TextView: TextView
    lateinit var forumList: List<Forum>
    lateinit var adapter: ForumExpandableAdapter
    lateinit var myHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)

        myHelper = DBHelper(this, "WIKI", null, 1)
        if(intent.hasExtra("title")) {
            title = intent.getStringExtra("title").toString()
            // title = intent.getStringExtra("title").toString() 장르 등 설명 부분
        }

        Title_TextView = findViewById<TextView>(R.id.Title_TextView)
        Title_TextView.setText(title)

        val recyclerView = findViewById<RecyclerView>(R.id.forum_recyclerView)
        val contentArr = myHelper.wiki(title)

        forumList = ArrayList()
        forumList = loadData(contentArr)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ForumExpandableAdapter(forumList)
        recyclerView.adapter = adapter
    }

    private fun loadData(contentArray: ArrayList<String>): List<Forum> {
        val forumList = ArrayList<Forum>()
        val categories = resources.getStringArray(R.array.category)
        val contents = contentArray
        for (i in categories.indices) {
            val forum = Forum().apply {
                category = categories[i]
                content = contents[i]
            }
            forumList.add(forum)
        }
        return forumList
    }
}
