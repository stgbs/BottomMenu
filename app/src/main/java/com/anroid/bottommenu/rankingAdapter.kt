package com.anroid.bottommenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlin.collections.ArrayList

class rankingAdapter(val context: Context, val contentList: ArrayList<rankContent>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.ranking_list_item, null)

        val Rank = view.findViewById<TextView>(R.id.rank_textView)
        val Image = view.findViewById<ImageView>(R.id.Movie_Image)
        val Title = view.findViewById<TextView>(R.id.Title_textView)
        val Descripton = view.findViewById<TextView>(R.id.description_textView)

        val movie = contentList[position]

        Rank.text = movie.Rank.toString()
        Image.setImageResource(movie.Image)
        Title.text = movie.Title
        Descripton.text = movie.description

        return view
    }

    override fun getItem(position: Int): Any {
        return contentList[position]
    }

    override fun getItemId(position: Int): Long {
        /*사용 안 함 */
        return 0
    }

    override fun getCount(): Int {
        return contentList.size
    }

}