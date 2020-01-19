package com.studlabs.ubbpregatireadmitere.login

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import com.studlabs.ubbpregatireadmitere.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@Suppress("SameParameterValue", "UNUSED_PARAMETER", "DEPRECATION")
class ResetPassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pass)
        setListeners()
    }

    fun onClickReset(view: View) {
        when {
            bind<EditText>(R.id.textEditNewPass).text.isEmpty() -> {
                showToast("New password must not be empty!")
            }
            bind<EditText>(R.id.textEditNewPass).text.toString().length < 5 -> {
                showToast("New password must be longer than 4 characters!")
            }
            else -> {
                newPassRequest()
            }
        }
    }

    private fun newPassRequest() {
        doAsync {
            val mURL = URL("http://188.26.72.103:3000/studlabs/api/change-password")
            val rootObject = JSONObject()
            rootObject.put("newPassword", bind<EditText>(R.id.textEditNewPass).text.toString())
            try {
                with(mURL.openConnection() as HttpURLConnection)
                {
                    setRequestProperty("Content-Type", "application/json")
                    setRequestProperty("Authorization", "Bearer " + LogInActivity.token)
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
                        it.close()
                        uiThread { finish() }
                    }
                }
            } catch (ex: Exception) {
                println(ex.toString())
                uiThread { finish() }
            }
        }
    }

    private fun setListeners() {
        bind<EditText>(R.id.textEditNewPass).onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(v)
                }
            }
    }

    private fun <T : View> Activity.bind(@IdRes res: Int): T {
        @Suppress("UNCHECKED_CAST")
        return findViewById(res)
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
}
