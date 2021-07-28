package com.anroid.bottommenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
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

            category.text = forum.category

            btnMore.setOnClickListener {
                val show = toggleLayout(!forum.isExpanded, it, layoutExpand)
                forum.isExpanded = show
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