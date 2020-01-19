package com.studlabs.ubbpregatireadmitere

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.quiz_layout.view.*

class QuizAdapter : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {
    private var items: List<Quiz> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        return QuizViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.quiz_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        when(holder) {
            is QuizViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    fun submitList(newsList: List<Quiz>) {
        items = newsList
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class QuizViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.name
        private val difficulty: TextView = itemView.difficulty

        fun bind(quiz: Quiz) {
            name.text = quiz.name
            difficulty.text = quiz.difficulty
        }
    }
}