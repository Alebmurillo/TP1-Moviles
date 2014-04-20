package com.example.clinicappcr;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.clinicappcr.Usuario.OnLoginUsuario;


public class Login extends Activity implements OnLoginUsuario {

	private TextView lblGotoRegister;
	private Button btnLogin;
	private EditText inputEmail;
	private EditText inputPassword;
	private TextView loginErrorMsg;
	private Usuario usuario;

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
				
				usuario = Usuario.getInstance();
				usuario.setOnLoginUsuario(Login.this);
				usuario.login(Login.this, email, password);
				//falta comprobar usuario

				
			
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


	@Override
	public void onLoginCorrect(JSONObject json, String msg) {
		
		
		// TODO Auto-generated method stub
		Intent itemintent = new Intent(this, PrincipalActivity.class);
		//Intent itemintent = new Intent(view.getContext(), MapActivity.class);
 
        // 3. or you can add data to a bundle
        Bundle extras = new Bundle();
        extras.putString("nombre", usuario.getName());
        extras.putString("email", usuario.getEmail());
        extras.putString("apikey",usuario.getUID());
 
        // 4. add bundle to intent
        itemintent.putExtras(extras);
		Login.this.startActivity(itemintent);
		
	}


	@Override
	public void onLoginWrong(String msg) {
		// TODO Auto-generated method stub
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("ERROR");
		alertDialog.setMessage(msg);
		loginErrorMsg.setText(msg);
		
		// Set the Icon for the Dialog
		alertDialog.show();
		
	}

}