package com.aditya.applicationandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.applicationandroid.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: InfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = InfoAdapter()
        binding.rvListposts.adapter = mAdapter
        binding.rvListposts.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvListposts.setHasFixedSize(true)
        getPosts()
    }

    private fun getPosts() {
        val client = ApiConfig.getApiService().getPosts()
        client.enqueue(object : retrofit2.Callback<List<Posts>> {
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        mAdapter.submitList(responseBody)
                    }
                }
            }

            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}