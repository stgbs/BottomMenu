package com.anroid.bottommenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MovieRankActivity : AppCompatActivity() {
    var movieList = arrayListOf<Movie>(
        Movie(1, R.drawable.image, "겨울왕국", "겨울에 나온 영화!"),
        Movie(2, R.drawable.image, "여름왕국", "여름에 나온 영화!"),
        Movie(3, R.drawable.image, "봄왕국", "봄에 나온 영화!"),
        Movie(4, R.drawable.image, "가을왕국", "가을에 나온 영화!"),
        Movie(5, R.drawable.image, "사계왕국", "사계절에 모두 나온 영화!")
    )

    lateinit var btn_Back : FloatingActionButton
    lateinit var btn_popularity : Button
    lateinit var btn_rating : Button
    lateinit var btn_random : Button
    lateinit var listView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_rank)

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