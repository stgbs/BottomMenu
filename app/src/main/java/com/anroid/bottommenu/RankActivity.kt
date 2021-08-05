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


        // 인텐트로 전달받은 카테고리(MOVIE, MUSIC, BOOK)에 따라 페이지 타이틀 설정 및 데이터 SELECT
        // 랭킹 리스트뷰 초기 정렬 Flag는 random
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

        // 백버튼 클릭 이벤트
        btn_Back.setOnClickListener{
            onBackPressed()
        }

        myHelper.close()
    }

    // 카테고리 별 랭킹 버튼 클릭 이벤트
    fun RankbtnPress(contentList: ArrayList<rankContent>, category: String) {

        // 후기 많은 순 버튼 클릭 이벤트
        btn_popularity.setOnClickListener {
            Flag = "popularity"
            btn_popularity.setBackgroundColor(this.getResources().getColor(R.color.top_clk))        // 선택된 버튼, 그 외 버튼의 색깔 구분하여 변경
            btn_rating.setBackgroundColor(this.getResources().getColor(R.color.top))
            btn_random.setBackgroundColor(this.getResources().getColor(R.color.top))

            var contentList = myHelper.RANK_Select(Flag, category)                                  // popularity 플래그 값으로 RANK_Select()
            val Adapter = rankingAdapter(this, contentList)
            listView.adapter = Adapter
        }

        // 별점 높은 순 버튼 클릭 이벤트
        btn_rating.setOnClickListener {
            Flag = "rating"
            btn_popularity.setBackgroundColor(this.getResources().getColor(R.color.top))            // 선택된 버튼, 그 외 버튼의 색깔 구분하여 변경
            btn_rating.setBackgroundColor(this.getResources().getColor(R.color.top_clk))
            btn_random.setBackgroundColor(this.getResources().getColor(R.color.top))

            var contentList = myHelper.RANK_Select(Flag, category)                                  // rating 플래그 값으로 RANK_Select()
            val Adapter = rankingAdapter(this, contentList)
            listView.adapter = Adapter
        }

        // 랜덤 버튼 클릭 이벤트
        btn_random.setOnClickListener {
            Flag = "random"
            btn_popularity.setBackgroundColor(this.getResources().getColor(R.color.top))            // 선택된 버튼, 그 외 버튼의 색깔 구분하여 변경
            btn_rating.setBackgroundColor(this.getResources().getColor(R.color.top))
            btn_random.setBackgroundColor(this.getResources().getColor(R.color.top_clk))

            var contentList = myHelper.RANK_Select(Flag, category)                                  // random 플래그 값으로 RANK_Select()
            val Adapter = rankingAdapter(this, contentList)
            listView.adapter = Adapter
        }

    }
}