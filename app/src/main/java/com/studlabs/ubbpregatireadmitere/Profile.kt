package com.studlabs.ubbpregatireadmitere

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


class Profile : Fragment() {

    var mFullName = ""
    var mEmail = ""
    private lateinit var rootView:View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        val resetPassClick = rootView.findViewById<TextView>(R.id.textViewResetPassword)
        //rootView.findViewById<TextView>(R.id.textEditEmailProfile).text = LogInActivity.
        rootView.findViewById<TextView>(R.id.textEditUserNameProfile).text = LogInActivity.mUsername
        resetPassClick.setOnClickListener { startResetPassActivity() }
        getEmailRequest()
        getFullNameRequest()
        return rootView
    }

    private fun startResetPassActivity() {
        val intent = Intent(activity, ResetPassActivity::class.java)
        startActivity(intent)
    }

    private fun getFullNameRequest() {
        doAsync{
            val mURL = URL("http://188.26.72.103:3000/studlabs/api/fullname")
            try {
                with(mURL.openConnection() as HttpURLConnection)
                {
                    setRequestProperty("Authorization", "Bearer " + LogInActivity.token)
                    requestMethod = "GET"
                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()
                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        mFullName = response.toString()
                        println(response)
                        it.close()
                        uiThread {  rootView.findViewById<TextView>(R.id.textEditFullNameProfile).text = mFullName  }
                    }
                }
            } catch (ex: Exception) {
                println(ex.toString())
            }
        }
    }

    private fun getEmailRequest() {
        doAsync{
            val mURL = URL("http://188.26.72.103:3000/studlabs/api/email")
            try {
                with(mURL.openConnection() as HttpURLConnection)
                {
                    setRequestProperty("Authorization", "Bearer " + LogInActivity.token)
                    requestMethod = "GET"
                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()
                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        mEmail = response.toString()
                        it.close()
                        uiThread {  rootView.findViewById<TextView>(R.id.textEditEmailProfile).text = mEmail  }
                    }
                }
            } catch (ex: Exception) {
                println(ex.toString())
            }
        }
    }
}