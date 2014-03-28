package com.example.testplab;

import java.util.ArrayList;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class IntressesActivity extends Activity {
	
	private ArrayList<Integer> list = new ArrayList<Integer>();	//list that holds the id of the intersts
	private String toastText = "Intresses has been saved!";			//text for the toast
	
	//add an interests id to the list
	public void setArrayList(int id){
		list.add(id);
	}
	
	//remove an interests id from the list
	public void removeArrayList(int id){
		int index = list.indexOf(id);
		list.remove(index);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_intresses);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.intresses, menu);
		return true;
	}
	
	//call the read intresses funciton on resume
	@Override
	public void onResume(){
		super.onResume();
		readIntresses();
	}
	
	//read all the interests from the database 
	private void readIntresses(){
		DBConection dbHelper = new DBConection(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		// Define a projection that specifies which columns from the database
				// you will actually use after this query.
				String[] projection = {
				    DBConection.COLUMN_ID,
				    DBConection.COLUMN_CATEGORIE
				    };
			
				// How you want the results sorted in the resulting Cursor
				String sortOrder =
				    DBConection.COLUMN_ID + " DESC";
			
				Cursor c = db.query(
				    DBConection.TABLE_INTRESSES,  					// The table to query
				    projection,                               	// The columns to return
				    null,                                		// The columns for the WHERE clause
				    null,                            			// The values for the WHERE clause
				    null,                                     	// don't group the rows
				    null,                                     	// don't filter by row groups
				    sortOrder                                	// The sort order
				    );
				
				c.moveToFirst();
				if(c.isBeforeFirst() != true){
			        do{
			        	
			             int id = Integer.parseInt(c.getString(c.getColumnIndex(DBConection.COLUMN_ID)));
			             String categorie = c.getString(c.getColumnIndex(DBConection.COLUMN_CATEGORIE));
			             
			             //add an checkbox to the screen
			             addCheckbox(id, categorie);
			        }while(c.moveToNext());
				}
		        db.close();
		        dbHelper.close();
	}
	
	//add an checkbox to the screen
	@SuppressLint("NewApi")
	private void addCheckbox(int id, String categorie){
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayout);
    	
		CheckBox checkbox = new CheckBox(this);
		checkbox.setId(id);
		checkbox.setText(categorie);
		checkbox.setTextColor(Color.parseColor("#FFFFFF"));
		checkbox.callOnClick();
		
        linearLayout.addView(checkbox);
        
        //set on listener on the checkbox
        new CheckboxListener(this, checkbox, id);
	}
	
	//save the checked interest to the database
	public void saveIntresses(View v){
		DBConection dbHelper = new DBConection(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		db.execSQL("DROP TABLE IF EXISTS " + DBConection.TABLE_OWN_INTRESSES);
		db.execSQL(DBConection.CREATE_OWN_INTRESSES);
		
		for(int i=0; i<list.size(); i++){			
			ContentValues values = new ContentValues();
			values.put(DBConection.COLUMN_ID, list.get(i));
			db.insert(DBConection.TABLE_OWN_INTRESSES, null, values);
		}
        db.close();
        dbHelper.close();
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
        finish();
	}
}
