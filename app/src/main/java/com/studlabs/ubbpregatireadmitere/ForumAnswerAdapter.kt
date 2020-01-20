package com.studlabs.ubbpregatireadmitere

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.forum_answer_list.view.*

class ForumAnswerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<ForumAnswerModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ForumAnswerHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.forum_answer_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {

            is ForumAnswerHolder -> {
                holder.bind(items[position])
            }
        }
    }

    fun submitList(forumList: List<ForumAnswerModel>) {
        items = forumList
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ForumAnswerHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val forumAnswerDescription: TextView = itemView.comment

        fun bind(forumModel: ForumAnswerModel) {
            forumAnswerDescription.text = forumModel.description
        }
    }
}