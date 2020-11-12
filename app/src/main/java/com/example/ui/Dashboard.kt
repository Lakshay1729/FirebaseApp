package com.example.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.ui.CharacterForm
import com.example.ui.Dashboard.CustomAdapter.MyViewHolder
import com.example.ui.viewmodels.CharactersViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

import kotlinx.coroutines.coroutineScope
import java.util.*

class Dashboard : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var staggeredGridLayoutManager: StaggeredGridLayoutManager? = null
    var characterImages: ArrayList<*> = ArrayList(Arrays.asList(R.drawable.ironman, R.drawable.hulk, R.drawable.strange, R.drawable.wolverine, R.drawable.daredevil, R.drawable.spiderman, R.drawable.thor))
    var characterNames: ArrayList<*> = ArrayList(Arrays.asList("IronMan", "Hulk", "Doctor Strange", "Wolverine", "Daredevil", "Spiderman", "Thor"))
    private val layout: AppBarLayout? = null
    private val flag = false
    private var sharedPreferences: SharedPreferences? = null
    val sharedPrefs = "Shared"

    private val storage = FirebaseStorage.getInstance()
    var list: List<DocumentSnapshot> = ArrayList()
    var db = FirebaseFirestore.getInstance()
    private val flo: Flowable<List<DocumentSnapshot>>? = null
    private lateinit var li: List<DocumentSnapshot?>
    private val disposable: Disposable? = null
    private var customAdapter: CustomAdapter? = null
    private val listLiveData: MutableLiveData<List<DocumentSnapshot>>? = null
    private var characterViewModel: CharactersViewModel? = null
    private val livelist: MutableLiveData<List<DocumentSnapshot>>? = null
    private var editor: SharedPreferences.Editor? = null
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_dashboard)
        sharedPreferences = getSharedPreferences(sharedPrefs, MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView!!.layoutManager = staggeredGridLayoutManager
        val storageRef = storage.reference
//        customAdapter = CustomAdapter(applicationContext, li)
        characterViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(CharactersViewModel::class.java).apply {
            getallCharacters()
        }

        characterViewModel!!.getallCharacters().observe(this, { documentSnapshots -> //                            customAdapter.setData(li);

            customAdapter = CustomAdapter(applicationContext, documentSnapshots)
            recyclerView!!.adapter = customAdapter
        })



        findViewById<TextView>(R.id.favorites).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,FavoritesActivity::class.java))
        })


        findViewById<View>(R.id.logout).setOnClickListener {
            sharedPreferences!!.edit().clear().apply()
            startActivity(Intent(applicationContext, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }
        findViewById<View>(R.id.form_contribute).setOnClickListener { startActivity(Intent(applicationContext, CharacterForm::class.java)) }

    }

    private inner class CustomAdapter(var context: Context, list: List<DocumentSnapshot?>) : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
            return MyViewHolder(v)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            try {
                holder.name.text = if (li[position] != null) li[position]!!.data!!["CharacterName"].toString() else null
                Glide.with(applicationContext).load(li[position]!!.data!!["ImageLink"].toString()).into(holder.image)
            } catch (e: Exception) {
                Log.d("TAG", e.message.toString())
            }
            holder.itemView.setOnClickListener {
                editor!!.putString("pos", li[position]!!.id).apply()
                startActivity(Intent(applicationContext, StoryLine::class.java))
            }
        }

        override fun getItemCount(): Int {
            Log.d("Size", li.size.toString() + "")
            return li.size
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var name: TextView
            var image: ImageView

            init {
                name = itemView.findViewById<View>(R.id.text1) as TextView
                image = itemView.findViewById<View>(R.id.card_image) as ImageView
            }
        }

        init {
            li = list
        }
    }

    override fun onResume() {
        super.onResume()
        //        db.collection("characters").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (value != null) {
//                    li = value.getDocuments();
//                    customAdapter = new CustomAdapter(getApplicationContext(), li);
//                    recyclerView.setAdapter(customAdapter);
//                }
//                else if(error!=null)
//                {
//                    Log.d("Error",error.getMessage());
//                }
//            }
//        });
    }

    override fun onDestroy() {
        super.onDestroy()
        //        disposable.dispose();
    }

    internal inner class PostDiffCallback(private val oldPosts: List<DocumentSnapshot>, private val newPosts: List<DocumentSnapshot>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldPosts.size
        }

        override fun getNewListSize(): Int {
            return newPosts.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldPosts[oldItemPosition].id === newPosts[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldPosts[oldItemPosition] == newPosts[newItemPosition]
        }
    }

    companion object {
        private const val TAG = "Read"
    }
}