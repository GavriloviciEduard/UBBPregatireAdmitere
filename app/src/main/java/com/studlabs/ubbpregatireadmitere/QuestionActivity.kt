package com.studlabs.ubbpregatireadmitere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class QuestionActivity : AppCompatActivity() {

    private var title = ""
    private var body = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forum_question)
    }

    fun onClickSubmit(view: View) {

    }
}
