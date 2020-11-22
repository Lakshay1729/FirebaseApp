package com.example.ui.Repositories

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.media.MediaDrm
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executors

class ContributorsRepository(application: Context) {
    private lateinit var results: MutableLiveData<List<DocumentSnapshot>>
    private var string: String?
    lateinit var db:FirebaseFirestore

    fun getAllContributors(): MutableLiveData<List<DocumentSnapshot>> {
        db.collection("users").whereNotEqualTo("UID",string).addSnapshotListener(Executors.newSingleThreadExecutor(), EventListener { value, error ->
            results.postValue(value?.documents)
//            Log.d("Contributor",value?.documents!!.get(0).get("LastName").toString())
           error?.let {
               Log.d("ErrorContributors",it.toString())
           }
        })
//        db.collection("users").document("${string}").collection("userInfo").get().addOnCompleteListener(Executors.newSingleThreadExecutor(), OnCompleteListener {
//         results.postValue(it.result?.documents!!)
//            Log.d("Contributor",it.result?.documents!!.get(0).get("LastName").toString())
//        })
        return results
    }
    init {
        db= FirebaseFirestore.getInstance()
        results= MutableLiveData()
        string=application.getSharedPreferences("Shared",MODE_PRIVATE).getString("UID",null)
    }
}