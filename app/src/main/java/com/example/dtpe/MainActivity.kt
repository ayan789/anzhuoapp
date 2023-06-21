package com.example.dtpe

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


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

        val url = "https://category.dangdang.com/Standard/Search/Extend/hosts/api/json_api.php?cat_ids=16836&cat_paths=01.03.40.00.00.00&method=list.catepagebang|list.catebav|list.caterelatepid|&fileds=product_name,sale_price,percent,sub_title|product_name,sale_price,percent,sub_title|product_name,sale_price,sub_title|&pic_size=x|l|l|a&tag_id=reco_category_top|reco_category_bav|reco_category_alsobuy|cookie_history&jsoncallback=jQuery111306063094103963638_1687324301593&_=1687324301594"
        val response = HttpUtil.sendGetRequest(url)

        println("hhhhaaahhhhhhh"+response)

    }

    private fun getItemList(): List<Item> {
        // 返回图文列表项的数据集合
        // 每个 Item 包含图片资源 ID 和相应的文本


        var str = "{\n" +
                "    \"reco_category_top\": {\n" +
                "        \"total_count\": 20,\n" +
                "        \"items\": [\n" +
                "            {\n" +
                "                \"product_id\": 29264895,\n" +
                "                \"product_name\": \"魔戒三部曲（奥斯卡桂冠影片《指环王》三部曲原著小说，托尔金基金会指定译本，乔治·R.R.马丁、J.K.罗琳顶礼膜拜之作）\",\n" +
                "                \"sale_price\": \"131.40\",\n" +
                "                \"pic_url\": \"//img3m5.ddimg.cn/0/4/29264895-1_l.jpg\",\n" +
                "                \"product_link\": \"//product.dangdang.com/29264895.html#ddclick_reco_category_top\",\n" +
                "                \"percent\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"product_id\": 24535115,\n" +
                "                \"product_name\": \"美国众神：十周年作者修订版（作者入围2018年新文学院奖终选）\",\n" +
                "                \"sale_price\": \"34.90\",\n" +
                "                \"pic_url\": \"//img3m5.ddimg.cn/44/8/24535115-1_l.jpg\",\n" +
                "                \"product_link\": \"//product.dangdang.com/24535115.html#ddclick_reco_category_top\",\n" +
                "                \"percent\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"product_id\": 29150622,\n" +
                "                \"product_name\": \"西游·再见悟空（享誉奇幻小说圈十余年，一部被低估的西游小说）\",\n" +
                "                \"sale_price\": \"19.10\",\n" +
                "                \"pic_url\": \"//img3m2.ddimg.cn/72/24/29150622-1_l.jpg\",\n" +
                "                \"product_link\": \"//product.dangdang.com/29150622.html#ddclick_reco_category_top\",\n" +
                "                \"percent\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"product_id\": 23294346,\n" +
                "                \"product_name\": \"纳尼亚传奇全集（精装上下册)（全译本，与《魔戒》《哈利·波特》并称为世界三大奇幻经典巨著，托尔金的终身挚友，JK罗琳的精神导师完美呈现，被译成41种语言，好莱坞同名电影全球票房超10亿）\",\n" +
                "                \"sale_price\": \"49.00\",\n" +
                "                \"pic_url\": \"//img3m6.ddimg.cn/42/34/23294346-1_l.jpg\",\n" +
                "                \"product_link\": \"//product.dangdang.com/23294346.html#ddclick_reco_category_top\",\n" +
                "                \"percent\": 0\n" +
                "            },\n" +
                "            {\n" +
                "                \"product_id\": 29522522,\n" +
                "                \"product_name\": \"冰与火之歌1-15册（平装特装版）\",\n" +
                "                \"sale_price\": \"365.30\",\n" +
                "                \"pic_url\": \"//img3m2.ddimg.cn/29/0/29522522-1_l.jpg\",\n" +
                "                \"product_link\": \"//product.dangdang.com/29522522.html#ddclick_reco_category_top\",\n" +
                "                \"percent\": 0\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        val itemList = mutableListOf<Item>()

        // 解析 JSON 字符串
        val json = JSONObject(str)
        val items = json.getJSONObject("reco_category_top").getJSONArray("items")

        // 遍历 items 并提取 product_name 和 pic_url
        for (i in 0 until items.length()) {
            val itemObject = items.getJSONObject(i)
            val productName = itemObject.getString("product_name")
            val picUrl = itemObject.getString("pic_url")

            // 创建 Item 对象并添加到 itemList
            val item = Item(picUrl, productName)
            itemList.add(item)
        }

        return itemList
    }




}