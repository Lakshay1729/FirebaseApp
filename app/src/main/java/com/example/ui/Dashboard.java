package com.example.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ui.viewmodels.CharactersViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.realm.Realm;
//import io.realm.mongodb.App;
//import io.realm.mongodb.Credentials;
//import io.realm.mongodb.sync.SyncConfiguration;


public class Dashboard extends AppCompatActivity {
    private static final String TAG ="Read" ;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ArrayList characterImages = new ArrayList<>(Arrays.asList(R.drawable.ironman, R.drawable.hulk, R.drawable.strange, R.drawable.wolverine, R.drawable.daredevil, R.drawable.spiderman, R.drawable.thor));
    ArrayList characterNames = new ArrayList<>( Arrays.asList("IronMan", "Hulk", "Doctor Strange", "Wolverine", "Daredevil", "Spiderman", "Thor"));
    private AppBarLayout layout;
    private boolean flag=false;
    private SharedPreferences sharedPreferences;
    public final String sharedPrefs="Shared";
//    private SyncConfiguration syncConfiguration;
    private Realm realm1;
    private FirebaseStorage storage= FirebaseStorage.getInstance();
    List<DocumentSnapshot> list=new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Flowable<List<DocumentSnapshot>> flo;
    private List<DocumentSnapshot> li;
    private Disposable disposable;
    private CustomAdapter customAdapter;
    private MutableLiveData<List<DocumentSnapshot>> listLiveData;
    private CharactersViewModel characterViewModel;
    private MutableLiveData<List<DocumentSnapshot>> livelist;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        sharedPreferences=getSharedPreferences(sharedPrefs,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        StorageReference storageRef = storage.getReference();
        customAdapter = new CustomAdapter(getApplicationContext(), li);
         characterViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CharactersViewModel.class);
//        Log.d("DashboardLog",characterViewModel.getAllCharacters().getValue().get(0).get("CharcterName").toString());
        characterViewModel.getAllCharacters().observe(Dashboard.this, new Observer<List<DocumentSnapshot>>() {
                        @Override
                        public void onChanged(List<DocumentSnapshot> documentSnapshots) {
//                            customAdapter.setData(li);
                            customAdapter.notifyDataSetChanged();
                            customAdapter = new CustomAdapter(getApplicationContext(), documentSnapshots);
                            recyclerView.setAdapter(customAdapter);

                        }
                    });


//        characterViewModel = ViewModelProviders.of(this).get(CharactersViewModel.class);
//        characterViewModel=new CharactersViewModel(getApplication());

//        db.collection("characters").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    Log.w(TAG, "Listen failed.", error);
//                    return;
//                }
//                if (value != null) {
//                    li = value.getDocuments();
//                    listLiveData=new MutableLiveData<List<DocumentSnapshot>>(li);
//                    listLiveData.observe(Dashboard.this, new Observer<List<DocumentSnapshot>>() {
//                        @Override
//                        public void onChanged(List<DocumentSnapshot> documentSnapshots) {
//                            li=documentSnapshots;
//
//                            customAdapter.setData(li);
//                            customAdapter.notifyDataSetChanged();
////                            recyclerView.setAdapter(customAdapter);
//
//
//                        }
//                    });
////                    customAdapter.setData(li);
//                    recyclerView.setAdapter(customAdapter);
//
//                    Log.d(TAG, "Current cites in CA: " + flo);
//
//                }
//                Log.d("Values", value.toString());
//            }
//        });









//        MyApplication.app.loginAsync(Credentials.anonymous(), new App.Callback<io.realm.mongodb.User>() {
//            @Override
//            public void onResult(App.Result<io.realm.mongodb.User> result) {
//                if(result.isSuccess()) {
//                    //configuration=new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
//                    Log.d("Suxess","Success");
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
//                    Log.d("Suxess","UNSuccess");
//                }
//
//            }
//        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            sharedPreferences.edit().clear().apply();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();

            }
        });

        findViewById(R.id.form_contribute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CharacterForm.class));
            }
        });
//        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              layout=  (AppBarLayout)findViewById(R.id.appbar_layout);
//                   if(!flag) {
//                       layout.setExpanded(true);
//                       ((FloatingActionButton) findViewById(R.id.fab)).setImageResource(R.drawable.arrow_up);
//                       flag=true;
//                   }
//                   else {
//                       layout.setExpanded(false);
//                       flag=false;
//                       ((FloatingActionButton) findViewById(R.id.fab)).setImageResource(R.drawable.arrow_down);
//                   }
//                    //flag=false;
//            }
//        });
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        Context context;

        public CustomAdapter(Context context,List<DocumentSnapshot> list ) {
            this.context = context;
            li=list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }



        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            try {
                holder.name.setText( li.get(position) != null ? li.get(position).getData().get("CharacterName").toString() : null);
                Glide.with(getApplicationContext()).load(li.get(position).getData().get("ImageLink").toString()).into(holder.image);
            }
            catch (Exception e)
            {
                Log.d("TAG",e.getMessage().toString());
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putString("pos",li.get(position).getId()).apply();
                    startActivity(new Intent(getApplicationContext(),StoryLine.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            Log.d("Size",li.size()+"");
            return li.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            ImageView image;


            public MyViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.text1);
                image = (ImageView) itemView.findViewById(R.id.card_image);

            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
//        db.collection("characters").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (value != null) {
//                    li = value.getDocuments();
//                    customAdapter = new CustomAdapter(getApplicationContext(), li);
//                    recyclerView.setAdapter(customAdapter);
//                }
//                else if(error!=null)
//                {
//                    Log.d("Error",error.getMessage());
//                }
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        disposable.dispose();
    }


        class PostDiffCallback extends DiffUtil.Callback{

            private final List<DocumentSnapshot> oldPosts, newPosts;

            public PostDiffCallback(List<DocumentSnapshot> oldPosts, List<DocumentSnapshot> newPosts) {
                this.oldPosts = oldPosts;
                this.newPosts = newPosts;
            }

            @Override
            public int getOldListSize() {
                return oldPosts.size();
            }

            @Override
            public int getNewListSize() {
                return newPosts.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldPosts.get(oldItemPosition).getId() == newPosts.get(newItemPosition).getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
            }


        }


}
