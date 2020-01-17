package com.studlabs.ubbpregatireadmitere

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
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

@Suppress("SameParameterValue", "UNUSED_PARAMETER")
class RegisterActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setListeners()
    }

    private fun setListeners()
    {
        bind<EditText>(R.id.textEditRegisterNewPassword).onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus)
                {
                    hideKeyboard(v)
                }
            }
        bind<EditText>(R.id.textEditRegisterUserName).onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus)
                {
                    hideKeyboard(v)
                }
            }
        bind<EditText>(R.id.textEditEmail).onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus)
                {
                    hideKeyboard(v)
                }
            }
        bind<EditText>(R.id.textEditFullName).onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus)
                {
                    hideKeyboard(v)
                }
            }
    }

    private fun hideKeyboard(view: View)
    {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun <T : View> Activity.bind(@IdRes res: Int): T
    {
        @Suppress("UNCHECKED_CAST")
        return findViewById(res)
    }

    fun onClickSave(view: View)
    {
        if (bind<EditText>(R.id.textEditRegisterNewPassword).text.isEmpty()
            && bind<EditText>(R.id.textEditRegisterUserName).text.isEmpty() &&
            bind<EditText>(R.id.textEditEmail).text.isEmpty() &&
            bind<EditText>(R.id.textEditFullName).text.isEmpty()
        )
        {
            Toast.makeText(this, "Register data must not be empty!", Toast.LENGTH_SHORT).show()
        }
        else
        {
            val name = bind<EditText>(R.id.textEditFullName).text.toString()
            val result = name.split(" ").map { it.trim() }
            result.toMutableList().add("")
            sendRegisterRequest(
                bind<EditText>(R.id.textEditRegisterUserName).text.toString(),
                bind<EditText>(R.id.textEditRegisterNewPassword).text.toString(),
                result[0],
                result[1],
                bind<EditText>(R.id.textEditEmail).text.toString()
            )
        }
    }

    private fun sendRegisterRequest(
        userName: String,
        password: String,
        FirstName: String,
        LastName: String,
        email: String
    )
    {
        doAsync {
            val mURL = URL("http://188.26.72.103:3000/studlabs/api/register")
            val rootObject = JSONObject()
            rootObject.put("email", email)
            rootObject.put("firstName", FirstName)
            rootObject.put("lastName", LastName)
            rootObject.put("password", password)
            rootObject.put("username", userName)
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
                        uiThread { finish() }
                        it.close()
                    }
                }
            }
            catch (ex: Exception)
            {
                uiThread { finish() }
                println(ex.toString())
            }
        }
    }
}
