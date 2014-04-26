package com.example.clinicadmincr;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clinicadmincr.R;


public class PrincipalActivity extends FragmentActivity {	  
	MainLayout mLayout;  
	private ListView lvMenu;  
	private String[] lvMenuItems;  
	private String URLClinicas="http://192.168.0.189:80/api/v1/clinicas_json";
	Button btMenu;  
	TextView tvTitle;  
	ArrayList<String> listaClinicas,listaId ;


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//
		int id = item.getItemId();
		/*if (id == R.id.action_crear) {
			VentanaCrearCita fragment = new VentanaCrearCita();
			fragment.show(getFragmentManager(), "crear");
			return true;
		}*/
		if (id == R.id.action_menu) {
			toggleMenu(this.getCurrentFocus());  
			return true;

		}
		return super.onOptionsItemSelected(item); 
	}


	@Override  
	protected void onCreate(Bundle savedInstanceState) { 		 

		super.onCreate(savedInstanceState); 	 

		mLayout = (MainLayout) this.getLayoutInflater().inflate(  
				R.layout.activity_principal, null);  
		setContentView(mLayout);  

		// 1. get passed intent 
        //Intent intent = getIntent(); 		
		//Bundle bundle = intent.getExtras();		 
        // 5. get status value from bundle
        //String status = bundle.getString("status");
		
		lvMenuItems = getResources().getStringArray(R.array.menu_items);  
		lvMenu = (ListView) findViewById(R.id.menu_listview);  
		lvMenu.setAdapter(new ArrayAdapter<String>(this,  
				android.R.layout.simple_list_item_1, lvMenuItems));  
		lvMenu.setOnItemClickListener(new OnItemClickListener() {  
			@Override  
			public void onItemClick(AdapterView<?> parent, View view,  
					int position, long id) {  
				onMenuItemClick(parent, view, position, id);  
			}  

		});  
		
		new GetClinincas().execute();


		/*btMenu = (Button) findViewById(R.id.button_crear);  
		btMenu.setOnClickListener(new OnClickListener() {  
			@Override  
			public void onClick(View v) {  

				VentanaCrearCita fragment = new VentanaCrearCita();
				fragment.show(getFragmentManager(), "crear");

			}  
		});  */

		tvTitle = (TextView) findViewById(R.id.activity_main_content_title);
		FragmentManager fm = getFragmentManager();  
		FragmentTransaction ft = fm.beginTransaction();  
		MisCitasFragment fragment = new MisCitasFragment();  
		ft.add(R.id.activity_main_content_fragment, fragment,"misCitas");  
		ft.commit();    	

	}  

	@Override  
	public boolean onCreateOptionsMenu(Menu menu) {  
		getMenuInflater().inflate(R.menu.main, menu);  
		return true;  
	}  

	public void toggleMenu(View v) {  
		mLayout.toggleMenu();  
	}  

	private void onMenuItemClick(AdapterView<?> parent, View view,  
			int position, long id) {  
		String selectedItem = lvMenuItems[position];  
		String currentItem = tvTitle.getText().toString();  
		if (selectedItem.compareTo(currentItem) == 0) {  
			mLayout.toggleMenu();  
			return;  
		}  

		FragmentManager fm = getFragmentManager();  
		FragmentTransaction ft = fm.beginTransaction();  
		Fragment fragment = null;  


		if (selectedItem.compareTo("Doctor Citas") == 0) {  
			fragment = new MisCitasFragment();  
		}/* else if (selectedItem.compareTo("Consultorios") == 0) {  
			fragment = new ConsultoriosFragment();  
			//aqui
			Bundle args = new Bundle();
			args.putStringArrayList("id", listaId);
			args.putStringArrayList("nombreClinica", listaClinicas);
			
			fragment.setArguments(args);
		}  
		else if (selectedItem.compareTo("Especialistas") == 0) {  
			fragment = new EspecialistasFragment(); 		

		}  */

		if (fragment != null) {  
			ft.replace(R.id.activity_main_content_fragment, fragment,"misCitas");  
			ft.commit();  
			tvTitle.setText(selectedItem);  
		}  
		mLayout.toggleMenu();  
	}  

	@Override  
	public void onBackPressed() {  
		if (mLayout.isMenuShown()) {  
			mLayout.toggleMenu();  
		} else {  
			super.onBackPressed();  
		}  
	}  
	
	
	
	
	private class GetClinincas extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				httpHandler sh= new httpHandler(); 
				String jsonStr = sh.post(URLClinicas);
				Log.d("Response: ", "> " + jsonStr);		 

				if (jsonStr != null) {
					System.out.println(jsonStr);

					listaClinicas= new ArrayList<String>();
					listaId= new ArrayList<String>();
					listaClinicas.add("Todos");
					listaId.add("");
					try {
						JSONObject json = new JSONObject(jsonStr);
						JSONArray jsonEspecialidades  = json.getJSONArray("emp_info");
						for (int j = 0; j < jsonEspecialidades.length(); j++) {
							JSONArray clinica = jsonEspecialidades.getJSONArray(j);


							String id = clinica.get(0).toString();
							String nombre = clinica.get(1).toString();

							listaClinicas.add(nombre);
							listaId.add(id);
							//System.out.println("" +nombre+ " "+ id+"");

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
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			
			/**
			 * Updating parsed JSON data into ListView
			 * */


		}

	}
	
	
}  