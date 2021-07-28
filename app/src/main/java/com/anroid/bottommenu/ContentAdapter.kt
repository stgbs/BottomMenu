package com.anroid.bottommenu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ContentView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ContentAdapter (val contentList: ArrayList<Content>): RecyclerView.Adapter<ContentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_content, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    override fun onBindViewHolder(holder: ContentAdapter.ViewHolder, position: Int) {
        holder.image.setImageResource(contentList.get(position).Image)
        holder.title.text = contentList.get(position).Title

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView?.context, ForumActivity::class.java)
            intent.putExtra("title", holder.title.text)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.content_Image)
        val title = itemView.findViewById<TextView>(R.id.content_Title)


    }
}