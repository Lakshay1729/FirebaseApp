package com.example.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ui.Repositories.ContributorsRepository
import com.google.firebase.firestore.DocumentSnapshot

class ContributorsViewModel(application: Application) :AndroidViewModel(application)
{

    lateinit var repository:ContributorsRepository
    fun getAllLiveContributors():MutableLiveData<List<DocumentSnapshot>>
    {
        var list=MutableLiveData<List<DocumentSnapshot>>()
        list=repository.getAllContributors()
        return list
    }
    init {
        repository= ContributorsRepository(application.applicationContext)
    }
}