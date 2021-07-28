package com.anroid.bottommenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ExpandableAdapter (private val forumList: List<Forum>) : RecyclerView.Adapter<ExpandableAdapter.viewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpandableAdapter.viewHolder {
        return viewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_forum, parent, false))
    }

    override fun onBindViewHolder(holder: ExpandableAdapter.viewHolder, position: Int) {
        holder.bind(forumList[position])
    }

    override fun getItemCount(): Int {
        return forumList.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(forum: Forum) {
            val category = itemView.findViewById<TextView>(R.id.text_Category)
            val btnMore = itemView.findViewById<ImageButton>(R.id.btn_more)
            val layoutExpand = itemView.findViewById<LinearLayout>(R.id.layout_expand)
            val text_content = itemView.findViewById<TextView>(R.id.content_forum)
            val edit_content = itemView.findViewById<EditText>(R.id.edit_content_forum)
            val btn_save = itemView.findViewById<Button>(R.id.btn_save)

            category.text = forum.category

            btnMore.setOnClickListener {
                val show = toggleLayout(!forum.isExpanded, it, layoutExpand)
                forum.isExpanded = show
            }
            
            text_content.setOnClickListener{
                Toast.makeText(itemView.getContext(), "텍스트를 길게 눌러 내용을 추가하세요!", Toast.LENGTH_SHORT).show()
            }
            text_content.setOnLongClickListener{
                text_content.visibility = View.GONE
                edit_content.visibility = View.VISIBLE
                btn_save.visibility = View.VISIBLE

                return@setOnLongClickListener true
            }
            btn_save.setOnClickListener {
                text_content.visibility = View.VISIBLE
                edit_content.visibility = View.GONE
                btn_save.visibility = View.GONE
            }

        }

        private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
            ToggleAnimation.toggleArrow(view, isExpanded)
            if (isExpanded) {
                ToggleAnimation.expand(layoutExpand)
            } else {
                ToggleAnimation.collapse(layoutExpand)
            }
            return isExpanded
        }
    }
}