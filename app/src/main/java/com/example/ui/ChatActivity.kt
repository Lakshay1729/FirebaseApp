package com.example.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.Model.ChatModel
import com.example.ui.databinding.ActivityChatBinding
import com.example.ui.databinding.ChatMsgLayoutBinding
import com.example.ui.viewmodels.ChatViewModel
import java.text.SimpleDateFormat
import java.util.*


class ChatActivity : AppCompatActivity() {

    private var roomid: Int = 0
    private lateinit var chatViewModel: ChatViewModel

    lateinit var chat:ChatModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val binding=ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chat=ChatModel()

        val sharedPrefs=getSharedPreferences("Shared", MODE_PRIVATE)
        chatViewModel=ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(ChatViewModel::class.java)
        roomid=getRoomId(sharedPrefs.getString("user_key","")!!,sharedPrefs.getString("UID","")!!)
        binding.sendMsg.setOnClickListener(View.OnClickListener {

            chat.msg=binding.chatMsg.text?.toString()
            chat.sender="${sharedPrefs.getString("FirstName","")} ${sharedPrefs.getString("LastName","")}"
            chat.time= SimpleDateFormat("hh:mm", Locale.ENGLISH).format(Calendar.getInstance().time)


            chatViewModel.savemesgs(getRoomId(sharedPrefs.getString("user_key","")!!,sharedPrefs.getString("UID","")!!),chat)
            binding.chatMsg.setText("")
        })


        chatViewModel.getAllmessages(getRoomId(sharedPrefs.getString("user_key","")!!,sharedPrefs.getString("UID","")!!)).observe(this, androidx.lifecycle.Observer {

            binding.chatLog.setHasFixedSize(true)
            binding.chatLog.adapter?.notifyItemInserted(it.size-1)
            binding.chatLog.scrollToPosition(it.size-1)
               binding.chatLog.adapter=MyCustomAdapter(it)
           })

    }
    private fun getRoomId(string1:String, string2: String):Int{
        var number:Int=0
        Log.d("msg",string1)
        for(i in string1.indices){
          number= number.plus(string1[i].toInt()).plus(string2[i].toInt())
            Log.d("Number",number.toString()+string1[i].toInt()+string2)
        }
        return number
    }

    class MyCustomAdapter(messages: List<ChatModel>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private lateinit var bindingg: ChatMsgLayoutBinding
        private var listmessages: List<ChatModel>?

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view =LayoutInflater.from(parent.context).inflate(R.layout.chat_msg_layout,parent,false)

            bindingg= ChatMsgLayoutBinding.bind(view)
            return MyViewHolder(bindingg.root)
        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            MyViewHolder(holder.itemView).bind(listmessages?.get(position),bindingg)
        }

        override fun getItemCount(): Int{
            return listmessages?.size!!
        }
        class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
            fun bind(get: ChatModel?, bindingg: ChatMsgLayoutBinding) {
                val name = "${itemView.context.getSharedPreferences("Shared", MODE_PRIVATE).getString("FirstName", null)} ${itemView.context.getSharedPreferences("Shared", MODE_PRIVATE).getString("LastName", null)}"
                if (get?.sender?.equals(name)!!) {
                    bindingg.rightLayout.visibility=View.VISIBLE
                    bindingg.leftLayout.visibility=View.INVISIBLE
                    bindingg.senderMsgRight.setText(get.msg)
                    bindingg.senderNameRight.setText(get.sender)
                    bindingg.senderTimeRight.setText(get.time)
                } else {
                    bindingg.leftLayout.visibility=View.VISIBLE
                    bindingg.rightLayout.visibility=View.INVISIBLE
                    bindingg.senderMsg.setText(get.msg)
                    bindingg.senderName.setText(get.sender)
                    bindingg.senderTime.setText(get.time)
                }
            }
        }

        init {
          listmessages=  messages
        }
    }
}