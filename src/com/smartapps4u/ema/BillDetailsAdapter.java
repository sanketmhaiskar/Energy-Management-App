package com.smartapps4u.ema;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;


public class BillDetailsAdapter extends BaseAdapter{
public int[] checkedpos=new int[12];

private static ArrayList<BillDetailsResults> ArrayList;
	
	private LayoutInflater mInflater;
	
	public BillDetailsAdapter(Context context, ArrayList<BillDetailsResults> results) {
		ArrayList = results;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.billdetails_format, null);
			holder = new ViewHolder();
			
			holder.name_check = (CheckBox) convertView.findViewById(R.id.format_checkBox);
			holder.img_bill = (ImageView) convertView.findViewById(R.id.format_imageView);
									
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.name_check.setText(ArrayList.get(position).getBill_details());
		holder.img_bill.setImageBitmap(ArrayList.get(position).getBill_path());
	    holder.name_check.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CheckBox cb=(CheckBox) v;
				if (cb.isChecked()) {
					ArrayList.get(position).setCb1(false);
					Log.d("UnChecked-->Checked", ArrayList.get(position).getBill_details().toString());
					checkedpos[position]=1;
			     }
				else
				{
					ArrayList.get(position).setCb1(true);
					Log.d("Checked-->Unchecked", ArrayList.get(position).getBill_details().toString());
					checkedpos[position]=0;
					
				}
				for (int i=0;i<ArrayList.size();i++)
				{
					Log.d("Checkedpos",String.valueOf(checkedpos[i]));
				}

				
			}
		});
				
		return convertView;
	}
	
	static class ViewHolder {
		CheckBox name_check;
		ImageView img_bill;
	}

}
