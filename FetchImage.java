package com.sandeepkambojiftmu.firebasestorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class FetchImage extends AppCompatActivity {

    ImageView mImageView,imageView1;
    StorageReference storageReference,storageReference1;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_image);

        mImageView=findViewById(R.id.image1);
        imageView1=findViewById(R.id.image2);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReferenceFromUrl("gs://sandeep-4e7e4.appspot.com").child("hcllogo.png");
        storageReference1=storage.getReferenceFromUrl("gs://sandeep-4e7e4.appspot.com").child("pizza.jpg");
        try {
            final File localfile=File.createTempFile("images","jpg");
            storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    imageView1.setImageBitmap(bitmap);
                    mImageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                }
            });
        } catch (IOException e){

        }
        final long ONE_MEGABYTE=1024*1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap=BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                mImageView.setImageBitmap(bitmap);
            }
        });
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("Tuts+","uri:"+uri.toString());
            }
        });

        storageReference1.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap=BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView1.setImageBitmap(bitmap);
            }
        });
        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("Tuts+","uri:"+uri.toString());
            }
        });
    }
}