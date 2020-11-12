package com.example.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.ui.Dashboard


class LoginActivity : AppCompatActivity() {
    //    private Realm realm1;
    //    private RealmConfiguration configuration;
     var sharedPreferences: SharedPreferences? = null
    val sharedPrefs = "Shared"
    private var editor: SharedPreferences.Editor? = null
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
        //        realm1=Realm.getDefaultInstance();
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
            startActivity(Intent(applicationContext, Dashboard::class.java))
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