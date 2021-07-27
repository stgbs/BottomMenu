package com.anroid.bottommenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class MovieAdapter(val context: Context, val MovieList : ArrayList<Movie>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item_movie, null)

        val movieRank = view.findViewById<TextView>(R.id.rank_textView)
        val movieImage = view.findViewById<ImageView>(R.id.Movie_Image)
        val movieTitle = view.findViewById<TextView>(R.id.Title_textView)
        val movieDescripton = view.findViewById<TextView>(R.id.description_textView)

        val movie = MovieList[position]

        movieRank.text = movie.Rank.toString()
        movieImage.setImageResource(movie.Image)
        movieTitle.text = movie.Title
        movieDescripton.text = movie.description

        return view
    }

    override fun getItem(position: Int): Any {
        return MovieList[position]
    }

    override fun getItemId(position: Int): Long {
        /*사용 안 함 */
        return 0
    }

    override fun getCount(): Int {
        return MovieList.size
    }

}