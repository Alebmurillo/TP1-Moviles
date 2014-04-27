package com.example.clinicappcr;

import java.util.Arrays;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.clinicappcr.Usuario.OnLoginFacebook;
import com.example.clinicappcr.Usuario.OnLoginUsuario;
import com.example.clinicappcr.Usuario.OnRegisterUsuario;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class Login extends Activity implements OnLoginUsuario, OnLoginFacebook,
		OnRegisterUsuario {

	private TextView lblGotoRegister;
	private Button btnLogin;
	private LoginButton btnFLogin;

	private EditText inputEmail;
	private EditText inputPassword;
	private TextView loginErrorMsg;
	private Usuario usuario;
	private GraphUser user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		inputEmail = (EditText) findViewById(R.id.txtEmail);
		inputPassword = (EditText) findViewById(R.id.txtPass);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnFLogin = (LoginButton) findViewById(R.id.flogin);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);

		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();

				usuario = Usuario.getInstance();
				usuario.setContext(getApplication().getApplicationContext());
				usuario.setURL();
				usuario.setOnLoginUsuario(Login.this);
				usuario.login(Login.this, email, password);

			}
		});

		Session session = Session.getActiveSession();
		if (session != null) {
			session.close();
		}
		btnFLogin.setReadPermissions(Arrays.asList("email"));
		btnFLogin
				.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
					@Override
					public void onUserInfoFetched(GraphUser user) {
						Login.this.user = user;

						Session session = Session.getActiveSession();
						boolean enableButtons = (session != null && session
								.isOpened());

						if (enableButtons && user != null) {
							
							String id = user.getId().toString();
							String email = user.asMap().get("email").toString();
							usuario = Usuario.getInstance();
							usuario.setContext(getApplication().getApplicationContext());
							usuario.setURL();
							usuario.setOnRegisterFacebook(Login.this);
							usuario.loginFacebook(Login.this, email, id);

						} else {

						}
						
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}
	

	@Override
	public void onLoginCorrect(JSONObject json, String msg) {

		// TODO Auto-generated method stub
		Intent itemintent = new Intent(this, PrincipalActivity.class);	
		Bundle extras = new Bundle();
		extras.putString("nombre", usuario.getName());
		extras.putString("email", usuario.getEmail());
		extras.putString("apikey", usuario.getUID());
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
		alertDialog.show();

	}

	@Override
	public void onLoginFacebookFinish(JSONObject json, String msg) {
		Intent itemintent = new Intent(this, PrincipalActivity.class);
		
		Bundle extras = new Bundle();
		extras.putString("nombre", usuario.getName());
		extras.putString("email", usuario.getEmail());
		extras.putString("apikey", usuario.getUID());

		itemintent.putExtras(extras);
		Login.this.startActivity(itemintent);

	}

	@Override
	public void onLoginFacebookException(Exception e, String msg) {
		// TODO Auto-generated method stub
		loginErrorMsg.setText(msg);

	}

	@Override
	public void onLoginFacebookFail(String msg) {
		// TODO Auto-generated method stub
		String nombre = user.getFirstName().toString();
		String id = user.getId().toString();
		String gender = user.asMap().get("gender").toString();
		String email = user.asMap().get("email").toString();

		usuario = Usuario.getInstance();
		usuario.setOnRegisterUsuario(Login.this);
		usuario.register(Login.this, nombre, email, id, gender);

		loginErrorMsg.setText(msg);

	}

	@Override
	public void onRegisterFinish(JSONObject json, String msg) {
		// TODO Auto-generated method stub
		Intent itemintent = new Intent(this, PrincipalActivity.class);
		Login.this.startActivity(itemintent);

	}

	@Override
	public void onRegisterException(Exception e, String msg) {
		// TODO Auto-generated method stub
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("ERROR");
		alertDialog.setMessage(msg);
		loginErrorMsg.setText(msg);
		Session session = Session.getActiveSession();
		session.close();

		alertDialog.show();
	}

	@Override
	public void onRegisterFail(String msg) {
		Session session = Session.getActiveSession();
		session.close();
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("ERROR");
		alertDialog.setMessage(msg);
		loginErrorMsg.setText(msg);

		alertDialog.show();
	}
}
