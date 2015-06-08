package com.maid.mauctionsample;


import java.util.ArrayList;

import com.maid.mauctionsample.DataModel.TBBean;
import com.maid.mauctionsample.Databases.DbTBidAProduct;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TBilBidNow extends Activity implements OnItemSelectedListener {
//	private String[] state = { "Cupcake", "Donut", "Eclair",
//			"Froyo", "Gingerbread", "HoneyComb", "IceCream Sandwich",
//			"Jellybean", "kitkat"};
	private String[] state;
	ArrayList<String > state1;
	Spinner spinnerOsversions;
	DbTBidAProduct dbTbidAproduct;
	String str_UserId;
	SharedPreferences myPrefs;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.tbid_now);
	
	 myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
    String str_UserId = myPrefs.getString("userId", "");
    if(str_UserId.equalsIgnoreCase("")){
    	str_UserId=getIntent().getStringExtra("useId");
    }

	
	
	
	
	state1=getIntent().getStringArrayListExtra("prod_name");
	System.out.println("state1       "+state1.size());
	state = new String[state1.size()];
	state = state1.toArray(state);
	/*for(int i=0;i<state1.size();i++){
		state[i] = state1.get(i) ;
	}*/
	dbTbidAproduct=new DbTBidAProduct(getApplicationContext());
	dbTbidAproduct.open();
	
	
	Init();
}

private void Init() {
	// TODO Auto-generated method stub
	spinnerOsversions = (Spinner) findViewById(R.id.osversions);
	
	SetValue();
	
}

private void SetValue() {
	// TODO Auto-generated method stub
	ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, state);
	adapter_state
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spinnerOsversions.setAdapter(adapter_state);
	spinnerOsversions.setOnItemSelectedListener(this);
}

@Override
public void onItemSelected(AdapterView<?> parent, View view, int position,
		long id) {
	// TODO Auto-generated method stub
	spinnerOsversions.setSelection(position);
	String selState = (String) spinnerOsversions.getSelectedItem();
//	selVersion.setText("Selected Android OS:" + selState);
	
	ArrayList<TBBean> TBLIST = new ArrayList<TBBean>();
	TBLIST = dbTbidAproduct.getWheelItem(str_UserId,selState);
	System.out.println(TBLIST.size());
	
	String prod_name = TBLIST.get(0).getName();
	String prod_code = TBLIST.get(0).getName();
	String prod_des = TBLIST.get(0).getName();
	String prod_price = TBLIST.get(0).getName();
	
}

@Override
public void onNothingSelected(AdapterView<?> parent) {
	// TODO Auto-generated method stub
	
}

}
