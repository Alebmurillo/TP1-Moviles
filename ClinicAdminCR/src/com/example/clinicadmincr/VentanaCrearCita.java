package com.example.clinicadmincr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.clinicadmincr.R;

public class VentanaCrearCita extends DialogFragment {

	private Spinner mspinnerDoctor, mspinnerClinica;
	private EditText mEditStartDate;
	private EditText mEditEndtDate;
	private String URL = "http://192.168.0.189:80/Citas/test.php";

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	public ICitaCreadaListener mListener;

	public void setListener(ICitaCreadaListener listener) {
		mListener = listener;
	}

	List<String> listDoctores;
	List<String> listIdDoctores;
	List<String> listClinicas;
	List<String> listIdClinicas;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View ventanaRoot = inflater.inflate(R.layout.crear_cita, null);
		// mListener;

		mspinnerDoctor = (Spinner) ventanaRoot.findViewById(R.id.spinner1);
		mspinnerClinica = (Spinner) ventanaRoot.findViewById(R.id.spinner2);
		listIdDoctores = getArguments().getStringArrayList("idDoc");
		listDoctores = getArguments().getStringArrayList("nombreDoc");
		listIdClinicas = getArguments().getStringArrayList("idClinic");
		listClinicas = getArguments().getStringArrayList("nombreClinic");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				listDoctores);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mspinnerDoctor.setAdapter(dataAdapter);

		dataAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, listClinicas);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mspinnerClinica.setAdapter(dataAdapter);

		// mspinnerDoctor = (Spinner) ventanaRoot.findViewById(R.id.spinner1);
		// mspinnerClinica = (Spinner) ventanaRoot.findViewById(R.id.spinner2);
		mEditStartDate = (EditText) ventanaRoot
				.findViewById(R.id.editStartDate);
		mEditEndtDate = (EditText) ventanaRoot.findViewById(R.id.editEndDate);
		// mEditPlace = (EditText) ventanaRoot.findViewById(R.id.editPlace);

		ventanaRoot.findViewById(R.id.btn_guardar).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// GUARDAR LA CITA

						new SetCitas().execute();
						dismiss();

					}

				});

		return ventanaRoot;
	}

	@Override
	public void onDismiss(DialogInterface dialog) {

	}

	private void sendResult(int INT_CODE, String doctor, String place,
			String startdate, String enddate) {

		ArrayList<String> array = new ArrayList<String>();
		array.add(doctor);
		array.add(place);
		array.add(startdate);
		array.add(enddate);

		HashMap<String, String> contact = new HashMap<String, String>();

		// adding each child node to HashMap key => value
		contact.put("doctor", array.get(0));
		contact.put("lugar", array.get(1));
		contact.put("fecha", array.get(2));
		contact.put("hora", array.get(3));

		if (mListener != null) {
			mListener.citaCreada(contact);
			System.out.println("LLAMANDO INTERFACE");

		}

		// Intent i = new Intent();
		// i.putStringArrayListExtra("cita", array);
		// getTargetFragment().onActivityResult(getTargetRequestCode(),
		// INT_CODE, i);
	}

	private ProgressDialog pDialog;

	// private SimpleAdapter adapter ;
	private class SetCitas extends AsyncTask<Void, Void, ArrayList<String>> {

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
		protected ArrayList<String> doInBackground(Void... arg0) {

			int posicion = mspinnerDoctor.getSelectedItemPosition();
			int posicionClinic = mspinnerClinica.getSelectedItemPosition();
			ArrayList<String> result = new ArrayList<String>();
			String doctor = listIdDoctores.get(posicion);
			String place = listIdClinicas.get(posicionClinic);
			String startdate = mEditStartDate.getText().toString();
			// final String place =mEditPlace.getText().toString();
			String enddate = mEditEndtDate.getText().toString();

			try {
				httpHandler handler = new httpHandler();

				handler.addNameValue("user", "1");
				handler.addNameValue("doctor", doctor);
				handler.addNameValue("place", place);
				handler.addNameValue("initDate", startdate);
				handler.addNameValue("endDate", enddate);

				String jsonStr = handler.postPairs(URL);
				// System.out.println(txt);
				if (jsonStr != null) {

					try {
						JSONObject json = new JSONObject(jsonStr);
						System.out.println(json.toString());

						JSONArray listaCitas = json.getJSONArray("emp_info");
						int last = listaCitas.length() - 1;

						JSONArray cita = listaCitas.getJSONArray(last);

						String fecha = cita.get(0).toString();
						String hora = cita.get(1).toString();
						String doctor1 = cita.get(2).toString();
						String lugar = cita.get(3).toString();

						result.add(doctor1);
						result.add(lugar);
						result.add(fecha);
						result.add(hora);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					Log.e("ServiceHandler",
							"Couldn't get any data from the url");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);

			sendResult(0, result.get(0), result.get(1), result.get(2),
					result.get(3));
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
		}

	}

}
