package com.anroid.bottommenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ReviewFragment : Fragment() {
    private var alias: Int = 0
    private var title: String = ""
    private var reviewContent: String = ""
    private var category: String = ""
    private var genre: String = ""
    private var description: String = ""
    private var ratingScore: Float = 0.0f
    private var emotion: String = ""
    private var recommend: String = ""

    lateinit var db: DBHelper

    lateinit var edt_alias: TextView
    lateinit var ratingBar: RatingBar
    lateinit var summary: EditText
    lateinit var edt_title: EditText
    lateinit var review: EditText

    lateinit var btn_good: CheckBox
    lateinit var btn_hate: CheckBox

    lateinit var btn_happy: CheckBox
    lateinit var btn_sad: CheckBox
    lateinit var btn_bored: CheckBox

    lateinit var btn_add: Button
    lateinit var btn_rev: Button
    lateinit var btn_del: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_review, container, false)
        db = DBHelper(getActivity(), "GURU", null, 1)

        edt_alias = view.findViewById<TextView>(R.id.edt_alias)
        summary = view.findViewById<EditText>(R.id.summary)
        edt_title = view.findViewById<EditText>(R.id.edt_title)
        review = view.findViewById<EditText>(R.id.review)

        ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)

        btn_good = view.findViewById<CheckBox>(R.id.btn_good)
        btn_hate = view.findViewById<CheckBox>(R.id.btn_hate)

        btn_add = view.findViewById<Button>(R.id.btn_add)
        btn_del = view.findViewById<Button>(R.id.btn_del)
        btn_rev = view.findViewById<Button>(R.id.btn_rev)

        btn_happy = view.findViewById<CheckBox>(R.id.btn_happy)
        btn_sad = view.findViewById<CheckBox>(R.id.btn_sad)
        btn_bored = view.findViewById<CheckBox>(R.id.btn_bored)

        arguments?.let {
            alias = it.getInt("alias")
            title = it.getString("title").toString()
            reviewContent = it.getString("reviewContent").toString()
            genre = it.getString("genre").toString()
            description = it.getString("description").toString()
            ratingScore = it.getFloat("rating")
            emotion = it.getString("emotion").toString()
            recommend = it.getString("recommend").toString()
        }

        edt_title.setText(title)
        summary.setText(description)
        review.setText(reviewContent)
        ratingBar.rating = ratingScore

        when(emotion){
            "HAPPY" -> btn_happy.isChecked = true
            "SAD" -> btn_sad.isChecked = true
            "BORED" -> btn_bored.isChecked = true
            else -> false
        }

        when(recommend){
            "YES" -> btn_good.isChecked = true
            "NOPE" -> btn_hate.isChecked = true
            else -> false
        }

        //Event
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingScore = rating
        }

        emotion_btnEvent()
        recommend_btnEvent()
        btnEvent()

        db.close()
        return view
    }

    private fun emotion_btnEvent(){
        btn_happy.setOnClickListener{
            btn_sad.isChecked = false
            btn_bored.isChecked = false
            emotion = "HAPPY"
        }
        btn_sad.setOnClickListener {
            btn_happy.isChecked = false
            btn_bored.isChecked = false
            emotion = "SAD"
        }
        btn_bored.setOnClickListener {
            btn_sad.isChecked = false
            btn_happy.isChecked = false
            emotion = "BORED"
        }
    }

    private fun recommend_btnEvent(){
        btn_good.setOnClickListener{
            btn_hate.isChecked = false
            recommend = "YES"
        }
        btn_hate.setOnClickListener {
            btn_good.isChecked = false
            recommend = "NOPE"
        }
    }

    private fun btnEvent(){
        btn_add.setOnClickListener {
            title = edt_title.text.toString()
            reviewContent = review.text.toString()
            description = summary.text.toString()

            when(alias){
                0 -> db.REVIEW_Insert(title, reviewContent, genre, category, description, ratingScore, emotion, recommend)
                else -> db.REVIEW_Update(alias, title, reviewContent, genre, category, description, ratingScore, emotion, recommend)
            }
            (activity as MainActivity2).reviewToMypage()
        }

        btn_del.setOnClickListener {
            db.REVIEW_Delete(alias)
            (activity as MainActivity2).reviewToMypage()
        }
    }
}