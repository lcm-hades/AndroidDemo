package HDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Hades on 2015/11/30.
 */
public class HDatabaseHelper extends SQLiteOpenHelper {

    private static final String name = "XNRDB";

    private static final int version = 1;

    public HDatabaseHelper(Context context){
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS person(personid integer primary key autoincrement, name varchar(20), age INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("test", oldVersion + "------------------" + newVersion);
        db.execSQL("ALTER TABLE person ADD phone VARCHAR(12)");
    }

}
