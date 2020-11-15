package com.example.ui.Repositories

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.concurrent.Executors

class FavoritesRepository(applicationContext: Context)
{
    private var string: String?
    private val db: FirebaseFirestore
    //    private val string: String?
    var executor= Executors.newSingleThreadExecutor()
    fun getFavoritesCharacters(): MutableLiveData<List<DocumentSnapshot>>
    {
        val document= MutableLiveData<List<DocumentSnapshot>>()
        db.collection("characters").whereEqualTo("liked",true).whereEqualTo("UID",string).addSnapshotListener(executor, EventListener<QuerySnapshot>(){ value, error ->
            if(error!=null)
            {
                return@EventListener
            }
            else{
                document.postValue(value?.documents)
                Log.d("document",value?.documents?.get(0)?.get("liked").toString())
            }
        })
        return document
    }
    init {
        db = FirebaseFirestore.getInstance()
        string=applicationContext.getSharedPreferences("Shared",MODE_PRIVATE).getString("UID",null)
    }
}