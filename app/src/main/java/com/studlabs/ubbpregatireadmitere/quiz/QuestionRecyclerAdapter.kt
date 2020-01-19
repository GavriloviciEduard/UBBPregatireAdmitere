package com.studlabs.ubbpregatireadmitere.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studlabs.ubbpregatireadmitere.quiz.Data.QuestionData
import com.studlabs.ubbpregatireadmitere.R
import kotlinx.android.synthetic.main.layout_list_question.view.*

class QuestionRecyclerAdapter : RecyclerView.Adapter<QuestionRecyclerAdapter.QuestionViewHolder>() {

    private var items: List<QuestionData> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_list_question,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun submitList(newsList: List<QuestionData>) {
        items = newsList
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class QuestionViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val content: TextView = itemView.content
        private val verticalLayout: LinearLayout = itemView.vertical_layout

        fun bind(question: QuestionData) {
            content.text = question.content
            for(q in question.answers) {
                val checkBox = CheckBox(this.content.context)
                checkBox.text = q.content
                checkBox.isChecked = false
                verticalLayout.addView(checkBox)
            }
        }
    }
}