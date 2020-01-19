package com.studlabs.ubbpregatireadmitere

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_news_open.*

class NewsOpenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_open)
        titleNews.setTypeface(null, Typeface.BOLD)
        titleNews.text = News.data[News.mPosition].title
        bodyNews.text = News.data[News.mPosition].body
    }


}
