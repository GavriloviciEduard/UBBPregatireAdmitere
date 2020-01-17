package com.studlabs.ubbpregatireadmitere

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

@Suppress("SameParameterValue", "UNUSED_PARAMETER")
class ForgotPassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        setListeners()
    }

    private fun setListeners()
    {
        bind<EditText>(R.id.textEditEmailReset).onFocusChangeListener = View.OnFocusChangeListener {
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

    fun onClickReset(view: View)
    {
        if(bind<EditText>(R.id.textEditEmailReset).text.isEmpty())
        {
            Toast.makeText(this, "Email must not be empty!", Toast.LENGTH_SHORT).show()
        }
        else
        {
            sendPostRequest(bind<EditText>(R.id.textEditEmailReset).text.toString())
        }
    }
    private fun sendPostRequest(email:String)
    {
        val mURL = URL("https://login-proiect-colectiv.herokuapp.com/login/api/forgot-password")
        val rootObject= JSONObject()
        rootObject.put("email",email)
        try
        {
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
                    while (inputLine != null)
                    {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()

                }
            }
        }
        catch (ex:Exception)
        {
            println(ex.toString())
        }
    }

}
