package com.studlabs.ubbpregatireadmitere

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.net.SocketTimeoutException
import java.net.URL

class ProblemsFragment : Fragment()
{
    private var listProblems = mutableListOf<String>()
    private var listDescriptions = mutableListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_problems, container, false)
        createProblemList(view)
        return view
    }

    private fun sendGetRequest(): List<String>
    {
        return try
        {
            val response = URL("http://172.20.10.3:8014//api//problems").readText()
            val result: List<String> = response.split(",").map { it.trim() }
            result
        }
        catch(ex: SocketTimeoutException)
        {
            emptyList()
        }
    }

    private fun createProblemList(view: View)
    {
        val lv = view.findViewById(R.id.problems_list) as ListView
        val result = sendGetRequest()
        for (x in result.indices)
        {
            if (x%2==0)
            {
                listProblems.add(result[x])
            }
            else
            {
                listDescriptions.add(result[x])
            }
        }
        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1,
                listProblems) }
        lv.adapter = adapter
        lv.setOnItemClickListener { _, _, position, _ ->

            Toast.makeText(activity, listDescriptions[position],Toast.LENGTH_LONG).show()
        }
    }
}