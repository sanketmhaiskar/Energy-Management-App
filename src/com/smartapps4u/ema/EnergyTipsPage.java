package com.smartapps4u.ema;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EnergyTipsPage extends Activity{

	TextView tips_txt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tips_page);
		
		tips_txt = (TextView) findViewById(R.id.txtvw_tips);
		
		tips_txt.setText("EnergyTips");
		
	}
}
