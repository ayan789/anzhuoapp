package com.example.dtpe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val textView = findViewById<TextView>(R.id.textView)
//        textView.text = "999!"

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener { v ->
            val intent = Intent(this@MainActivity, BActivity::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(getItemList())
        recyclerView.adapter = adapter

    }

    private fun getItemList(): List<Item> {
        // 返回图文列表项的数据集合
        // 每个 Item 包含图片资源 ID 和相应的文本
        return listOf(
            Item(R.drawable.w0, "Item 111"),
            Item(R.drawable.w0, "Item 2222"),
            Item(R.drawable.w0, "Item 333"),
            Item(R.drawable.w0, "Item !"),
            Item(R.drawable.w0, "Item @"),
            Item(R.drawable.w0, "Item ￥"),
            Item(R.drawable.w0, "Item %"),
            Item(R.drawable.w0, "Item ^"),
            Item(R.drawable.w0, "Item *"),
            // 可以添加更多的列表项
        )
    }
}