package com.example.testplab;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.testplab.R;
import com.example.testplab.LocationService.LocalBinder;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private LocationService mService;	//connection to the locationservice
    private boolean mBound = false;		//boolean to check of connection to service is up
    private boolean terminated = false;	//boolean to check of the service is terminated
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //remove title on top of activity with application name
		setContentView(R.layout.activity_main);		
	}
	
    @Override
    protected void onStart() {
        super.onStart();
        //Bind to the locationservice
        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        
        //sow the imei number of the phone on the main activity
        showIEMINumber();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            //unbindService(mConnection);
            mBound = false;
        }
    }
	
	/** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
        	LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// Get all the current and most recent location data to display on the screen
    public void onButtonClick(View v) {
        if (mBound) {
            // Call a method from the LocalService.           
            double lng = mService.getLocation_lng();
            double lat = mService.getLocation_lat();
            double distance = mService.getDistance();
            
            // Display the location data on the screen
            setLocation(lat, lng, distance);            
        }else{
        	Log.i("onButtonClick", "locationservice not bound");
        }
    }
    
    //Terminate method that will stop the service in the background
    public void onTerminateButtonClick(View v){
    	
    	//check if the service hasn't been terminated yet
    	if(!terminated){
    		this.unbindService(mConnection);
    		mBound = false;
    		this.stopService(new Intent(MainActivity.this,LocationService.class));
    		terminated = true;
    		Log.i("onTerminateButtonClick", "Service has been stopped");
    	}
    	
    }
    
    //Method that asks a async worker thread to send an httprequest with some params
    public void onSQLButtonClick(View V) {
    	
    	//random params because we don't have useful info at the moment
    	Random randomInt = new Random();
    	int intresse = randomInt.nextInt(4);
    	String ID = Integer.toString(intresse);
    	
    	int bid = randomInt.nextInt(10);
    	String bids = Integer.toString(bid);
    	
		//Get the unique imei number of the phone, this will be used as unique identifier in the database
    	TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		
		//Start the async worker thread to send the httprequest
		new sendHttpRequest().execute(imei, ID, bids);    		 
    }
    
    //Method to get the content of the table boards from the database
    public void syncTable(View V){
    	
    	String table = (String) V.getTag();
    	
    	//Start async worker thread to send the httprequest
    	GetSyncHTTPRequest getSyncHTTPRequest = new GetSyncHTTPRequest();
    	getSyncHTTPRequest.execute(table);
    	try {
    		
    		//Get the returned json array from the worker thread
			JSONArray jsonArray = (JSONArray) getSyncHTTPRequest.get();
			
			//Start a method that punts the json array in a local database
			if(jsonArray != null){
				insertTableInDB(jsonArray, table);
			}
		} catch (InterruptedException e) {
			Log.e("InterruptedException", "syncBoards", e);
			e.printStackTrace();
		} catch (ExecutionException e) {
			Log.e("ExecutionException", "syncBoards", e);
			e.printStackTrace();
		} catch (JSONException e) {
			Log.e("JSONException", "syncBoards", e);
			e.printStackTrace();
		}
    }
    
    //Method that puts a json array into a local database
    public void insertTableInDB(JSONArray jsonArray, String table) throws JSONException{
	
		DBConection dbHelper = new DBConection(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
    	
    	//clear tables
    	if(table.equals("Boards")){
			db.execSQL("DROP TABLE IF EXISTS " + DBConection.TABLE_BOARDS);
			db.execSQL(DBConection.CREATE_BOARDS);
    	}else if(table.equals("Intresses")){
			db.execSQL("DROP TABLE IF EXISTS " + DBConection.TABLE_INTRESSES);
			db.execSQL(DBConection.CREATE_INTRESSES);
    	}
    	
    	//insert new values
    	for(int i=0; i<jsonArray.length(); i++){
    		JSONObject jsonObject = jsonArray.getJSONObject(i);
			if(table.equals("Boards")){			
				ContentValues values = new ContentValues();
				values.put(DBConection.COLUMN_ID, jsonObject.getString("id"));
				values.put(DBConection.COLUMN_LNG, jsonObject.getString("lng"));
				values.put(DBConection.COLUMN_LAT, jsonObject.getString("lat"));
				
				@SuppressWarnings("unused")
				long newRowId = db.insert(DBConection.TABLE_BOARDS, null, values);
    		
			}else if(table.equals("Intresses")){				
				ContentValues values = new ContentValues();
				values.put(DBConection.COLUMN_ID, jsonObject.getString("id"));
				values.put(DBConection.COLUMN_CATEGORIE, jsonObject.getString("categorie"));
				
				@SuppressWarnings("unused")
				long newRowId = db.insert(DBConection.TABLE_INTRESSES, null, values);
    		}
    	}
    		
		db.close();
		Toast.makeText(this, "Table "+table+" has been synced", Toast.LENGTH_SHORT).show();
		
    }
    
    //Display the most recent location on the screen
	private void setLocation(double lat, double lng, double distance) {
		
		TextView lng_coordinaten = (TextView) findViewById(R.id.textView3);
		TextView lat_coordinaten = (TextView) findViewById(R.id.textView4);
		TextView distance_calculated = (TextView) findViewById(R.id.textView6);
		
		lng_coordinaten.setText(Double.toString(lng));
		lat_coordinaten.setText(Double.toString(lat));
		distance_calculated.setText(Double.toString(distance));
		
	}
	
	//Display the imei number on the screen
	private void showIEMINumber(){
		TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		
		TextView iemi_number = (TextView) findViewById(R.id.textView8);
		iemi_number.setText(imei);
	}

}