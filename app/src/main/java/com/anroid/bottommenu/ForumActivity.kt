package com.anroid.bottommenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ForumActivity : AppCompatActivity() {

    lateinit var forumList: List<Forum>
    lateinit var adapter: ExpandableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)

        // DB 연동을 위한 초석
        if(intent.hasExtra("title")) {
            var txt = intent.getStringExtra("title")
        }

        val recyclerView = findViewById<RecyclerView>(R.id.forum_recyclerView)

        forumList = ArrayList()
        forumList = loadData()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpandableAdapter(forumList)
        recyclerView.adapter = adapter

    }

    private fun loadData(): List<Forum> {
        val people = ArrayList<Forum>()
        val categories = resources.getStringArray(R.array.category)
        for (i in categories.indices) {
            val forum = Forum().apply {
                category = categories[i]
            }
            people.add(forum)
        }
        return people
    }
}
