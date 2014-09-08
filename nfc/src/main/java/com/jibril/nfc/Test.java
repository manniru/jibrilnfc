package com.jibril.nfc;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		Db db = new Db("javadb","jdbc:derby:","3306","NfcDB","admin","admin");
		//db.create("USERS");
		///User user = new User();
		Dao dao = new Dao();
		
		//User user = dao.userTest(1);
		
		List<Transact> trn = dao.lisTransact();
		for(Transact tr: trn) { System.out.println(tr.getTagid()); }
		
		
		
		
	}
	
	public void test1() {
		Db db = new Db("javadb","jdbc:derby:","3306","NfcDB","admin","admin");
		//db.create("USERS");
		///User user = new User();
		UserDao dao = new UserDao();
		// add user
				//User us = dao.userTest(2); dao.addUser(us);
				
				// get user
				// User dbuser1 = dao.getUser(1); System.out.println(dao.toString(dbuser1));
				
				// update
				// User us1 = dao.getUser(1); System.out.println("before:"+us1.getFullname());
				// User us2 = dao.userTest(1);
				// us2.setFullname("Auwal Ali 2");
				// dao.editUser(us2);
				// User us3 = dao.getUser(1); System.out.println("after:"+us3.getFullname());
				
				// delete user
				//dao.deleteUser(2);
				
				// list users
				List<User> users = dao.lisUsers();
				for(User user: users) { System.out.println(user.getFullname()); }

			}
	

}
