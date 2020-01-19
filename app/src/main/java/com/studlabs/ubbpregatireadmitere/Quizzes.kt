package com.studlabs.ubbpregatireadmitere

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.*
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

class Quizzes : Fragment() {
    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var quizAdapter: QuizAdapter

    companion object {
        lateinit var questions: List<Question>
        private lateinit var quizes: List<Quiz>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_quizzes, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerViewQuiz)
        initRecyclerView()
        rootView.recyclerViewQuiz.adapter = quizAdapter
        getQuizzes()
        return rootView
    }

    private fun initRecyclerView(){
        rootView.recyclerViewQuiz.apply {
            rootView.recyclerViewQuiz.layoutManager = LinearLayoutManager(context)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            quizAdapter = QuizAdapter()
            rootView.recyclerViewQuiz.adapter = quizAdapter
            recyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(context, rootView.recyclerViewQuiz, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(activity, QuizActivity::class.java)
                        questions = quizes[position].questions
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
            )
        }
    }


    private fun populateView(jsonString: String): MutableList<Quiz>  {
        val quizArray = JSONArray(jsonString)
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
        }
        quizes = quizList
        return quizList
    }

    private fun getQuizzes() {
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

class RecyclerItemClickListener(context: Context, recyclerView: RecyclerView, private val mListener: OnItemClickListener?) : RecyclerView.OnItemTouchListener {

    private val mGestureDetector: GestureDetector

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)

        fun onItemLongClick(view: View?, position: Int)
    }

    init {

        mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val childView = recyclerView.findChildViewUnder(e.x, e.y)

                if (childView != null && mListener != null) {
                    mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView))
                }
            }
        })
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)

        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView))
        }

        return false
    }

    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}}