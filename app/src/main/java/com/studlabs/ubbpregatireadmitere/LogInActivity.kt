package com.studlabs.ubbpregatireadmitere

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


@Suppress("SameParameterValue", "UNUSED_PARAMETER", "DEPRECATION")
class LogInActivity : AppCompatActivity() {
    companion object {
        var token = ""
        var mUsername = ""
        var mPassword = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setListeners()
    }

    private fun setListeners() {
        bind<EditText>(R.id.textEditUserName).onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(v)
                }
            }
        bind<EditText>(R.id.textEditPassword).onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(v)
                }
            }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun <T : View> Activity.bind(@IdRes res: Int): T {
        @Suppress("UNCHECKED_CAST")
        return findViewById(res)
    }

    fun onClickForgotPassword(view: View) {
        val intent = Intent(this, ForgotPassActivity::class.java)
        startActivity(intent)
    }

    fun onClickLogIn(view: View) {
        bind<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
        logInEvent()
    }

    private fun logInEvent() {
        if (bind<EditText>(R.id.textEditUserName).text.isEmpty() && bind<EditText>(R.id.textEditPassword).text.isEmpty()) {
            showToast("Credentials must not be empty!")
        } else if (bind<EditText>(R.id.textEditUserName).text.isEmpty()) {
            showToast("Username must not be empty!")
        } else if (bind<EditText>(R.id.textEditPassword).text.isEmpty()) {
            showToast("Password must not be empty!")
        } else {
            logInRequest()
        }
    }

    private fun logInRequest() {
        doAsync {
            mUsername = bind<EditText>(R.id.textEditUserName).text.toString()
            mPassword = bind<EditText>(R.id.textEditPassword).text.toString()
            val mURL = URL("http://188.26.72.103:3000/studlabs/api/auth")
            val rootObject = JSONObject()
            rootObject.put("username", mUsername)
            rootObject.put("password", mPassword)
            try {
                with(mURL.openConnection() as HttpURLConnection)
                {
                    setRequestProperty("Content-Type", "application/json")
                    requestMethod = "POST"
                    val wr = OutputStreamWriter(outputStream)
                    wr.write(rootObject.toString())
                    wr.flush()
                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()
                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        val responseObject = JSONObject(response.toString())
                        token = responseObject.get("accessToken").toString()
                        it.close()
                        uiThread { logIn() }
                    }
                }
            } catch (ex: Exception) {
                uiThread { showToast("User does not exist!") }
            }
        }
    }

    private fun logIn() {
        bind<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(str: String) {
        bind<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    fun onClickRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}