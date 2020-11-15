package com.example.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.ui.Dashboard
import com.example.ui.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    //    private Realm realm1;
    //    private RealmConfiguration configuration;
     var sharedPreferences: SharedPreferences? = null
    val sharedPrefs = "Shared"
    lateinit var binding:ActivityLoginBinding
    private var editor: SharedPreferences.Editor? = null
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()

        sharedPreferences = getSharedPreferences(sharedPrefs, MODE_PRIVATE)
        editor =  getSharedPreferences(sharedPrefs, MODE_PRIVATE).edit()
        if (sharedPreferences!!.contains("email")) {
            startActivity(Intent(applicationContext, Dashboard::class.java))
            finish()
        }
        //        MyApplication.app.loginAsync(Credentials.anonymous(), new App.Callback<io.realm.mongodb.User>() {
//            @Override
//            public void onResult(App.Result<io.realm.mongodb.User> result) {
//                if(result.isSuccess()) {
//                    //configuration=new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
//                    Log.d("SuxessL","Success");
//                    io.realm.mongodb.User user = MyApplication.app.currentUser();
//                    Long partitionValue = 123L;
//                    assert user != null;
//                    syncConfiguration = new SyncConfiguration.Builder(user, partitionValue).build();
//                    Realm.setDefaultConfiguration(syncConfiguration);
//                    Realm.getInstanceAsync(syncConfiguration, new Realm.Callback() {
//                        @Override
//                        public void onSuccess(Realm realm) {
//                            realm1=realm;
//                        }
//                    });
//                }
//                else
//                {
//                    Log.d("SuxessL","UNSuccess");
//                }
//
//            }
//        });
        findViewById<TextView>(R.id.signup).setOnClickListener { startActivity(Intent(applicationContext, Signup::class.java)) }
        findViewById<AppCompatButton>(R.id.login).setOnClickListener { //                loginAuth();
            auth.signInWithEmailAndPassword(binding.loginemail.text.toString(),binding.loginpass.text.toString()).addOnCompleteListener(){
                if(it.isSuccessful){
                    val editor= getSharedPreferences("Shared", MODE_PRIVATE).edit()
                    editor.putString("UID",it.result?.user?.uid).apply()
                    startActivity(Intent(this,Dashboard::class.java))
                    finish()

                }
                else{
                    Snackbar.make(findViewById(R.id.parent),"Login failed",Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    //    public void loginAuth()
    //    {
    //        User user=null;
    //        if(!String.valueOf(((TextInputEditText) findViewById(R.id.loginemail)).getText()).isEmpty()) {
    //            user = realm1.where(User.class).equalTo("email", String.valueOf(((TextInputEditText) findViewById(R.id.loginemail)).getText())).findFirst();
    //            //Toast.makeText(this, ""+user.getPassword(), Toast.LENGTH_SHORT).show();
    //
    ////            assert user != null;
    ////            if (user!=null) {
    //
    //            Log.d("user",realm1.where(User.class).findAll().toString());
    //            Log.d("emailc",String.valueOf(((TextInputEditText) findViewById(R.id.loginemail)).getText()));
    //                if (user.getEmail().equals(Objects.requireNonNull(((TextInputEditText) findViewById(R.id.loginemail)).getText()).toString()) && (user.getPassword().equals(((TextInputEditText) findViewById(R.id.loginpass)).getText().toString())))
    //                {
    //                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
    //                            finish();
    ////                }
    //            }
    //        }
    //        assert user != null;
    ////        startActivity(new Intent(getApplicationContext(), Dashboard.class));
    ////        finish();
    //        Log.d("email",user.getEmail());
    //        editor.putString("email",user.getEmail()).apply();
    //
    //
    //    }
    override fun onDestroy() {
        super.onDestroy()
        //        realm1.close(); // Remember to close Realm when done.
    }
}