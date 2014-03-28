package com.example.testplab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import static java.lang.Math.*;

public class LocationService extends Service implements LocationListener {

private LocationManager locationManager;
private final IBinder mBinder = new LocalBinder();
private double location_lng;
private double location_lat;
private double distance;
private int seconds = 10; 	//tijdinterval voor locatiupdates
private int meters = 1;	//minimum afstand voor een nieuwe locatieupdate

//variabelen voor gps locatie optimalisitie
Location location1;
Location location2;
Location location3;
Location location4;
Location location5;
int curloc = 1;
boolean report = false;

/**
 * Class used for the client Binder.  Because we know this service always
 * runs in the same process as its clients, we don't need to deal with IPC.
 */
public class LocalBinder extends Binder {
    LocationService getService() {
        // Return this instance of LocalService so clients can call public methods
        return LocationService.this;
    }
}

//return the binder
@Override
public IBinder onBind(Intent intent) {
    return mBinder;
}

//request the location updates 
@Override
public void onCreate() {
	
	System.out.print("lol");
	
    Criteria criteria = new Criteria();
    criteria.setAccuracy(Criteria.ACCURACY_FINE);
    criteria.setCostAllowed(false);
    // Get a location manager from the system services
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    // if GPS is not enabled. Show alert to user.
    if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
    }

    // Get the location provider that best matches the criteria
    String bestProvider = locationManager.getBestProvider(criteria, false);

    if (bestProvider != null) {
        // Get the user's last known location
        if (locationManager.isProviderEnabled(bestProvider)) {
            locationManager.requestLocationUpdates(bestProvider, 1000 * seconds, meters, this);
            locationManager.requestLocationUpdates(bestProvider, 1000 * seconds, meters, this);
            locationManager.requestLocationUpdates(bestProvider, 1000 * seconds, meters, this);
            locationManager.requestLocationUpdates(bestProvider, 1000 * seconds, meters, this);
            locationManager.requestLocationUpdates(bestProvider, 1000 * seconds, meters, this);
        }
        
    }
    
}

@Override
public int onStartCommand(Intent intent, int flags, int startId) {
    // We want this service to continue running until it is explicitly
    // stopped, so return sticky.
    return START_STICKY;
}

@Override
public void onLocationChanged(Location newLocation) {   
	
    	Location location = newLocation;
    	//kijken hoe precies hij nou eigenlijk is
    	System.out.print(location.getAccuracy());
    	
    	if(curloc == 1) {
    		
    		location1 = newLocation;
    		curloc = 2;
    		report = false;
    		
    	}else if(curloc == 2){
    		
    		location2 = newLocation;
    		curloc = 3;
    		
    	}else if(curloc == 3){
    		
    		location3 = newLocation;
    		curloc = 4;
    		
    	}else if(curloc == 4){
    		
    		location4 = newLocation;
    		curloc = 5;
    		
    	}else if(curloc == 5){
    		
    		location5 = newLocation;
    		curloc = 1;
    		report = true;
    		
    	}
    	
    	if(report == true){
    		reportLocation();
    	}
        
}

public void reportLocation(){

	//binnen halen longitude en latitude van locatie 1
	double location_lng1 = location1.getLongitude();
	double location_lat1 = location1.getLatitude();
	
	//binnen halen longitude en latitude van locatie 2
	double location_lng2 = location2.getLongitude();
	double location_lat2 = location2.getLatitude();
	
	//binnen halen longitude en latitude van locatie 3
	double location_lng3 = location3.getLongitude();
	double location_lat3 = location3.getLatitude();
	
	//binnen halen longitude en latitude van locatie 4
	double location_lng4 = location4.getLongitude();
	double location_lat4 = location4.getLatitude();
	
	//binnen halen longitude en latitude van locatie 5
	double location_lng5 = location5.getLongitude();
	double location_lat5 = location5.getLatitude();
	
	//gemiddelde nemen van de locaties die genomen zijn
	location_lng = (location_lng1 + location_lng2 + location_lng3 + location_lng4 + location_lng5) / 5;
	location_lat = (location_lat1 + location_lat2 + location_lat3 + location_lat4 + location_lat5) / 5;
	
	//afstand berekening
	processGPS(location_lat, location_lng);

	System.out.println(location_lng);
	System.out.println(location_lat);
	
	
};

//get the longtitude
public double getLocation_lng(){
	return location_lng;
}

//get the latitude
public double getLocation_lat(){
	return location_lat;
}

//get the best location that is avelible at the moment
public static Location getBestLocation(LocationManager locationManager) {

    Location location_gps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    Location location_network = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

    // If both are available, get the most recent
    if(location_gps!=null && location_network !=null) {
        return (location_gps.getTime() > location_network.getTime())?location_gps:location_network;
    }
    else if(location_gps==null && location_network ==null){
        return null;
    }
    else
        return (location_gps==null)?location_network:location_gps;

}

//functions that we dont use
@Override
public void onProviderEnabled(String s){}
@Override
public void onProviderDisabled(String s){}
@Override
public void onStatusChanged(String s, int i, Bundle b){}

//release the locationmanager
@Override
public void onDestroy() {       
    locationManager.removeUpdates(this);
}

private static final int TWO_MINUTES = 1000 * 60 * 2;

/** Determines whether one Location reading is better than the current Location fix
 * @param location  The new Location that you want to evaluate
 * @param currentBestLocation  The current Location fix, to which you want to compare the new one
 */
protected boolean isBetterLocation(Location location, Location currentBestLocation) {
    if (currentBestLocation == null) {
        // A new location is always better than no location
        return true;
    }
    
    if (location == null){
    	return true;
    }

    // Check whether the new location fix is newer or older
    long timeDelta = location.getTime() - currentBestLocation.getTime();
    boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
    boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
    boolean isNewer = timeDelta > 0;

    // If it's been more than two minutes since the current location, use the new location
    // because the user has likely moved
    if (isSignificantlyNewer) {
        return true;
        // If the new location is more than two minutes older, it must be worse
    } else if (isSignificantlyOlder) {
        return false;
    }

    // Check whether the new location fix is more or less accurate
    int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
    boolean isLessAccurate = accuracyDelta > 0;
    boolean isMoreAccurate = accuracyDelta < 0;
    boolean isSignificantlyLessAccurate = accuracyDelta > 200;

    // Check if the old and new location are from the same provider
    boolean isFromSameProvider = isSameProvider(location.getProvider(),
            currentBestLocation.getProvider());

    // Determine location quality using a combination of timeliness and accuracy
    if (isMoreAccurate) {
        return true;
    } else if (isNewer && !isLessAccurate) {
        return true;
    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
        return true;
    }
    return false;
}

/** Checks whether two providers are the same */
private boolean isSameProvider(String provider1, String provider2) {
    if (provider1 == null) {
        return provider2 == null;
    }
    return provider1.equals(provider2);
}

//proccess the gps location, if the location is in the range of an board, send an signal
private void processGPS(double lat1, double lon1) {

	String bids = calcDistance(lat1, lon1);
			
	//als afstand gelijk aan 5 of kleiner dan 5 is dan is hij in range
	if(distance <= 5){
		
		Toast.makeText(this, "You are within range of a PLAB", Toast.LENGTH_SHORT).show();
		
		ArrayList<String> intressesList = getIntresses();
		
		Random generator = new Random();
		int y = generator.nextInt(intressesList.size());
		
		String ID = intressesList.get(y);
		
		System.out.println(ID);
    	
		//Get the unique imei number of the phone, this will be used as unique identifier in the database
    	TelephonyManager telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		
		//Start the async worker thread to send the httprequest
		new sendHttpRequest().execute(imei, ID, bids);  		
	
	}else{
	
		System.out.println("You are not in range of a PLAB");
	
	}

	System.out.println(distance);
	calcTimeInterval(distance);

	}

	//calculate the distence between our location, and the location from the boards
	private String calcDistance(double lat1, double lon1){
		
		DBConection dbHelper = new DBConection(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		ArrayList<Double> distanceList = new ArrayList<Double>();
		
		// Define a projection that specifies which columns from the database
				// you will actually use after this query.
				String[] projection = {
					    DBConection.COLUMN_ID,
					    DBConection.COLUMN_LAT,
					    DBConection.COLUMN_LNG
				    };
			
				// How you want the results sorted in the resulting Cursor
				String sortOrder =
				    DBConection.COLUMN_ID + " DESC";
			
				Cursor c = db.query(
				    DBConection.TABLE_BOARDS,  					// The table to query
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
			        	
			        	//calculate distance
			    		double lat2 = c.getDouble(c.getColumnIndex(DBConection.COLUMN_LAT)); //latitude location of a plab
			    		double lon2 = c.getDouble(c.getColumnIndex(DBConection.COLUMN_LNG));  //longitude of plab
			    		
			    		//alles naar radians converten
			    		double radius = 6371;
			    		lat1 = toRadians(lat1);
			    		lat2 = toRadians(lat2);
			    		lon1 = toRadians(lon1);
			    		lon2 = toRadians(lon2);
			    		
			    		//x en y opstellen voor uti eindelijke formule
			    		double x = (lon2 - lon1)  * cos((lat1 + lat2) / 2); 
			    		double y = (lat2 - lat1);
			    		
			    		//afstand
			    		distance = (sqrt(x * x + y * y ) * radius) * 100; 
			    		
			    		if(distance <= 5){
			    			
			    			String id = c.getString(c.getColumnIndex(DBConection.COLUMN_ID));
			    			return id;
			    			
			    		} else {
			    			
			    			distanceList.add(distance);
			    			
			    		}
			        	
			        }while(c.moveToNext());
				}
		db.close();
		dbHelper.close();
			
		distance = Collections.min(distanceList);
		
		return null;
	}

	//get the intresses from this person, from the database
	private ArrayList<String> getIntresses(){
		
		DBConection dbHelper = new DBConection(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		ArrayList<String> intressesList = new ArrayList<String>();
		
		// Define a projection that specifies which columns from the database
				// you will actually use after this query.
				String[] projection = {
				    DBConection.COLUMN_ID
				    };
			
				// How you want the results sorted in the resulting Cursor
				String sortOrder =
				    DBConection.COLUMN_ID + " DESC";
			
				Cursor c = db.query(
				    DBConection.TABLE_OWN_INTRESSES,  					// The table to query
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
			        	intressesList.add(c.getString(c.getColumnIndex(DBConection.COLUMN_ID)));
			        }while(c.moveToNext());
				}
		        db.close();
		        dbHelper.close();
		
		return intressesList;
		
	}

	//calculate the time interval to the next location update
	private void calcTimeInterval(double distance){
		
		double ddistance = distance + 100;
		
		//seconden wordt hoger namaten afstand groter wordt vanuit gaande van de formule: (((x+100)^3)/(10^6*2.5))+10
		seconds = (int) (((ddistance*ddistance*ddistance)/(10^6*(255/100)))+10);
		
		if (seconds > 900){
			
			seconds = 900;
			
		}
		
	}

//get the distence
public double getDistance(){
	return distance;
}

}