package com.example.clinicadmincr;

import org.json.JSONObject;


public interface OnDeleteCita {
	void onDeleteCorrect(JSONObject json, String msg);

	void onDeleteWrong(String msg);
	
	void onConfirmDelete();
}

