package com.example.dtpe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "999!"

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener { v ->
            val intent = Intent(this@MainActivity, BActivity::class.java)
            startActivity(intent)
        }


    }
}