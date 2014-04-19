package com.example.clinicadmincr;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import com.example.clinicadmincr.httpHandler.OnExecuteHttpPostAsyncListener;

public class Doctor {

	private static Doctor instance = null;

	protected Doctor() {
		// Exists only to defeat instantiation.
	}

	public static Doctor getInstance() {
		if (instance == null) {
			instance = new Doctor();
		}
		return instance;
	}

	private String Name = "";
	private String Email = "";
	private String UID = "";

	private static String KEY_SUCCESS = "success";
	private static String KEY_UID = "apiKey";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	// private static String KEY_CREATED_AT = "created_at";
	// private static String KEY_USER = "user";

	private static String loginURL = "http://192.168.0.189:80/api/v1/login";
	private static String registerURL = "http://192.168.0.189:80/api/v1/register";

	private static String login_tag = "login";
	private static String register_tag = "register";

	private Activity activity;

	public void login(Activity activity, String email, String password) {
		this.activity = activity;
		httpHandler httpclient = new httpHandler(activity);
		httpclient.addNameValue("tag", login_tag);
		httpclient.addNameValue("email", email);
		httpclient.addNameValue("password", password);
		httpclient
				.setOnExecuteHttpPostAsyncListener(new OnExecuteHttpPostAsyncListener() {
					@Override
					public void onExecuteHttpPostAsyncListener(
							String ResponseBody) {
						try {
							JSONObject json = new JSONObject(ResponseBody);
							if (json.getString(KEY_SUCCESS) != null) {
								if ((Integer.parseInt(json
										.getString(KEY_SUCCESS)) == 1)) {
									if (Doctor.this.saveLogin(
											Doctor.this.activity, json)) {
										ListenerLoginDoctor.onLoginCorrect(
												json, "Login correcto");
									} else
										ListenerLoginDoctor
												.onLoginWrong("Login incorrecto");
								} else {
									ListenerLoginDoctor
											.onLoginWrong("Login incorrecto");
								}
							} else {
								ListenerLoginDoctor
										.onLoginWrong("Login incorrecto");
							}
						} catch (JSONException e) {
							ListenerLoginDoctor
									.onLoginWrong("Login incorrecto");
						}
					}

					@Override
					public void onErrorHttpPostAsyncListener(String message) {
					}
				});

		httpclient.executeHttpPost(loginURL);
	}

	public void register(Activity activity, String username, String email,
			String password, String sexo) {
		this.activity = activity;
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
							if (json.getString(KEY_SUCCESS) != null) {
								if ((Integer.parseInt(json
										.getString(KEY_SUCCESS)) == 1)) {
									if (Doctor.this.saveLogin(
											Doctor.this.activity, json)) {
										ListenerRegisterUsuario
												.onRegisterFinish(json,
														"Registro correcto");

									} else
										ListenerRegisterUsuario
												.onRegisterFail("Estas registrado pero no puedes loguearte ahora");
								} else
									ListenerRegisterUsuario
											.onRegisterFail("Error durante el registro");
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

	private OnLoginUsuario ListenerLoginDoctor;

	public void setOnLoginDoctor(OnLoginUsuario l) {
		ListenerLoginDoctor = l;
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

}