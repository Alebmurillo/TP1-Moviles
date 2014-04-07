package com.example.clinicappcr;



import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class VentanaCrearCita extends DialogFragment {

	private Spinner mspinnerDoctor;
	private EditText mEditStartDate;	
	private EditText mEditEndtDate;
	private EditText mEditPlace;
	 private Spinner spinner1, spinner2;
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
	}
	
	List<String> listDoctores ;
	List<String> listIdDoctores ;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			
		View ventanaRoot= inflater.inflate(R.layout.crear_cita, null);
		
		spinner2 = (Spinner) ventanaRoot.findViewById(R.id.spinner1);
		listIdDoctores=getArguments().getStringArrayList("idDoc");
		listDoctores=getArguments().getStringArrayList("nombreDoc");
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, listDoctores);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(dataAdapter);
		
		mspinnerDoctor = (Spinner) ventanaRoot.findViewById(R.id.spinner1);
		mEditStartDate = (EditText) ventanaRoot.findViewById(R.id.editStartDate);
		mEditEndtDate = (EditText) ventanaRoot.findViewById(R.id.editEndDate);
		mEditPlace = (EditText) ventanaRoot.findViewById(R.id.editPlace);

		ventanaRoot.findViewById(R.id.btn_guardar).setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {
				//GUARDAR LA CITA
				int posicion =mspinnerDoctor.getSelectedItemPosition();
				final String doctor =listIdDoctores.get(posicion);
				final String startdate = mEditStartDate.getText().toString();
				final String place =mEditPlace.getText().toString();
				final String enddate = mEditEndtDate.getText().toString();
				 Thread thread = new Thread(new Runnable(){
			            @Override
			            public void run() {
			                try {
			                	httpHandler handler= new httpHandler();
			                	
			                	handler.addNameValue("user", "0" );
			                	handler.addNameValue("doctor", doctor );
			                	handler.addNameValue("place", place );
			                	handler.addNameValue("initDate",startdate );
			                	handler.addNameValue("endDate", enddate );

			                    String txt = handler.postPairs("http://192.168.1.3:80/Citas/test.php");  
			                    //System.out.println(txt);
			                    sendResult(0,doctor,place,startdate,enddate);
			                } catch (Exception e) {
			                    e.printStackTrace();
			                }
			            }
			        });

			        thread.start(); 		       
					dismiss();
				
			}

			
		});		
		
		return ventanaRoot;		
	}
	
	@Override
	public void onDismiss(DialogInterface dialog){
		
		
	}
	
	private void sendResult(int INT_CODE,String doctor,String place,String startdate,String enddate) {
	    Intent i = new Intent();
	    ArrayList<String> array =new ArrayList<String>();
	    array.add(doctor);
	    array.add(place);
	    array.add(startdate);
	    array.add(enddate);
	    
	    i.putStringArrayListExtra("cita", array);
	    getTargetFragment().onActivityResult(getTargetRequestCode(), INT_CODE, i);
	}
		
}
