package com.anroid.bottommenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class MypageFragment : Fragment() {

    lateinit var db: DBHelper

    lateinit var mypageList: ListView
    var reviewList: List<Review> = ArrayList<Review>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mypage, container, false)

        val context = getActivity()

        mypageList = view.findViewById<ListView>(R.id.mypageList)
        db = DBHelper(getActivity(), "GURU", null, 1)

        reviewList = db.REVIEW_Select()
        val Adapter = ReviewAdapter(context!!, reviewList)
        mypageList.adapter = Adapter

        return view
    }

}