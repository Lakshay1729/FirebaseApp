package com.example.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ui.R
import com.example.ui.Repositories.StoryLineRepositories
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class StoryLineViewModel(application: Application) : AndroidViewModel(application) {
     var likedvalue: Boolean=false
    var repository: StoryLineRepositories
    var liked: MutableLiveData<Boolean>
   fun getData(): MutableLiveData<DocumentSnapshot?>
   {
       return repository.getFirebaseData()
   }
    fun updateData(objectHashMap: HashMap<String?, Any?>?) {
        repository.updateFirebaseData(objectHashMap)
    }

    init {
        liked = MutableLiveData()
        repository = StoryLineRepositories(application.applicationContext)
    }
}