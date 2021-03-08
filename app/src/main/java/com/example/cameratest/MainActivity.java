package com.example.cameratest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imgView = findViewById(R.id.imgView);

        findViewById(R.id.btn_picture).setOnClickListener(onclickListener);
        findViewById(R.id.btn_gallery).setOnClickListener(onclickListener);

        //권한 체크하기
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {         //권한 허용
            Toast.makeText(MainActivity.this, "권한 허용", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) { //권한 거부
            Toast.makeText(MainActivity.this, "권한 거부", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener onclickListener = v -> {
      switch (v.getId()){
          // 촬영
          case R.id.btn_picture:
              Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
              if(intent.resolveActivity(getPackageManager()) != null){
                  File photoFile = null;
                  try {
                      photoFile = createImageFile();
                  }catch (IOException e){
                      e.printStackTrace();
                  }
                  if(photoFile != null){
                      photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName(), photoFile);
                      intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                      startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                  }
              }
//                if(ContextCompat.checkSelfPermission(MainActivity.this,
//                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//
//                }

                    break;
          // 이미지 선택 (Adapter 사용해야할듯)
          case R.id.btn_gallery:
                    break;
      }
    };

    private File createImageFile() throws IOException{
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_"+ timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File img =  File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = img.getAbsolutePath();
        return img;
    }

}