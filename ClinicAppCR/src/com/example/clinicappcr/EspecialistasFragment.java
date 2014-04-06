package com.example.clinicappcr;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.EditText;
import android.widget.SimpleAdapter;
  
public class EspecialistasFragment extends ListFragment {  

Button btBuscar;
EditText eSearch;
ArrayList<HashMap<String, String>> especialistasList;
String searchTxt;
private String URL="http://192.168.1.3:80/Citas/doctores_json.php";
  
  @Override
  	public void onCreate(Bundle savedInstanceState) {
  		super.onCreate(savedInstanceState);
  		especialistasList = new ArrayList<HashMap<String, String>>();	
	}
  
 @Override  
 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
   Bundle savedInstanceState) {  
  View view = inflater.inflate(R.layout.fragment_especialistas, null);  
  eSearch = (EditText) view.findViewById(R.id.entryEspecialistas);
  btBuscar = (Button) view.findViewById(R.id.btnBuscarEspecialistas);  
          btBuscar.setOnClickListener(new OnClickListener() {  
          	@Override  
          	public void onClick(View v) {
          		
          		//funcionalidad de buscar
          		
          		searchTxt= eSearch.getText().toString();
          		//System.out.println(searchTxt.isEmpty());
          		//System.out.println(btBuscar.getText().length());
          		//System.out.println(btBuscar.getText());
          		
          		
          		especialistasList = new ArrayList<HashMap<String, String>>();	
          		
          		new GetEspecialistas().execute();
  				  		
  			}  
		}); 
  
 
   return view;  

}  


private ProgressDialog pDialog;
	private SimpleAdapter adapter ;
	
		 
		 private class GetEspecialistas extends AsyncTask<Void, Void, Void> {
			 
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
		            String jsonStr;
		                  sh.addNameValue("especialista", searchTxt);
			            searchTxt = "";
			            jsonStr = sh.postPairs(URL);
		          
		            
		            // Making a request to url and getting response
		           // String jsonStr = sh.makeServiceCall("http://192.168.1.3:80/Citas/usuarios_json.php", ServiceHandler.GET);
		            

		            Log.d("Response: ", "> " + jsonStr);
		 
		            
		            if (jsonStr != null) {
		            	
		                try {
		                	JSONObject json = new JSONObject(jsonStr);
		                	//data = new ArrayList<Cita>();
							JSONArray listaEspecialistas  = json.getJSONArray("emp_info");
							//System.out.println(listaEspecialistas.toString());
							for (int j = 0; j < listaEspecialistas.length(); j++) {
								JSONArray cita = listaEspecialistas.getJSONArray(j);

								String id = cita.get(0).toString();	
								String nombre = cita.get(1).toString();
								String tel = cita.get(2).toString();
								String especialidad = cita.get(5).toString();
														
								
		 
		                        // tmp hashmap for single doctor
		                        HashMap<String, String> doctor = new HashMap<String, String>();
		 
		                        // adding each child node to HashMap key => value
		                        doctor.put("id", id);
		                        doctor.put("nombre", nombre);
		                        doctor.put("tel", tel);
		                        doctor.put("especialidad", especialidad);
		 
		                        // adding doctor to doctor list
		                        especialistasList.add(doctor);
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
		                    getActivity(), especialistasList,
		                    R.layout.fila_cita, new String[] { "id", "nombre",
		                    	"tel","especialidad" }, new int[] { R.id.view_fecha,
		                            R.id.view_hora, R.id.view_doctor,R.id.view_lugar });
		 
		            setListAdapter(adapter);
		        }
		 
		    }
		 
		}