package com.example.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ui.Repositories.CharacterRepository
import com.example.ui.Repositories.FavoritesRepository
import com.example.ui.Repositories.StoryLineRepositories
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executors

class FavoritesViewModel(application: Application) :AndroidViewModel(application)
{
fun getavoriteCharacters(): MutableLiveData<List<DocumentSnapshot>> {

    return FavoritesRepository().getFavoritesCharacters()
}
}