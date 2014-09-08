package com.jibril.nfc;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Db extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_NAME = "users";
    private final ArrayList<User> user_list = new ArrayList<User>();

    public Db(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
	String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("+
    "id INTEGER PRIMARY KEY,"+
	"username TEXT,"+
    "password TEXT,"+
	"mobileno TEXT,"+
    "email TEXT,"+
	"fullname TEXT)";
	db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	onCreate(db);
    }

    public void addUser(User user) {
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put("id", user.getId());
	values.put("username", user.getUsername());
	values.put("password", user.getPassword());
	values.put("mobileno", user.getMobileno());
	values.put("email", user.getEmail());
	values.put("fullname", user.getFullname());

	db.insert(TABLE_NAME, null, values);
	Log.d("JibrilNFC", "useradded="+user.getFullname());
	db.close();
    }

    User getUser(int id) {
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.query(TABLE_NAME, new String[] { "id", "username", "password", "mobileno", "email", "mobileno"},"id=?",
		new String[] { String.valueOf(id) }, null, null, null, null);
	if (cursor != null) cursor.moveToFirst();
	
	User user = new User();
	user.setId(Integer.parseInt(cursor.getString(0)));
	user.setUsername(cursor.getString(1));
	user.setPassword(cursor.getString(2));
	user.setMobileno(cursor.getString(3));
	user.setEmail(cursor.getString(4));
	user.setFullname(cursor.getString(5));
	
	cursor.close();
	db.close();

	return user;
    }

    public ArrayList<User> getUsers() {
	try { user_list.clear();
	    String selectQuery = "SELECT  * FROM " + TABLE_NAME;
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);

	    if (cursor.moveToFirst()) {
		do { User user = new User();
		    user.setId(Integer.parseInt(cursor.getString(0)));
			user.setUsername(cursor.getString(2));
			user.setPassword(cursor.getString(3));
			user.setMobileno(cursor.getString(4));
			user.setEmail(cursor.getString(5));
			user.setFullname(cursor.getString(6));
			user_list.add(user);
		} while (cursor.moveToNext()); }

	    cursor.close();
	    db.close();
	    return user_list;
	} catch (Exception e) { Log.e("all_contact", "" + e); }

	return user_list;
    }

    public int updateUser(User user) {
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues values = new ContentValues();
	values.put("username", user.getUsername());
	values.put("password", user.getPassword());
	values.put("mobileno", user.getMobileno());
	values.put("email", user.getEmail());
	values.put("fullname", user.getFullname());

	return db.update(TABLE_NAME, values, " id= ?", new String[] { String.valueOf(user.getId()) });
    }

    public void deleteUser(int id) {
	SQLiteDatabase db = this.getWritableDatabase();
	db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(id) });
	db.close();
    }

    public int getTotalUsers() {
	String countQuery = "SELECT  * FROM " + TABLE_NAME;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.rawQuery(countQuery, null);
	cursor.close();
	return cursor.getCount();
    }

}