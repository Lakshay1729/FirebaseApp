package com.example.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.ui.Dashboard
import com.example.ui.Model.ChatModel
import com.example.ui.Model.UserModel
import com.example.ui.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executors


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    //    private Realm realm1;
    //    private RealmConfiguration configuration;
    var sharedPreferences: SharedPreferences? = null
    val sharedPrefs = "Shared"
    lateinit var binding:ActivityLoginBinding
    private var editor: SharedPreferences.Editor? = null
    private lateinit var db:FirebaseFirestore
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        sharedPreferences = getSharedPreferences(sharedPrefs, MODE_PRIVATE)
        editor =  getSharedPreferences(sharedPrefs, MODE_PRIVATE).edit()
        if (sharedPreferences!!.contains("email")) {
            auth.signInWithEmailAndPassword(sharedPreferences!!.getString("email",null)!!,sharedPreferences!!.getString("password",null)!!).addOnCompleteListener(){
                if(it.isSuccessful){
                    val editor= getSharedPreferences("Shared", MODE_PRIVATE).edit()
                    editor.putString("UID",it.result?.user?.uid).apply()
//                    it.result?.user.
                    db.collection("users").whereEqualTo("UID",it.result?.user?.uid).get().addOnCompleteListener(OnCompleteListener {
                        it.result?.documents?.get(0)?.data?.get("FirstName")
                        editor.putString("FirstName", it.result?.documents?.get(0)?.data?.get("FirstName").toString()).apply()
                        editor.putString("LastName",it.result?.documents?.get(0)?.data?.get("LastName").toString()).apply()
                        startActivity(Intent(this,Dashboard::class.java))
                        finish()
                    }).addOnCanceledListener {
                        Log.d("ErrorMsg",it.exception?.message!!)
                    }
                }
                else{
                    Snackbar.make(findViewById(R.id.parent),"Login failed",Snackbar.LENGTH_LONG).show()
                }
            }
        }

        findViewById<TextView>(R.id.signup).setOnClickListener { startActivity(Intent(applicationContext, Signup::class.java)) }
        findViewById<AppCompatButton>(R.id.login).setOnClickListener { //                loginAuth();
            auth.signInWithEmailAndPassword(binding.loginemail.text.toString(),binding.loginpass.text.toString()).addOnCompleteListener(){
                if(it.isSuccessful){
                    val editor= getSharedPreferences("Shared", MODE_PRIVATE).edit()
                    editor.putString("UID",it.result?.user?.uid).apply()
                    db.collection("users").whereEqualTo("UID",it.result?.user?.uid).get().addOnCompleteListener(OnCompleteListener {
                        it.result?.documents?.get(0)?.data?.get("FirstName")
                        editor.putString("FirstName", it.result?.documents?.get(0)?.data?.get("FirstName").toString()).apply()
                        editor.putString("LastName",it.result?.documents?.get(0)?.data?.get("LastName").toString()).apply()
                        editor.putString("email",binding.loginemail.text.toString()).apply()
                        editor.putString("password",binding.loginpass.text.toString()).apply()
                        startActivity(Intent(this,Dashboard::class.java))
                        finish()
                    }).addOnCanceledListener {
                        Log.d("ErrorMsg",it.exception?.message!!)
                    }

                }
                else{
                    Snackbar.make(findViewById(R.id.parent),"Login failed",Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //        realm1.close(); // Remember to close Realm when done.
    }
}