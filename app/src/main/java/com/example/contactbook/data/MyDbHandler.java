package com.example.contactbook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.contactbook.MainActivity;
import com.example.contactbook.Model.Contact;
import com.example.contactbook.Params.params;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context){
        super(context, params.DB_NAME, null , params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create = "CREATE TABLE " + params.TABLE_NAME + "( " + params.Key_id +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + params.Key_Name + " TEXT , " + params.Key_ContactNum +
                 " TEXT, " + params.password + " TEXT , " + params.Key_Description  +
                " TEXT )";

        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addContact(Contact contact){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(params.Key_Name, contact.getName());
        values.put(params.Key_ContactNum, contact.getPhoneNumber());
        values.put(params.Key_Description, contact.getDescription());
        values.put(params.password, contact.getPassword());

        long a = db.insert(params.TABLE_NAME , null , values);
        Log.d("db check"+contact.getId() , "Successfully inserted");

        db.close();
        return a;
    }
    public List<Contact> getAllContacts(){
        int k=0;
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);
        cursor.moveToFirst();


        while (cursor.moveToNext())
        {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contact.setPassword(cursor.getString(cursor.getColumnIndex(params.password)));
                contact.setDescription(cursor.getString(4));
                contactList.add(contact);
        }


        return contactList;
    }


    public Contact contactAtposition(int pos){

        ArrayList<Contact> allContacts = (ArrayList<Contact>) getAllContacts();
        Contact contact1 = new Contact();
        int i=0;
        for(Contact contact : allContacts){

                contact1 = contact;
                if(i==pos) break;
                i++;
        }
        return contact1;

    }

    public Contact contactWithId(int id){

        ArrayList<Contact> allContacts = (ArrayList<Contact>) getAllContacts();
        Contact contact1 = new Contact();

        for(Contact contact : allContacts){
            if(contact.getId() == id){
                break;
            }
        }
        return contact1;
    }

    public int Update_contacts(int id, String name , String description, String phoneNum,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(params.Key_Name,name);
        contentValues.put(params.Key_ContactNum,phoneNum);
        contentValues.put(params.Key_Description,description);
        contentValues.put(params.password,password);
        int a = db.update(params.TABLE_NAME,contentValues, params.Key_id+ " = ?", new String[] {String.valueOf(id)});
        db.close();
        return a;
    }

    public void delete_contacts(int pos){

        SQLiteDatabase db = this.getWritableDatabase();
        Contact contact = this.contactAtposition(pos);
        int id = contact.getId();
        db.delete(params.TABLE_NAME, params.Key_id + " = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

}

