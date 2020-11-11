package com.example.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.ui.viewmodels.CharactersViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

//import static com.example.ui.MyApplication.app;

public class CharacterForm extends AppCompatActivity {
    private static final String TAG ="Log" ;

    Realm realm1;
//    private Characters character;
    private SharedPreferences sharedPreferences;
    public final String sharedPrefs="Shared";
    private SharedPreferences.Editor editor;
//    private User user;
private FirebaseStorage storage= FirebaseStorage.getInstance();

    private byte[] byteArray;
    private Uri picUri;

    private Task<Uri> downloadUri;
    private HashMap<String, Object> character;
    private int flag=0;
    private String name;
    private boolean yes=false;
    private UploadTask uploadTask;
    private StorageReference ref;
    private String generatedFilePath;
    private CharactersViewModel characterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_character_form);
        sharedPreferences=getSharedPreferences(sharedPrefs,MODE_PRIVATE);
        characterViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CharactersViewModel.class);
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
//        character = new Characters( 123L);
//        user=realm1.where(User.class).equalTo("email",sharedPreferences.getString("email","aaa")).findFirst();

        findViewById(R.id.character_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickerIntent=new Intent(Intent.ACTION_PICK);
                pickerIntent.setType("image/*");
                startActivityForResult(pickerIntent,100);
            }
        });
        findViewById(R.id.submit_form).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createCharacter();
            }
        });
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        super.onActivityResult(requestCode,resultCode,intent);
        if(resultCode==RESULT_OK)
        {
            Log.d("path",intent.getData().getPath());
            picUri = intent.getData();
            Bitmap bp=null ;
            if(picUri !=null) {
                try {
                    bp = MediaStore.Images.Media.getBitmap(
                            this.getContentResolver(),
                            picUri);
                    ((CircleImageView)findViewById(R.id.character_image)).setImageBitmap(bp);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bp.compress(Bitmap.CompressFormat.JPEG,80,stream);
             byteArray = stream.toByteArray();
//            character.setImage(byteArray);
        }
    }

    private void createCharacter() {
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//
//                //realm.createObject(User.class);
//                if (!String.valueOf(((TextInputEditText) findViewById(R.id.character_name)).getText()).isEmpty())
//                    character.setName(String.valueOf(((TextInputEditText) findViewById(R.id.character_name)).getText()));
//                else
//                    ((TextInputEditText) findViewById(R.id.character_name)).setError("Enter Character's Name");
//
//                if (!String.valueOf(((TextInputEditText) findViewById(R.id.description_character)).getText()).isEmpty())
//                    character.setDescription(String.valueOf(((TextInputEditText) findViewById(R.id.description_character)).getText()));
//                else
//                    ((TextInputEditText) findViewById(R.id.description_character)).setError("Enter Character's description");
//
//                user.getCharacters().add(character);
//
//                realm.insertOrUpdate(user);
//            }
//
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(CharacterForm.this, "Successful", Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(CharacterForm.this, ""+error, Toast.LENGTH_LONG).show();
//            }
//        });
        String random=UUID.randomUUID().toString();
        String path="characterImages/"+ random+".png";
        StorageReference fireimageRef=storage.getReference(path);
        StorageMetadata metadata=new StorageMetadata.Builder().setCustomMetadata("caption",String.valueOf(((TextInputEditText) findViewById(R.id.character_name)).getText())).build();

        uploadTask=fireimageRef.putBytes(byteArray,metadata);
        uploadTask.addOnProgressListener(CharacterForm.this, new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                ((ProgressBar)findViewById(R.id.progress_horizontal_1)).setVisibility(View.VISIBLE);
                ((ProgressBar)findViewById(R.id.progress_horizontal_1)).setMax(10000);
                ((ProgressBar)findViewById(R.id.progress_horizontal_1)).setProgress((int)(10000*snapshot.getBytesTransferred()/snapshot.getTotalByteCount()));
                ((ProgressBar)findViewById(R.id.progress_horizontal_1)).setSecondaryProgress((int)(10000*snapshot.getBytesTransferred()/snapshot.getTotalByteCount())+500);
            }
        });
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                return task.getResult().getStorage().getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                generatedFilePath = task.getResult().toString();

                Log.d("DEKHE", generatedFilePath);

                character = new HashMap<String, Object>();
                character.put("CharacterName", String.valueOf(((TextInputEditText) findViewById(R.id.character_name)).getText()));
                character.put("Description", String.valueOf(((TextInputEditText) findViewById(R.id.description_character)).getText()));
                character.put("ImageLink", generatedFilePath);


               characterViewModel.db.collection("characters")
                        .add(character)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                finish();
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failyre",e.getMessage());
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        realm1.close(); // Remember to close Realm when done.
    }
}
