package com.example.ui.Repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.ui.Dashboard
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CharacterRepository(){
    private val db: FirebaseFirestore
//    private val string: String?
var executor= Executors.newSingleThreadExecutor()
     fun getAllCharacters(): MutableLiveData<List<DocumentSnapshot>>
    {
        val document=MutableLiveData<List<DocumentSnapshot>>()
          db.collection("characters").addSnapshotListener(executor,EventListener<QuerySnapshot>(){value, error ->
              if(error!=null)
              {
                  return@EventListener
              }
              else{

                  document.postValue(value?.documents)
              }
          })
        return document
    }
    init {
        db = FirebaseFirestore.getInstance()
    }
}