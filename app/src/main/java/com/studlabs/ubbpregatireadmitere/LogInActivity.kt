package com.studlabs.ubbpregatireadmitere

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import java.net.URL
import android.os.StrictMode
import java.net.SocketTimeoutException
import java.net.URLEncoder


@Suppress("SameParameterValue", "UNUSED_PARAMETER")
class LogInActivity : AppCompatActivity()
{

    private val resetPassURL = "http://www.google.com"
    private var mUsername = ""
    private var mPassword = ""
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setListeners()
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    private fun setListeners()
    {
        bind<EditText>(R.id.textEditUserName).onFocusChangeListener = View.OnFocusChangeListener {
                v, hasFocus ->  if (!hasFocus)  { hideKeyboard(v) } }
        bind<EditText>(R.id.textEditPassword).onFocusChangeListener = View.OnFocusChangeListener {
                v, hasFocus ->  if (!hasFocus)  { hideKeyboard(v) } }
    }

    private fun hideKeyboard(view: View)
    {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
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
        if(sendGetRequest(bind<EditText>(R.id.textEditUserName).text.toString(),bind<EditText>(R.id.textEditPassword).text.toString()))
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else
        {
            Toast.makeText(this, "User does not exist.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendGetRequest(userName:String, password:String) : Boolean
    {
        return true
        return try
        {
            mUsername = userName
            mPassword = password
            var reqParam = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8")
            reqParam += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
            val response = URL("http://172.20.10.3:8014//api//users?$reqParam").readText()
            response == "true"
        }
        catch(ex: SocketTimeoutException)
        {
            Toast.makeText(this, "Cannot connect to server.", Toast.LENGTH_SHORT).show()
            false
        }
    }
}