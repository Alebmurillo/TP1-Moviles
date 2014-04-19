package com.example.clinicadmincr;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import com.example.clinicadmincr.R;

public class MisCitasFragment extends ListFragment implements
		ICitaCreadaListener {

	private String URL = "http://192.168.0.189:80/api/v1/citas";
	String usuarioActual = "";
	Button btCrear;
	static final int DIALOG_CONFIRM = 0;
	protected static final int REQUEST_CODE = 10;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> contactList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contactList = new ArrayList<HashMap<String, String>>();
		// Calling async task to get json

	}

	@Override
	public void onResume() {
		super.onResume();
		contactList = new ArrayList<HashMap<String, String>>();
		// Calling async task to get json
		new GetCitas().execute();

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) { // make sure fragment codes match up {

			ArrayList<String> array = data.getStringArrayListExtra("cita");
			HashMap<String, String> contact = new HashMap<String, String>();

			// adding each child node to HashMap key => value
			contact.put("doctor", array.get(0));
			contact.put("lugar", array.get(1));
			contact.put("fecha", array.get(2));
			contact.put("hora", array.get(3));
			contactList.add(contact);
			System.out.println("YA NOTIFIQUE");
			// adapter.notifyDataSetChanged();
			System.out.println("ok");
		}
	}

	ArrayList<String> listaNombresDoc;
	ArrayList<String> listaId;
	ArrayList<String> listaClinicas;
	ArrayList<String> listaIdClinicas;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_citas, null);
		// final FragmentManager fm = getFragmentManager();
		// final VentanaCrearCita fragment = new VentanaCrearCita();
		// fragment.setTargetFragment(this, 0);

		//
		new GetInfo().execute();

		btCrear = (Button) view.findViewById(R.id.button_crear);
		btCrear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentManager fm = getFragmentManager();
				VentanaCrearCita fragment = new VentanaCrearCita();
				fragment.setListener(MisCitasFragment.this);
				// System.out.println(getFragmentManager().findFragmentByTag("misCitas"));
				fragment.setTargetFragment(getFragmentManager()
						.findFragmentByTag("misCitas"), 0);

				System.out.println("Listo");
				Bundle args = new Bundle();
				args.putStringArrayList("idDoc", listaId);
				args.putStringArrayList("nombreDoc", listaNombresDoc);
				args.putStringArrayList("idClinic", listaIdClinicas);
				args.putStringArrayList("nombreClinic", listaClinicas);
				fragment.setArguments(args);
				fragment.show(fm, "crear");
			}
		});

		return view;
	}

	private ProgressDialog pDialog;
	private SimpleAdapter adapter;

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
			httpHandler sh = new httpHandler();
			// Making a request to url and getting response
			sh.addNameValue("usuario", Doctor.getInstance().getUID());
			String jsonStr = sh.postPairs(URL);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				System.out.println(jsonStr.toString());

				try {
					JSONObject json = new JSONObject(jsonStr);
					System.out.println(json.toString());

					JSONArray listaCitas = json.getJSONArray("emp_info");
					for (int j = 0; j < listaCitas.length(); j++) {
						JSONArray cita = listaCitas.getJSONArray(j);

						String fecha = cita.get(0).toString();
						String hora = cita.get(1).toString();
						String doctor = cita.get(2).toString();
						String lugar = cita.get(3).toString();

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

			adapter = new SimpleAdapter(getActivity(), contactList,
					R.layout.fila_cita, new String[] { "fecha", "hora",
							"doctor", "lugar" }, new int[] { R.id.view_fecha,
							R.id.view_hora, R.id.view_doctor, R.id.view_lugar });

			setListAdapter(adapter);
		}

	}

	private class GetInfo extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				httpHandler sh = new httpHandler();
				sh.addNameValue("especialista", "");
				String jsonStr = sh
						.postPairs("http://192.168.0.189:80/Citas/doctores_json.php");
				Log.d("Response: ", "> " + jsonStr);

				if (jsonStr != null) {
					listaNombresDoc = new ArrayList<String>();
					listaId = new ArrayList<String>();

					try {
						JSONObject json = new JSONObject(jsonStr);
						JSONArray listaDoctores = json.getJSONArray("emp_info");
						for (int j = 0; j < listaDoctores.length(); j++) {
							JSONArray doctor = listaDoctores.getJSONArray(j);

							String id = doctor.get(0).toString();
							String nombre = doctor.get(1).toString();

							listaNombresDoc.add(nombre);
							listaId.add(id);

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					Log.e("ServiceHandler",
							"Couldn't get any data from the url");
				}

				jsonStr = sh
						.post("http://192.168.0.189:80/Citas/clinicas_json.php");
				if (jsonStr != null) {
					// System.out.println(jsonStr);
					listaClinicas = new ArrayList<String>();
					listaIdClinicas = new ArrayList<String>();
					try {
						JSONObject json = new JSONObject(jsonStr);
						JSONArray listaDoctores = json.getJSONArray("emp_info");
						for (int j = 0; j < listaDoctores.length(); j++) {
							JSONArray doctor = listaDoctores.getJSONArray(j);

							String idclinic = doctor.get(0).toString();
							String nombreClinic = doctor.get(1).toString();
							listaClinicas.add(nombreClinic);
							;
							listaIdClinicas.add(idclinic);
							;

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					Log.e("ServiceHandler",
							"Couldn't get any data from the url");
				}

				// System.out.println(txt);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			System.out.println("POST");
			// Dismiss the progress dialog

			/**
			 * Updating parsed JSON data into ListView
			 * */

		}

	}

	@Override
	public void citaCreada(HashMap<String, String> contact) {
		contactList.add(contact);
		// TODO Auto-generated method stub
		System.out.println("NOTIFICANDO");
		((BaseAdapter) getListAdapter()).notifyDataSetChanged();

	}

}
