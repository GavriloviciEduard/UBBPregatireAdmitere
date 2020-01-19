package com.studlabs.ubbpregatireadmitere.news

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.studlabs.ubbpregatireadmitere.R
import kotlinx.android.synthetic.main.activity_news_open.*

class NewsOpenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_open)
        titleNews.setTypeface(null, Typeface.BOLD)
        titleNews.text = NewsFragment.data[NewsFragment.mPosition].title
        bodyNews.text = NewsFragment.data[NewsFragment.mPosition].body
    }
}
