package com.example.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ui.Model.UserModel
import com.example.ui.databinding.ActivitySignupBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.UploadTask
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.HashMap


//import static com.example.ui.MyApplication.app;
class Signup : AppCompatActivity() {
    private lateinit var userModel: UserModel
    private lateinit var binding: ActivitySignupBinding
    private lateinit var generatedFilePath: String
    private lateinit var uploadTask: UploadTask
    private lateinit var storage: FirebaseStorage
    private var picUri: Uri? = null
    private lateinit var byteArray: ByteArray
    private val PICK_IMAGE: Int=10
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
         binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storage = FirebaseStorage.getInstance()
        db=FirebaseFirestore.getInstance()
        userModel=UserModel()
        binding.userImage.setOnClickListener(View.OnClickListener {
             val intent=Intent()
            intent.type="image/*"
            intent.action=Intent.ACTION_PICK

            val intent2=Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)

            val chooserIntent=Intent.createChooser(intent, "Select Picture")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(intent2))
            startActivityForResult(chooserIntent, PICK_IMAGE);
        })
// ...
// Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        binding.signUp.setOnClickListener(View.OnClickListener {
            val map=HashMap<String,String>()
            val random = UUID.randomUUID().toString()
            val path = "users/$random.png"
            val fireimageRef = storage.getReference(path)
            val metadata = StorageMetadata.Builder().setCustomMetadata("caption", (binding.nameField.text.toString())).build()
            uploadTask = fireimageRef.putBytes(byteArray, metadata)
            val downloadTask=uploadTask.continueWithTask{
                task->task.result!!.storage.downloadUrl
            }

            downloadTask.addOnCompleteListener(OnCompleteListener {
                generatedFilePath=  it.result.toString()
                    }).continueWithTask{
                auth.createUserWithEmailAndPassword(binding.emailField.text.toString(), binding.passfield.text.toString()).addOnCompleteListener(this) {

                    if (it.isSuccessful) {
                        Log.d("Result_ignIn", it.result.toString())

                        val editor= getSharedPreferences("Shared", MODE_PRIVATE).edit()
                        editor.putString("UID",it.result?.user?.uid).apply()
                        userModel.UID=it.result?.user?.uid.toString()
                        userModel.Email=binding.emailField.text.toString()
                        userModel.Password=binding.passfield.text.toString()
                        userModel.UserImage=generatedFilePath
                        userModel.FirstName=binding.nameField.text.toString()
                        userModel.LastName=binding.lastnameField.text.toString()
                        userModel.PhoneNumber=binding.numberField.text.toString()
//                        map.put("UID",it.result?.user?.uid.toString())
//                        map.put("Email",binding.emailField.text.toString())
//                        map.put("Password",binding.passfield.text.toString())
//                        map.put("UserImage",generatedFilePath)
//                        map.put("FirstName",binding.nameField.text.toString())
//                        map.put("LastName",binding.lastnameField.text.toString())
//                        map.put("PhoneNumber",binding.numberField.text.toString())
                        db.collection("users").add(userModel).addOnCompleteListener(OnCompleteListener {
                            startDashboard()
                        })
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Result_ignIn", "createUserWithEmail:failure", it.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            if(requestCode==10)
            { picUri = data?.data
//                data?.extras?.get("data")
                Log.d("picUri",picUri.toString())
                var bp: Bitmap? = null
                if (picUri != null) {
                    try {
                        bp = MediaStore.Images.Media.getBitmap(this.contentResolver, picUri)
                       binding.userImage.setImageBitmap(bp)
                    } catch (e: FileNotFoundException) {
                        throw RuntimeException(e)
                    } catch (e: IOException) {
                        throw RuntimeException(e)
                    }
                }
                val stream = ByteArrayOutputStream()
                bp!!.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                byteArray = stream.toByteArray()
            }
        }
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