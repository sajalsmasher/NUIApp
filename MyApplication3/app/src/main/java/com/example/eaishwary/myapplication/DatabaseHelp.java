package com.example.eaishwary.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelp extends SQLiteOpenHelper {

    public static String database_name="task.db";
    public static String table_name="tasks";
    public static String col1="TASK_NO";
    public static String col2="TASK";
    public static String col3="DATE";
    public static String col4="DEADLINE";
    public static String col5="TYPE";
    public static String col6="NOTES";



    public DatabaseHelp(Context context) {
        super(context,database_name,null,1);

        // TODO Auto-generated constructor stub


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL("create table "+table_name+"("+col1+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+col2+" TEXT NOT NULL,"+col3+" TEXT,"+col4+" TEXT,"+col5+" TEXT,"+col6+" TEXT )");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
        db.execSQL("drop table if exists "+table_name);
        onCreate(db);
    }


    public boolean insertData(String task,String date,String deadline,String type,String notes){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(col2, task);
        contentValues.put(col3, date);
        contentValues.put(col4, deadline);
        contentValues.put(col5, type);
        contentValues.put(col6, notes);

        long result = db.insert(table_name, null, contentValues);
        if(result==-1)
            return false;
        else
            return true;

    }

    public void deleteData(String d)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from "+table_name+" where "+col2+"='"+d+"'");
        //database.delete(table_name, col2 + "=" + d , null);
        // database.close();
    }

    public Cursor details(String d)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor r=database.rawQuery("select * from "+table_name+" where "+col2+"='"+d+"'",null);
        //database.delete(table_name, col2 + "=" + d , null);
        database.close();
        return r;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+table_name, null);

        return res;

    }

}
