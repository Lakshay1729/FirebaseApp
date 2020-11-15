package com.example.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ui.Repositories.FavoritesRepository
import com.google.firebase.firestore.DocumentSnapshot

class FavoritesViewModel(application: Application) :AndroidViewModel(application)
{
    private var repository: FavoritesRepository

    fun getavoriteCharacters(): MutableLiveData<List<DocumentSnapshot>> {

    return repository.getFavoritesCharacters()
}
    init {
        repository=FavoritesRepository(application.applicationContext)
    }
}