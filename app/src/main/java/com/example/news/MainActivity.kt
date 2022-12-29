package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

//import kotlinx.android.synthetic.main.activity.main.*

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter=mAdapter
    }
    private fun fetchData(){
        val url="https://newsapi.org/v2/top-headlines?country=in&apiKey=c8a9ad34713d481587acafaccdd4a47c"
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,
                {
                val newsJsonArray=it.getJSONArray("articles")
                    val newsArray=ArrayList<News>()
                    for (i in 0 until newsJsonArray.length())
                    {
                        val newsJsonObject=newsJsonArray.getJSONObject(i)
                        val news=News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                        )
                        newsArray.add(news)
                    }
                    mAdapter.updateNews(newsArray)
            },
                {

                 }
            )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClick(item: News) {
//        Toast.makeText(this,"clicked item $item", Toast.LENGTH_LONG).show()
    }
}