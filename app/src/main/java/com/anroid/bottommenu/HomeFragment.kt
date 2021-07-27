package com.anroid.bottommenu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {
    private val contentList_happy = arrayListOf(
        Content(R.drawable.image, "겨울왕국"),
        Content(R.drawable.image, "여름왕국"),
        Content(R.drawable.image, "봄왕국"),
        Content(R.drawable.image, "가을왕국"),
        Content(R.drawable.image, "사계왕국"),
        Content(R.drawable.image, "왕국왕국왕국왕왕왕국"),
    )

    private val contentList_sad = arrayListOf(
        Content(R.drawable.image, "겨울왕국"),
        Content(R.drawable.image, "여름왕국"),
        Content(R.drawable.image, "봄왕국"),
        Content(R.drawable.image, "가을왕국"),
        Content(R.drawable.image, "사계왕국"),
        Content(R.drawable.image, "왕국왕국왕국왕왕왕국왕국왕국왕국왕왕왕국"),
    )

    private val contentList_newMovie = arrayListOf(
        Content(R.drawable.image, "겨울왕국"),
        Content(R.drawable.image, "여름왕국"),
        Content(R.drawable.image, "봄왕국"),
        Content(R.drawable.image, "가을왕국"),
        Content(R.drawable.image, "사계왕국"),
        Content(R.drawable.image, "왕국왕국왕국왕왕왕국왕국왕국왕국왕왕왕국"),
    )

    private val contentList_newBook = arrayListOf(
        Content(R.drawable.image, "겨울왕국"),
        Content(R.drawable.image, "여름왕국"),
        Content(R.drawable.image, "봄왕국"),
        Content(R.drawable.image, "가을왕국"),
        Content(R.drawable.image, "사계왕국"),
        Content(R.drawable.image, "왕국왕국왕국왕왕왕국왕국왕국왕국왕왕왕국"),
    )

    private val contentList_newMusic = arrayListOf(
        Content(R.drawable.image, "겨울왕국"),
        Content(R.drawable.image, "여름왕국"),
        Content(R.drawable.image, "봄왕국"),
        Content(R.drawable.image, "가을왕국"),
        Content(R.drawable.image, "사계왕국"),
        Content(R.drawable.image, "왕국왕국왕국왕왕왕국왕국왕국왕국왕왕왕국"),
    )

    lateinit var btn_movie : Button
    lateinit var btn_book : Button
    lateinit var btn_music : Button

    lateinit var rv_content_happy : RecyclerView
    lateinit var rv_content_sad : RecyclerView
    lateinit var rv_content_newMovie : RecyclerView
    lateinit var rv_content_newBook : RecyclerView
    lateinit var rv_content_newMusic : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        rv_content_happy = view.findViewById(R.id.rv_content_happy)
        rv_content_happy.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_content_happy.setHasFixedSize(true)
        rv_content_happy.adapter = ContentAdapter(contentList_happy)

        rv_content_sad = view.findViewById(R.id.rv_content_Sad)
        rv_content_sad.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_content_sad.setHasFixedSize(true)
        rv_content_sad.adapter = ContentAdapter(contentList_sad)

        btn_movie = view.findViewById<Button>(R.id.btn_movie)
        btn_book = view.findViewById<Button>(R.id.btn_book)
        btn_music = view.findViewById<Button>(R.id.btn_music)

        rv_content_newMovie = view.findViewById(R.id.rv_content_newMovie)
        rv_content_newMovie.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_content_newMovie.setHasFixedSize(true)
        rv_content_newMovie.adapter = ContentAdapter(contentList_newMovie)

        rv_content_newBook = view.findViewById(R.id.rv_content_newBook)
        rv_content_newBook.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_content_newBook.setHasFixedSize(true)
        rv_content_newBook.adapter = ContentAdapter(contentList_newBook)

        rv_content_newMusic = view.findViewById(R.id.rv_content_newMusic)
        rv_content_newMusic.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_content_newMusic.setHasFixedSize(true)
        rv_content_newMusic.adapter = ContentAdapter(contentList_newMusic)

        btn_movie.setOnClickListener {
            val intent = Intent(getActivity(), MovieRankActivity::class.java)
            startActivity(intent)
        }
        btn_book.setOnClickListener {

        }
        btn_music.setOnClickListener {

        }

        return view
    }
}