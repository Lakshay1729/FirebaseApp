package com.example.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ui.CharacterForm
import com.example.ui.viewmodels.CharactersViewModel
import com.google.android.gms.tasks.Task
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*


class CharacterForm : AppCompatActivity() {
    //    private Characters character;
    private var sharedPreferences: SharedPreferences? = null
    val sharedPrefs = "Shared"
    private val editor: SharedPreferences.Editor? = null

    //    private User user;
    private val storage = FirebaseStorage.getInstance()
    private lateinit var byteArray: ByteArray
    private var picUri: Uri? = null
    private val downloadUri: Task<Uri>? = null
    private var character: HashMap<String, Any>? = null
    private val flag = 0
    private val name: String? = null
    private val yes = false
    private var uploadTask: UploadTask? = null
    private val ref: StorageReference? = null
    private var generatedFilePath: String? = null
    private var characterViewModel: CharactersViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_character_form)
        sharedPreferences = getSharedPreferences(sharedPrefs, MODE_PRIVATE)
        characterViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(CharactersViewModel::class.java)
        findViewById<View>(R.id.character_image).setOnClickListener {
            val pickerIntent = Intent(Intent.ACTION_PICK)
            pickerIntent.type = "image/*"
            startActivityForResult(pickerIntent, 100)
        }
        findViewById<View>(R.id.submit_form).setOnClickListener { createCharacter() }
    }
    fun getCameraPhotoOrientation(context: Context, imageUri: Uri?, imagePath: String?): Int {
        var rotate = 0
        try {
            context.getContentResolver().notifyChange(imageUri!!, null)
            val imageFile = File(imagePath)
            val exif = ExifInterface(imageFile.getAbsolutePath())
            val orientation: Int = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
            }
            Log.i("RotateImage", "Exif orientation: $orientation")
            Log.i("RotateImage", "Rotate value: $rotate")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return rotate
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK) {
            Log.d("path", intent!!.data!!.path)
            picUri = intent.data
            var bp: Bitmap? = null
            if (picUri != null) {
                try {
                    bp = MediaStore.Images.Media.getBitmap(
                            this.contentResolver,
                            picUri)
                    var rotate=getCameraPhotoOrientation(applicationContext,picUri,intent.data?.path)
                    (findViewById<View>(R.id.character_image) as ShapeableImageView).setImageBitmap(bp)
                } catch (e: FileNotFoundException) {
                    throw RuntimeException(e)
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }


            val stream = ByteArrayOutputStream()
            bp!!.compress(Bitmap.CompressFormat.JPEG, 80, stream)
            byteArray = stream.toByteArray()
            //            character.setImage(byteArray);
        }
    }

    private fun createCharacter() {

        val random = UUID.randomUUID().toString()
        val path = "characterImages/$random.png"
        val fireimageRef = storage.getReference(path)
        val metadata = StorageMetadata.Builder().setCustomMetadata("caption", (findViewById<View>(R.id.character_name) as TextInputEditText).text.toString()).build()
        uploadTask = fireimageRef.putBytes(byteArray, metadata)
        uploadTask!!.addOnProgressListener(this@CharacterForm) { snapshot ->
            (findViewById<View>(R.id.progress_horizontal_1) as ProgressBar).visibility = View.VISIBLE
            (findViewById<View>(R.id.progress_horizontal_1) as ProgressBar).max = 10000
            (findViewById<View>(R.id.progress_horizontal_1) as ProgressBar).progress = (10000 * snapshot.bytesTransferred / snapshot.totalByteCount).toInt()
            (findViewById<View>(R.id.progress_horizontal_1) as ProgressBar).secondaryProgress = (10000 * snapshot.bytesTransferred / snapshot.totalByteCount).toInt() + 500
        }
        uploadTask!!.continueWithTask { task -> task.result!!.storage.downloadUrl }.addOnCompleteListener { task ->
            generatedFilePath = task.result.toString()
            Log.d("DEKHE", generatedFilePath)
            character = HashMap()
            character!!["CharacterName"] = (findViewById<View>(R.id.character_name) as TextInputEditText).text.toString()
            character!!["Description"] = (findViewById<View>(R.id.description_character) as TextInputEditText).text.toString()
            character!!["ImageLink"] = generatedFilePath!!
            character!!["UID"]=sharedPreferences?.getString("UID",null).toString()
            characterViewModel!!.db.collection("characters")
                    .add(character!!)
                    .addOnSuccessListener { documentReference ->
                        finish()
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id)
                    }
                    .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
        }.addOnFailureListener { e -> Log.d("Failyre", e.message) }
    }

    override fun onDestroy() {
        super.onDestroy()
        //        realm1.close(); // Remember to close Realm when done.
    }

    companion object {
        private const val TAG = "Log"
    }
}