package com.anroid.bottommenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ForumExpandableAdapter(private val forumList: List<Forum>, private val title: String) : RecyclerView.Adapter<ForumExpandableAdapter.viewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForumExpandableAdapter.viewHolder {
        return viewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_forum,
                parent,
                false
            ), title
        )
    }

    override fun onBindViewHolder(holder: ForumExpandableAdapter.viewHolder, position: Int) {
        holder.bind(forumList[position])
    }

    override fun getItemCount(): Int {
        return forumList.size
    }

    class viewHolder(itemView: View, private val title: String) : RecyclerView.ViewHolder(itemView) {
        lateinit var myHelper : DBHelper
        fun bind(forum: Forum) {
            val category = itemView.findViewById<TextView>(R.id.text_Category)
            val btnMore = itemView.findViewById<ImageButton>(R.id.btn_more)
            val layoutExpand = itemView.findViewById<LinearLayout>(R.id.layout_expand)
            val text_content = itemView.findViewById<TextView>(R.id.content_forum)
            val edit_content = itemView.findViewById<EditText>(R.id.edit_content_forum)
            val btn_save = itemView.findViewById<Button>(R.id.btn_save)

            myHelper = DBHelper(itemView.getContext(), "GURU", null, 1)

            // WIKI 페이지의 목차, 목차 별 내용(TextView, EditText) 세팅
            category.text = forum.category
            text_content.text = forum.content
            edit_content.setText(forum.content)

            // 더보기 버튼 클릭 이벤트
            btnMore.setOnClickListener {
                val show = toggleLayout(!forum.isExpanded, it, layoutExpand)
                forum.isExpanded = show
            }

            // 텍스트뷰 클릭 이벤트
            text_content.setOnClickListener{
                Toast.makeText(itemView.getContext(), "텍스트를 길게 눌러 내용을 추가하세요!", Toast.LENGTH_SHORT).show()
            }
            
            // 텍스트뷰 롱클릭 이벤트
            // 텍스트뷰가 GONE, 에디트 텍스트와 저장 버튼이 VISIBLE 되면서 텍스트 수정 가능  
            text_content.setOnLongClickListener{
                text_content.visibility = View.GONE
                edit_content.visibility = View.VISIBLE
                btn_save.visibility = View.VISIBLE

                return@setOnLongClickListener true
            }
            
            // 저장 버튼 클릭 이벤트
            // 에디트 텍스트, 저장 버튼이 GONE, 텍스트 뷰가 VISIBLE
            // 수정된 내용을 DB 내 현재 itemView와 같은 Postion에 저장
            // 텍스트 뷰의 내용을 에디트 텍스트의 입력 데이터로 즉시 업데이트
            btn_save.setOnClickListener {
                text_content.visibility = View.VISIBLE
                edit_content.visibility = View.GONE
                btn_save.visibility = View.GONE

                var update_text = edit_content.getText().toString();
                val itemPosition = getAdapterPosition()
                myHelper.WIKI_Update(update_text, title, itemPosition)
                Toast.makeText(itemView.getContext(), "저장되었습니다", Toast.LENGTH_SHORT).show()

                text_content.text = edit_content.text
            }

            myHelper.close()
        }

        // isExpanded(접기/펼치기) T, F 여부에 따라 확장
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