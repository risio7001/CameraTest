package com.example.cameratest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imgView = findViewById(R.id.imgView);

        findViewById(R.id.btn_picture).setOnClickListener(onclickListener);
        findViewById(R.id.btn_gallery).setOnClickListener(onclickListener);
    }

    View.OnClickListener onclickListener = v -> {
      switch (v.getId()){
          // 촬영
          case R.id.btn_picture:

          // 이미지 선택
          case R.id.btn_gallery:

      }
    };
}