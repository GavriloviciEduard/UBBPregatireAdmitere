package com.studlabs.ubbpregatireadmitere.forum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.studlabs.ubbpregatireadmitere.R

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
