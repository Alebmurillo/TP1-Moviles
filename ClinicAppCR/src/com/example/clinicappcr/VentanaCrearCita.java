package com.example.clinicappcr;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class VentanaCrearCita extends DialogFragment {

	private Spinner mspinnerDoctor,spinnerfechas,spinnerhoras;
	//private EditText mEditStartDate;	
	//private EditText mEditEndtDate;
	//private EditText mEditPlace;
	//private Spinner spinner1, spinner2;
	private String URL="http://192.168.0.189:80/api/v1/test";
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
	}

	public ICitaCreadaListener mListener;

	public void setListener(ICitaCreadaListener listener){
		mListener =listener;
	}

	List<String> listDoctores ;
	List<String> listIdDoctores ;
	ArrayList<String> listFechas ;
	List<String> listHoras ;
	//List<String> listClinicas ;
	//List<String> listIdClinicas ;
	// private TextView tvfechaactual;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View ventanaRoot= inflater.inflate(R.layout.crear_cita, null);
		//mListener;
		Calendar cal = Calendar.getInstance(); 
		// tvfechaactual = (TextView)ventanaRoot.findViewById(R.id.textView1);
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd"); 
	        String formattedDate1 = df1.format(cal.getTime());
	        //tvfechaactual.setText(formattedDate1);
	        listFechas= new ArrayList<String>();
	        listHoras= new ArrayList<String>();
	        listFechas.add(formattedDate1);
	        
	        for (int i =0; i<15;i++){
	            cal.add(Calendar.DATE, 1);
	            String newdate = df1.format(cal.getTime());
	            //System.out.println(newdate);
	            listFechas.add(newdate);	        	
	        }
	        for (int i =8; i<18;i++){
	            StringBuilder Builder = new StringBuilder();
	            Builder.append('0');
	            Builder.append(String.valueOf(i));
	            Builder.append(":00:00");
	            //System.out.println(Builder.toString());
	            listHoras.add(Builder.toString());	        	
	        }
	        
	        listFechas.add(formattedDate1);
	        spinnerhoras = (Spinner) ventanaRoot.findViewById(R.id.spinner3);
		mspinnerDoctor = (Spinner) ventanaRoot.findViewById(R.id.spinner1);
		spinnerfechas = (Spinner) ventanaRoot.findViewById(R.id.spinner2);
		listIdDoctores=getArguments().getStringArrayList("idDoc");
		listDoctores=getArguments().getStringArrayList("nombreDoc");
		//listIdClinicas=getArguments().getStringArrayList("idClinic");
		//listClinicas=getArguments().getStringArrayList("nombreClinic");
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, listDoctores);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mspinnerDoctor.setAdapter(dataAdapter);

		dataAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, listFechas);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerfechas.setAdapter(dataAdapter);
		
		dataAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, listHoras);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerhoras.setAdapter(dataAdapter);

		mspinnerDoctor.setOnItemSelectedListener(new OnItemSelectedListener() {	        
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				//tvfechaactual.setText(spinnerfechas.getSelectedItem().toString());				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	    });
		//mspinnerDoctor = (Spinner) ventanaRoot.findViewById(R.id.spinner1);
		//spinnerfechas = (Spinner) ventanaRoot.findViewById(R.id.spinner2);
		//mEditStartDate = (EditText) ventanaRoot.findViewById(R.id.editStartDate);
		//mEditEndtDate = (EditText) ventanaRoot.findViewById(R.id.edithora);
		//mEditPlace = (EditText) ventanaRoot.findViewById(R.id.editPlace);

		ventanaRoot.findViewById(R.id.btn_guardar).setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {
				//GUARDAR LA CITA			
				new SetCitas().execute();
				
				
			}
		});		

		return ventanaRoot;		
	}

	@Override
	public void onDismiss(DialogInterface dialog){


	}

	private void sendResult(int INT_CODE,String doctor,String place,String startdate,String hora) {


		ArrayList<String> array =new ArrayList<String>();
		array.add(doctor);
		array.add(place);
		array.add(startdate);
		array.add(hora);


		HashMap<String, String> contact = new HashMap<String, String>();

		// adding each child node to HashMap key => value
		contact.put("doctor", array.get(0));
		contact.put("lugar", array.get(1));
		contact.put("fecha", array.get(2));
		contact.put("hora", array.get(3));

		if(mListener != null){
			mListener.citaCreada(contact);
			//System.out.println("LLAMANDO INTERFACE");

		}    


		//Intent i = new Intent();
		//i.putStringArrayListExtra("cita", array);
		//getTargetFragment().onActivityResult(getTargetRequestCode(), INT_CODE, i);
	}


	private ProgressDialog pDialog;
	//private SimpleAdapter adapter ;
	private class SetCitas extends AsyncTask<Void, Void, ArrayList<String>> {

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
		protected ArrayList<String> doInBackground(Void... arg0) {

			int posicion =mspinnerDoctor.getSelectedItemPosition();
			int posicionClinic=spinnerfechas.getSelectedItemPosition();
			ArrayList<String> result = new ArrayList<String>();
			String doctor =listIdDoctores.get(posicion);
			//String place =listIdClinicas.get(posicionClinic);
			String startdate = spinnerfechas.getSelectedItem().toString();
			//final String place =mEditPlace.getText().toString();
			String hora = spinnerhoras.getSelectedItem().toString();

			try {
				httpHandler handler= new httpHandler();

				handler.addNameValue("user", Usuario.getInstance().getUID() );
				handler.addNameValue("doctor", doctor );
				//handler.addNameValue("place", place );
				handler.addNameValue("fecha",startdate );
				handler.addNameValue("hora", hora );

				String jsonStr = handler.postPairs(URL);  
				//System.out.println(jsonStr);
				if (jsonStr != null) {
					try {
						JSONObject json = new JSONObject(jsonStr);
						//System.out.println(json.toString());
						if((Integer.parseInt(json.getString("success")) == 1)){
							JSONArray listaCitas  = json.getJSONArray("emp_info");
							int last =listaCitas.length()-1;

							JSONArray cita = listaCitas.getJSONArray(last);

							String fecha = cita.get(0).toString();
							String hora1 = cita.get(1).toString();
							String doctor1 = cita.get(2).toString();
							String lugar = cita.get(3).toString();	
							
							result.add(doctor1);
							result.add(lugar);
							result.add(fecha);
							result.add(hora1);
						}	else{
							result=null;
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					Log.e("ServiceHandler", "Couldn't get any data from the url");
				}


			} catch (Exception e) {
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(ArrayList<String>  result) {
			super.onPostExecute(result);
			//System.out.print(result);
			if(result!=null){
				System.out.println("***************"+result+"");
				sendResult(0,result.get(0),result.get(1),result.get(2),result.get(3));
				// Dismiss the progress dialog				
				dismiss();

			}else{
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		        builder.setTitle("Error")
		        .setMessage("Horario no disponible")
		        .setCancelable(false)
		        .setNegativeButton("Close",new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		            }
		        });
		        AlertDialog alert = builder.create();
		        alert.show();
		    
			}
			if (pDialog.isShowing())
				pDialog.dismiss();
			
		}

	}

}
