package com.example.gezginapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class AddPhotoActivity extends AppCompatActivity {

    ImageView userPhoto;
    Button selectPhotoButton;
    Button savePhotoButton;
    private static final int IMAGE_REQUEST = 111;
    Uri filePath;
    FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        firebaseStorage = FirebaseStorage.getInstance();//resmi firebase e kaydetme

        userPhoto = (ImageView) findViewById(R.id.user_saved_photo);
        selectPhotoButton = (Button) findViewById(R.id.select_photo_button);
        savePhotoButton = (Button) findViewById(R.id.save_photo_button);

        showPhoto();

        selectPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });

        savePhotoButton.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhoto();
            }
        });
    }

    private void selectPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Resim seçiniz"), IMAGE_REQUEST);
    }

    private void savePhoto() {
        if (filePath != null) {
            StorageReference storageReference = firebaseStorage.getReference();
            storageReference.child("userprofilphoto").putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddPhotoActivity.this, "Fotoğraf başarıyla kaydedildi", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddPhotoActivity.this, "Fotoğraf kaydedilemedi", Toast.LENGTH_LONG).show();
                }
            });//child userprofil fotyu bulur
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//sağ tıkla generate- override methods-onactivityresult

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();//resmi seçtiğimizde getiren urlrl gibi

            try {//hata olmasın diye try catch ekle
                Picasso.with(AddPhotoActivity.this).load(filePath).fit().centerCrop().into(userPhoto);//centercrop fotografın orta kısmını alır
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
private void showPhoto() {
    StorageReference storageReference = firebaseStorage.getReference();
    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        @Override
        public void onSuccess(Uri uri) {

            //Picasso.with(AddPhotoActivity.this).load(uri).fit().centerCrop().into(userPhoto);
            Glide.with(AddPhotoActivity.this).load(uri).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>(200, 200) {//into ile bipmap resmi alınır
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    userPhoto.setImageBitmap(resource); //bitmap tuurnde bir şey bekeldiğinde resourse gönderilir
                }
            });
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

            Toast.makeText(AddPhotoActivity.this, "Fotoğraf yükleme işlemi başarısız", Toast.LENGTH_SHORT).show();
        }
    });
}}