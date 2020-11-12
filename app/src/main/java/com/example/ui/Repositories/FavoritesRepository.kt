package com.example.ui.Repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.concurrent.Executors

class FavoritesRepository ()
{
    private val db: FirebaseFirestore
    //    private val string: String?
    var executor= Executors.newSingleThreadExecutor()
    fun getFavoritesCharacters(): MutableLiveData<List<DocumentSnapshot>>
    {
        val document= MutableLiveData<List<DocumentSnapshot>>()
        db.collection("characters").whereEqualTo("liked",true).addSnapshotListener(executor, EventListener<QuerySnapshot>(){ value, error ->
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
    }
}