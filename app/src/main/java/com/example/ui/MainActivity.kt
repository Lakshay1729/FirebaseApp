package com.example.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
             if(getSharedPreferences("Shared", MODE_PRIVATE).contains("UID")){

                startActivity(Intent(applicationContext, Dashboard::class.java))

                finish()
            }else {
                 startActivity(Intent(applicationContext, LoginActivity::class.java))

                 finish()
             }
        }, 2000)
    }
}