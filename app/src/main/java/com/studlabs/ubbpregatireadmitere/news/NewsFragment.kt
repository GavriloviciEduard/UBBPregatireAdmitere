package com.studlabs.ubbpregatireadmitere.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.studlabs.ubbpregatireadmitere.*
import com.studlabs.ubbpregatireadmitere.login.LogInActivity
import com.studlabs.ubbpregatireadmitere.news.Data.NewsData
import com.studlabs.ubbpregatireadmitere.utils.RecyclerItemClickListener
import com.studlabs.ubbpregatireadmitere.utils.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_news.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class NewsFragment : Fragment() {

    companion object {
        val data = ArrayList<NewsData>()
        var mPosition : Int = 0
    }

    private lateinit var rootView: View
    private lateinit var newsAdapter: NewsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_news, container, false)
        initRecyclerView()
        getNewsRequest()
        return rootView
    }

    private fun addDataSet() {
        newsAdapter.submitList(data)
        newsAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        rootView.recycler_view.apply {
            rootView.recycler_view.layoutManager = LinearLayoutManager(context)
            val topSpacingItemDecoration =
                TopSpacingItemDecoration(
                    30
                )
            addItemDecoration(topSpacingItemDecoration)
            newsAdapter =
                NewsRecyclerAdapter()
            rootView.recycler_view.adapter = newsAdapter
            rootView.recycler_view.setHasFixedSize(true)
            rootView.recycler_view.addOnItemTouchListener(
                RecyclerItemClickListener(
                    context,
                    rootView.recycler_view,
                    object :
                        RecyclerItemClickListener.OnItemClickListener {

                        override fun onItemClick(view: View, position: Int) {
                            mPosition =
                                position
                            val intent = Intent(
                                activity,
                                NewsOpenActivity::class.java
                            )
                            startActivity(intent)
                        }

                        override fun onItemLongClick(view: View?, position: Int) {
                        }
                    })
            )

        }
    }

    private fun getNewsRequest() {
        doAsync {
            val mURL = URL("http://188.26.72.103:3000/studlabs/news/all")
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
                            data.add(
                                NewsData(
                                    element.get("title").toString(),
                                    element.get("text").toString(),
                                    element.get("createdOn").toString()
                                )
                            )
                        }
                        it.close()
                        uiThread { addDataSet() }
                    }
                }
            } catch (ex: Exception) {
                println(ex.toString())
            }
        }
    }
}