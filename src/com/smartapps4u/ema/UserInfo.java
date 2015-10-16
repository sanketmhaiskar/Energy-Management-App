package com.smartapps4u.ema;

import java.io.File;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class UserInfo extends Activity{

	EditText edttxt_elec_consumption, edttxt_cost, edttxt_year;
	Spinner spinner_month;
	Bitmap theImage;
	private static int TAKE_PICTURE = 1;
	private Uri outputFileUri;
	SQLiteDatabase db = null;
	static DatabaseHelper dbhelper;
	String str_month="", str_year="";
	Button btn_take_pic, btn_submit_info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		
		dbhelper = new DatabaseHelper(this);
		db = dbhelper.getWritableDatabase();
		db.setLockingEnabled(true);
		
		
		edttxt_elec_consumption = (EditText) findViewById(R.id.edttxt_consumption);
		edttxt_cost = (EditText) findViewById(R.id.edttxt_cost);
		edttxt_year = (EditText) findViewById(R.id.edttxt_year);
		
		btn_take_pic = (Button) findViewById(R.id.btn_takepic);
		btn_submit_info = (Button) findViewById(R.id.btn_submit);
		
		btn_take_pic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str_month = String.valueOf(spinner_month.getSelectedItem());
				str_year = edttxt_year.getText().toString();
				String imagename=str_month+"_"+str_year+".jpg";
				
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    String newFolder = "/EMA";
			    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
			    File myNewFolder = new File(extStorageDirectory + newFolder);
			    myNewFolder.mkdir();
			    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/EMA/", imagename);
			  
			   outputFileUri = Uri.fromFile(file);
			   intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			   startActivityForResult(intent, TAKE_PICTURE);
				
			}
		});
		
		btn_submit_info.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str_month = String.valueOf(spinner_month.getSelectedItem());
				str_year = edttxt_year.getText().toString();
				
				
				ContentValues insert_values = new ContentValues();
				
				insert_values.put(DatabaseHelper.COL_MONTH,String.valueOf(spinner_month.getSelectedItem()));
				insert_values.put(DatabaseHelper.COL_YEAR, edttxt_year.getText().toString());
				insert_values.put(DatabaseHelper.COL_ELEC_CONSUME, edttxt_elec_consumption.getText().toString());
				insert_values.put(DatabaseHelper.COL_COST, edttxt_cost.getText().toString());
				insert_values.put(DatabaseHelper.COL_BILL_PIC_PATH, str_month+"_"+str_year);
				insert_values.put(DatabaseHelper.COL_INT_MONTH, Integer.parseInt(str_month));
				insert_values.put(DatabaseHelper.COL_INT_YEAR, Integer.parseInt(str_year));
				
				db.insertOrThrow(DatabaseHelper.TABLE_NAME, null, insert_values);
				Log.i("EMA", "-----------------Data inserted");
				
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
			}
		});		
				
		spinner_month = (Spinner) findViewById(R.id.spinner_month);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.month_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_month.setAdapter(adapter);
						
	}	

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if (requestCode==0)
    	{
    		theImage= (Bitmap) data.getExtras().get("data");    		
       	}
    }
	
}
