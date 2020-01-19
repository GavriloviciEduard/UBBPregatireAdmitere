package com.studlabs.ubbpregatireadmitere.quiz

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.studlabs.ubbpregatireadmitere.login.LogInActivity
import com.studlabs.ubbpregatireadmitere.quiz.Data.AnswerData
import com.studlabs.ubbpregatireadmitere.quiz.Data.QuestionData
import com.studlabs.ubbpregatireadmitere.quiz.Data.QuizData
import com.studlabs.ubbpregatireadmitere.R
import com.studlabs.ubbpregatireadmitere.utils.RecyclerItemClickListener
import com.studlabs.ubbpregatireadmitere.utils.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_quizzes.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class QuizFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var quizAdapter: QuizRecyclerAdapter
    private lateinit var quizzes: List<QuizData>

    companion object {
        lateinit var questions: List<QuestionData>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_quizzes, container, false)
        initRecyclerView()
        getQuizzesRequest()
        return rootView
    }

    private fun initRecyclerView(){
        rootView.recyclerViewQuiz.apply {
            rootView.recyclerViewQuiz.layoutManager = LinearLayoutManager(context)
            val topSpacingItemDecoration =
                TopSpacingItemDecoration(
                    30
                )
            addItemDecoration(topSpacingItemDecoration)
            quizAdapter = QuizRecyclerAdapter()
            rootView.recyclerViewQuiz.adapter = quizAdapter
            rootView.recyclerViewQuiz.setHasFixedSize(true)
            rootView.recyclerViewQuiz.addOnItemTouchListener(
                RecyclerItemClickListener(
                    context,
                    rootView.recyclerViewQuiz,
                    object :
                        RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            val intent = Intent(activity, QuizOpenActivity::class.java)
                            questions = quizzes[position].questions
                            startActivity(intent)
                        }

                        override fun onItemLongClick(view: View?, position: Int) {
                        }
                    })
            )
        }
    }

    private fun populateView(jsonString: String): MutableList<QuizData>  {
        val quizArray = JSONArray(jsonString)
        val quizList: MutableList<QuizData> = ArrayList()
        for(i in 0 until quizArray.length()) {
            var obj = quizArray.getJSONObject(i)
            val idQuiz = obj.getInt("id")
            val nameQuiz = obj.getString("name")
            val difficultyQuiz = obj.getString("difficulty")
            val quiz = QuizData(
                idQuiz,
                nameQuiz.toString(),
                difficultyQuiz.toString(),
                ArrayList()
            )
            val questionArray = obj.getJSONArray("questions")
            for(j in 0 until questionArray.length()) {
                obj = questionArray.getJSONObject(j)
                val idQuestion = obj.getInt("id")
                val contentQuestion = obj.getString("content")
                val question =
                    QuestionData(
                        idQuestion,
                        contentQuestion,
                        ArrayList()
                    )
                val answerArray = obj.getJSONArray("answers")
                for(k in 0 until answerArray.length()) {
                    obj = answerArray.getJSONObject(k)
                    val idAnswer = obj.getInt("id")
                    val contentAnswer = obj.getString("content")
                    val correctAnswer = obj.getBoolean("correct")
                    val answer =
                        AnswerData(
                            idAnswer,
                            contentAnswer,
                            correctAnswer
                        )
                    question.answers.add(answer)
                }
                quiz.questions.add(question)
            }
            quizList.add(quiz)
        }
        quizzes = quizList
        return quizList
    }

    private fun getQuizzesRequest() {
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
                        uiThread { quizAdapter.submitList(populateView(response.toString()))
                                    quizAdapter.notifyDataSetChanged() }
                    }
                }
            } catch (ex: Exception) {
                println(ex.toString())
            }
        }
    }
}