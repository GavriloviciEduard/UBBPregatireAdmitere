package com.studlabs.ubbpregatireadmitere

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.studlabs.ubbpregatireadmitere.forum.ForumFragment
import com.studlabs.ubbpregatireadmitere.news.NewsFragment
import com.studlabs.ubbpregatireadmitere.profile.ProfileFragment
import com.studlabs.ubbpregatireadmitere.quiz.QuizFragment
import com.studlabs.ubbpregatireadmitere.utils.TabAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabAdapter = TabAdapter(
            supportFragmentManager
        )
        tabAdapter.addFragment(ProfileFragment(), "")
        tabAdapter.addFragment(QuizFragment(), "")
        tabAdapter.addFragment(NewsFragment(), "")
        tabAdapter.addFragment(ForumFragment(), "")

        viewpager_main.adapter = tabAdapter
        tabs_main.setupWithViewPager(viewpager_main)

        tabs_main.getTabAt(0)?.setIcon(R.drawable.ic_profile)
        tabs_main.getTabAt(1)?.setIcon(R.drawable.ic_quiz)
        tabs_main.getTabAt(2)?.setIcon(R.drawable.ic_news)
        tabs_main.getTabAt(3)?.setIcon(R.drawable.ic_forum)
    }

    override fun onBackPressed() {
        logOutDialog()
    }

    private fun logOutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Log Out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton(android.R.string.yes) { _, _ -> finish() }
            .setNegativeButton(android.R.string.no) { _, _ -> return@setNegativeButton }
            .show()
    }


}
