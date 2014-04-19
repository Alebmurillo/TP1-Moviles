package com.example.clinicappcr;



import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PrincipalActivity extends FragmentActivity {	  
	MainLayout mLayout;  
	private ListView lvMenu;  
	private String[] lvMenuItems;  
	Button btMenu;  
	TextView tvTitle;  

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


		if (selectedItem.compareTo("Mis Citas") == 0) {  
			fragment = new MisCitasFragment();  
		} else if (selectedItem.compareTo("Clinicas") == 0) {  
			fragment = new ClinicasFragment();  
		}  
		else if (selectedItem.compareTo("Especialistas") == 0) {  
			fragment = new EspecialistasFragment();  

		}  

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
}  