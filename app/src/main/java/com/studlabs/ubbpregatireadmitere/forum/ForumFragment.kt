package com.studlabs.ubbpregatireadmitere.forum

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.studlabs.ubbpregatireadmitere.*
import com.studlabs.ubbpregatireadmitere.login.LogInActivity
import com.studlabs.ubbpregatireadmitere.utils.RecyclerItemClickListener
import com.studlabs.ubbpregatireadmitere.utils.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_forum.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ForumFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var forumAdapter: ForumRecyclerAdapter

    companion object {
        val data = ArrayList<ForumModel>()
        var mPosition: Int = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_forum, container, false)
        initRecyclerView()
        addDataSet()
        val newPassClick = rootView.findViewById<Button>(R.id.newQuestion)
        newPassClick.setOnClickListener { startNewQuestionActivity() }
        getDataRequest()
        return rootView
    }

    private fun addDataSet() {
        forumAdapter.submitList(data)
        forumAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        rootView.recycler_view.apply {
            rootView.recycler_view.layoutManager = LinearLayoutManager(context)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            forumAdapter = ForumRecyclerAdapter()
            rootView.recycler_view.adapter = forumAdapter
            rootView.recycler_view.setHasFixedSize(true)
            rootView.recycler_view.addOnItemTouchListener(
                RecyclerItemClickListener(
                    context,
                    rootView.recycler_view,
                    object : RecyclerItemClickListener.OnItemClickListener {

                        override fun onItemClick(view: View, position: Int) {
                            mPosition = position
                            val intent = Intent(activity, ForumAnswers::class.java)
                            startActivity(intent)
                        }

                        override fun onItemLongClick(view: View?, position: Int) {
                        }
                    })
            )
        }
    }

    private fun getDataRequest() {
        doAsync {
            val mURL = URL("http://188.26.72.103:3000/studlabs/forum/all")
            try {
                with(mURL.openConnection() as HttpURLConnection)
                {
                    setRequestProperty("Authorization", "Bearer " + LogInActivity.token)
                    requestMethod = "GET"
                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()
                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        val quizArray = JSONArray(response.toString())
                        for (i in 0 until quizArray.length()) {
                            val element = quizArray.getJSONObject(i)
                            val comments = ArrayList<Comment>()
                            val commentsArray = JSONArray(response.toString())

                            for (j in 0 until commentsArray.length()) {
                                val element1 = quizArray.getJSONObject(j)
                                comments.add(
                                    Comment(
                                        element1.get("text").toString()
                                    )
                                )
                            }
                            data.add(
                                ForumModel(
                                    element.get("title").toString(),
                                    element.get("rating").toString(),
                                    element.get("createdOn").toString(),
                                    comments,
                                    element.get("text").toString()
                                )
                            )
                        }
                        it.close()
                        uiThread { addDataSet() }
                    }
                }
            } catch (ex: Exception) {
            }
        }

    }

    private fun startNewQuestionActivity() {
        val intent = Intent(context, QuestionActivity::class.java)
        startActivity(intent)
    }
}