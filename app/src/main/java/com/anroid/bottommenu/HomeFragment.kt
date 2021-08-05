package com.anroid.bottommenu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    lateinit var btn_movie : Button
    lateinit var btn_book : Button
    lateinit var btn_music : Button

    lateinit var rv_content_happy : RecyclerView
    lateinit var rv_content_sad : RecyclerView
    lateinit var rv_content_boring : RecyclerView
    lateinit var rv_content_newMovie : RecyclerView
    lateinit var rv_content_newBook : RecyclerView
    lateinit var rv_content_newMusic : RecyclerView

    lateinit var myHelper : DBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        btn_movie = view.findViewById<Button>(R.id.btn_movie)
        btn_book = view.findViewById<Button>(R.id.btn_book)
        btn_music = view.findViewById<Button>(R.id.btn_music)

        myHelper = DBHelper(getActivity(), "GURU", null, 1)

        // # 기쁠 때 보기 좋은 컨텐츠 추천 리스트
        // CONTENT_Select_EMOTION()으로 HAPPY 컬럼 기준 정렬하여 출력
        val contentList_happy = myHelper.CONTENT_Select_EMOTION("HAPPY")
        rv_content_happy = view.findViewById(R.id.rv_content_happy)
        rv_content_happy.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_content_happy.setHasFixedSize(true)
        rv_content_happy.adapter = ContentAdapter(contentList_happy)

        // # 슬플 때 보기 좋은 컨텐츠 추천 리스트
        // CONTENT_Select_EMOTION()으로 SAD 컬럼 기준 정렬하여 출력
        val contentList_sad = myHelper.CONTENT_Select_EMOTION("SAD")
        rv_content_sad = view.findViewById(R.id.rv_content_Sad)
        rv_content_sad.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_content_sad.setHasFixedSize(true)
        rv_content_sad.adapter = ContentAdapter(contentList_sad)

        // # 지루할 때 보기 좋은 컨텐츠 추천 리스트
        // CONTENT_Select_EMOTION()으로 BORED 컬럼 기준 정렬하여 출력
        val contentList_boring = myHelper.CONTENT_Select_EMOTION("BORED")
        rv_content_boring = view.findViewById(R.id.rv_content_Boring)
        rv_content_boring.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_content_boring.setHasFixedSize(true)
        rv_content_boring.adapter = ContentAdapter(contentList_boring)

        // # 최신 영화 컨텐츠 추천 리스트
        // CONTENT_Select_NEW()로 MOVIE 카테고리의 Date 컬럼 기준 정렬하여 출력
        val contentList_newMovie = myHelper.CONTENT_Select_NEW("MOVIE")
        rv_content_newMovie = view.findViewById(R.id.rv_content_newMovie)
        rv_content_newMovie.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_content_newMovie.setHasFixedSize(true)
        rv_content_newMovie.adapter = ContentAdapter(contentList_newMovie)

        // # 최신 도서 컨텐츠 추천 리스트
        // CONTENT_Select_NEW()로 BOOK 카테고리의 Date 컬럼 기준 정렬하여 출력
        val contentList_newBook = myHelper.CONTENT_Select_NEW("BOOK")
        rv_content_newBook = view.findViewById(R.id.rv_content_newBook)
        rv_content_newBook.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_content_newBook.setHasFixedSize(true)
        rv_content_newBook.adapter = ContentAdapter(contentList_newBook)

        // # 최신 음악 컨텐츠 추천 리스트
        // CONTENT_Select_NEW()로 MUSIC 카테고리의 Date 컬럼 기준 정렬하여 출력
        val contentList_newMusic = myHelper.CONTENT_Select_NEW("MUSIC")
        rv_content_newMusic = view.findViewById(R.id.rv_content_newMusic)
        rv_content_newMusic.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_content_newMusic.setHasFixedSize(true)
        rv_content_newMusic.adapter = ContentAdapter(contentList_newMusic)


        // 영화/드라마 버튼 클릭 이벤트
        // MOVIE 텍스트와 함께 RankActivity로 이동
        btn_movie.setOnClickListener {
            val intent = Intent(getActivity(), RankActivity::class.java)
            intent.putExtra("RankCategory", "MOVIE")
            startActivity(intent)
        }

        // 도서 버튼 클릭 이벤트
        // BOOK 텍스트와 함께 RankActivity로 이동
        btn_book.setOnClickListener {
            val intent = Intent(getActivity(), RankActivity::class.java)
            intent.putExtra("RankCategory", "BOOK")
            startActivity(intent)
        }

        // 음악 버튼 클릭 이벤트
        // MUSIC 텍스트와 함께 RankActivity로 이동
        btn_music.setOnClickListener {
            val intent = Intent(getActivity(), RankActivity::class.java)
            intent.putExtra("RankCategory", "MUSIC")
            startActivity(intent)
        }

        myHelper.close()
        return view
    }
}