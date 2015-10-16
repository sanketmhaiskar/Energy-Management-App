package com.smartapps4u.ema;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutPage extends Activity{

	TextView about_txt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_page);
		
		about_txt = (TextView) findViewById(R.id.txtvw_about);
		
		about_txt.setText('\t'+"The Scavenger Developers"+'\n'+"We hope you liked the app");
	}
}
