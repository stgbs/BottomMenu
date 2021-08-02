package com.anroid.bottommenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RankActivity : AppCompatActivity() {
    private var Flag : String = "random"
    private var category: String = "* RANKING"

    lateinit var textView_Rank : TextView
    lateinit var btn_Back : FloatingActionButton
    lateinit var btn_popularity : Button
    lateinit var btn_rating : Button
    lateinit var btn_random : Button
    lateinit var listView : ListView
    lateinit var myHelper : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)

        myHelper = DBHelper(this, "GURU", null, 1)

        textView_Rank = findViewById<TextView>(R.id.textView_Rank)
        btn_Back = findViewById<FloatingActionButton>(R.id.btn_back)
        btn_popularity = findViewById(R.id.btn_popularity)
        btn_rating = findViewById(R.id.btn_rating)
        btn_random = findViewById(R.id.btn_random)
        listView = findViewById(R.id.listView)

        if(intent.hasExtra("RankCategory")) {
            category = intent.getStringExtra("RankCategory").toString()
            textView_Rank.text = category + " RANK"

            when(category){
                "MOVIE" -> {
                    var movieList = myHelper.RANK_Select(Flag, category)
                    val Adapter = rankingAdapter(this, movieList)
                    listView.adapter = Adapter

                    RankbtnPress(movieList, category)
                }
                "BOOK" -> {
                    var bookList = myHelper.RANK_Select(Flag, category)
                    val Adapter = rankingAdapter(this, bookList)
                    listView.adapter = Adapter

                    RankbtnPress(bookList, category)
                }
                "MUSIC" -> {
                    var musicList = myHelper.RANK_Select(Flag, category)
                    val Adapter = rankingAdapter(this, musicList)
                    listView.adapter = Adapter

                    RankbtnPress(musicList, category)
                }
            }
        }

        btn_Back.setOnClickListener{
            onBackPressed()
        }

        myHelper.close()
    }

    fun RankbtnPress(contentList: ArrayList<rankContent>, category: String) {
        btn_popularity.setOnClickListener {
            Flag = "popularity"
            btn_popularity.setBackgroundColor(this.getResources().getColor(R.color.teal_700))
            btn_rating.setBackgroundColor(this.getResources().getColor(R.color.teal_200))
            btn_random.setBackgroundColor(this.getResources().getColor(R.color.teal_200))

            var contentList = myHelper.RANK_Select(Flag, category)
            val Adapter = rankingAdapter(this, contentList)
            listView.adapter = Adapter
        }
        btn_rating.setOnClickListener {
            Flag = "rating"
            btn_popularity.setBackgroundColor(this.getResources().getColor(R.color.teal_200))
            btn_rating.setBackgroundColor(this.getResources().getColor(R.color.teal_700))
            btn_random.setBackgroundColor(this.getResources().getColor(R.color.teal_200))

            var contentList = myHelper.RANK_Select(Flag, category)
            val Adapter = rankingAdapter(this, contentList)
            listView.adapter = Adapter
        }
        btn_random.setOnClickListener {
            Flag = "random"
            btn_popularity.setBackgroundColor(this.getResources().getColor(R.color.teal_200))
            btn_rating.setBackgroundColor(this.getResources().getColor(R.color.teal_200))
            btn_random.setBackgroundColor(this.getResources().getColor(R.color.teal_700))

            var contentList = myHelper.RANK_Select(Flag, category)
            val Adapter = rankingAdapter(this, contentList)
            listView.adapter = Adapter
        }

    }
}