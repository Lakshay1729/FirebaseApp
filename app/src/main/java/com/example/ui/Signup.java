package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ui.RealmDB.User;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Console;

import io.realm.Realm;
import io.realm.RealmChangeListener;


//import static com.example.ui.MyApplication.app;


public class Signup extends AppCompatActivity {

    private Realm realm1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);


//       final App app= new App(new AppConfiguration.Builder("ui-eygrw").build());


//        app.loginAsync(Credentials.anonymous(), new App.Callback<io.realm.mongodb.User>() {
//            @Override
//            public void onResult(App.Result<io.realm.mongodb.User> result) {
//                if(result.isSuccess()) {
//                    //configuration=new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
//                    Log.d("Suxess","Success");
//                    io.realm.mongodb.User user = app.currentUser();
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
//                    Log.d("Suxess","UNSuccess");
//                }
//
//            }
//        });


        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createUser(realm1);
            }
        });
    }

    public void createUser(Realm realm)
    {
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                User user = new User( 123L);
//                //realm.createObject(User.class);
//
//
//                if (!String.valueOf(((TextInputEditText) findViewById(R.id.nameField)).getText()).isEmpty())
//                    user.setFirstName(String.valueOf(((TextInputEditText) findViewById(R.id.nameField)).getText()));
//                else
//                    ((TextInputEditText) findViewById(R.id.nameField)).setError("Enter Your First Name");
//
//                if (!String.valueOf(((TextInputEditText) findViewById(R.id.lastname_Field)).getText()).isEmpty())
//                    user.setLastName(String.valueOf(((TextInputEditText) findViewById(R.id.lastname_Field)).getText()));
//                else
//                    ((TextInputEditText) findViewById(R.id.lastname_Field)).setError("Enter Your Last Name");
//
//                if (!String.valueOf(((TextInputEditText) findViewById(R.id.emailField)).getText()).isEmpty())
//                    user.setEmail(String.valueOf(((TextInputEditText) findViewById(R.id.emailField)).getText()));
//                else
//                    ((TextInputEditText) findViewById(R.id.emailField)).setError("Enter Your Email");
//
//                if (!String.valueOf(((TextInputEditText) findViewById(R.id.numberField)).getText()).isEmpty())
//                    user.setPhoneNumber(String.valueOf(((TextInputEditText) findViewById(R.id.numberField)).getText()));
//                else
//                    ((TextInputEditText) findViewById(R.id.numberField)).setError("Enter Your Phone Number");
//
//                if (String.valueOf(((TextInputEditText) findViewById(R.id.passfield)).getText()).equals(String.valueOf(((TextInputEditText) findViewById(R.id.conpassfield)).getText()))) {
//                    user.setPassword(String.valueOf(((TextInputEditText) findViewById(R.id.passfield)).getText()));
//
//                } else {
//                    ((TextInputEditText) findViewById(R.id.conpassfield)).setError("Password does not match");
//                    ((TextInputEditText) findViewById(R.id.conpassfield)).setText("");
////                    ((TextInputEditText) findViewById(R.id.conpassfield)).;
//
//
//
//                }
//                 realm.insertOrUpdate(user);
//            }
//
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(Signup.this, "Successful", Toast.LENGTH_SHORT).show();
//                Log.d("sync","sucess");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        finish();
//                    }
//                },1000);
//
//            }
//        }, new Realm.Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
//                Toast.makeText(Signup.this, ""+error, Toast.LENGTH_LONG).show();
//            }
//        });
//        realm.addChangeListener(new RealmChangeListener<Realm>() {
//            @Override
//            public void onChange(Realm realm) {
//                Toast.makeText(Signup.this, "Successful"+realm.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        realm1.close(); // Remember to close Realm when done.
    }
}
