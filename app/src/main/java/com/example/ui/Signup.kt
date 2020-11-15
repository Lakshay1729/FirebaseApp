package com.example.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ui.databinding.ActivitySignupBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


//import static com.example.ui.MyApplication.app;
class Signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
// ...
// Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        binding.signUp.setOnClickListener(View.OnClickListener {
            auth.createUserWithEmailAndPassword(binding.emailField.text.toString(), binding.passfield.text.toString()).addOnCompleteListener(this) {

                if (it.isSuccessful) {
                    Log.d("Result_ignIn", it.result.toString())
//                    val user = auth.currentUser
                    val editor= getSharedPreferences("Shared", MODE_PRIVATE).edit()
                    editor.putString("UID",it.result?.user?.uid).apply()
                    startDashboard()


                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Result_ignIn", "createUserWithEmail:failure", it.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

    private fun startDashboard() {
        startActivity(Intent(this,Dashboard::class.java))
        finish()
    }

    override fun onStart(){
        super.onStart()
//        auth.currentUser?.let {
//            startActivity(Intent(this,Dashboard::class.java))
//        }
    }




}