package com.studlabs.ubbpregatireadmitere

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_quizzes.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class TopSpacingItemDecoration(private val padding: Int):RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = padding
    }
}


class Quizzes : Fragment() {
    private lateinit var rootView:View
    private lateinit var recyclerView: RecyclerView
    private lateinit var quizAdapter: QuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_quizzes, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerViewQuiz)
        initRecyclerView()
        rootView.recyclerViewQuiz.adapter = quizAdapter
        getQuizes()
        return rootView
    }

    private fun initRecyclerView(){
        rootView.recyclerViewQuiz.apply {
            rootView.recyclerViewQuiz.layoutManager = LinearLayoutManager(context)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            quizAdapter = QuizAdapter()
            rootView.recyclerViewQuiz.adapter = quizAdapter
        }
    }

    private fun getQuizes() {
        doAsync {
            val mURL = URL("http://188.26.72.103:3000/studlabs/quiz/all")
            try {
                with(mURL.openConnection() as HttpURLConnection)
                {
                    setRequestProperty("Authorization", "Bearer " + LogInActivity.token)
                    requestMethod = "GET"
                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()
                        var inputLine = it.readLine()
                        while(inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        val quizArray = JSONArray(response.toString())
                        val quizList: MutableList<Quiz> = ArrayList()
                        var i = 0
                        while (i < quizArray.length()) {
                            var obj = quizArray.getJSONObject(i)
                            println(i)
                            i++
                            val idQuiz = obj.getInt("id")
                            val nameQuiz = obj.getString("name")
                            val difficultyQuiz = obj.getString("difficulty")
                            val quiz = Quiz(idQuiz, nameQuiz.toString(), difficultyQuiz.toString())
                            val questionArray = obj.getJSONArray("questions")
                            var j = 0
                            while (j < questionArray.length()) {
                                obj = questionArray.getJSONObject(j)
                                j++
                                val idQuestion = obj.getInt("id")
                                val contentQuestion = obj.getString("content")
                                val question = Question(idQuestion, contentQuestion)
                                val answerArray = obj.getJSONArray("answers")
                                var k = 0
                                while (k < answerArray.length()) {
                                    obj = answerArray.getJSONObject(k)
                                    k++
                                    val idAnswer = obj.getInt("id")
                                    val contentAnswer = obj.getString("content")
                                    val correctAnswer = obj.getBoolean("correct")
                                    val answer = Answer(idAnswer, contentAnswer, correctAnswer)
                                    question.answers.add(answer)
                                }
                                quiz.questions.add(question)
                            }
                            quizList.add(quiz)
                            println(quizList.count())
                            uiThread { quizAdapter.submitList(quizList)
                                quizAdapter.notifyDataSetChanged() }
                        }
                    }
                }
            } catch (ex: Exception) {
                println(ex.toString())
            }
        }
    }
}