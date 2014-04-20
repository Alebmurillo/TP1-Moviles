package com.example.clinicappcr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class ClinicasFragment extends ListFragment {
	
	
	
	Button btBuscar;
	EditText eSearch;
	ArrayList<HashMap<String, String>> especialistasList;
	String searchTxt;
	private Spinner spinner1;
	//private String URL="http://192.168.0.189:80/clinicas/clinicaes_json.php";
	private String URL="http://192.168.0.189:80/api/v1/clinicas_jason";
	List<String> listaClinicas,listaIdClinicas ;
	ArrayList<HashMap<String, String>> clinicList;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		clinicList = new ArrayList<HashMap<String, String>>();	

	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		clinicList = new ArrayList<HashMap<String, String>>();
		// Calling async task to get json
		new GetClinicas().execute();

	}	

	
	@Override  
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	   Bundle savedInstanceState) {  
	  View view = inflater.inflate(R.layout.fragment_clinicas, null);  
	 
		btBuscar = (Button) view.findViewById(R.id.btnBuscar);  
		btBuscar.setOnClickListener(new OnClickListener() { 
			//Busqueda de especialidades
			@Override  
			public void onClick(View v) {			
				especialistasList = new ArrayList<HashMap<String, String>>();
				new GetClinicas().execute();
			}  
		}); 
		return view;  
	}  

	private ProgressDialog pDialog;
	private SimpleAdapter adapter ;

	private class GetClinicas extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			httpHandler sh = new httpHandler();
			String jsonStr;
			//sh.addNameValue("clin", searchTxt);
			searchTxt = "";
			jsonStr = sh.post(URL);
			Log.d("Response: ", "> " + jsonStr);
			
			
			jsonStr = sh.post("http://192.168.0.189:80/api/v1/clinicas_json");
			if (jsonStr != null) {
				System.out.println(jsonStr);
				try {
					JSONObject json = new JSONObject(jsonStr);
					JSONArray jsonlistaClinicas  = json.getJSONArray("emp_info");
					System.out.println("vamos bien");
					listaClinicas= new ArrayList<String>();
					listaIdClinicas= new ArrayList<String>();
					for (int j = 0; j < jsonlistaClinicas.length(); j++) {
						System.out.println("bien la "+j+"");

						JSONArray clinica = jsonlistaClinicas.getJSONArray(j);
						System.out.println(clinica.toString());
						String idclinic = clinica.get(0).toString();
						String nombreClinic = clinica.get(1).toString();	
						listaClinicas.add(nombreClinic);
						listaIdClinicas.add(idclinic);					
						System.out.println("aqui bien");

						// tmp hashmap for single clinic
						HashMap<String, String> clinic = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						clinic.put("nombre", nombreClinic);

						// adding clinic to clinic list
						clinicList.add(clinic);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}		
			

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
						
			adapter = new SimpleAdapter(
					getActivity(), clinicList,
					R.layout.fila_clinica, new String[] { "nombre" }, new int[] { R.id.view_lugar });

			setListAdapter(adapter);
			
		}
		
	}

	
	
	
	
}  