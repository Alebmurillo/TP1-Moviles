package com.example.clinicappcr;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends Activity {

	private TextView lblGotoLogin;
	private Button btnRegister;
	private EditText inputFullName;
	private EditText inputEmail;
	private EditText inputPassword;
	private TextView registerErrorMsg;    

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registro);        

		inputFullName = (EditText) findViewById(R.id.txtUserName);
		inputEmail = (EditText) findViewById(R.id.txtEmail);
		inputPassword = (EditText) findViewById(R.id.txtPass);
		btnRegister = (Button) findViewById(R.id.btnRegister);

		registerErrorMsg = (TextView) findViewById(R.id.register_error);

		btnRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String name = inputFullName.getText().toString();
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				Usuario usuario = new Usuario();
				//falta registrar usuario
				Intent itemintent = new Intent(view.getContext(), PrincipalActivity.class);
				Register.this.startActivity(itemintent);
			}

			});
	}
}




		