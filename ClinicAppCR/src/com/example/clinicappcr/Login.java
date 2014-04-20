package com.example.clinicappcr;

import org.json.JSONException;
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
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;



public class Login extends Activity implements OnLoginUsuario, OnLoginFacebook, OnRegisterUsuario {

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
				usuario.setOnLoginUsuario(Login.this);
				usuario.login(Login.this, email, password);
				//falta comprobar usuario

				
			
			}
		});
		
		Session session = Session.getActiveSession();
		if(session!=null){
			session.close();
		}
		btnFLogin.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                Login.this.user = user;
                
                Session session = Session.getActiveSession();
    	        boolean enableButtons = (session != null && session.isOpened());

    	        if (enableButtons && user != null) {
    	        	
    	        	JSONObject json=user.getInnerJSONObject();
    	        	//System.out.println(json.toString());

    	        	String nombre=user.getFirstName().toString();
					//System.out.println(nombre);
					String id=user.getId().toString();
					//System.out.println(id);
					String gender=user.asMap().get("gender").toString();
					//System.out.println(gender);
					String email=user.getUsername().toString().concat("@clinic.com");
					//System.out.println(email);
					usuario = Usuario.getInstance();
					usuario.setOnRegisterFacebook(Login.this);
					usuario.loginFacebook(Login.this, email, id);
					System.out.println("bien");
    	        	
    	            
    	        } else {
    	            
    	        }
                // It's possible that we were waiting for this.user to be populated in order to post a
                // status update.
                //handlePendingAction();
                
                
                /*String email =json. ;
				String password = inputPassword.getText().toString();
				
				usuario = Usuario.getInstance();
				usuario.setOnLoginUsuario(Login.this);
				usuario.login(Login.this, email, password);*/
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
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	  }
	 
	 
	 /*private void updateUI() {
	        
	    }
*/

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
	@Override
	public void onLoginFacebookFinish(JSONObject json, String msg) {
		// TODO Auto-generated method stub
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
	public void onLoginFacebookException(Exception e, String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
		loginErrorMsg.setText(msg);
		
	}
	@Override
	public void onLoginFacebookFail(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
		String nombre=user.getFirstName().toString();		
		String id=user.getId().toString();
		String gender=user.asMap().get("gender").toString();		
		String email=user.getUsername().toString().concat("@clinic.com");
		System.out.println("REGISTRANDO "+email + "  pass= " +id);
		
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
		
		// Set the Icon for the Dialog
		alertDialog.show();
	}

	@Override
	public void onRegisterFail(String msg) {
		// TODO Auto-generated method stub
		
		Session session = Session.getActiveSession();
		session.close();
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("ERROR");
		alertDialog.setMessage(msg);
		loginErrorMsg.setText(msg);
		
		// Set the Icon for the Dialog
		alertDialog.show();
	}
}
	
