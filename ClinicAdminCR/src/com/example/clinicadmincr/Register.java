package com.example.clinicadmincr;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.example.clinicadmincr.R;
import com.example.clinicadmincr.Doctor.OnRegisterUsuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Register extends Activity implements OnRegisterUsuario {

	// private TextView lblGotoLogin;
	private Button btnRegister;
	private EditText inputFullName;
	private EditText inputEmail;
	private EditText inputPassword;
	private TextView registerErrorMsg;
	private Doctor doctor;

	private Spinner spinner1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registro);

		inputFullName = (EditText) findViewById(R.id.txtUserName);
		inputEmail = (EditText) findViewById(R.id.txtEmail);
		inputPassword = (EditText) findViewById(R.id.txtPass);
		btnRegister = (Button) findViewById(R.id.btnRegister);

		List<String> listsexo = new ArrayList<String>();
		listsexo.add("masculino");
		listsexo.add("femenino");

		spinner1 = (Spinner) this.findViewById(R.id.spinner1);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listsexo);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);

		registerErrorMsg = (TextView) findViewById(R.id.register_error);

		btnRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String username = inputFullName.getText().toString();
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				String sexo = spinner1.getSelectedItem().toString();
				doctor = Doctor.getInstance();
				doctor.setOnRegisterUsuario(Register.this);
				doctor.register(Register.this, username, email, password, sexo);
				// falta registrar doctor

			}

		});
	}

	@Override
	public void onRegisterFinish(JSONObject json, String msg) {
		// TODO Auto-generated method stub
		Intent itemintent = new Intent(this, PrincipalActivity.class);
		Register.this.startActivity(itemintent);

	}

	@Override
	public void onRegisterException(Exception e, String msg) {
		// TODO Auto-generated method stub
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("ERROR");
		alertDialog.setMessage(msg);
		registerErrorMsg.setText(msg);

		// Set the Icon for the Dialog
		alertDialog.show();
	}

	@Override
	public void onRegisterFail(String msg) {
		// TODO Auto-generated method stub
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("ERROR");
		alertDialog.setMessage(msg);
		registerErrorMsg.setText(msg);

		// Set the Icon for the Dialog
		alertDialog.show();
	}
}
