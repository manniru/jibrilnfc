package com.jibril.nfc;

import java.text.SimpleDateFormat;

import android.content.ContentValues;

public class Test {
	private static final String DB_NAME = "jibrilnfcDb";
    private static final int DB_VERSION = 1; 
    public static final String TABLE_NAME = "Users";
    public static final String COL_NAME = "pName";
    public static final String COL_DATE = "pDate";
    private static final String STRING_CREATE = "CREATE TABLE "+TABLE_NAME+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        +COL_NAME+" TEXT, "+COL_DATE+" DATE);";
    
    public Test() {
    	System.out.println("this is constructor");
    }

	public static void main(String[] args) {
		System.out.println("hello");
		System.exit(0);
		ContentValues cv = new ContentValues(2);
		Test db = new Test();
		User user = new User();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //cv.put(COL_DATE, dateFormat.format(new Date())); 
		
		String username = "user1";
		String password = "user1";
		String mobileno = "60113084937";
		String email = "user1@gmail.com";
		String fullname = "User One";
		
		user.setId(1);
		user.setUsername(username);
		user.setPassword(password);
		user.setMobileno(mobileno);
		user.setEmail(email);
		user.setFullname(fullname);
		
		db.insert(user);

	}

	private void insert(User user) {
		System.out.println("save user");
		
	}

}
