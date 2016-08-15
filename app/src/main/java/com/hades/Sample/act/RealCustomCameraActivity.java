package com.hades.Sample.act;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.hades.Sample.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RealCustomCameraActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_custom_camera);

        String path = getIntent().getStringExtra("picPath");
        ImageView imageView = (ImageView)findViewById(R.id.picture);

        try {
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            FileInputStream fis = new FileInputStream(path);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


//        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        imageView.setImageBitmap(bitmap);
    }

}
