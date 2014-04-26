package com.example.clinicadmincr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.example.clinicadmincr.httpHandler.OnExecuteHttpPostAsyncListener;

public class MisCitasFragment extends ListFragment {

	private String URLcitas;
	private String URLdeleteCitas;
	private String URLdoctores;
	private String URLclinicas;
	String usuarioActual = "";
	Button btCrear;
	static final int DIALOG_CONFIRM = 0;
	protected static final int REQUEST_CODE = 10;
	Activity activity;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> contactList;
	private List<String> listIdCitas;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		contactList = new ArrayList<HashMap<String, String>>();
		URLcitas = getString(R.string.IPserver) + "/api/v1/getCitasDoctor";
		URLdeleteCitas = getString(R.string.IPserver) + "/api/v1/eliminarCita";
		URLdoctores = getString(R.string.IPserver) + "/api/v1/getDoctores";
		URLclinicas = getString(R.string.IPserver) + "/api/v1/clinicas_json";
		// Calling async task to get json

	}

	@Override
	public void onResume() {
		super.onResume();
		contactList = new ArrayList<HashMap<String, String>>();
		// Calling async task to get json
		new GetCitas().execute();

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
			String jsonStr = sh.postPairs(URLcitas);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				System.out.println(jsonStr.toString());
				listIdCitas = new ArrayList<String>();
				try {
					JSONObject json = new JSONObject(jsonStr);
					System.out.println("CITAS= " + json.toString());

					JSONArray listaCitas = json.getJSONArray("emp_info");
					for (int j = 0; j < listaCitas.length(); j++) {
						JSONArray cita = listaCitas.getJSONArray(j);
						String idcita = cita.get(0).toString();
						String fecha = cita.get(1).toString();
						String hora = cita.get(2).toString();
						String doctor = cita.get(3).toString();
						String lugar = cita.get(4).toString();

						// tmp hashmap for single contact
						HashMap<String, String> contact = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						contact.put("fecha", fecha);
						contact.put("hora", hora);
						contact.put("doctor", doctor);
						contact.put("lugar", lugar);
						listIdCitas.add(idcita);

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

	
	

	protected void eliminarCita(Activity activity, String idCita) {
		this.activity = activity;
		httpHandler httpclient = new httpHandler(activity);
		httpclient.addNameValue("user", Doctor.getInstance().getUID());
		httpclient.addNameValue("id", idCita);
		System.out.println("ESTA ES LA ID=   " + idCita);
		// httpclient.addNameValue("hora", hora);
		httpclient
				.setOnExecuteHttpPostAsyncListener(new OnExecuteHttpPostAsyncListener() {
					@Override
					public void onExecuteHttpPostAsyncListener(
							String ResponseBody) {
						try {
							System.out.println(ResponseBody);
							JSONObject json = new JSONObject(ResponseBody);
							if (json.getString("success") != null) {
								if ((Integer.parseInt(json.getString("success")) == 1)) {
									// se elimino correctamente
									ListenerCitaEliminada.onDeleteCorrect(json,
											"Se elimino Correctamente la Cita");
								} else {
									// no se elimnno
									ListenerCitaEliminada.onDeleteWrong(json
											.getString("message"));
								}
							} else {
								ListenerCitaEliminada
										.onDeleteWrong("error al eliminar");
							}
						} catch (JSONException e) {
							ListenerCitaEliminada
									.onDeleteWrong("Error de conexion");
						}
					}

					@Override
					public void onErrorHttpPostAsyncListener(String message) {
					}
				});

		httpclient.executeHttpPost(URLdeleteCitas);
	}

	private OnDeleteCita ListenerCitaEliminada;

	public void setOnDeleteCita(OnDeleteCita l) {
		ListenerCitaEliminada = l;
	}

	
	

}
