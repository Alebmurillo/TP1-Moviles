package com.example.clinicappcr;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MisCitasFragment extends ListFragment  {
	
	Button btMenu; 
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setContentView(R.layout.activity_post_list);
		
		if (savedInstanceState == null){
	
			data = new ArrayList<Cita>();
	    	 
	    	      data.add(new Cita("1","19/09/2012", "PC" , "false"));
	    	      data.add(new Cita("2","23/09/2012", "parámetros" , "false"));
	    	      data.add(new Cita("3","30/09/2012", "Autenticación de Dos Factores ahora mismo" , "false"));
	    	      data.add(new Cita("4","07/10/2012", "imagen" , "false"));
	    	      data.add(new Cita("5","21/10/2012", "Comandos" , "false"));
	    	      data.add(new Cita("6","28/10/2012", "Enlaces" , "false"));
	    	      data.add(new Cita("7","04/11/2012", "Nueva" , "false"));
	    	      data.add(new Cita("8","11/11/2012", "Personalizar" , "false"));
	    	      data.add(new Cita("9","18/11/2012", "Humor" , "false"));
	    	      data.add(new Cita("10","25/11/2012", "Bastao" , "false"));
			adapter = new CitasAdapter(getActivity(), data);
		} else{
			data = savedInstanceState.getParcelableArrayList("savedData");
			adapter = new CitasAdapter(getActivity(), data);
		}
			setListAdapter(adapter);
		
	}
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {      
    	
        View view = inflater.inflate(R.layout.fragment_citas, null); 
        
       /* data = new ArrayList<Cita>();
   	 
	      data.add(new Cita("","19/09/2012", "PC" , "false"));
	      data.add(new Cita("","23/09/2012", "parámetros" , "false"));
	      data.add(new Cita("","30/09/2012", "Autenticación de Dos Factores ahora mismo" , "false"));
	      data.add(new Cita("","07/10/2012", "imagen" , "false"));
	      data.add(new Cita("","21/10/2012", "Comandos" , "false"));
	      data.add(new Cita("","28/10/2012", "Enlaces" , "false"));
	      data.add(new Cita("","04/11/2012", "Nueva" , "false"));
	      data.add(new Cita("","11/11/2012", "Personalizar" , "false"));
	      data.add(new Cita("","18/11/2012", "Humor" , "false"));
	      data.add(new Cita("","25/11/2012", "Bastao" , "false"));
	 
        adapter = new CitasAdapter(getActivity(), data);
        //ListView a=(ListView) view.findViewById(R.id.list);
        //ListView a=getListView();
	    this.setListAdapter(adapter);   	
	    
	    */
	    
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
		
        
       /* FragmentManager m = getFragmentManager();
		FragmentTransaction trans =m.beginTransaction();
		trans.add(R.id.container, new ListaCitasFragment(),"lista");
		trans.commit();*/
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					httpHandler handler= new httpHandler();		                
					//con la ip se sabe que es lo que se solicita
					String txt = handler.post("http://192.168.1.3:80/Citas/usuarios_json.php");
					JSONObject json = new JSONObject(txt);

					//TextView textview = (TextView) view.findViewById(R.id.TextViewTestInfo);
					//textview.setText(info.toString());

					try {
						JSONArray info  = json.getJSONArray("emp_info");
						//System.out.println(info.toString());
						//textview.setText(info.toString());
					} catch (JSONException e) {
						//System.out.println("JSON Parser"+ "Error parsing data " + e.toString());
						//textview.setText("JSON Parser"+ "Error parsing data " + e.toString());
					}
					
					/*for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        textview.setText(jsonObject.getString("doctor"));
                    }*/
					//JSONObject json = new JSONObject(txt);

					//System.out.println(txt);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start(); 
		
		
		
		
		return view;  
	}  
    
//    
//    
//	
//	@Override
//	public void onAttach(Activity activity){
//		super.onAttach(activity);
//		mContext = (Context) activity.getApplicationContext();
//	}
//	
//	@Override
//	public void onResume(){
//		super.onResume();
//		Log.e("on Resume","On resume");
//	}
//	
//	
	static final int DIALOG_CONFIRM = 0;
	protected static final int REQUEST_CODE = 10;

	private CitasAdapter adapter;
	private ArrayList<Cita> data;
	
	
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("savedData", data);
		super.onSaveInstanceState(outState);
	}

}  