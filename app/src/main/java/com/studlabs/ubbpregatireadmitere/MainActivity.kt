package com.studlabs.ubbpregatireadmitere

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


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

    private fun createTabAdapter()
    {

    }

    override fun onBackPressed()
    {
        logOutDialog()
    }

    private fun logOutDialog()
    {
        AlertDialog.Builder(this)
            .setTitle("Log Out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton(android.R.string.yes) { _, _ -> finish() }
            .setNegativeButton(android.R.string.no) { _, _ -> return@setNegativeButton  }
            .show()
    }

    private fun <T : View> Activity.bind(@IdRes res : Int) : T
    {
        @Suppress("UNCHECKED_CAST")
        return findViewById(res)
    }
}
