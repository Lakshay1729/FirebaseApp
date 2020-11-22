package com.example.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ui.databinding.ActivityContributorsBinding
import com.example.ui.databinding.ListviewBinding
import com.example.ui.viewmodels.ContributorsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentSnapshot

class ContributorsActivity : AppCompatActivity() {
    lateinit var liste:List<DocumentSnapshot>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val binding=ActivityContributorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ContriViewModel=ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(ContributorsViewModel::class.java)
        ContriViewModel.getAllLiveContributors().observe(this, Observer {
            binding.recyclerViewProfiles.scheduleLayoutAnimation()
            binding.recyclerViewProfiles.adapter = MyAdapter(it)
        })

    }

    class MyAdapter(list: List<DocumentSnapshot>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        lateinit var results:List<DocumentSnapshot>
        lateinit var binding:ListviewBinding
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val vh=LayoutInflater.from(parent.context).inflate(R.layout.listview, parent, false)
            binding= ListviewBinding.bind(vh)
            return MyViewHolder(binding.root)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            MyViewHolder(holder.itemView).bind(position, this)
        }

        override fun getItemCount(): Int {
           return results.size
        }
        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            fun bind(position: Int, myAdapter: MyAdapter) {
//                myAdapter.results.get(position).
                myAdapter.binding.chat.setOnClickListener(View.OnClickListener {
                    val editor= itemView.context.getSharedPreferences("Shared", MODE_PRIVATE).edit()
                    Log.d("user_id",myAdapter.results.get(position).get("UID").toString())
                    editor.putString("user_key",myAdapter.results.get(position).get("UID").toString()).apply()
                    itemView.context.startActivity(Intent(it.context,ChatActivity::class.java))

                })
                Glide.with(itemView).load(myAdapter.results.get(position).get("UserImage").toString()).into(myAdapter.binding.userImage)
                myAdapter.binding.userName.text=myAdapter.results.get(position).get("FirstName").toString()+" "+myAdapter.results.get(position).get("LastName").toString()
                myAdapter.binding.userEmail.text=myAdapter.results.get(position).get("Email").toString()
            }
        }
        init{
           results =list

        }
    }



}