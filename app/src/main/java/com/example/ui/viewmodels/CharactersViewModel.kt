package com.example.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ui.Repositories.CharacterRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executors

//import com.example.ui.Model.CharacterModel;
class CharactersViewModel(application: Application) : AndroidViewModel(application) {
//    private lateinit var livelist: MutableLiveData<List<DocumentSnapshot>>
//    private val executorService: ExecutorService
    var db: FirebaseFirestore=FirebaseFirestore.getInstance()
    private var result: List<DocumentSnapshot>? = null
    var  executorService = Executors.newSingleThreadExecutor()
     var allCharacters= MutableLiveData<List<DocumentSnapshot>>()
      fun getallCharacters(): MutableLiveData<List<DocumentSnapshot>> {
          allCharacters=CharacterRepository().getAllCharacters()
          return allCharacters
        }
}