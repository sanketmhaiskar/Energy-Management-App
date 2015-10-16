package com.smartapps4u.ema;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class BillDetails extends Activity{
	
	ListView lstvw;
	SQLiteDatabase ret_db = null;
	static DatabaseHelper db_helper;
	Cursor curs = null, graph_curs=null, setting_curs=null;
	String billpath="";
	Bitmap bm;
	BillDetailsAdapter listAdapter;
	int listitemcount,selecteditemcount=0;
	int[] elec_consume=new int[12];
	String[] elec_month=new String[12];
	String[] elec_year=new String[12];
	Integer int_no;
	String[] months=new String[]{"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.billdetails_list);
		
		lstvw = (ListView) findViewById(R.id.listView_details);
		
				
		db_helper = new DatabaseHelper(this);
		ret_db = db_helper.getWritableDatabase();
		ret_db.setLockingEnabled(true);
				  
		ret_list();
		
		//lstvw.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lstvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				Object bill_obj = listAdapter.getItem(pos);
				BillDetailsResults result_obj = (BillDetailsResults) bill_obj;
				Log.d("ItemCLicked",result_obj.getBill_details());				
			}
		});

		
	}
	
	public void ret_list(){
		ArrayList<BillDetailsResults> results = new ArrayList<BillDetailsResults>();
		curs=null;
		curs = ret_db.rawQuery("select * from "+DatabaseHelper.TABLE_NAME+" order by intyear desc,intmonth desc", null);
		
		while (curs.moveToNext()) {
			billpath = curs.getString(curs.getColumnIndex(DatabaseHelper.COL_BILL_PIC_PATH));
			bm=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/EMA/"+billpath+".jpg");
			BillDetailsResults sr1 = new BillDetailsResults();
			int temp=Integer.parseInt(curs.getString(curs.getColumnIndex(DatabaseHelper.COL_MONTH)));
			sr1.setBill_details(months[temp-1]+" "+curs.getString(curs.getColumnIndex(DatabaseHelper.COL_YEAR)));
			sr1.setBill_path(bm);
			results.add(sr1);
			
		}
		listAdapter = new BillDetailsAdapter(this, results);  
		//lstvw.setAdapter(new BillDetailsAdapter(this,results));
		lstvw.setAdapter(listAdapter);
		lstvw.setTextFilterEnabled(true);
		lstvw.setClickable(true);
		lstvw.setItemsCanFocus(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menu_inflater = getMenuInflater();
		menu_inflater.inflate(R.layout.list_menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		
		case R.id.delete_record:
			
			curs=null;
			curs = ret_db.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_NAME, null);
			curs.moveToFirst();
			for(int i=0;i<listAdapter.getCount();i++)
			{
				
				if(listAdapter.checkedpos[i]==1)
				{					
					String month_str = curs.getString(curs.getColumnIndex(DatabaseHelper.COL_MONTH));
					String year_str = curs.getString(curs.getColumnIndex(DatabaseHelper.COL_YEAR));
										
					try {
						ret_db.execSQL("DELETE FROM "+DatabaseHelper.TABLE_NAME+" WHERE "+DatabaseHelper.COL_MONTH+"="+month_str+" AND "+DatabaseHelper.COL_YEAR+"="+year_str);
						
					} catch (Exception e) {
						// TODO: handle exception
						Log.i("Delete query", e.toString());
					}					
					
				}
				curs.moveToNext();					
			}
			
			ret_list();
			break;
		
		case R.id.show_graph:
			
			for (int i = 0; i < listAdapter.checkedpos.length; i++) {
				Log.i("checkedpos["+i+"]", String.valueOf(listAdapter.checkedpos[i]));
			}
			
			graph_curs=null;
			graph_curs = ret_db.rawQuery("select * from "+DatabaseHelper.TABLE_NAME+" order by intyear desc,intmonth desc", null);
			graph_curs.moveToFirst();
			for(int i=0;i<listAdapter.getCount();i++)
			{
				
				if(listAdapter.checkedpos[i]==1)
				{
					elec_consume[selecteditemcount]= Integer.parseInt(graph_curs.getString(graph_curs.getColumnIndex(DatabaseHelper.COL_ELEC_CONSUME)));
					elec_month[selecteditemcount]=graph_curs.getString(graph_curs.getColumnIndex(DatabaseHelper.COL_MONTH));
				    elec_year[selecteditemcount]=graph_curs.getString(graph_curs.getColumnIndex(DatabaseHelper.COL_YEAR));
				    Log.d("Array",String.valueOf(listAdapter.checkedpos[i]));
					
					//gdata[selecteditemcount]=new GraphViewData(elec_month, elec_consume);
					selecteditemcount++;
				}
				graph_curs.moveToNext();
					
			}
			
			if (selecteditemcount<2) {
				Toast.makeText(getApplicationContext(), "Select minimum 2 records. ", Toast.LENGTH_LONG).show();
				selecteditemcount=0;
			}else {
				try {
					setting_curs=null;
					setting_curs = ret_db.rawQuery("SELECT * FROM "+DatabaseHelper.CFLMINUS_TABLE_NAME, null);
					setting_curs.moveToFirst();
					int_no = setting_curs.getInt(setting_curs.getColumnIndex(DatabaseHelper.COL_NO_INDIVIDUAL));
				} catch (Exception e) {
					// TODO: handle exception
					Log.i("Billdet", e.toString());
					Log.d("Number of people", String.valueOf(int_no));
				}
				
				try {
					Bundle bd=new Bundle();
					bd.putIntArray("elec_consume", elec_consume);
					bd.putStringArray("elec_month", elec_month);
					bd.putStringArray("elec_year", elec_year);
					bd.putInt("no_people", int_no);
					bd.putInt("selecteditemcount", selecteditemcount);
					startActivityForResult(new Intent(this,GraphViewDemo.class).putExtras(bd), 0);
				} catch (Exception e) {
					Log.i("Error", e.toString());
				}
				
				
				//startActivity(new Intent(this,GraphViewDemo.class).putExtras(bd));
			}
			
			
			   break;
			
		case R.id.settings:
		
		try {
			setting_curs=null;
			setting_curs = ret_db.rawQuery("SELECT * FROM "+DatabaseHelper.CFLMINUS_TABLE_NAME, null);
			setting_curs.moveToFirst();
			int_no = setting_curs.getInt(setting_curs.getColumnIndex(DatabaseHelper.COL_NO_INDIVIDUAL));
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("Billdet", e.toString());
		}	
			
		final Dialog settings_dialog = new Dialog(BillDetails.this);
		settings_dialog.setContentView(R.layout.settings);
		settings_dialog.setTitle("Edit Quantity Option");
		settings_dialog.setCancelable(true);
		
		final Spinner spinner_no_individuals = (Spinner) settings_dialog.findViewById(R.id.spinner_individuals);
		ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.people_array, android.R.layout.simple_spinner_item);
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_no_individuals.setAdapter(spinner_adapter);
		spinner_no_individuals.setSelection(int_no-1);
		
		Button btn_save = (Button) settings_dialog.findViewById(R.id.save_btn);
		Button btn_cancel = (Button) settings_dialog.findViewById(R.id.cancel_btn);
		
		btn_save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					String str_no = String.valueOf(spinner_no_individuals.getSelectedItem());
										
					try {
						ret_db.execSQL("UPDATE " +DatabaseHelper.CFLMINUS_TABLE_NAME+ " SET "+DatabaseHelper.COL_NO_INDIVIDUAL+"="+str_no);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					settings_dialog.cancel();
								
			}
		});
		
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				settings_dialog.cancel();
				
			}
		});
		settings_dialog.show();
			break;
			
		case R.id.about_page:
			startActivity(new Intent(this, AboutPage.class));
			break;
			
		case R.id.tips_page:
			startActivity(new Intent(this, EnergyTipsPage.class));
			break;
			
			}		
		return true;
		}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode==0)
		{
			selecteditemcount=0;
			Log.d("Sucessss",String.valueOf(selecteditemcount));
		}
	}
	
	}


