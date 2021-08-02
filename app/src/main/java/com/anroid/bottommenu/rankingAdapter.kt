package com.anroid.bottommenu

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlin.collections.ArrayList

class rankingAdapter(val context: Context, val contentList: ArrayList<rankContent>) : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.ranking_list_item, null)

        val Rank = view.findViewById<TextView>(R.id.rank_textView)
        val Image = view.findViewById<ImageView>(R.id.Image)
        val Title = view.findViewById<TextView>(R.id.Title_textView)
        val Descripton = view.findViewById<TextView>(R.id.description_textView)

        val content = contentList[position]
        val bitmap = BitmapFactory.decodeByteArray(contentList.get(position).Image, 0, contentList.get(position).Image.size)

        Rank.text = content.Rank.toString()
        Image.setImageBitmap(bitmap)
        Title.text = content.Title
        Descripton.text = content.description

        view.setOnClickListener{
            val intent = Intent(view?.context, ForumActivity::class.java)
            intent.putExtra("title", Title.text)
            ContextCompat.startActivity(view.context, intent, null)
        }

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