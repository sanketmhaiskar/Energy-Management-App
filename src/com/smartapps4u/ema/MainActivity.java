package com.smartapps4u.ema;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity{
		
	SQLiteDatabase main_db;
	static DatabaseHelper db_helper;
	Cursor db_curs = null;
	ImageButton add_btn, display_btn, tips_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		add_btn = (ImageButton) findViewById(R.id.imgbtn_add);
		display_btn = (ImageButton) findViewById(R.id.imgbtn_display);	
		tips_btn = (ImageButton) findViewById(R.id.imgbtn_tips);	
		CheckList();
		
		add_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), UserInfo.class));
			}
		});
		
		display_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), BillDetails.class));
			}
		});
		
		tips_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), EnergyTipsPage.class));
			}
		});
		
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		CheckList();
	}



	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		CheckList();
	}



	public void CheckList(){
		db_helper = new DatabaseHelper(this);
		main_db = db_helper.getReadableDatabase();
		main_db.setLockingEnabled(true);	
		
		
		try {
			
			db_curs = null;
			db_curs = main_db.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_NAME, null);
			if (db_curs.moveToFirst()==false) {
				display_btn.setVisibility(View.GONE);
				db_curs.close();
				db_helper.close();
				main_db.close();
				
			}else{
				display_btn.setVisibility(View.VISIBLE);
				db_curs.close();
				db_helper.close();
				main_db.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("trycatch err", e.toString());
		}
	}
}
