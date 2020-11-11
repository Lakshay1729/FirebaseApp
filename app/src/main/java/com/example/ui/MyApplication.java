package com.example.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

//import io.realm.Realm;
//import io.realm.RealmConfiguration;
//import io.realm.log.RealmLog;
//import io.realm.mongodb.App;
//import io.realm.mongodb.AppConfiguration;
//import io.realm.mongodb.Credentials;
//import io.realm.mongodb.User;
//import io.realm.mongodb.sync.SyncConfiguration;

public class MyApplication extends Application {
//    private RealmConfiguration configuration;
//    private User user;
//    private Realm realm;
//    static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        // The default Realm file is "default.realm" in Context.getFilesDir();
        // we'll change it to "myrealm.realm"
       // String appID = "ui-vdvol";;// replace this with your App ID
//        String appID = "ui-eygrw"; // replace this with your App ID
//
//        Realm.init(getApplicationContext(),BuildConfig.VERSION_NAME);
//        app= new App(new AppConfiguration.Builder("ui-eygrw").build());
////       // final io.realm.mongodb.User[] user = new io.realm.mongodb.User[1];
////        Credentials credentials = Credentials.anonymous();
////
////        app.loginAsync(credentials, it -> {
////            if (it.isSuccess()) {
////                Log.v("QUICKSTART", "Successfully authenticated anonymously.");
////                 user = app.currentUser();
////
////                // interact with MongoDB Realm via user object here
////                Long partitionValue = 123L;
////                SyncConfiguration config = new SyncConfiguration.Builder(user, partitionValue)
////                        .build();
////
////                 realm = Realm.getInstance(config);
////
////            } else {
////                Log.e("QUICKSTART", "Failed to log in. Error: " + it.getError().toString());
////            }
////        });
    }

}
