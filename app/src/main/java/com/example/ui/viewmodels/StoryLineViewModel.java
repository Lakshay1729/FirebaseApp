package com.example.ui.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.ui.R;
import com.example.ui.Repositories.StoryLineRepositories;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;

public class StoryLineViewModel extends AndroidViewModel {
    public  StoryLineRepositories repository;
    public FirebaseFirestore db;
    public MutableLiveData<Boolean> liked;
    public String colorRed="#FF0000";
    public String colorGrey="#1100aa";
    public int color;

    public StoryLineViewModel(@NonNull Application application) {
        super(application);
        liked=new MutableLiveData<>(false);
       repository= new StoryLineRepositories(application.getApplicationContext());

    }
    public MutableLiveData<DocumentSnapshot> getData()
    {
        return repository.document;
    }
    public void updateData(HashMap<String,Object> objectHashMap){
        repository.updateFirebaseData(objectHashMap);
    }
    public void setColor(){
        color=liked.getValue()? R.color.colorPrimary:R.color.colorAccent;
    }

}

