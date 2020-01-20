package com.studlabs.ubbpregatireadmitere.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studlabs.ubbpregatireadmitere.quiz.Data.QuestionData
import com.studlabs.ubbpregatireadmitere.R

class QuizOpenActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var questionAdapter: QuestionRecyclerAdapter
    private lateinit var mQuestions: List<QuestionData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mQuestions = QuizFragment.questions
        setContentView(R.layout.activity_quiz_open)
        recyclerView = findViewById(R.id.questionRecyclerView)
        initRecyclerView()
        questionAdapter.submitList(mQuestions)
        questionAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            questionAdapter = QuestionRecyclerAdapter()
            recyclerView.adapter = questionAdapter
        }
    }
}