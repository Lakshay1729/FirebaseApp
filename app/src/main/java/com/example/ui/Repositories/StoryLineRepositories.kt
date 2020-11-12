package com.example.ui.Repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import java.util.*

class StoryLineRepositories(applicationContext: Context) {
    private val db: FirebaseFirestore
    private val string: String?
    lateinit var document: MutableLiveData<DocumentSnapshot?>

    fun updateFirebaseData(`object`: HashMap<String?, Any?>?) {

        db.collection("characters").document(string!!).update(`object`!!)
    }
    fun getFirebaseData(): MutableLiveData<DocumentSnapshot?> {
        runBlocking {  db.collection("characters").document(string!!).get().addOnCompleteListener { task -> document.value = task.result }
        }
        return document
    }
    init {
        db = FirebaseFirestore.getInstance()
        string = applicationContext.getSharedPreferences("Shared", Context.MODE_PRIVATE).getString("pos", null)
        document=MutableLiveData()
    }
}