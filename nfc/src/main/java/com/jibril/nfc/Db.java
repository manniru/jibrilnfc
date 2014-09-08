package com.jibril.nfc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {
	static String dbtype = "javadb";
	static String dbhost = "jdbc:derby:";
	static String dbport = "3306";
	static String dbname = "Database";
	static String dbuser = "admin";
	static String dbpass = "admin";

	public static void main(String[] args) {
		///Db db = new Db("javadb" , "jdbc:derby:", "3306", "NfcDB", "admin", "admin");
		///Connection cn = db.connect(dbtype, dbhost, dbname, dbuser, dbpass);
		///db.create("USERS");
		///db.createTransact();

	}
	
	public Db(String dbtype, String dbhost, String dbport, String dbname, String dbuser, String dbpass) {
		this.dbtype = dbtype;
		this.dbhost = dbhost;
		this.dbport = dbport;
		this.dbname = dbname;
		this.dbuser = dbuser;
		this.dbpass = dbpass;
		connect(dbtype, dbhost, dbname, dbuser, dbpass);
	}

	void create(String table) {
		try { cn().createStatement().execute("DROP TABLE "+table); } catch(Exception e1) { }
		try { Statement st = cn().createStatement();
		
		String sql = "CREATE TABLE ADMIN."+table+"\r\n" + 
				"(ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,\r\n" + 
				"USERNAME VARCHAR(20),\r\n" + 
				"PASSWORD VARCHAR(20),\r\n" + 
				"MOBILENO VARCHAR(20),\r\n" + 
				"EMAIL VARCHAR(50),\r\n" + 
				"FULLNAME VARCHAR(50),\r\n" + 
				"ANDROIDID VARCHAR(50),\r\n" + 
				"SIMID VARCHAR(50),\r\n" + 
				"IMEI VARCHAR(50),\r\n" + 
				"DATEREG DATE,\r\n" + 
				"PRIMARY KEY (ID))";
		
			st.execute(sql);
			System.out.println("Table  Created!");
		} catch (SQLException e) {System.out.println(e); }
		
	}

	Connection connect(String dbtype, String dbhost, String dbname, String dbuser, String dbpass) {
		Connection conn = null;
		try { conn = DriverManager.getConnection(dbhost + dbname + ";create=true", dbuser, dbpass);
			System.out.println("Database Connected!");
		} catch (SQLException e) { System.out.println(e); }
		
		return conn;
	}
	
	public Connection cn() { return connect(dbtype, dbhost, dbname, dbuser, dbpass); }
	
	public void createTransact() {
		try { cn().createStatement().execute("DROP TABLE ADMIN.TRANSACT"); } catch(Exception e1) { }
		try { Statement st = cn().createStatement();
		
		String sql = "CREATE TABLE ADMIN.TRANSACT"+"\r\n" + 
				"(ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,\r\n" + 
				"MOBILENO VARCHAR(20),\r\n" + 
				"TAGID VARCHAR(20),\r\n" + 
				"DETAILS VARCHAR(50),\r\n" + 
				"DATE TIMESTAMP,\r\n" + 
				"PRIMARY KEY (ID))";		
			st.execute(sql);
			System.out.println("Table  Created!");
		} catch (SQLException e) {System.out.println(e); }
	}

}
