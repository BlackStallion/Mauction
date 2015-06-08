package com.maid.mauctionsample.Databases;

import java.util.ArrayList;

import com.maid.mauctionsample.DataModel.TBBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbTBidAProduct {
	public static final String KEY_USERID = "userId";
	public static final String KEY_PROD_ID = "prod_id";
	public static final String KEY_PROD_CODE = "prod_code";
	public static final String KEY_PROD_NAME = "prod_name";
	public static final String KEY_PROD_DESC = "prod_desc";
	public static final String KEY_PROD_PRICE = "prod_price";

	private static final String DATABASE_NAME = "PordBidList";
	private static final String DATABASE_TABLE = "PordBidListDB";
	private static final int DATABASE_VERSION = 4;

	private static final String DATABASE_CREATE =
	"CREATE TABLE " + DATABASE_TABLE + "(" + "userId" + " TEXT,"
			+ "prod_id" + " TEXT ," + "prod_code" + " TEXT," + "prod_name"
			+ " TEXT," + "prod_desc" + " TEXT," + "prod_price" + " TEXT" + " )";

	private final Context context;

	public static DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DbTBidAProduct(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Log.w(TAG, "Upgrading database from version " + oldVersion+
			// " to "+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS tableone");
			onCreate(db);
		}
	}

	// ---opens the database---
	public DbTBidAProduct open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
	}

	// ---insert a title into the database---
	public void insertTBid_aProdcut(String str_UserId, String str_pId,
			String str_Code, String str_Name, String str_desc, String str_price) {
		SQLiteDatabase db = DBHelper.getWritableDatabase();
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERID, str_UserId);
		initialValues.put(KEY_PROD_ID, str_pId);
		initialValues.put(KEY_PROD_CODE, str_Code);
		initialValues.put(KEY_PROD_NAME, str_Name);
		initialValues.put(KEY_PROD_DESC, str_desc);
		initialValues.put(KEY_PROD_PRICE, str_price);

		db.insert(DATABASE_TABLE, null, initialValues);
//		db.close();

	}

	// // ---retrieves all the titles---
	// public Cursor getDBReg() {
	// return db.query(DATABASE_TABLE, new String[] { KEY_PROD_ID,
	// KEY_PROD_CODE, KEY_PROD_NAME, KEY_PROD_DESC, KEY_PROD_PRICE,
	// }, null, null, null, null, null);
	// }


	public ArrayList<String >getLabeId(String LockNo) {

		ArrayList<String> Key_Pin_array = new ArrayList<String>();

		Cursor cur= db.rawQuery("select * from PordBidListDB where userId=?", new String [] {String.valueOf(LockNo)});
		try {
			while (cur.moveToNext()) {
				System.out.println("Key_Pin" + cur.getString(1));
				Key_Pin_array.add(cur.getString(3));
			}
		} catch (Exception e) {
			System.out.println("error in getLabelID in DB() :" + e);
		} finally {
			cur.close();
		}
		return Key_Pin_array;
	}



	public int getRowCount() {

		Cursor cur = db.rawQuery("Select * from " + DATABASE_TABLE, null);
		int x = cur.getCount();
		cur.close();
		return x;
	}
	
	
	 //---retrieves a particular title---
    public ArrayList<TBBean> getWheelItem(String usrId,String name) throws SQLException 
    {
    	
    	
        Cursor mCursor =  db.query(true, DATABASE_TABLE, new String[] {
        		KEY_PROD_DESC, 
        		KEY_PROD_CODE,
        		KEY_PROD_NAME,
        		KEY_PROD_PRICE,
        		
                		}, 
                		KEY_USERID + "=" + usrId + "+ "+KEY_PROD_ID +"="+name, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        ArrayList<TBBean> tblist = new ArrayList<TBBean>();
        if (mCursor != null) {
           
            while(mCursor.moveToNext()) {
            	TBBean Tbbean = new TBBean(); 
                /*int keyRowIdColumnIndex = mCursor.getColumnIndex(KEY_PROD_DESC);
                int yourValue = mCursor.getColumnIndex(KEY_PROD_CODE);
                int keyRowIdColumnIndexsd = mCursor.getColumnIndex(KEY_PROD_NAME);
                int yourValuesd = mCursor.getColumnIndex(KEY_PROD_PRICE);
                String sdsdwe = mCursor.getString(keyRowIdColumnIndex);
                */
                String prod_des = mCursor.getString(mCursor.getColumnIndex(KEY_PROD_DESC));
                String prod_code = mCursor.getString(mCursor.getColumnIndex(KEY_PROD_CODE));
                String prod_name = mCursor.getString(mCursor.getColumnIndex(KEY_PROD_NAME));
                String prod_price = mCursor.getString(mCursor.getColumnIndex(KEY_PROD_PRICE));
                
                Tbbean.setDesc(prod_des);
                Tbbean.setCode(prod_code);
                Tbbean.setName(prod_name);
                Tbbean.setPrice(prod_price);
                
                tblist.add(Tbbean);
                
           }
        }
        return tblist;
    }

}
