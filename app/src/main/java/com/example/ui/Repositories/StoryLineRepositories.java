package com.example.ui.Repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class StoryLineRepositories {
    private final FirebaseFirestore db;
    private final String string;

   public MutableLiveData<DocumentSnapshot> document=new MutableLiveData<>();
    public StoryLineRepositories(Context applicationContext){
        db = FirebaseFirestore.getInstance();
        string=applicationContext.getSharedPreferences("Shared",Context.MODE_PRIVATE).getString("pos",null);}

    public void getFirebaseData(){
        db.collection("characters").document(string).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                document.setValue(task.getResult());
            }
        });



//    addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                result=task.getResult();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("Exception",e.getMessage());
//            }
//        });
        }

        public void updateFirebaseData(HashMap<String,Object> object){
        db.collection("characters").document(string).set(object);
        }



}
