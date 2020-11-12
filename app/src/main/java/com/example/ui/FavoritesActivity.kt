package com.example.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ui.viewmodels.FavoritesViewModel
import com.google.firebase.firestore.DocumentSnapshot

class FavoritesActivity : AppCompatActivity() {
    private lateinit var favoritesViewModel:FavoritesViewModel
    private var favorites: RecyclerView? = null
    lateinit var items:List<DocumentSnapshot>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_favorites)
        favorites=findViewById<RecyclerView>(R.id.favorites_list)
        items=ArrayList()
        favoritesViewModel=ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(FavoritesViewModel::class.java)
        favoritesViewModel.getavoriteCharacters().observe(this, Observer {
            favorites?.adapter=FavoritesAdapter(it!!)
            Log.d("values",it.get(0).get("ImageLink").toString())
        })

    }

    class FavoritesAdapter(it: List<DocumentSnapshot>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


        private var items: List<DocumentSnapshot>

        init {
            items = it
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val vh = LayoutInflater.from(parent.context).inflate(R.layout.favorites, parent, false)
            return MyViewHolder(vh)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
           MyViewHolder(holder.itemView).bind(position,items)
        }

        override fun getItemCount(): Int {
          return items.size
        }

        class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            var editor=itemView.context.getSharedPreferences("Shared", MODE_PRIVATE).edit()
            fun bind(position: Int, items: List<DocumentSnapshot>) {
                itemView.findViewById<TextView>(R.id.nameFav).setText(items.get(position).get("CharacterName").toString())
                itemView.findViewById<TextView>(R.id.desFav).setText(items.get(position).get("Description").toString())
                Glide.with(itemView).load(items.get(position).get("ImageLink").toString()).into(itemView.findViewById(R.id.imagecir))

                itemView.setOnClickListener(View.OnClickListener {
                    editor!!.putString("pos", items.get(position).id).apply()
                    it.context.startActivity(Intent(it.context,StoryLine::class.java))
                })
            }
        }
    }


}