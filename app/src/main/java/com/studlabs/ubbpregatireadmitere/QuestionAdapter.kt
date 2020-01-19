package com.studlabs.ubbpregatireadmitere

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.question_layout.view.*

class QuestionAdapter : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    private var items: List<Question> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.question_layout, parent, false))
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun submitList(newsList: List<Question>) {
        items = newsList
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class QuestionViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val content: TextView = itemView.content
        private val verticalLayout: LinearLayout = itemView.vertical_layout

        fun bind(question: Question) {
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