package com.studlabs.ubbpregatireadmitere.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studlabs.ubbpregatireadmitere.quiz.Data.QuizData
import com.studlabs.ubbpregatireadmitere.R
import kotlinx.android.synthetic.main.layout_list_quiz.view.*

class QuizRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<QuizData> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return QuizViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_list_quiz,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is QuizViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    fun submitList(newsList: List<QuizData>) {
        items = newsList
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class QuizViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.name
        private val difficulty: TextView = itemView.difficulty
        private val numberOfQuestions: TextView = itemView.numberOfQuestions

        fun bind(quiz: QuizData) {
            name.text = quiz.name
            difficulty.text = "Difficulty: " + quiz.difficulty
            numberOfQuestions.text = "Number of questions: " + quiz.questions.count().toString()
        }
    }
}