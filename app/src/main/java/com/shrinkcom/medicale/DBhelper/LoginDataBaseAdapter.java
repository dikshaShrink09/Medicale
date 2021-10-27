package com.shrinkcom.medicale.DBhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class LoginDataBaseAdapter
{
    static final String DATABASE_NAME = "addressDB";
    static final int DATABASE_VERSION = 1;
    static  final  String table_name ="LOGIN";
    public static final int NAME_COLUMN = 1;


    // TODO: Create public field for each column in your table.
// SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+table_name+
            "( " +"ID"+" integer primary key autoincrement,"+ "FullName text,Street text,City text,Zip text,State text,Email text,Phone text); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String full,String street,String city,String zip,String state,String email,String phone)
    {
        ContentValues newValues = new ContentValues();
// Assign values for each row.
        newValues.put("FullName", full);
        newValues.put("Street", street);
        newValues.put("City",city);
        newValues.put("Zip",zip);
        newValues.put("State",state);
        newValues.put("Email",email);
        newValues.put("Phone",phone);
        Log.d("values",newValues.toString());
// Insert the row into your table
        db.insert(table_name, null, newValues);
Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName)
    {
//String id=String.valueOf(ID);
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
// Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getSinlgeEntry(String userName)
    {
        Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }





    public void updateEntry(String userName,String name,String password)
    {
// Define the updated row content.
        ContentValues updatedValues = new ContentValues();
// Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("Name", name);
        updatedValues.put("PASSWORD",password);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});
    }

 public void getContact(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("LOGIN", new String[] { "ID",
                        "USERNAME", "Name" }, "ID" + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

//        GetUserModal contact = new GetUserModal(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
        // return contact

    }

    public List<GetUserModal> getAllContacts() {
        List<GetUserModal> contactList = new ArrayList<GetUserModal>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + table_name;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                GetUserModal contact = new GetUserModal();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.setFullname(cursor.getString(1));
                contact.setStreet(cursor.getString(2));
                contact.setCity(cursor.getString(3));
                contact.setZipcode(cursor.getString(4));
                contact.setState(cursor.getString(5));
                contact.setEmail(cursor.getString(6));
                contact.setPhone_number(cursor.getString(7));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + table_name;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

}
