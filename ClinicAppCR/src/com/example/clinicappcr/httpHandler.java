package com.example.clinicappcr;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class httpHandler  {

	private ArrayList<NameValuePair> nameValuePairs;
	private static String UrlService = "http://192.168.1.3:80/Citas/test.php";
	
	public String getUrlService(){
		return UrlService;
		}
	public void setUrlService(String UrlService){
    	this.UrlService = UrlService;
    	}
	
	
	public httpHandler(/*Activity activity*/){
 		//this.activity = activity;
 		nameValuePairs = new ArrayList<NameValuePair>();
    }
	
	private boolean isInternetAllowed(Activity activity){
	     ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo ni = cm.getActiveNetworkInfo();
	        if (ni!=null && ni.isAvailable() && ni.isConnected()) {
	         return true;
	        } else {
	         return false; 
	        }     
	    }  
	
	public void addNameValue(String name, String value){
		 nameValuePairs.add(new BasicNameValuePair(name,value));
		    }
	
	public String post(String posturl){
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(posturl);
			HttpResponse resp = httpclient.execute(httppost);
			HttpEntity ent = resp.getEntity();
			String text = EntityUtils.toString(ent);
			
			return text;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return e.toString();
		}
	}
	public String postPairs(String posturl){
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(posturl);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse resp = httpclient.execute(httppost);
			HttpEntity ent = resp.getEntity();
			String text = EntityUtils.toString(ent);
			
			return text;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return e.toString();
		}
	}
}

