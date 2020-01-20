package com.studlabs.ubbpregatireadmitere.news

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studlabs.ubbpregatireadmitere.news.Data.NewsData
import com.studlabs.ubbpregatireadmitere.R
import kotlinx.android.synthetic.main.layout_list_news.view.*

class NewsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<NewsData> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_list_news,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {

            is NewsViewHolder -> {
                holder.bind(items[position])
            }
        }

    }

    fun submitList(newsList: List<NewsData>) {
        items = newsList
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class NewsViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val newsTitle: TextView = itemView.news_title

        fun bind(newsData: NewsData) {
            newsTitle.setTypeface(null, Typeface.BOLD)
            newsTitle.text = newsData.title
        }

    }
}