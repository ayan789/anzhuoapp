package com.example.dtpe

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class BActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_b)

        val button: Button = findViewById(R.id.babutton)
        button.setOnClickListener {
            finish() // 销毁当前活动（B 页面）
        }
    }
}