package com.example.clinicappcr;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;

import com.example.clinicappcr.httpHandler.OnExecuteHttpPostAsyncListener;

public class Usuario {	
	
   private static Usuario instance = null;
   protected Usuario() {
      // Exists only to defeat instantiation.
	   


   }
   public static Usuario getInstance() {
      if(instance == null) {
         instance = new Usuario();
      }
      return instance;
   }
   public void setURL(){
	   loginURL=context.getString(R.string.IPserver) +"/api/v1/login";
	   registerURL=context.getString(R.string.IPserver) +"/api/v1/register";
   }


	
	private String Name = "";
	private String Email = "";
	private String UID = "";

	private static String KEY_SUCCESS = "success";
	private static String KEY_UID = "apiKey";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	//private static String KEY_CREATED_AT = "created_at";
	//private static String KEY_USER = "user";
	//URLespecialidades=getString(R.string.IPserver) +"/api/v1/especialidades";

	private static String loginURL ;
	private static String registerURL ;

	private static String login_tag = "login";
	private static String register_tag = "register";

	private Activity activity;

	public void login(Activity activity, String email, String password) {
		this.activity = activity;
		System.out.println(email);
		System.out.println( password);
		httpHandler httpclient = new httpHandler(activity);
		httpclient.addNameValue("tag", login_tag);
		httpclient.addNameValue("email", email);
		httpclient.addNameValue("password", password);
		httpclient.setOnExecuteHttpPostAsyncListener(new OnExecuteHttpPostAsyncListener() {
					@Override
					public void onExecuteHttpPostAsyncListener(
							String ResponseBody) {
						try {
							JSONObject json = new JSONObject(ResponseBody);
							if (json.getString(KEY_SUCCESS) != null) {
								if ((Integer.parseInt(json.getString(KEY_SUCCESS)) == 1)) {
									if (Usuario.this.saveLogin(	Usuario.this.activity, json)){
										ListenerLoginUsuario.onLoginCorrect(json, "Login correcto");
									}else
										ListenerLoginUsuario
												.onLoginWrong("Login incorrecto");
								} else {
									ListenerLoginUsuario
											.onLoginWrong("Login incorrecto");
								}
							} else {
								ListenerLoginUsuario
										.onLoginWrong("Login incorrecto");
							}
						} catch (JSONException e) {
							ListenerLoginUsuario
									.onLoginWrong("Login incorrecto");
						}
					}

					@Override
					public void onErrorHttpPostAsyncListener(String message) {
					}
				});

		httpclient.executeHttpPost(loginURL);
	}
	
	
	
	public void loginFacebook(Activity activity, String email, String password) {
		this.activity = activity;
		System.out.println(email);
		System.out.println( password);
		httpHandler httpclient = new httpHandler(activity);
		httpclient.addNameValue("tag", login_tag);
		httpclient.addNameValue("email", email);
		httpclient.addNameValue("password", password);
		httpclient.setOnExecuteHttpPostAsyncListener(new OnExecuteHttpPostAsyncListener() {
					private Exception Exception;

					@Override
					public void onExecuteHttpPostAsyncListener(
							String ResponseBody) {
						try {
							JSONObject json = new JSONObject(ResponseBody);
							if (json.getString(KEY_SUCCESS) != null) {
								if ((Integer.parseInt(json.getString(KEY_SUCCESS)) == 1)) {
									if (Usuario.this.saveLogin(	Usuario.this.activity, json)){
										ListenerLoginFacebook.onLoginFacebookFinish(json, "Login correcto");
									}else{
										//ListenerLoginFacebook
												//.onLoginFacebookFail("Login incorrecto");
									}
								} else {
									ListenerLoginFacebook
									.onLoginFacebookFail(json.getString("message"));
									
								}
							} else {
								ListenerLoginFacebook
								.onLoginFacebookException(Exception,"Login error");
							}
						} catch (JSONException e) {
							ListenerLoginFacebook
							.onLoginFacebookException(e, "Login response error");
						}
					}

					@Override
					public void onErrorHttpPostAsyncListener(String message) {
					}
				});

		httpclient.executeHttpPost(loginURL);
	}
	

	
	public void register(Activity activity, String username, String email, String password, String sexo) {
		this.activity = activity;
		System.out.println(email);
		System.out.println( password);
		httpHandler httpclient = new httpHandler(activity);
		httpclient.addNameValue("tag", register_tag);
		httpclient.addNameValue("nombre", username);
		httpclient.addNameValue("email", email);
		httpclient.addNameValue("password", password);
		httpclient.addNameValue("sexo", sexo);

		httpclient
				.setOnExecuteHttpPostAsyncListener(new OnExecuteHttpPostAsyncListener() {
					@Override
					public void onExecuteHttpPostAsyncListener(
							String ResponseBody) {
						try {
							JSONObject json = new JSONObject(ResponseBody);
							System.out.println("----------"+json.toString());
							if (json.getString(KEY_SUCCESS) != null) {
								if ((Integer.parseInt(json
										.getString(KEY_SUCCESS)) == 1)) {
									if (Usuario.this.saveLogin(Usuario.this.activity, json)){
										ListenerRegisterUsuario.onRegisterFinish(json,"Registro correcto");

									}else
										ListenerRegisterUsuario
												.onRegisterFail("Estas registrado pero no puedes loguearte ahora");
								} else
									ListenerRegisterUsuario
											.onRegisterFail(json.getString("message"));
							} else {
								ListenerRegisterUsuario
										.onRegisterFail("Error durante el registro");
							}
						} catch (JSONException e) {
							ListenerRegisterUsuario.onRegisterException(e,
									"Error durante el registro");
						}
					}

					@Override
					public void onErrorHttpPostAsyncListener(String message) {
					}
				});
		httpclient.executeHttpPost(registerURL);
	}

	private boolean saveLogin(Activity activity, JSONObject json) {
		

		boolean save = false;
		this.logout(activity);
		try {
			
			Object name = json.get(KEY_NAME);
			Object email = json.get(KEY_EMAIL);
			Object apiKey = json.get(KEY_UID);
			setEmail(email.toString());
			setName(name.toString());

			setUID(apiKey.toString());
			// DBLogin dblogin = new DBLogin(activity);
			// dblogin.addUser(user.getString(KEY_NAME),
			// user.getString(KEY_EMAIL), json.getString(KEY_UID),
			// user.getString(KEY_CREATED_AT));
			// dblogin.close();
			save = true;
		} catch (JSONException e) {
		}
		return save;
	}

	public void logout(Activity activity) {
		// DBLogin dblogin = new DBLogin(activity);
		// dblogin.resetTables();
		// dblogin.close();
	}

	public boolean isUserLoggedIn(Activity activity) {
		// DBLogin db = new DBLogin(activity);
		// int count = db.getRowCount();
		// db.close();
		// if(count > 0){
		// user logged in
		// return true;
		// }
		return false;
	}

	public void readUser(Activity activity) {
		// DBLogin dblogin = new DBLogin(activity);
		// Cursor cursor = dblogin.getCursorUsuario();
		// if(cursor.moveToNext()){
		// this.setName(cursor.getString(0));
		// this.setEmail(cursor.getString(1));
		// this.setUID(cursor.getString(2));
		// }
		// cursor.close();
		// dblogin.close();

	}
	
	
	public interface OnLoginFacebook {
		void onLoginFacebookFinish(JSONObject json, String msg);

		void onLoginFacebookException(Exception e, String msg);

		void onLoginFacebookFail(String msg);
	}

	private OnLoginFacebook ListenerLoginFacebook;

	public void setOnRegisterFacebook(OnLoginFacebook l) {
		ListenerLoginFacebook = l;
	}
	

	public interface OnRegisterUsuario {
		void onRegisterFinish(JSONObject json, String msg);

		void onRegisterException(Exception e, String msg);

		void onRegisterFail(String msg);
	}

	private OnRegisterUsuario ListenerRegisterUsuario;

	public void setOnRegisterUsuario(OnRegisterUsuario l) {
		ListenerRegisterUsuario = l;
	}

	public interface OnLoginUsuario {
		void onLoginCorrect(JSONObject json, String msg);

		void onLoginWrong(String msg);
	}

	private OnLoginUsuario ListenerLoginUsuario;
	private Context context;

	public void setOnLoginUsuario(OnLoginUsuario l) {
		ListenerLoginUsuario = l;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}
	public void setContext(Context applicationContext) {
		// TODO Auto-generated method stub
		context=applicationContext;
		
	}

}