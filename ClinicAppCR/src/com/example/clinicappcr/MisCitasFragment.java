package com.example.clinicappcr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.clinicappcr.httpHandler.OnExecuteHttpPostAsyncListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MisCitasFragment extends ListFragment implements OnDeleteCita,
		ICitaCreadaListener {

	private String URLcitas ;
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
		URLcitas=getString(R.string.IPserver) +"/api/v1/getCitas";
		URLdeleteCitas=getString(R.string.IPserver) +"/api/v1/eliminarCita";
		URLdoctores=getString(R.string.IPserver) +"/api/v1/getDoctores";
		URLclinicas=getString(R.string.IPserver) +"/api/v1/clinicas_json";
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
		new GetInfo().execute();

		btCrear = (Button) view.findViewById(R.id.button_crear);
		btCrear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentManager fm = getFragmentManager();
				VentanaCrearCita fragment = new VentanaCrearCita();
				fragment.setListener(MisCitasFragment.this);
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
			sh.addNameValue("usuario", Usuario.getInstance().getUID());
			String jsonStr = sh.postPairs(URLcitas);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				System.out.println(jsonStr.toString());
				listIdCitas = new ArrayList<String>();
				try {
					JSONObject json = new JSONObject(jsonStr);
					System.out.println("CITAS= "+json.toString());

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
			setOnDeleteCita(MisCitasFragment.this);
			ListView lv = getListView();
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					posicionActual = position;
					//Intent itemintent = new Intent(getActivity(),	BasicMapDemoActivity.class);

					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
							builder.setTitle("Cita")
							.setMessage("Cita a las "+contactList.get(position).get("hora")+
									" el "+contactList.get(position).get("fecha")+
									" con el doctor "+contactList.get(position).get("doctor")+"")
							.setCancelable(false)
							.setPositiveButton("Eliminar Cita",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// eliminar
											ListenerCitaEliminada
													.onConfirmDelete();
											dialog.cancel();

										}
									})
							.setNegativeButton("Cancelar",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.cancel();
										}
									});
					AlertDialog alert = builder.create();
					alert.show();

				}
			});
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
						.postPairs(URLdoctores);
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
						.post(URLclinicas);
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
		listIdCitas.add(contact.get("id"));
		// TODO Auto-generated method stub
		//System.out.println("NOTIFICANDO");
		((BaseAdapter) getListAdapter()).notifyDataSetChanged();

	}

	protected void eliminarCita(Activity activity, String idCita) {
		this.activity = activity;
		httpHandler httpclient = new httpHandler(activity);
		httpclient.addNameValue("user", Usuario.getInstance().getUID());
		httpclient.addNameValue("id", idCita);
		System.out.println("ESTA ES LA ID=   "+idCita);
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
									ListenerCitaEliminada
											.onDeleteCorrect(json,"Se elimino Correctamente la Cita");
								} else {
									// no se elimnno
									ListenerCitaEliminada
									.onDeleteWrong(json.getString("message"));
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

	@Override
	public void onDeleteCorrect(JSONObject json, String msg) {
		// TODO Auto-generated method stub
		ListView lv = getListView();
		contactList.remove(posicionActual);
		listIdCitas.remove(posicionActual);

		((BaseAdapter) getListAdapter()).notifyDataSetChanged();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Exito")
        .setMessage(msg)
        .setCancelable(false)
        .setNegativeButton("Close",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    

	}
	private int posicionActual=0;

	@Override
	public void onDeleteWrong(String msg) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Error")
        .setMessage(msg)
        .setCancelable(false)
        .setNegativeButton("Close",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    

	}

	@Override
	public void onConfirmDelete() {
		// TODO Auto-generated method stub
		System.out.println("POSICION ACTUAL=  " + posicionActual);
		MisCitasFragment.this.eliminarCita(getActivity(),
				listIdCitas.get(posicionActual));

	}

}
