package com.hades.Sample.Photo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Hades on 2015/10/19.
 */
public class PickPhoto {

    public static final int PHOTO = 0;
    public static final int CAMERA = 1;
    public static final int PHOTO_CRO = 2;
    public static final int CAMERA_CROP = 3;

    public static void startCamera(Activity activity, Uri imageUri, int requstCode){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, requstCode);
    }

    public static void startPhoto(Activity activity, int requstCode){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
//        .setType("image/*")
//                .putExtra("crop", "true")
//                .putExtra("scale", true)
//                .putExtra("aspectX", 1)
//                .putExtra("aspectY", 1)
//                .putExtra("outputX", 300)
//                .putExtra("outputY", 300)
//                .putExtra("return-data", false)
                // .putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
//                .putExtra("noFaceDetection", true)
//                .putExtra("scaleUpIfNeeded", true)
 //        .putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, requstCode);
    }

}
