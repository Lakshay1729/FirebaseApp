package com.example.ui.Repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.concurrent.Executors

class CharacterRepository(applicationContext: Context){
    private var string: String?
    private val db: FirebaseFirestore
//    private val string: String?
var executor= Executors.newSingleThreadExecutor()
     fun getAllCharacters(): MutableLiveData<List<DocumentSnapshot>>
    {
        val document=MutableLiveData<List<DocumentSnapshot>>()
          db.collection("characters").whereEqualTo("UID",string).addSnapshotListener(executor,EventListener<QuerySnapshot>(){value, error ->
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
        string=applicationContext.getSharedPreferences("Shared", Context.MODE_PRIVATE).getString("UID", null)
    }
}