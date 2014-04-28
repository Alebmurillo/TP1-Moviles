package com.example.clinicappcr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Login extends Activity implements OnLoginUsuario, OnLoginFacebook,
		OnRegisterUsuario {

	private TextView lblGotoRegister;
	private Button btnLogin,btnLoginTwitter;
	private LoginButton btnFLogin;

	private EditText inputEmail;
	private EditText inputPassword;
	private TextView loginErrorMsg;
	private Usuario usuario;
	private GraphUser user;
	private ParseUser twitteruser;
	 private boolean twitterlogin;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
    	twitterlogin=false;

		Parse.initialize(this, "TpsAPz5K0d3QWDqJsACbhQuavVjhQADNSwhO4WT3", "uxJCKGIc5BmwZ7KNz7fIbMUeINwZSiZMwc4ju9zm");

		ParseUser user = new ParseUser();
		user.setUsername("my name");
		user.setPassword("my pass");
		user.setEmail("email@example.com");
		  
		// other fields can be set just like with ParseObject
		user.put("phone", "650-555-0000");
		  
		user.signUpInBackground(new SignUpCallback() {
		  public void done(ParseException e) {
		    if (e == null) {
		      // Hooray! Let them use the app now.
		    } else {
		      // Sign up didn't succeed. Look at the ParseException
		      // to figure out what went wrong
		    }
		  }
		});
		
		ParseTwitterUtils.initialize("0ewrjAnFbGXIhFfFKP0ckqUni", "y5nhrbvLcBQ2w3glleYOrb3ecmToB79SbLVdnpAGVndrRWL4j5");
		btnLoginTwitter = (Button)findViewById(R.id.btnLoginTwitter);
		btnLoginTwitter.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				ParseTwitterUtils.logIn(Login.this, new LogInCallback() {
					 
					@Override
					  public void done(ParseUser user, ParseException err) {
						 
					    if (user == null) {
					      Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
					    					      
					    } else {
					    	twitteruser=user;
					    	twitteruser.getObjectId();
					    	twitterlogin=true;
					    	System.out.println(ParseTwitterUtils.getTwitter().getScreenName());
					    	
					    	String id = ParseTwitterUtils.getTwitter().getUserId();
					    	
							String email = ParseTwitterUtils.getTwitter().getScreenName().concat("@twitter.com");
							usuario = Usuario.getInstance();
							usuario.setContext(getApplication().getApplicationContext());
							usuario.setURL();
							usuario.setOnRegisterFacebook(Login.this);
							usuario.loginFacebook(Login.this, email, id);
							
					    }

					  }
					});
				
			}
		});
		
		inputEmail = (EditText) findViewById(R.id.txtEmail);
		inputPassword = (EditText) findViewById(R.id.txtPass);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnFLogin = (LoginButton) findViewById(R.id.flogin);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);

		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
		    	twitterlogin=false;

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
					    	twitterlogin=false;

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
		String nombre ="";
		String id ="";
		String gender="";
		String email ="";
		if(!twitterlogin){
			 nombre = user.getFirstName().toString();
			 id = user.getId().toString();
			 gender = user.asMap().get("gender").toString();
			 email = user.asMap().get("email").toString();
		}else if(twitterlogin){
			 twitterlogin=false;

			nombre = ParseTwitterUtils.getTwitter().getScreenName();
			 id = ParseTwitterUtils.getTwitter().getUserId();
			 email = ParseTwitterUtils.getTwitter().getScreenName().concat("@twitter.com");
		}


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
