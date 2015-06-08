package com.maid.mauctionsample;

import java.util.ArrayList;

import com.maid.mauctionsample.Databases.DbTBidAProduct;
import com.maid.mauctionsample.Databases.DbUserRegistration;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TBidAProduct extends Activity implements OnClickListener {

	EditText edt_TbiAP_pId,edt_TbiAP_code, edt_TbiAP_name, edt_TbiAP_desc, edt_TbiAP_price;
	Button btn_add_prod, btn_bidNow;
	DbTBidAProduct dbTBIAproduct;
	String str_UserId;
	TextView txtLastLogIn;
	ArrayList<String > state;
	SharedPreferences myPrefs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.t_bid_aproduct);
		dbTBIAproduct=new DbTBidAProduct(getApplicationContext());
		 myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
	     str_UserId = myPrefs.getString("userId", "");
	    if(str_UserId.equalsIgnoreCase("")){
	    	str_UserId=getIntent().getStringExtra("usrid");
	    }
		
		Log.d(this.getLocalClassName(), "usrid :"+str_UserId);
		Init();
		
	}

	private void Onclick() {
		// TODO Auto-generated method stub
		btn_add_prod.setOnClickListener(this);
		btn_bidNow.setOnClickListener(this);
	}

	private void Init() {
		// TODO Auto-generated method stub
		txtLastLogIn=(TextView) findViewById(R.id.txt_lstLogin);
		edt_TbiAP_pId = (EditText) findViewById(R.id.edt_tbid_aproduct_id);
		edt_TbiAP_code = (EditText) findViewById(R.id.edt_tbid_aproduct_code);
		edt_TbiAP_name = (EditText) findViewById(R.id.edt_tbid_aproduct_name);
		edt_TbiAP_desc = (EditText) findViewById(R.id.edt_tbid_aproduct_des);
		edt_TbiAP_price = (EditText) findViewById(R.id.edt_tbid_aproduct_price);

		btn_add_prod = (Button) findViewById(R.id.btn_tbidAP_Add_Prod);
		btn_bidNow = (Button) findViewById(R.id.btn_tbidAP_Bidnow);
		SetValueIntials();
		
		Onclick();
	}

	private void SetValueIntials() {
		// TODO Auto-generated method stub
		DbUserRegistration dbUserRegistration=new DbUserRegistration(getApplicationContext());
		dbUserRegistration.open();
//		String strLogInAttempt = null;
//		try {
//			 strLogInAttempt=dbUserRegistration.getLastLogIn(str_UserId);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		if(strLogInAttempt.equalsIgnoreCase("")||strLogInAttempt!=null){
//			strLogInAttempt = myPrefs.getString("format1", "");
//		}
		String strLogInAttempt = myPrefs.getString("format1", "");
		txtLastLogIn.setText("Your last Login :"+strLogInAttempt);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_tbidAP_Add_Prod:
			
			String str_pId=edt_TbiAP_pId.getText().toString();
			String str_Code=edt_TbiAP_code.getText().toString();
			String str_Name=edt_TbiAP_name.getText().toString();
			String str_desc=edt_TbiAP_desc.getText().toString();
			String str_price=edt_TbiAP_price.getText().toString();
			dbTBIAproduct.open();
			dbTBIAproduct.insertTBid_aProdcut(str_UserId,str_pId,str_Code,str_Name,str_desc,str_price);
			 state=dbTBIAproduct.getLabeId(str_UserId);
			
			Intent intentBidnow=new Intent(TBidAProduct.this,TBilBidNow.class);
			intentBidnow.putExtra("useId", str_UserId);
			intentBidnow.putStringArrayListExtra("prod_name", state);
			startActivity(intentBidnow);
			break;
		case R.id.btn_tbidAP_Bidnow:
//			dbTBIAproduct.open();
//			state=dbTBIAproduct.getLabeId(str_UserId);
//			Intent intentTbiNow=new Intent(TBidAProduct.this,TBilBidNow.class);
//			intentTbiNow.putExtra("useId", str_UserId);
//			
//			startActivity(intentTbiNow);
			break;

		default:
			break;
		}
	}

}
