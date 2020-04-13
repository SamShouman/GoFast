package com.example.start;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DBHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION=10;
    private static final String DATABASE_NAME="users.db";
    public static final String  TABLE_NAME="users";
    public static final String COLUMN_USERNAME="username";
    public static final String COLUMN_PASSWORD ="password";
    public static final String COLUMN_EMAIL="email";
    //---------------------------------------------------------------
    public static final String FACEBOOK_TABLE="facebook";
    public static final String FACEBOOK_USER="username";
    public static final String FACEBOOK_FRIEND_NAME="friend_name";
    public static final String FACEBOOK_URL="facebook_url";
    //--------------------------------------------------------------------
    public static final String INSTAGRAM_TABLE="instagram";
    public static final String INSTAGRAM_USER="username";
    public static final String INSTAGRAM_FRIEND_NAME="friend_name";
    public static final String INSTAGRAM_URL="instagram_url";
    //--------------------------------------------------------------------
    public static final String WHATSAPP_TABLE="whatsapp";
    public static final String WHATSAPP_USER="username";
    public static final String WHATSAPP_FRIEND_NAME="friend_name";
    public static final String WHATSAPP_URL="whatsapp_url";
    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE " + TABLE_NAME +  " ( " +
                COLUMN_USERNAME + " TEXT PRIMARY KEY " + " , "+
                COLUMN_PASSWORD + " TEXT " +" , "+
                COLUMN_EMAIL+ " TEXT "+ " );";
        db.execSQL(query);

        String facebook_query="CREATE TABLE " + FACEBOOK_TABLE + " ( " +
                FACEBOOK_USER+ " TEXT " + " , " +
                FACEBOOK_FRIEND_NAME + " TEXT " + " , " +
                FACEBOOK_URL + " TEXT " + " ); ";

        db.execSQL(facebook_query);

        String instagram_query="CREATE TABLE "+ INSTAGRAM_TABLE +
                " ( " + INSTAGRAM_USER + " TEXT " + " , "
                + INSTAGRAM_FRIEND_NAME + " TEXT " + " , " +
                INSTAGRAM_URL + " TEXT " + " ); ";
        db.execSQL(instagram_query);

        String whatsapp_query="CREATE TABLE "+ WHATSAPP_TABLE +
                " ( " + WHATSAPP_USER + " TEXT " + " , "
                + WHATSAPP_FRIEND_NAME + " TEXT " + " , " +
                WHATSAPP_URL + " TEXT " + " ); ";
        db.execSQL(whatsapp_query);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+FACEBOOK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ INSTAGRAM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ WHATSAPP_TABLE);
        onCreate(db);

    }

    public void addUser(String username,String password,String email){
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_PASSWORD,password);
        contentValues.put(COLUMN_USERNAME,username);
        contentValues.put(COLUMN_EMAIL,email);
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_NAME,null,contentValues);
        db.close();

    }

    public boolean login(String username,String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c=db.rawQuery("SELECT username,password FROM "+TABLE_NAME,null); //"SELECT * FROM "+ TABLE_NAME+ " WHERE "+COLUMN_USERNAME+ " =?  AND "+ COLUMN_PASSWORD + " =?"  +" ; ",new String[]{username,password})
        String u,p;
       if(c.moveToNext()){
           do{
               u=c.getString(0);
               p=c.getString(1);
               if(u.equals(username)&&p.equals(password)){
                   c.close();
                   return true;
               }

           }while(c.moveToNext());


       }
        return false;
    }

    public void deleteAll(){
        SQLiteDatabase db=getWritableDatabase();
      //  db.delete(TABLE_NAME,null,null);
       // String query="DROP TABLE IF EXISTS "+ TABLE_NAME;
        //db.execSQL(query);
        onUpgrade(db,1,3);
        db.close();
    }

    public boolean checkUsername(String username){
        SQLiteDatabase db=getReadableDatabase();
        String query="Select username from "+ TABLE_NAME;
        Cursor c=db.rawQuery(query,null);
        String u;
        if(c.moveToNext()){
            do{
                u=c.getString(0);
                if(u.equals(username)){
                    c.close();
                    return true;
                }

            }while(c.moveToNext());


        }
        return false;
    }

    public void addFacebookLink(String username,String friends_name,String link){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(FACEBOOK_USER,username);
        contentValues.put(FACEBOOK_FRIEND_NAME,friends_name);
        contentValues.put(FACEBOOK_URL,link);
        db.insert(FACEBOOK_TABLE,null,contentValues);
        db.close();



    }

    public HashMap<String,String> getFacebookNames(String name){
        HashMap<String,String> names=new HashMap<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT username,friend_name,facebook_url FROM " + FACEBOOK_TABLE,null);
        if(c.moveToNext()) {
            do {
                String a = c.getString(0);
                if (a.equals(name)) {
                    String link =
                            names.put(c.getString(1), c.getString(2));
                }
            } while (c.moveToNext());
        }

            return names;
    }

    public String getFacebookURL(String name){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT username,friend_name,facebook_url FROM "+ FACEBOOK_TABLE,null);
        if(c.moveToNext()){
            do{
                String x=c.getString(1);
                if(x.equals(name))
                    return c.getString(2);
            }while(c.moveToNext());
        }
        return null;
    }

    public void deleteFacebookUser(String name){

        SQLiteDatabase db=getWritableDatabase();
       // db.delete(FACEBOOK_TABLE,FACEBOOK_FRIEND_NAME+" = "+ name,null);

                    String query="DELETE FROM facebook WHERE friend_name = '"+name+ "'";
                    db.execSQL(query);

    }

    public HashMap<String,String> getInstagramNames(String name){
        HashMap<String,String> names=new HashMap<>();
        SQLiteDatabase db=getReadableDatabase();
            Cursor c=db.rawQuery("SELECT username,friend_name,instagram_url FROM " + INSTAGRAM_TABLE,null);
        if(c.moveToNext()) {
            do {
                String a = c.getString(0);
                if (a.equals(name)) {
                    String link =
                            names.put(c.getString(1), c.getString(2));
                }
            } while (c.moveToNext());
        }

        return names;
    }

    public void addInstagramLink(String username,String friends_name,String link){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(INSTAGRAM_USER,username);
        contentValues.put(INSTAGRAM_FRIEND_NAME,friends_name);
        contentValues.put(INSTAGRAM_URL,link);
        db.insert(INSTAGRAM_TABLE,null,contentValues);
        db.close();



    }

    public String getInstagramURL(String name){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT username,friend_name,instagram_url FROM "+ INSTAGRAM_TABLE,null);
        if(c.moveToNext()){
            do{
                String x=c.getString(1);
                if(x.equals(name))
                    return c.getString(2);
            }while(c.moveToNext());
        }
        return null;
    }

    public void deleteInstagramUser(String name){

        SQLiteDatabase db=getWritableDatabase();
        // db.delete(FACEBOOK_TABLE,FACEBOOK_FRIEND_NAME+" = "+ name,null);

        String query="DELETE FROM instagram WHERE friend_name = '"+name+ "'";
        db.execSQL(query);

    }

    public void addContact(String username,String friends_name,String number){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(WHATSAPP_USER,username);
        contentValues.put(WHATSAPP_FRIEND_NAME,friends_name);
        contentValues.put(WHATSAPP_URL,number);
        db.insert(WHATSAPP_TABLE,null,contentValues);
        db.close();



    }

    public HashMap<String,String> getContactsNames(String name){
        HashMap<String,String> names=new HashMap<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT username,friend_name,whatsapp_url FROM " + WHATSAPP_TABLE,null);
        if(c.moveToNext()) {
            do {
                String a = c.getString(0);
                if (a.equals(name)) {
                    String link =
                            names.put(c.getString(1), c.getString(2));
                }
            } while (c.moveToNext());
        }

        return names;
    }


    public String getContactNumber(String name){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT username,friend_name,whatsapp_url FROM "+ WHATSAPP_TABLE,null);
        if(c.moveToNext()){
            do{
                String x=c.getString(1);
                if(x.equals(name))
                    return c.getString(2);
            }while(c.moveToNext());
        }
        return null;
    }

    public void deleteContact(String name){

        SQLiteDatabase db=getWritableDatabase();
        // db.delete(FACEBOOK_TABLE,FACEBOOK_FRIEND_NAME+" = "+ name,null);

        String query="DELETE FROM whatsapp WHERE friend_name = '"+name+ "'";
        db.execSQL(query);

    }

    public boolean checkWhatsappContact(String name){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT username,friend_name FROM " + WHATSAPP_TABLE,null);
        if(c.moveToNext()){
            do {
                String k = c.getString(1);
                if (k.equals(name))
                    return true;
            }while(c.moveToNext());
        }



        return false;
    }

    public boolean checkFacebookUsername(String name){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT username,friend_name FROM " + FACEBOOK_TABLE,null);
        if(c.moveToNext()){
            do {
                String k = c.getString(1);
                if (k.equals(name))
                    return true;
            }while(c.moveToNext());
        }



        return false;
    }

    public boolean checkInstagramUsername(String name){
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT username,friend_name FROM " + INSTAGRAM_TABLE,null);
        if(c.moveToNext()){
            do {
                String k = c.getString(1);
                if (k.equals(name))
                    return true;
            }while(c.moveToNext());
        }



        return false;
    }

    public void deleteFacebookUsers(String name){
        SQLiteDatabase db=getWritableDatabase();
        String query="DELETE FROM facebook WHERE username = '"+name+ "'";
        Cursor c=db.rawQuery("SELECT username FROM "+ FACEBOOK_TABLE,null) ;
        if(c.moveToNext()){
            do{
                String k=c.getString(0);
                if(k.equals(name))
                    db.execSQL(query);
            }while(c.moveToNext());
        }
    }

    public void deleteInstagramUsers(String name){
        SQLiteDatabase db=getWritableDatabase();
        String query="DELETE FROM instagram WHERE username = '"+name+ "'";
        Cursor c=db.rawQuery("SELECT username FROM "+ INSTAGRAM_TABLE,null) ;
        if(c.moveToNext()){
            do{
                String k=c.getString(0);
                if(k.equals(name))
                    db.execSQL(query);
            }while(c.moveToNext());
        }
    }

    public void deleteWhatsappContacts(String name){
        SQLiteDatabase db=getWritableDatabase();
        String query="DELETE FROM whatsapp WHERE username = '"+name+ "'";
        Cursor c=db.rawQuery("SELECT username FROM "+ WHATSAPP_TABLE,null) ;
        if(c.moveToNext()){
            do{
                String k=c.getString(0);
                if(k.equals(name))
                    db.execSQL(query);
            }while(c.moveToNext());
        }
    }

}

