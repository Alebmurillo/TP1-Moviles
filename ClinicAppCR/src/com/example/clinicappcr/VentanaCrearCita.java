package com.example.clinicappcr;



import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

public class VentanaCrearCita extends DialogFragment {

	private EditText mEditDoctor;
	private EditText mEditStartDate;	
	private EditText mEditEndtDate;
	private EditText mEditPlace;

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View ventanaRoot= inflater.inflate(R.layout.crear_cita, null);		
		mEditDoctor = (EditText) ventanaRoot.findViewById(R.id.editDoctor);
		mEditStartDate = (EditText) ventanaRoot.findViewById(R.id.editStartDate);
		mEditEndtDate = (EditText) ventanaRoot.findViewById(R.id.editEndDate);
		mEditPlace = (EditText) ventanaRoot.findViewById(R.id.editPlace);

		ventanaRoot.findViewById(R.id.btn_guardar).setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				//GUARDAR LA CITA
				final String doctor =mEditDoctor.getText().toString();
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

			                    String txt = handler.postPairs(handler.getUrl());  
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
