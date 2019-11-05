package com.studlabs.ubbpregatireadmitere

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes



@Suppress("SameParameterValue", "UNUSED_PARAMETER")
class LogInActivity : AppCompatActivity()
{
    private val resetPassURL = "http://www.google.com"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setListeners()
    }

    private fun setListeners()
    {
        bind<EditText>(R.id.textEditUserName).onFocusChangeListener = View.OnFocusChangeListener{
                v, hasFocus ->  if (!hasFocus)  { hideKeyboard(v) } }
        bind<EditText>(R.id.textEditPassword).onFocusChangeListener = View.OnFocusChangeListener {
                v, hasFocus ->  if (!hasFocus)  { hideKeyboard(v) } }
    }

    private fun hideKeyboard(view: View)
    {
        //TODO
        //val inputMethodManager =
            //getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun <T : View> Activity.bind(@IdRes res : Int) : T
    {
        @Suppress("UNCHECKED_CAST")
        return findViewById(res)
    }


    private fun openURL(url: String)
    {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        startActivity(openURL)
    }

    fun onClickForgotPassword(view: View)
    {
        openURL(resetPassURL)
    }

    fun onClickLogIn(view: View)
    {
        logInEvent()
    }

    private fun logInEvent()
    {
        if(bind<EditText>(R.id.textEditUserName).text.isEmpty() && bind<EditText>(R.id.textEditPassword).text.isEmpty())
        {
            Toast.makeText(this, "Credentials must not be empty!", Toast.LENGTH_SHORT).show()
        }

        else if(  bind<EditText>(R.id.textEditUserName).text.isEmpty())
        {
            Toast.makeText(this, "Username must not be empty!", Toast.LENGTH_SHORT).show()
        }

        else if(  bind<EditText>(R.id.textEditPassword).text.isEmpty())
        {
            Toast.makeText(this, "Password must not be empty!", Toast.LENGTH_SHORT).show()
        }

        else
        {
            logInServerRequest()
        }
    }

    private fun logInServerRequest()
    {
        Toast.makeText(this, "Not Implemented yet!", Toast.LENGTH_SHORT).show()
    }
}
