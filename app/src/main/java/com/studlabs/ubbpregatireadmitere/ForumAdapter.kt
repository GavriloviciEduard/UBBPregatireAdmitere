package com.studlabs.ubbpregatireadmitere

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_list.view.*

class ForumRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<ForumModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_list,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {

            is NewsViewHolder -> {
                holder.bind(items[position])
            }

        }
    }

    fun submitList(forumList: List<ForumModel>) {
        items = forumList
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class NewsViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val forumTitle : TextView = itemView.forum_title

        fun bind(forumModel: ForumModel) {
            forumTitle.text = forumModel.title
        }
    }


}