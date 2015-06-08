package com.maid.mauctionsample;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TableRow;

import com.maid.mauctionsample.Databases.DbUserRegistration;


public class MRegistration extends Activity implements OnClickListener, OnCheckedChangeListener {

	EditText edt_fName,edt_lName,edt_email,edt_sCode,edt_Pwd,edt_mNumber;
	Button btn_Reg_Submit;
	CheckBox chkAdmin,chkALogin;
	 DbUserRegistration db;
	 TableRow tabScode;
	 String str_Chk_Alogin=null,str_isAdmin=null;
	 SharedPreferences myPrefs;
		SharedPreferences.Editor prefsEditor;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_table);
       db=new DbUserRegistration(getApplicationContext());
       
        Init();
        Onclick();
    }


    private void Onclick() {
		// TODO Auto-generated method stub
		btn_Reg_Submit.setOnClickListener(this);
		chkAdmin.setOnCheckedChangeListener(this);
		chkALogin.setOnCheckedChangeListener(this);
	}


	private void Init() {
		// TODO Auto-generated method stub
		edt_fName=(EditText) findViewById(R.id.edt_reg_FName);
		edt_lName=(EditText) findViewById(R.id.edt_reg_LName);
		edt_email=(EditText) findViewById(R.id.edt_reg_Email);
		edt_Pwd=(EditText) findViewById(R.id.edt_reg_Pwd);
		edt_sCode=(EditText) findViewById(R.id.edt_reg_Scode);
		chkALogin=(CheckBox) findViewById(R.id.chkALogin);
		edt_mNumber=(EditText) findViewById(R.id.edt_reg_MNumber);
		chkAdmin=(CheckBox) findViewById(R.id.checkadmin);
		btn_Reg_Submit=(Button) findViewById(R.id.btn_reg_submit);
		tabScode=(TableRow) findViewById(R.id.tabScode);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btn_reg_submit:
			String str_Fname=edt_fName.getText().toString();
			String str_Lname=edt_lName.getText().toString();
			String str_emial=edt_email.getText().toString();
			String str_mobile_number=edt_mNumber.getText().toString();
			String str_Pwd=edt_Pwd.getText().toString();
			String str_Scode=edt_sCode.getText().toString();
			Date now = new Date(); // java.util.Date, NOT java.sql.Date or java.sql.Timestamp!
			
			
			String format1 = new SimpleDateFormat("yyyyMMddHHmm").format(now);
			
//	        db.open();
	        db.insertDBReg(str_Fname, str_Lname, str_emial, str_mobile_number, 
	        		str_Pwd, str_Chk_Alogin, format1, str_Scode, str_isAdmin);
	        
	        db.open();
	        String userId=db.getUserId(str_emial, str_Pwd);
	        myPrefs= this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
	        prefsEditor = myPrefs.edit();
	        prefsEditor.putString("userId", userId);
	        prefsEditor.putString("format1", format1);
	        prefsEditor.commit();
	        if(str_isAdmin.equalsIgnoreCase("yes")){
	        	Intent intentAdmin=new Intent(MRegistration.this,TBidAProduct.class);
				intentAdmin.putExtra("usrid", userId);
				startActivity(intentAdmin);
	        }
	        else{
	        	Intent intentuser=new Intent(MRegistration.this,TBIDUProduct.class);
				intentuser.putExtra("usrid", userId);
				startActivity(intentuser);
	        }
	       
			break;

		default:
			break;
		}
	}


	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.checkadmin:
			if(buttonView.isChecked()){
				str_isAdmin="yes";
			}
			else{
				str_isAdmin="no";
			}
			break;
		case R.id.chkALogin:
			if(buttonView.isChecked()){
				str_Chk_Alogin="yes";
				tabScode.setVisibility(View.VISIBLE);
				
			}
			else{
				str_Chk_Alogin="no";
				tabScode.setVisibility(View.GONE);
			}
			break;
		default:
			break;
		}
		
	}
}
