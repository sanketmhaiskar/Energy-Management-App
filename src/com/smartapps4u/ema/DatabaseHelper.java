package com.smartapps4u.ema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	Context context;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	 public static final String DB_NAME="ema_database";
	 static final int DB_VERSION=1;
	 public static final String TABLE_NAME="bill_details";
	 public static final String COL_MONTH="month";
	 public static final String COL_YEAR="year";
	 public static final String COL_ELEC_CONSUME="consumed";
	 public static final String COL_COST="cost";
	 public static final String COL_BILL_PIC_PATH="path";
	 
	 public static final String COL_INT_MONTH="intmonth";
	 public static final String COL_INT_YEAR="intyear";
	 
	 public static final String CFLMINUS_TABLE_NAME="cflminus_details";
	 public static final String COL_NO_INDIVIDUAL="no_of_people";
	 
	 
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " +TABLE_NAME+ " ("+COL_MONTH+ " text, "+COL_YEAR+ " text, "+COL_ELEC_CONSUME+ " text, "+COL_COST+ " text, "+COL_BILL_PIC_PATH+ " text, "+COL_INT_MONTH+ " INTEGER, "+COL_INT_YEAR+ " INTEGER)");
		db.execSQL("CREATE TABLE " +CFLMINUS_TABLE_NAME+ " ("+COL_NO_INDIVIDUAL+ " INTEGER)");
		db.execSQL("INSERT INTO " +CFLMINUS_TABLE_NAME+ " VALUES(1)");
				

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP "+TABLE_NAME+" IF EXISTS");
		
	}

}
