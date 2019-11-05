package com.studlabs.ubbpregatireadmitere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.addFragment(ProfileFragment(),"Profile")
        tabAdapter.addFragment(QuizzesFragment(), "Quizzes")
        tabAdapter.addFragment(ProblemsFragment(), "Problems")
        tabAdapter.addFragment(ForumFragment(), "Forum")

        viewpager_main.adapter = tabAdapter
        tabs_main.setupWithViewPager(viewpager_main)

        tabs_main.getTabAt(0)?.setIcon(R.drawable.ic_profile)
        tabs_main.getTabAt(1)?.setIcon(R.drawable.ic_quiz)
        tabs_main.getTabAt(2)?.setIcon(R.drawable.ic_problems)
        tabs_main.getTabAt(3)?.setIcon(R.drawable.ic_forum)
    }
}
