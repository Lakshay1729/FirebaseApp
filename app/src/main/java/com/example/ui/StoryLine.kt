package com.example.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.ui.viewmodels.StoryLineViewModel
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.example.ui.R
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.bumptech.glide.Glide
import java.util.HashMap

class StoryLine : AppCompatActivity() {
    var storyimage: AppCompatImageView? = null
    var storyname: AppCompatTextView? = null
    var blog: AppCompatTextView? = null
    private val doc: Map<String, Any>? = null
    private var character: HashMap<String?, Any?>? = null
    private var storyLineViewModel: StoryLineViewModel? = null
    private var sharedPreference: SharedPreferences? = null
    private var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_story_line)
        storyimage = findViewById<View>(R.id.story_image) as AppCompatImageView
        storyname = findViewById<View>(R.id.story_name) as AppCompatTextView
        blog = findViewById<View>(R.id.blog) as AppCompatTextView
        storyLineViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(StoryLineViewModel::class.java)

        storyLineViewModel!!.getData().observe(this, Observer { documentSnapshot ->
            Glide.with(applicationContext).load(documentSnapshot?.get("ImageLink")).into(storyimage!!)
            storyname!!.text = documentSnapshot?.get("CharacterName").toString()
            blog!!.text = documentSnapshot?.get("Description").toString()
        })
        storyLineViewModel!!.liked.observe(this@StoryLine, {
            character = HashMap()
            character!!["liked"] = storyLineViewModel!!.liked.value
            storyLineViewModel!!.updateData(character)
            storyLineViewModel!!.likedvalue=it

        })
        findViewById<View>(R.id.like).setOnClickListener {
            storyLineViewModel!!.liked.setValue(!storyLineViewModel!!.liked.value!!)

        }
    }
}