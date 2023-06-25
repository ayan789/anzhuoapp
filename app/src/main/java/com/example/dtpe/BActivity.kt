package com.example.dtpe

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.MediaController
import android.widget.RelativeLayout
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class BActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_b)


        videoView = findViewById(R.id.videoView)
        val videoUrl = "https://oss.datianpao.com/sweet/2023-6-8/mda-maunc8q8gpwgjts8.mp4"
        val videoUri = Uri.parse(videoUrl)

        // 创建一个MediaController对象，用于控制视频的播放
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // 设置视频的URI
        videoView.setVideoURI(videoUri)

        // 开始播放视频
        videoView.start()

        val fullscreenButton: VideoView = findViewById(R.id.videoView)
        fullscreenButton.setOnClickListener {
            var lastClickTime = 0L
            val doubleClickTimeThreshold = 300L // 双击时间阈值（毫秒）

            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < doubleClickTimeThreshold) {
                // 双击事件
                enterFullScreen()
            }
            lastClickTime = currentTime
        }


        val button: Button = findViewById(R.id.babutton)
        button.setOnClickListener {
            finish() // 销毁当前活动（B 页面）
        }
    }

    private var isFullscreen = false

    private fun toggleFullscreen() {
        if (isFullscreen) {
            // 退出全屏模式
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            supportActionBar?.show()
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            // 进入全屏模式
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
            supportActionBar?.hide()
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        isFullscreen = !isFullscreen
    }

    private fun enterFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        ) // 设置全屏

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // 设置横屏

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) // 常亮

        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
    }



}