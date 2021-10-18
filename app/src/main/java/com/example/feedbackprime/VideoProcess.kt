package com.example.feedbackprime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.feedbackprime.databinding.ActivityVideoProcessBinding
import org.json.JSONObject

class VideoProcess : AppCompatActivity() {
    lateinit var binding: ActivityVideoProcessBinding
    lateinit var token: String
    lateinit var url: String
    lateinit var name: String
    lateinit var conv: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        token = intent.extras?.getString("accessToken").toString()
        url = intent.extras?.getString("url").toString()
        name = intent.extras?.getString("name").toString()
        getConversationId()

        println(conv)
        Log.i("Conversation ID", "Conversation ID fetched")
    }

    private fun getConversationId() {
        val body = JSONObject()
        body.put("url", url)
        body.put("name", name)
        val queue = Volley.newRequestQueue(this)

        val req = object : JsonObjectRequest(Request.Method.POST, url, body,
            {
                conv = it.getString("conversationId")
                //just logs (ignore)
                Log.i("VideoProcess", "Conversation API id extracted")
                Log.i("Extraction", "API called second")
            }, {
                Toast.makeText(this, "Error in video", Toast.LENGTH_SHORT).show()
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer$token"
                return headers
            }
        }
//        req.headers.set("Authorization",token)
        queue.add(req)
    }
}