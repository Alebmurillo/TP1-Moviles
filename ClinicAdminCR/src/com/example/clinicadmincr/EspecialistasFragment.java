package com.example.clinicadmincr;

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
import com.example.clinicadmincr.R;

public class EspecialistasFragment extends ListFragment {

	Button btBuscar;
	EditText eSearch;
	ArrayList<HashMap<String, String>> especialistasList;
	String searchTxt;
	private String URL = "http://192.168.0.189:80/Citas/doctores_json.php";

	// private String URL="http://192.168.0.189:80/api/v1/doctores_jason";

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
			// Busqueda de especialidades
			@Override
			public void onClick(View v) {

				searchTxt = eSearch.getText().toString();
				especialistasList = new ArrayList<HashMap<String, String>>();
				new GetEspecialistas().execute();
			}
		});
		return view;
	}

	private ProgressDialog pDialog;
	private SimpleAdapter adapter;

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
			httpHandler sh = new httpHandler();
			String jsonStr;
			sh.addNameValue("especialista", searchTxt);
			searchTxt = "";
			jsonStr = sh.postPairs(URL);
			Log.d("Response: ", "> " + jsonStr);
			if (jsonStr != null) {

				try {
					JSONObject json = new JSONObject(jsonStr);
					JSONArray listaEspecialistas = json
							.getJSONArray("emp_info");
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
			adapter = new SimpleAdapter(getActivity(), especialistasList,
					R.layout.fila_especialista, new String[] { "nombre",
							"especialidad", "tel" }, new int[] {
							R.id.view_nDoctor, R.id.view_especialidad,
							R.id.view_tel });
			setListAdapter(adapter);
		}

	}

}