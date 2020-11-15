package com.example.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ui.Repositories.CharacterRepository
import com.example.ui.Repositories.StoryLineRepositories
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executors

//import com.example.ui.Model.CharacterModel;
class CharactersViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: CharacterRepository

    var db: FirebaseFirestore=FirebaseFirestore.getInstance()
      fun getallCharacters(): MutableLiveData<List<DocumentSnapshot>> {
          return repository.getAllCharacters()
        }
    init {
        repository = CharacterRepository(application.applicationContext)
    }
}