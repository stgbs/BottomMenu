package com.anroid.bottommenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MovieRankActivity : AppCompatActivity() {

    lateinit var btn_Back : FloatingActionButton
    lateinit var btn_popularity : Button
    lateinit var btn_rating : Button
    lateinit var btn_random : Button
    lateinit var listView : ListView
    lateinit var myHelper : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_rank)

        myHelper = DBHelper(this, "CONTENT", null, 1)
        var movieList = myHelper.Rank()

        btn_Back = findViewById<FloatingActionButton>(R.id.btn_back)
        btn_popularity = findViewById(R.id.btn_popularity)
        btn_rating = findViewById(R.id.btn_rating)
        btn_random = findViewById(R.id.btn_random)
        listView = findViewById(R.id.listView)

        btn_Back.setOnClickListener{
            onBackPressed()
        }

        btn_popularity.setOnClickListener {
            val Adapter = MovieAdapter(this, movieList)
            listView.adapter = Adapter
        }
        btn_rating.setOnClickListener {

        }
        btn_random.setOnClickListener {

        }

    }
}