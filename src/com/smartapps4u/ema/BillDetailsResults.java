package com.smartapps4u.ema;

import android.graphics.Bitmap;
import android.widget.CheckBox;

public class BillDetailsResults {
private String str_bill_details="";
Bitmap str_bill_path;
CheckBox cb2;
private boolean cb1;
	
	public void setBill_details(String str_bill_details) {
		this.str_bill_details = str_bill_details;
	}

	public String getBill_details() {
		return str_bill_details;
	}

	public void setBill_path(Bitmap str_bill_path) {
		this.str_bill_path = str_bill_path;
	}

	public Bitmap getBill_path() {
		return str_bill_path;
	}
	public boolean getCb1()  
    {  
        return cb1;  
    }  
    public void setCb1(boolean state)  
    {  
    	   this.cb1 = state;
    	  
    }  

}
