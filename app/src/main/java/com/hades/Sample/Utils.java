package com.hades.Sample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.List;

/**
 * Created by Hades on 2015/10/19.
 */
public class Utils {

    public static void openCLD(String packageName,Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pi = null;

        try {
            pi = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

        }
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null ) {
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }

    public static Bitmap getCompressBitmap(Context context, Uri uri, int w,
                                           int h) {
        String path = Utils.getPathFromUri(context, uri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = caculateInSampleSize(options, w, h);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static String getPathFromUri(Context context, Uri uri) {
        String res = null;
        String[] datas = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, datas, null,
                null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public static int caculateInSampleSize(BitmapFactory.Options options,
                                           int w, int h) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (w == 0 || h == 0)
            return 1;
        if (height > h || width > w) {
            int heightRatio = Math.round((float) height / (float) h);
            int widthRatio = Math.round((float) width / (float) w);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

}
