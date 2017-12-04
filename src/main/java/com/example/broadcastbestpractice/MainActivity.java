package com.example.broadcastbestpractice;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //我是新添加的内容 测试git
        //我是新添加的内容 测试git2
        //我是新添加的内容 测试git3
        Button forceOffline = findViewById(R.id.force_offline);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });


        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 4);
        Button createDatabase = findViewById(R.id.create);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
                Log.d("MainActivity", "onClick: createDatabase");
            }
        });


        Button addData = findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建数据库
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Log.d("MainActivity", "onClick: addData");
                //添加数据
                ContentValues values = new ContentValues();
                //NO.1 info
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.98);
                db.insert("Book", null, values);
                values.clear();
                //NO.2 info
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 451);
                values.put("price", 19.59);
                db.insert("Book", null, values);

//                //# add
//                db.execSQL("insert into Book(name,author,pages,price) values('?','?','?','?')", new String[]{"Just for fun","Linus","355","99.9"});
//                //# update
//                db.execSQL("update Book set price = '?' where name = '?'",new String[]{"88","Just for fun"});
//                //# del
//                db.execSQL("delete from Book where pages > '?'", new String[]{"300"});
//                //# query
//                db.rawQuery("select * from Book",null);
            }
        });

        Button updateData = findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                values.put("pages", 454);
                db.update("Book", values, "name=?", new String[]{"The Da Vinci Code"});
                Log.d("MainActivity", "onClick: updateData");
            }
        });

        Button deleteButton = findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book", "pages>?", new String[]{"500"});
                Log.d("MainActivity", "onClick: deleteButton");
            }
        });

        Button queryButton = findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "book name is: "+ name);
                        Log.d("MainActivity", "book name is: "+ author);
                        Log.d("MainActivity", "book name is: "+ pages);
                        Log.d("MainActivity", "book name is: "+ price);
                    } while (cursor.moveToNext());
                }
                cursor.close();
//                db.execSQL("insert into Book(name,author,pages,price) values(?,`?`,`?`,`?`)", new String[]{"Just for fun","Linus","355","99.9"});
                Log.d("MainActivity", "onClick: queryButton");
            }
        });
    }
}
