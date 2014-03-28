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

import android.os.AsyncTask;
import android.util.Log;

//Class that sends an httprequest to the server, returns nothing
public class sendHttpRequest extends AsyncTask<String, Integer, Long> {

	private String url = "http://plab.carstenoort-pt.com/plab/insertUser.php";	//location of the web service
	
	@Override
	protected Long doInBackground(String... arg) {
		//get the params that the server needs
		String imei = arg[0];
		String intresse_id = arg[1];
		String bids = arg[2];
		
		HttpClient httpClient = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost(url);
    	
    	//add params to the httprequest
    	List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
    	nameValuePair.add(new BasicNameValuePair("imei", imei));
    	nameValuePair.add(new BasicNameValuePair("id_Intresses", intresse_id));
    	nameValuePair.add(new BasicNameValuePair("id_Boards", bids));
    	
    	//catch unsupported encoding exception
    	try{
    		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
    	}catch(UnsupportedEncodingException e){
    		Log.e("UnsupportedEncodingException", "sendHttpRequest", e);
    		e.printStackTrace();
    	}
    	
    	//checks if the server has handled the request
    	try{
    		HttpResponse response = httpClient.execute(httpPost);
    		HttpEntity responseEntity = response.getEntity();
    		String responseString = EntityUtils.toString(responseEntity);
    		Log.i("sendHttpRequestResponse", responseString);
    	}catch(ClientProtocolException e){
    		Log.e("clientProtocolException", "sendHttpRequest", e);
    		e.printStackTrace();
    	}catch(IOException e){
    		Log.e("ioException", "sendHttpRequest", e);
    		e.printStackTrace();
    	}
    	
		return null;
	}

}
