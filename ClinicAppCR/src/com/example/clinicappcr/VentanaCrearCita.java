package com.example.clinicappcr;



import android.app.Activity;
import android.app.DialogFragment;
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
			                	/*List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			                    pairs.add(new BasicNameValuePair("user", "0" ));
			                    pairs.add(new BasicNameValuePair("doctor", doctor));
			                    pairs.add(new BasicNameValuePair("place", place));
			                    pairs.add(new BasicNameValuePair("initDate",startdate ));
			                    pairs.add(new BasicNameValuePair("endDate", enddate));*/
			                	handler.addNameValue("user", "0" );
			                	handler.addNameValue("doctor", doctor );
			                	handler.addNameValue("place", place );
			                	handler.addNameValue("initDate",startdate );
			                	handler.addNameValue("endDate", enddate );

			                    String txt = handler.postPairs(handler.getUrlService());  
			                    System.out.println(txt);
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
		
}
