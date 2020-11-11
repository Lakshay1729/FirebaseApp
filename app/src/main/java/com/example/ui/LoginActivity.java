package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ui.RealmDB.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class LoginActivity extends AppCompatActivity {


//    private Realm realm1;
//    private RealmConfiguration configuration;
    private SharedPreferences sharedPreferences;
    public final String sharedPrefs="Shared";
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
//        realm1=Realm.getDefaultInstance();
        sharedPreferences=getSharedPreferences(sharedPrefs, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
      if(sharedPreferences.contains("email"))
      {
          startActivity(new Intent(getApplicationContext(),Dashboard.class));
          finish();
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

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),Signup.class));
            }
        });
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loginAuth();
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }
        });
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        realm1.close(); // Remember to close Realm when done.
    }

}
