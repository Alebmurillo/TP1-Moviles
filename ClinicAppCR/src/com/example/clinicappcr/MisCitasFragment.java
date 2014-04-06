package com.example.clinicappcr;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MisCitasFragment extends ListFragment  {
	
	Button btMenu; 
	static final int DIALOG_CONFIRM = 0;
	protected static final int REQUEST_CODE = 10;

	private CitasAdapter adapter;
	private ArrayList<Cita> data;
	
	
	// Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contactList = new ArrayList<HashMap<String, String>>();
		// Calling async task to get json
        new GetContacts().execute();
		//setContentView(R.layout.activity_post_list);
		
		if (savedInstanceState == null){
			
			data = new ArrayList<Cita>();
			//getCitas();
//	    	      data.add(new Cita("1","19/09/2012", "PC" , "false"));
//	    	      data.add(new Cita("2","23/09/2012", "parámetros" , "false"));
//	    	      data.add(new Cita("3","30/09/2012", "Autenticación de Dos Factores ahora mismo" , "false"));
//	    	      data.add(new Cita("4","07/10/2012", "imagen" , "false"));
//	    	      data.add(new Cita("5","21/10/2012", "Comandos" , "false"));
//	    	      data.add(new Cita("6","28/10/2012", "Enlaces" , "false"));
//	    	      data.add(new Cita("7","04/11/2012", "Nueva" , "false"));
//	    	      data.add(new Cita("8","11/11/2012", "Personalizar" , "false"));
//	    	      data.add(new Cita("9","18/11/2012", "Humor" , "false"));
//	    	      data.add(new Cita("10","25/11/2012", "Bastao" , "false"));
			//adapter = new CitasAdapter(getActivity(), data);
		} else{
			//data = savedInstanceState.getParcelableArrayList("savedData");
			//adapter = new CitasAdapter(getActivity(), data);
		}
			//setListAdapter(adapter);
		
	}
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {      
    	
        View view = inflater.inflate(R.layout.fragment_citas, null); 
       // getCitas();
        btMenu = (Button) view.findViewById(R.id.button_crear);  
        btMenu.setOnClickListener(new OnClickListener() {  
        	@Override  
        	public void onClick(View v) {  
				FragmentManager fm = getFragmentManager();
				//FragmentTransaction ft = fm.beginTransaction(); 
				VentanaCrearCita fragment = new VentanaCrearCita();
				//fragment.mListener =(IContactoCreadoListener)getFragmentManager().findFragmentByTag("lista");
				fragment.show(fm, "crear");        		
			}  
		}); 
		       
		
		return view;  
	}  
    
    private void getCitas(){
    	Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					httpHandler handler= new httpHandler();		                
					//con la ip se sabe que es lo que se solicita
					String txt = handler.post("http://192.168.1.3:80/Citas/usuarios_json.php");
					JSONObject json = new JSONObject(txt);

					try {
						data = new ArrayList<Cita>();
						JSONArray listaCitas  = json.getJSONArray("emp_info");
						System.out.println(listaCitas.toString());
						for (int j = 0; j < listaCitas.length(); j++) {
							JSONArray cita = listaCitas.getJSONArray(j);

							
							String fecha = cita.get(1).toString();
							String hora = cita.get(2).toString();
							String doctor = cita.get(3).toString();
							String lugar = cita.get(5).toString();							
							
					    	data.add(new Cita(doctor,lugar,fecha,hora));							
						}
						adapter.notifyDataSetChanged();
					} catch (JSONException e) {
						System.out.println("JSON Parser"+ "Error parsing data " + e.toString());
						//textview.setText("JSON Parser"+ "Error parsing data " + e.toString());
					}					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
    	);
		thread.start(); 
		
    }
    
	
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("savedData", data);
		super.onSaveInstanceState(outState);
	}
	
	private ProgressDialog pDialog;
	
		 
		 private class GetContacts extends AsyncTask<Void, Void, Void> {
			 
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
		            ServiceHandler sh = new ServiceHandler();
		 
		            // Making a request to url and getting response
		            String jsonStr = sh.makeServiceCall("http://192.168.1.3:80/Citas/usuarios_json.php", ServiceHandler.GET);
		 
		            Log.d("Response: ", "> " + jsonStr);
		 
		            
		            if (jsonStr != null) {
		            	
		                try {
		                	JSONObject json = new JSONObject(jsonStr);
		                	//data = new ArrayList<Cita>();
							JSONArray listaCitas  = json.getJSONArray("emp_info");
							System.out.println(listaCitas.toString());
							for (int j = 0; j < listaCitas.length(); j++) {
								JSONArray cita = listaCitas.getJSONArray(j);

								
								String fecha = cita.get(1).toString();
								String hora = cita.get(2).toString();
								String doctor = cita.get(3).toString();
								String lugar = cita.get(5).toString();							
								
						    	//data.add(new Cita(doctor,lugar,fecha,hora));		
		 
		                        // tmp hashmap for single contact
		                        HashMap<String, String> contact = new HashMap<String, String>();
		 
		                        // adding each child node to HashMap key => value
		                        contact.put("fecha", fecha);
		                        contact.put("hora", hora);
		                        contact.put("doctor", doctor);
		                        contact.put("lugar", lugar);
		 
		                        // adding contact to contact list
		                        contactList.add(contact);
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
		            
		            ListAdapter adapter = new SimpleAdapter(
		                    getActivity(), contactList,
		                    R.layout.fila_cita, new String[] { "fecha", "hora",
		                    	"doctor","lugar" }, new int[] { R.id.view_fecha,
		                            R.id.view_hora, R.id.view_doctor,R.id.view_lugar });
		 
		            setListAdapter(adapter);
		        }
		 
		    }
		 
		}
	

