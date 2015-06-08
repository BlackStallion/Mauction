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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.maid.mauctionsample.Databases.DbUserRegistration;


public class MLogin extends Activity implements OnClickListener, OnCheckedChangeListener {

	EditText edt_log_emailId,edt_log_pwd;
	 DbUserRegistration db;
	Button btn_LogIn,btn_signUP;
	SharedPreferences myPrefs;
	SharedPreferences.Editor prefsEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mlogin);
        
        Init();
        Onclick();
      
    }


    private void Onclick() {
		// TODO Auto-generated method stub
    	btn_LogIn.setOnClickListener(this);
    	btn_signUP.setOnClickListener(this);
	}


	private void Init() {
		// TODO Auto-generated method stub
		btn_LogIn=(Button) findViewById(R.id.btn_logIn);
		btn_signUP=(Button) findViewById(R.id.btn_SignUp);
		edt_log_emailId=(EditText) findViewById(R.id.edt_login_emailId);
		edt_log_pwd=(EditText) findViewById(R.id.edt_login_pwd);
		
		onSetDataTest();
	}


	private void onSetDataTest() {
		// TODO Auto-generated method stub
		edt_log_emailId.setText("m@m.com");
		edt_log_pwd.setText("m");
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btn_logIn:
			
			
			
			
			
			String str_emailId=edt_log_emailId.getText().toString();
			String str_pwd=edt_log_pwd.getText().toString();
			
			
			db=new DbUserRegistration(getApplicationContext());
	        db.open();
//	        String userStatus=db.getUserStatus(str_emailId, str_pwd);
	        int ilogin=db.getuserlogin(str_emailId, str_pwd);
	        String userId=db.getUserId(str_emailId, str_pwd);
	        Date now = new Date(); // java.util.Date, NOT java.sql.Date or java.sql.Timestamp!
			String format1 = new SimpleDateFormat("yyyyMMddHHmm").format(now);
			db.GetUpdateLogInAttempt(userId, format1);
			
			myPrefs= this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
	        prefsEditor = myPrefs.edit();
	        prefsEditor.putString("userId", userId);
	        prefsEditor.putString("format1", format1);
	        prefsEditor.commit();
			
	    	if(ilogin<1)
			{
				//login unsuccessful]
//	    		Intent intentSignUp= new Intent(MLogin.this,MRegistration.class);
//	    		startActivity(intentSignUp);
	    		Toast.makeText(getApplicationContext(), "Please try again.... ", 300).show();
			}
			else if(ilogin>0)
			{
				//login successful
				Toast.makeText(getApplicationContext(), "Yapee Party ", 300).show();
				
				String userStatus=db.getUserStatus(str_emailId, str_pwd);
				
				db.close();
				
			
			if(userStatus.equalsIgnoreCase("yes")){
				Intent intentAdmin=new Intent(MLogin.this,TBidAProduct.class);
				intentAdmin.putExtra("usrid", userId);
				startActivity(intentAdmin);
				
			}
			else{
				Intent intentuser=new Intent(MLogin.this,TBIDUProduct.class);
				intentuser.putExtra("usrid", userId);
				startActivity(intentuser);
			}
			}
	        
	    	db.close();
			break;
		case R.id.btn_SignUp:
			
			Intent intentSignUp= new Intent(MLogin.this,MRegistration.class);
    		startActivity(intentSignUp);
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
	
			break;
		default:
			break;
		}
		
	}
}
