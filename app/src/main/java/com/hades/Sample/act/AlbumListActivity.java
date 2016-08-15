package com.hades.Sample.act;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hades.Sample.R;

import java.util.HashMap;

public class AlbumListActivity extends AppCompatActivity {

    ContentResolver cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        cr = this.getContentResolver();
    }

    /**
     * 获取缩略图
     */

    public void getThumbnail(){
        String[] projection = { MediaStore.Images.Thumbnails._ID,
                MediaStore.Images.Thumbnails.IMAGE_ID,
                MediaStore.Images.Thumbnails.DATA };
        Cursor cursor = cr.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection, null, null, null);
        if (cursor.moveToFirst()){
            int _id;
            int image_id;
            String image_path;
            int _idColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails._ID);
            int image_idColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
            int dataColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);

            do {
                _id = cursor.getInt(_idColumn);
                image_id = cursor.getInt(image_idColumn);
                image_path = cursor.getString(dataColumn); // 缩略图图片地址

            }while (cursor.moveToNext());
        }
    }

    /**
     * 获取原图
     */

    public void getAlbum(){
        String[] projection = { MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.ALBUM_KEY,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS };
        Cursor cursor = cr.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null, null, null);
        if (cursor.moveToFirst()){
            int _id;
            String album;
            String albumArt;
            String albumKey;
            String artist;
            int numOfSongs;

            int _idColumn = cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
            int albumColumn = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            int albumArtColumn = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            int albumKeyColumn = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_KEY);
            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
            int numOfSongsColumn = cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);

            do {
                _id = cursor.getInt(_idColumn);
                album = cursor.getString(albumColumn);
                albumArt = cursor.getString(albumArtColumn);
                albumKey = cursor.getString(albumKeyColumn);
                artist = cursor.getString(artistColumn);
                numOfSongs = cursor.getInt(numOfSongsColumn);

                HashMap<String, String> hash = new HashMap<>();
                hash.put("_id", String.valueOf(_id));
                hash.put("album", album);
                hash.put("albumArt", albumArt);
                hash.put("albumKey", albumKey);
                hash.put("artist", artist);
                hash.put("numOfSongs", String.valueOf(numOfSongs));


            }while (cursor.moveToNext());

        }
    }
}
