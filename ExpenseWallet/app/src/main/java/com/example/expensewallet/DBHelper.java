package com.example.expensewallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "expensewallet";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "record";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String AMOUNT_COL = "amount";
    private static final String TYPE_COL = "type";

    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COL + " TEXT,"
            + AMOUNT_COL + " INTEGER,"
            + TYPE_COL + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addRecord(String name, int amount, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COL,name);
        contentValues.put(AMOUNT_COL,amount);
        contentValues.put(TYPE_COL,type);

        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }

    public ArrayList<Record> getAllRecord(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);

        ArrayList<Record> records = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int amount = cursor.getInt(2);
                String type = cursor.getString(3);

                records.add(new Record(id,name,amount,type));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

    public ArrayList<Record> getRecordType(String recordType){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + TYPE_COL + " = '" + recordType +"'",null);

        ArrayList<Record> records = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int amount = cursor.getInt(2);
                String type = cursor.getString(3);

                records.add(new Record(id,name,amount,type));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

    public void updateRecord(int id, String name, String amount, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME_COL,name);
        contentValues.put(AMOUNT_COL,amount);
        contentValues.put(TYPE_COL,type);

        db.update(TABLE_NAME,contentValues,"id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteRecord(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "id=?",new String[]{String.valueOf(id)});
        db.close();
    }
}
