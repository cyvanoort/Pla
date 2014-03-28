package com.example.testplab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//class that handles the connection to the locale database
public class DBConection extends SQLiteOpenHelper {
	
	//Names of the database and the columns
	public static final String TABLE_BOARDS = "Boards";
	public static final String TABLE_INTRESSES = "Intresses";
	public static final String TABLE_OWN_INTRESSES = "OwnIntresses";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_LNG = "lng";
	public static final String COLUMN_LAT = "lat";
	public static final String COLUMN_CATEGORIE = "categorie";

	private static final String DATABASE_NAME = "plab.db";
	private static final int DATABASE_VERSION = 3;
	
	// Database creation sql statements
	static final String CREATE_BOARDS = "create table "
	      + TABLE_BOARDS + "(" 
		  + COLUMN_ID
	      + " varchar not null, " 
	      + COLUMN_LNG
	      + " varchar not null, "
	      + COLUMN_LAT
	      + " varchar not null "
	      + ");";
	
	static final String CREATE_INTRESSES = "create table "
		  + TABLE_INTRESSES + "(" 
		  + COLUMN_ID
	      + " varchar not null, " 
	      + COLUMN_CATEGORIE
	      + " varchar not null "
	      + ");";
	
	static final String CREATE_OWN_INTRESSES = "create table "
			  + TABLE_OWN_INTRESSES + "(" 
			  + COLUMN_ID
		      + " varchar not null " 
		      + ");";

	//Database drop statement
	private static final String SQL_DELETE_ENTRIES_BOARDS =
    "DROP TABLE IF EXISTS " + TABLE_BOARDS;
	private static final String SQL_DELETE_ENTRIES_INTRESSES =
	"DROP TABLE IF EXISTS " + TABLE_INTRESSES;
	private static final String SQL_DELETE_ENTRIES_OWN_INTRESSES =
	"DROP TABLE IF EXISTS " + TABLE_OWN_INTRESSES;

	public DBConection(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	//create the tables on first use
	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(CREATE_BOARDS);
	    database.execSQL(CREATE_INTRESSES);
	    database.execSQL(CREATE_OWN_INTRESSES);
	  }

	  //remove the old tables on an update
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_BOARDS);
        db.execSQL(SQL_DELETE_ENTRIES_INTRESSES);
        db.execSQL(SQL_DELETE_ENTRIES_OWN_INTRESSES);
        onCreate(db);		
	}
	
}
