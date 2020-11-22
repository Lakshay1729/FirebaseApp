package com.example.ui.Repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ui.Model.ChatModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

class ChatRepository {

    private var list_msgs: MutableList<ChatModel>
    private lateinit var message:MutableLiveData<MutableList<ChatModel>>
    private lateinit var database: DatabaseReference

    public fun writeNewMsg(roomId: Int, msg:ChatModel?) {
        val mesage = msg
        database.child("chatlog").child(roomId.toString()).push().setValue(mesage)
    }
     public fun getMsg(roomId: Int): MutableLiveData<MutableList<ChatModel>> {

        runBlocking {
            val postListener=object:ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    snapshot.getValue<ChatModel>()?.let {
                        list_msgs.add(it)
                        message.value=list_msgs
                        Log.d("Response",message.value?.get(0)?.msg!!)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
            database.child("chatlog").child(roomId.toString()).addChildEventListener(postListener)
        }
        return message
    }

    init {
        database = Firebase.database.reference
        message= MutableLiveData<MutableList<ChatModel>>()
         list_msgs= mutableListOf<ChatModel>()
    }

}