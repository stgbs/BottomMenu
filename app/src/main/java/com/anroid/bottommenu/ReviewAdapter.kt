package com.anroid.bottommenu

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ReviewAdapter(val context: Context, val reviewList: List<Review>): BaseAdapter() {

    private val mainActivity = MainActivity.getInstance()
    private var alias: Int = 0
    private var title: String = ""
    private var reviewContent = ""
    private var description = ""
    private var rating = 0.0f
    private var emotion = ""
    private var recommend = ""

    lateinit var db: DBHelper
    lateinit var sqlDB: SQLiteDatabase

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View = LayoutInflater.from(context).inflate(R.layout.review_contents, null)

        val textId = rowView.findViewById<TextView>(R.id.textId)
        val imageView = rowView.findViewById<ImageView>(R.id.imageView)
        val textContent = rowView.findViewById<TextView>(R.id.textContent)
        val textDescription = rowView.findViewById<TextView>(R.id.textDescription)

        textId.text = reviewList[position].alias.toString()
        imageView.setImageResource(R.drawable.image)         // Person 클래스 이미지 없음
        textContent.text = reviewList[position].title
        textDescription.text = reviewList[position].description

        db = DBHelper(context, "GURU", null, 1)
        sqlDB = db.readableDatabase
        var cursor: Cursor
        alias = reviewList[position].alias
        cursor = sqlDB.rawQuery("SELECT * FROM REVIEW WHERE alias='$alias';", null) // MEMBER를 조건으로

        while (cursor.moveToNext()){
            alias = cursor.getInt(0)
            title = cursor.getString(1)
            //image = cursor.getBlob(2)
            //category = cursor.getString(3)
            reviewContent = cursor.getString(4)
            description = cursor.getString(5)
            rating = cursor.getFloat(6)
            emotion = cursor.getString(7)
            recommend = cursor.getString(8)
        }
        val review_content = Review(alias, title, reviewContent, description, rating, emotion, recommend)

        //이벤트
        rowView.setOnClickListener {
            mainActivity?.mypageToReview(ReviewFragment(), review_content)
        }

        db.close()
        sqlDB.close()
        cursor.close()
        return rowView
    }

    override fun getCount(): Int {
        return reviewList.size
    }

    override fun getItem(position: Int): Any {
        return reviewList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
}