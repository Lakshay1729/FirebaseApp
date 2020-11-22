package com.example.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ui.Model.ChatModel
import com.example.ui.Repositories.ChatRepository

class ChatViewModel(application: Application) :AndroidViewModel(application) {

    private lateinit var repository: ChatRepository
    fun getAllmessages(roomId: Int): MutableLiveData<MutableList<ChatModel>> {
//        val mutableIterable= MutableLiveData<MutableList<ChatModel>>()
       return repository.getMsg(roomId)
    }
    fun savemesgs(roomId: Int, msg:ChatModel){
        return repository.writeNewMsg(roomId,msg)
    }

   init {
        repository=ChatRepository()
    }
}