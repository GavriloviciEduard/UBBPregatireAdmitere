package com.studlabs.ubbpregatireadmitere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.studlabs.ubbpregatireadmitere.forum.ForumFragment
import com.studlabs.ubbpregatireadmitere.utils.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_forum_answers.*
import kotlinx.android.synthetic.main.fragment_forum.view.*

class ForumAnswers : AppCompatActivity() {

    private lateinit var forumAdapter: ForumAnswerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum_answers)
        initRecyclerView()
        forum_answer_description.text = ForumFragment.data[ForumFragment.mPosition].description
        addDataSet()
    }

    private fun addDataSet(){
        val comments = ArrayList<ForumAnswerModel>()
        for(el in ForumFragment.data[ForumFragment.mPosition].comments) {
            comments.add(ForumAnswerModel(el.body))
        }
        forumAdapter.submitList(comments)
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            recycler_view.layoutManager = LinearLayoutManager(this@ForumAnswers)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            forumAdapter = ForumAnswerAdapter()
            recycler_view.adapter = forumAdapter
        }
    }
}
