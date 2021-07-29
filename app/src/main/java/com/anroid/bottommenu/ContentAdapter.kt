package com.anroid.bottommenu

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayInputStream
import java.util.*


class ContentAdapter(val contentList: ArrayList<Content>): RecyclerView.Adapter<ContentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_content, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    override fun onBindViewHolder(holder: ContentAdapter.ViewHolder, position: Int) {
        val bitmap = BitmapFactory.decodeByteArray(contentList.get(position).Image, 0, contentList.get(position).Image.size)
        holder.image.setImageBitmap(bitmap)
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