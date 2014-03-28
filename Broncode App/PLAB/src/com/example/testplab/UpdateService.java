package com.example.testplab;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class UpdateService extends Service {

	private static final long updateTime = 1000 * 60 * 60 * 24; //24 hours
	public class LocalBinder extends Binder {
	    UpdateService getService() {
	        // Return this instance of LocalService so clients can call public methods
	        return UpdateService.this;
	    }
	}
	
	@Override
    public void onCreate() {
            	
		//start a timer to only execute syncTable once every 24 hours
        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {                
                syncTable("Intresses");
            	syncTable("Boards");
            }
        };

        timer.schedule (hourlyTask, 0l, updateTime); 
        
		
    }
    
    private void syncTable(String table){
    	
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
    private void insertTableInDB(JSONArray jsonArray, String table) throws JSONException{
	
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
		//Toast.makeText(this, "Table "+table+" has been synced", Toast.LENGTH_SHORT).show();
		
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
    	
    	
    	
    }


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
