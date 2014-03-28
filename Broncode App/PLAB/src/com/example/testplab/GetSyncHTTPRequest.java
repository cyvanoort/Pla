package com.example.testplab;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import android.os.AsyncTask;
import android.util.Log;


//send httprequest to server, returns json object with a table
public class GetSyncHTTPRequest extends AsyncTask<String, Integer, Object> {

	private String url = "http://plab.carstenoort-pt.com/plab/syncTable.php";	//location of the web service
	
	@Override
	protected  Object doInBackground(String... arg) {
		
		String table = arg[0];
		
		HttpClient httpClient = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost(url);
    	JSONArray jsonArray = null;
    	
    	//add params to the httprequest
    	List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
    	nameValuePair.add(new BasicNameValuePair("table", table));
    	
    	try{
    		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
    	}catch(UnsupportedEncodingException e){
    		Log.e("UnsupportedEncodingException", "GetSyncHTTPRequest", e);
    		e.printStackTrace();
    	}
    	
    	//send the http request, and get the response
    	try{
    		HttpResponse response = httpClient.execute(httpPost);
    		HttpEntity responseEntity = response.getEntity();
    		String responseString = EntityUtils.toString(responseEntity);
       		jsonArray = new JSONArray(responseString);       		
       	}catch(ClientProtocolException e){
       		Log.e("clientProtocolException", "GetSyncBoardsHTTPRequest", e);
       		e.printStackTrace();
    	}catch(IOException e){
    		Log.e("ioException", "GetSyncBoardsHTTPRequest", e);
    		e.printStackTrace();
    	} catch (JSONException e) {
    		Log.e("JSONException", "GetSyncBoardsHTTPRequest", e);
			e.printStackTrace();
		}
    	
		return jsonArray ;
	}
}
