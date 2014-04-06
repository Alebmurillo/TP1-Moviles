package com.example.clinicappcr;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class MisCitasFragment extends ListFragment  {
	
	Button btCrear; 
	static final int DIALOG_CONFIRM = 0;
	protected static final int REQUEST_CODE = 10;

	//private CitasAdapter adapter;
	//private ArrayList<Cita> data;
	
	
	// Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contactList = new ArrayList<HashMap<String, String>>();
		// Calling async task to get json
        //new GetCitas().execute();	
	}
	
	@Override
	public void onResume() {
		super.onResume();
		contactList = new ArrayList<HashMap<String, String>>();
		System.out.print("onresume ********");
		// Calling async task to get json
        new GetCitas().execute();	
	}
	
		
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 0){ //make sure fragment codes match up {
	    	System.out.println("PRUEBA SIRVIO ********");

	    	System.out.println();
	    	
	    	ArrayList<String> array = data.getStringArrayListExtra("cita");
	    	HashMap<String, String> contact = new HashMap<String, String>();
			 
            // adding each child node to HashMap key => value
	    	contact.put("doctor", array.get(0));
            contact.put("lugar", array.get(1));
            contact.put("fecha", array.get(2));
            contact.put("hora", array.get(3));
            System.out.println(contactList.size()+" *********TAMAÒO");
            contactList.add(contact);
            adapter.notifyDataSetChanged();
	        
	    }
	}
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {      
    	
        View view = inflater.inflate(R.layout.fragment_citas, null); 
       // getCitas();
        final FragmentManager fm = getFragmentManager();
		//FragmentTransaction ft = fm.beginTransaction(); 
		final VentanaCrearCita fragment = new VentanaCrearCita();
		fragment.setTargetFragment(this, 0);
        
        btCrear = (Button) view.findViewById(R.id.button_crear);  
        btCrear.setOnClickListener(new OnClickListener() {  
        	@Override  
        	public void onClick(View v) {  
				//FragmentManager fm = getFragmentManager();
				//VentanaCrearCita fragment = new VentanaCrearCita();
				//fragment.setTargetFragment(this, 0);
				//fragment.mListener =(IContactoCreadoListener)getFragmentManager().findFragmentByTag("lista");
				fragment.show(fm, "crear");        		
			}  
		}); 
		       
		
		return view;  
	}  
    
    /*private void getCitas(){
    	Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					httpHandler handler= new httpHandler();		                
					//con la ip se sabe que es lo que se solicita
					String txt = handler.post("http://192.168.1.3:80/Citas/usuarios_json.php");
					JSONObject json = new JSONObject(txt);

					try {
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
						}
						//adapter.notifyDataSetChanged();
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
    
	*/
	
	
	/*@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("savedData", data);
		super.onSaveInstanceState(outState);
	}
	*/
	private ProgressDialog pDialog;
	private SimpleAdapter adapter ;
	
		 
		 private class GetCitas extends AsyncTask<Void, Void, Void> {
			 
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
		            //ServiceHandler sh = new ServiceHandler();
		            httpHandler sh = new httpHandler();
		            // Making a request to url and getting response
		           // String jsonStr = sh.makeServiceCall("http://192.168.1.3:80/Citas/usuarios_json.php", ServiceHandler.GET);
		            String jsonStr = sh.post("http://192.168.1.3:80/Citas/usuarios_json.php");

		            Log.d("Response: ", "> " + jsonStr);
		 
		            
		            if (jsonStr != null) {
		            	
		                try {
		                	JSONObject json = new JSONObject(jsonStr);
		                	//data = new ArrayList<Cita>();
							JSONArray listaCitas  = json.getJSONArray("emp_info");
							//System.out.println(listaCitas.toString());
							for (int j = 0; j < listaCitas.length(); j++) {
								JSONArray cita = listaCitas.getJSONArray(j);

								
								String fecha = cita.get(1).toString();
								String hora = cita.get(2).toString();
								String doctor = cita.get(3).toString();
								String lugar = cita.get(5).toString();							
								
		 
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
		            
		            adapter = new SimpleAdapter(
		                    getActivity(), contactList,
		                    R.layout.fila_cita, new String[] { "fecha", "hora",
		                    	"doctor","lugar" }, new int[] { R.id.view_fecha,
		                            R.id.view_hora, R.id.view_doctor,R.id.view_lugar });
		 
		            setListAdapter(adapter);
		        }
		 
		    }
		 
		}
	

