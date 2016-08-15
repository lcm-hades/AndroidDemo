package com.hades.Sample.act;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hades.Sample.Model.Person;
import com.hades.Sample.R;

import java.util.ArrayList;
import java.util.List;

import HDatabase.HDatabaseHelper;

public class DBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        initData();
    }

    private void initData() {
        HDatabaseHelper helper = new HDatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        // db.execSQL("insert into person(name, age) values(?, ?)", new Object[]{"李", 25});
        List<Person> persons = getAllData(db);
        for (int i=0; i<persons.size(); i++){
            Person per = persons.get(i);
            Log.i("test", per.id + " ======= " + per.name + " ====== " + per.age);
        }
        db.close();
        helper.close();
    }

    private List<Person> getAllData(SQLiteDatabase db){
        List<Person> persons = new ArrayList<>();
        Person person = null;
        Cursor cursor = db.rawQuery("select * from person", null);
        while (cursor.moveToNext()){
            person = new Person();
            person.id = cursor.getInt(0);
            person.name = cursor.getString(1);
            person.age = cursor.getInt(2);
            persons.add(person);
        }
        return persons;
    }

}
