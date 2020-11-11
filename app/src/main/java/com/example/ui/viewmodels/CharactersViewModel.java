package com.example.ui.viewmodels;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


//import com.example.ui.Model.CharacterModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CharactersViewModel extends AndroidViewModel {


    private ExecutorService executorService;
    public   FirebaseFirestore db;
    public MutableLiveData<List<DocumentSnapshot>> livelist;
    private List<DocumentSnapshot> result;

    public CharactersViewModel(@NonNull Application application) {
        super(application);
        db = FirebaseFirestore.getInstance();
        executorService = Executors.newSingleThreadExecutor();
        livelist=new MutableLiveData<List<DocumentSnapshot>>();
    }

    public MutableLiveData<List<DocumentSnapshot>> getAllCharacters() {
        db.collection("characters").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                  Log.d("Error",error.getMessage());
                     return;
                }
                result=value.getDocuments();
                livelist.setValue(result);
                Log.d("List",result.get(0).get("CharacterName").toString());
            }
        });
                return livelist;
        }
}
