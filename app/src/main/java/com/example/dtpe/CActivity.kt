package com.example.dtpe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class CActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var client: OkHttpClient

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_c)
        recyclerView = findViewById(R.id.recyclerView)
        articleAdapter = ArticleAdapter(mutableListOf())
        recyclerView.adapter = articleAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        client = OkHttpClient()

        fetchArticles(page)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    // 到达列表底部，加载更多文章
                    page++
                    fetchArticles(page)
                }
            }
        })


//        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
//        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.menu_item1 -> {
//                    // 处理菜单项1的点击事件
//                    navigateToAPage()
//                    false
//                }
//                R.id.menu_item2 -> {
//                    // 处理菜单项2的点击事件
//                    navigateToBPage()
//                    false
//                }
//                R.id.menu_item3 -> {
//                    // 处理菜单项3的点击事件
//                    navigateToCPage()
//                    true
//                }
//                else -> false
//            }
//        }
    }

//    private fun navigateToCPage() {
//        val intent = Intent(this, CActivity::class.java)
//        startActivity(intent)
//    }
//
//    private fun navigateToBPage() {
//        val intent = Intent(this, BActivity::class.java)
//        startActivity(intent)
//    }
//
//    private fun navigateToAPage() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//    }

    private fun fetchArticles(page: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val url = "https://www.datianpao.com/dtp/store/back/store/banner/page"

            val json = JSONObject()
            json.put("storeId", "9")
            json.put("pageNum", page)
            json.put("pageSize", 10)
            json.put("bannerStatus", "")

            val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json.toString())
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            val client = OkHttpClient()
            try {
                val response = client.newCall(request).execute()
                val responseData = response.body?.string() // 使用 response.body 属性获取响应体
                val articles = parseArticlesFromResponse(responseData)
                launch(Dispatchers.Main) {
                    articleAdapter.addArticles(articles)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun parseArticlesFromResponse(responseData: String?): List<Article> {
        val articles = mutableListOf<Article>()

        val responseJson = JSONObject(responseData)
        val dataJson = responseJson.getJSONObject("data")
        val listJson = dataJson.getJSONArray("list")

        for (i in 0 until listJson.length()) {
            val itemJson = listJson.getJSONObject(i)
            val bannerId = itemJson.getString("bannerId")
            val bannerImageUrl = itemJson.getString("bannerImageUrl")

            val article = Article(bannerId, bannerImageUrl)
            articles.add(article)
        }

        return articles
    }
}