package com.smartapps4u.ema;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.smartapps4u.ema.GraphView.GraphViewData;
import com.smartapps4u.ema.GraphView.GraphViewSeries;
import com.smartapps4u.ema.GraphView.GraphViewStyle;
import com.smartapps4u.ema.GraphView.LegendAlign;

/**
 * GraphViewDemo creates some dummy data to demonstrate the GraphView component.
 *
 * IMPORTANT: For examples take a look at GraphView-Demos (https://github.com/jjoe64/GraphView-Demos)
 *
 * Copyright (C) 2011 Jonas Gehring
 * Licensed under the GNU Lesser General Public License (LGPL)
 * http://www.gnu.org/licenses/lgpl.html
 */
public class GraphViewDemo extends Activity {
	/**
	 * @param savedInstanceState
	 */
	int selecteditemcount=0,no_people=1;
	int[] elec_consume=new int[12];
	String[] elec_month=new String[12];
	String[] elec_year=new String[12];
	double cflminus=94.65;
	String[] months=new String[]{"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
	String[] cflvalues =new String[]{"67.35","94.65","139.95","190.55","257.25"};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle detail = getIntent().getExtras();
		selecteditemcount=detail.getInt("selecteditemcount");
		elec_consume=detail.getIntArray("elec_consume");
		elec_year=detail.getStringArray("elec_year");
		elec_month=detail.getStringArray("elec_month");
		no_people=detail.getInt("no_people");
		LineGraphView graphView = new LineGraphView(
				this
				, "Power Consumption"
		);
		GraphViewData[] data =  new GraphViewData[] {
				//new GraphViewData(4, 50),
				//new GraphViewData(5, 100)
				new GraphViewData(2012.4, 1.5)
				, new GraphViewData(2012.5, 300)
				//, new GraphViewData(3, 2.5)
				//, new GraphViewData(4, 1.0)
				//, new GraphViewData(5, 3.0)
		};
		cflminus=Double.parseDouble(cflvalues[no_people-1]);
		String[] horlabels=new String[selecteditemcount];
		try {
			GraphViewData[] data2 = new GraphViewData[selecteditemcount];
			GraphViewData[] cfldata = new GraphViewData[selecteditemcount];
			for(int i=0;i<selecteditemcount;i++)
			{
				Log.d("Graphvalues", String.valueOf(elec_consume[i]));
				Log.d("Graphvalues", elec_month[i]);
				Log.d("Graphvalues", elec_year[i]);
													
				data2[i]=new GraphViewData(i,elec_consume[i]);
				cfldata[i]=new GraphViewData(i,cflminus);
				horlabels[i]=months[Integer.parseInt(elec_month[selecteditemcount-i-1])-1]+"/"+elec_year[selecteditemcount-i-1];
				
		
			}
			
			GraphViewStyle gyellow =new GraphViewStyle(Color.YELLOW, 4);
			GraphViewStyle gblue =new GraphViewStyle(Color.BLUE, 4);
			GraphViewSeries graph1=new GraphViewSeries("Power Consumption", gyellow, data2);
			GraphViewSeries ideal=new GraphViewSeries("You can save if you use CFLs", gblue, cfldata);
			graphView.setLegendWidth(200);
			graphView.addSeries(graph1);
			graphView.addSeries(ideal);
		} catch (Exception e) {
			Log.d("Error", e.toString());
		}
		
		
		graphView.setScaleX("On X axis : 1 unit=1 Month");
		graphView.setScaleY("On Y axis : 1 unit=");
		graphView.setShowLegend(true);
		graphView.setHorizontalLabels(horlabels);
		//graphView.setViewPort(0, selecteditemcount);
		graphView.setScrollable(true);
		setContentView(graphView);
		
	}
	
	
	
	
}
