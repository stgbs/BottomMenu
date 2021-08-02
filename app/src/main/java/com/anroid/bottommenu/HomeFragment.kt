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

        myHelper = DBHelper(getActivity(), "GURU", null, 1)

        // # 기쁠 때
        val contentList_happy = myHelper.CONTENT_Select()
        rv_content_happy = view.findViewById(R.id.rv_content_happy)
        rv_content_happy.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_content_happy.setHasFixedSize(true)
        rv_content_happy.adapter = ContentAdapter(contentList_happy)

        // # 슬플 때
        val contentList_sad = myHelper.CONTENT_Select()
        rv_content_sad = view.findViewById(R.id.rv_content_Sad)
        rv_content_sad.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_content_sad.setHasFixedSize(true)
        rv_content_sad.adapter = ContentAdapter(contentList_sad)

        // # 최신 영화
        val contentList_newMovie = myHelper.CONTENT_Select_NEW("MOVIE")
        rv_content_newMovie = view.findViewById(R.id.rv_content_newMovie)
        rv_content_newMovie.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_content_newMovie.setHasFixedSize(true)
        rv_content_newMovie.adapter = ContentAdapter(contentList_newMovie)

        // # 최신 도서
        val contentList_newBook = myHelper.CONTENT_Select_NEW("BOOK")
        rv_content_newBook = view.findViewById(R.id.rv_content_newBook)
        rv_content_newBook.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_content_newBook.setHasFixedSize(true)
        rv_content_newBook.adapter = ContentAdapter(contentList_newBook)

        // # 최신 음악
        val contentList_newMusic = myHelper.CONTENT_Select_NEW("MUSIC")
        rv_content_newMusic = view.findViewById(R.id.rv_content_newMusic)
        rv_content_newMusic.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_content_newMusic.setHasFixedSize(true)
        rv_content_newMusic.adapter = ContentAdapter(contentList_newMusic)

        // 위키로 이동
        btn_movie = view.findViewById<Button>(R.id.btn_movie)
        btn_book = view.findViewById<Button>(R.id.btn_book)
        btn_music = view.findViewById<Button>(R.id.btn_music)

        btn_movie.setOnClickListener {
            val intent = Intent(getActivity(), RankActivity::class.java)
            intent.putExtra("RankCategory", "MOVIE")
            startActivity(intent)
        }
        btn_book.setOnClickListener {
            val intent = Intent(getActivity(), RankActivity::class.java)
            intent.putExtra("RankCategory", "BOOK")
            startActivity(intent)
        }
        btn_music.setOnClickListener {
            val intent = Intent(getActivity(), RankActivity::class.java)
            intent.putExtra("RankCategory", "MUSIC")
            startActivity(intent)
        }

        myHelper.close()
        return view
    }
}