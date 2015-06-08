package com.maid.mauctionsample.Databases;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbUserRegistration 
{
	 public static final String KEY_USERNAME = "userId";
	 public static final String KEY_FIRST_NAME = "first_name";
	 public static final String KEY_LAST_NAME = "last_name";
	 public static final String KEY_EMAIL = "email";
	 public static final String KEY_MOBILE_NUMBER = "mobile_number";
	 public static final String KEY_PASSWORD = "password";
	 public static final String KEY_AUTOLOGIN= "auto_login";
	 public static final String KEY_LAST_LOGIN_ATTEMPT = "last_login_attempt";
	 public static final String KEY_SECURITY_CODE = "security_code";
	 public static final String KEY_ADMIN="admin";

	   // private static final String TAG = "DBAdapter";
	    
	    private static final String DATABASE_NAME = "UserInfo";
	    private static final String DATABASE_TABLE = "UserInfoDB";
	    private static final int DATABASE_VERSION = 4;

	    private static final String DATABASE_CREATE =

//	    		 "CREATE TABLE " + DATABASE_TABLE + "("
//	    		 + "username" + " INTEGER PRIMARY KEY," + "first_name" + " TEXT,"
//					+ "last_name" + " TEXT" + " )";
//	    
	    
	    "CREATE TABLE " + DATABASE_TABLE + "("
		 + "userId" + " INTEGER PRIMARY KEY," + "first_name" + " TEXT,"
		 + "last_name" + " TEXT," + "email" + " TEXT,"
		 + "mobile_number" + " TEXT," + "password" + " TEXT,"
		 + "auto_login" + " TEXT," + "last_login_attempt" + " TEXT,"
		 + "security_code" + " TEXT," 
			+ "admin" + " TEXT" + " )";
	    
	    
//	    "CREATE TABLE " + DATABASE_TABLE + "("
//        + "username" + " INTEGER PRIMARY KEY  autoincrement," + "first_name" + " TEXT,"
//        + "last_name" + " TEXT" + "" +
//         "email" + " TEXT" + "" +
//         "mobile_number" + " TEXT" + "" +
//         "password" + " TEXT" + "" +
//         "auto_login" + " TEXT" + "" +
//         "last_login_attempt" + " TEXT" + "" +
//         "security_code" + " TEXT" + "" +
//         "admin" + " TEXT" + ")";
	    
	    
	        
	    private final Context context; 
	    
	    public static DatabaseHelper DBHelper;
	    private SQLiteDatabase db;

	    public DbUserRegistration(Context ctx) 
	    {
	        this.context = ctx;
	        DBHelper = new DatabaseHelper(context);
	    }
	        
	    private static class DatabaseHelper extends SQLiteOpenHelper 
	    {
	        DatabaseHelper(Context context) 
	        {
	            super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        }

	        @Override
	        public void onCreate(SQLiteDatabase db) 
	        {
	            db.execSQL(DATABASE_CREATE);
	        

	        }

	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	        {
	            //Log.w(TAG, "Upgrading database from version " + oldVersion+ " to "+ newVersion + ", which will destroy all old data");
	            db.execSQL("DROP TABLE IF EXISTS tableone");
	            onCreate(db);
	        }
	    }    
	    
	   //---opens the database---
	    public DbUserRegistration open() throws SQLException 
	    {
	        db = DBHelper.getWritableDatabase();
	         return this;
	    }

	    //---closes the database---    
	    public void close() 
	    {
	        DBHelper.close();
	    }
	    
	   //---insert a title into the database---
	    public void insertDBReg(String first_name,String last_name,String email,String mobile_number
	    		,String password,String auto_login,String last_login_attempt,String security_code,String admin
	    		) 
	    {
	    	 SQLiteDatabase db = DBHelper.getWritableDatabase();
	        ContentValues initialValues = new ContentValues();
	        
	        initialValues.put(KEY_FIRST_NAME, first_name);
	        initialValues.put(KEY_LAST_NAME, last_name);
	        initialValues.put(KEY_EMAIL, email);
	        initialValues.put(KEY_MOBILE_NUMBER, mobile_number);
	        initialValues.put(KEY_PASSWORD, password);
	        initialValues.put(KEY_AUTOLOGIN, auto_login);
	        initialValues.put(KEY_LAST_LOGIN_ATTEMPT, last_login_attempt);
	        initialValues.put(KEY_SECURITY_CODE, security_code);
	        initialValues.put(KEY_ADMIN, admin);       
	        
	         db.insert(DATABASE_TABLE, null, initialValues);
	         db.close();
	        
	    }
	 
	    //---retrieves all the titles---
	    public Cursor getDBReg() 
	    {
	        return db.query(DATABASE_TABLE, new String[] {
	        		KEY_USERNAME, 
	        		KEY_FIRST_NAME,
	        		KEY_LAST_NAME,
	        		KEY_EMAIL,
	        		KEY_MOBILE_NUMBER,
	        		KEY_PASSWORD,
	        		KEY_AUTOLOGIN,
	        		KEY_LAST_LOGIN_ATTEMPT,
	        		KEY_SECURITY_CODE,
	        		KEY_ADMIN,
	        		}, 
	                null, 
	                null, 
	                null, 
	                null, 
	                null);
	    }

	    //---retrieves a particular title---
	    public Cursor getDBRegLogin(long rowId) throws SQLException 
	    {
	        Cursor mCursor =  db.query(true, DATABASE_TABLE, new String[] {
	        		KEY_USERNAME, 
	        		KEY_FIRST_NAME,
	        		KEY_LAST_NAME,
	        		KEY_EMAIL,
	        		KEY_MOBILE_NUMBER,
	        		KEY_PASSWORD,
	        		KEY_AUTOLOGIN,
	        		KEY_LAST_LOGIN_ATTEMPT,
	        		KEY_SECURITY_CODE,
	        		KEY_ADMIN,
	                		}, 
	                		KEY_USERNAME + "=" + rowId, 
	                		null,
	                		null, 
	                		null, 
	                		null, 
	                		null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
	    }

	    //---updates a title---
	    public long GetUpdateLogInAttempt(String userid,String logintime )
		 {
			
			 ContentValues cv=new ContentValues();
//			  cv.put(KEY_USERNAME, userid);
		       cv.put(KEY_LAST_LOGIN_ATTEMPT, logintime);
			 return db.update(DATABASE_TABLE, cv, KEY_USERNAME+"=?", new String []{String.valueOf(userid)});
			 
		 }
	    public int getRowCount()
		 {
			
			Cursor cur= db.rawQuery("Select * from "+DATABASE_TABLE, null);
			int x= cur.getCount();
			cur.close();
			return x;
		 }
	    public int getuserlogin(String username,String password)
		 {
			
			Cursor cur= db.rawQuery("select * from UserInfoDB where email=? and password=?", new String [] {String.valueOf(username),String.valueOf(password)});
			int x= cur.getCount();
//			if(x<1)
//			{
//				//login unsuccessful
//			}
//			else if(x>0)
//			{
//				//login successful
//			}
//				
			cur.close();
			return x;
		 }

		
	    public String getUserStatus(String username,String password)
		 {
			
			Cursor cur= db.rawQuery("select * from UserInfoDB where email=? and password=?", new String [] {String.valueOf(username),String.valueOf(password)});
//			int x= cur.getCount();
			String admin=null;
			try {
				if(cur.getCount() > 0) {

					cur.moveToFirst();
					admin = cur.getString(cur.getColumnIndex("admin"));
	            }
				 

			} catch (Exception e) {
				// TODO: handle exception
			}
			
           
//				
			finally {

				cur.close();
            }
			return admin;
			
		 }
	
		
	    public String getUserId(String username,String password)
		 {
			
			Cursor cur= db.rawQuery("select * from UserInfoDB where email=? and password=?", new String [] {String.valueOf(username),String.valueOf(password)});
//			int x= cur.getCount();
			String userId=null;
			try {
				if(cur.getCount() > 0) {

					cur.moveToFirst();
					userId = cur.getString(cur.getColumnIndex("userId"));
	            }
				 

			} catch (Exception e) {
				// TODO: handle exception
			}
			
           
//				
			finally {

				cur.close();
            }
			return userId;
			
		 }
	    
	    
	    public String getLastLogIn(String userId)
		 {
			
			Cursor cur= db.rawQuery("select * from UserInfoDB where userId=?", new String [] {String.valueOf(userId)});
//			int x= cur.getCount();
			String last_login_attempt=null;
			try {
				if(cur.getCount() > 0) {

					cur.moveToFirst();
					last_login_attempt = cur.getString(cur.getColumnIndex("last_login_attempt"));
	            }
				 

			} catch (Exception e) {
				// TODO: handle exception
			}
			
           
//				
			finally {

				cur.close();
            }
			return last_login_attempt;
			
		 }
	    
	}

