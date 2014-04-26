package com.example.clinicappcr;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class httpHandler  {

	private ArrayList<NameValuePair> nameValuePairs;
	private String UrlService;


	private String responseBody = "";

	private Activity activity;
	private ProgressDialog m_ProgressDialog = null;
	private Runnable viewOrders;

	private String strMessageHeadLoading = "Por favor espera";
	public String getStrMessageHeadLoading(){ return strMessageHeadLoading; }
	public void setStrMessageHeadLoading(String message){ strMessageHeadLoading = message; }

	private String strMessageBodyLoading  = "Enviando información...";
	public String getStrMessageBodyLoading(){ return strMessageBodyLoading; }
	public void setStrMessageBodyLoading(String message){ strMessageBodyLoading = message; }

	public String getUrl(){ return UrlService;}
	public void setUrl(String UrlService){this.UrlService = UrlService;}

	public String getResponseBody(){return responseBody;}
	private void setResponseBody(String ResponseBody){responseBody = ResponseBody;}


	public httpHandler(){
		//this.activity = activity;
		nameValuePairs = new ArrayList<NameValuePair>();
	}
	public httpHandler(Activity activity){
		this.activity = activity;
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

	public void executeHttpPost(String UrlService){
		setUrl(UrlService);

		viewOrders = new Runnable(){
			public void run() {
				try {
					executeHttpPostAsync(activity, getUrl());
				} catch (Exception e) {}
			}
		};
		Thread thread =  new Thread(null, viewOrders, "Background");
		thread.start();
		m_ProgressDialog = ProgressDialog.show(activity, getStrMessageHeadLoading(), getStrMessageBodyLoading(), true);

	}


	private void executeHttpPostAsync(Activity activity, String UrlService){
		if(isInternetAllowed(activity)){
			try{
				
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(UrlService);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse resp = httpclient.execute(httppost);
				HttpEntity ent = resp.getEntity();
				String text = EntityUtils.toString(ent);				
				setResponseBody(text);
////
				activity.runOnUiThread(returnRes);
			}catch(HttpResponseException hre){
				ListenerExecuteHttpPostAsync.onErrorHttpPostAsyncListener("Se ha producido un error al conectar con el servidor");
			}catch(Exception e){
				ListenerExecuteHttpPostAsync.onErrorHttpPostAsyncListener("Se ha producido un error");
			}
		} else{
			ListenerExecuteHttpPostAsync.onErrorHttpPostAsyncListener("No es posible realizar la conexión. Comprueba tu conexión de datos.");
		}
	}

	private Runnable returnRes = new Runnable() {
		public void run() {
			m_ProgressDialog.dismiss();
			ListenerExecuteHttpPostAsync.onExecuteHttpPostAsyncListener(getResponseBody());
		}
	};	

	public interface OnExecuteHttpPostAsyncListener{
		void onExecuteHttpPostAsyncListener(String ResponseBody);
		void onErrorHttpPostAsyncListener(String message);
	}

	private OnExecuteHttpPostAsyncListener ListenerExecuteHttpPostAsync;

	public void setOnExecuteHttpPostAsyncListener(OnExecuteHttpPostAsyncListener l){ListenerExecuteHttpPostAsync = l;}




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
			return e.toString();
		}
	}
}

