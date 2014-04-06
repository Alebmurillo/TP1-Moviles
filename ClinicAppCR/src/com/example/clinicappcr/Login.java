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


public class Login extends Activity {

	private TextView lblGotoRegister;
	private Button btnLogin;
	private EditText inputEmail;
	private EditText inputPassword;
	private TextView loginErrorMsg;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		inputEmail = (EditText) findViewById(R.id.txtEmail);
		inputPassword = (EditText) findViewById(R.id.txtPass);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);

		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();

				Usuario usuario = new Usuario();
				//falta comprobar usuario

				Intent itemintent = new Intent(view.getContext(), PrincipalActivity.class);
				Login.this.startActivity(itemintent);
			
			}
		});

		lblGotoRegister = (TextView) findViewById(R.id.link_to_register);
		lblGotoRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent itemintent = new Intent(v.getContext(), Register.class);
				Login.this.startActivity(itemintent);
			}
		});
	}

}