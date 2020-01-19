package com.studlabs.ubbpregatireadmitere

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class QuizActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var mQuestions: List<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.mQuestions = Quizzes.questions
        setContentView(R.layout.activity_quiz)
        recyclerView = findViewById(R.id.questionRecyclerView)
        initRecyclerView()
        questionAdapter.submitList(mQuestions)
        questionAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            questionAdapter = QuestionAdapter()
            recyclerView.adapter = questionAdapter
        }
    }
}