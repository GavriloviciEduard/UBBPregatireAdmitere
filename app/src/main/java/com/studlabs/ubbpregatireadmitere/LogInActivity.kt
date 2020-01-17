package com.studlabs.ubbpregatireadmitere

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


@Suppress("SameParameterValue", "UNUSED_PARAMETER", "DEPRECATION")
class LogInActivity : AppCompatActivity() {
    private var token = ""
    private var mUsername = ""
    private var mPassword = ""

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
        logInEvent()
    }

    private fun logInEvent() {
        if (bind<EditText>(R.id.textEditUserName).text.isEmpty() && bind<EditText>(R.id.textEditPassword).text.isEmpty()) {
            Toast.makeText(this, "Credentials must not be empty!", Toast.LENGTH_SHORT).show()
        } else if (bind<EditText>(R.id.textEditUserName).text.isEmpty()) {
            Toast.makeText(this, "Username must not be empty!", Toast.LENGTH_SHORT).show()
        } else if (bind<EditText>(R.id.textEditPassword).text.isEmpty()) {
            Toast.makeText(this, "Password must not be empty!", Toast.LENGTH_SHORT).show()
        } else {
            logInRequest()
        }
    }

    private fun logInRequest() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun logIn() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    fun onClickRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}