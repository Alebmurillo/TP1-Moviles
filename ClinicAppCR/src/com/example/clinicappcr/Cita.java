package com.example.clinicappcr;

import android.os.Parcel;
import android.os.Parcelable;

public class Cita implements Parcelable {
	public String doctor;
	public String lugar;
	public String fecha;
	public String hora;
	
	public Cita( String mdoctor, String mlugar, String mfecha, String mhora){
		  this.doctor=mdoctor;
		  this.lugar=mlugar;
		  this.fecha=mfecha;
		  this.hora=mhora;
	}
	public Cita(Parcel in){
		this.doctor=in.readString();
		this.lugar=in.readString();
		this.fecha=in.readString();
		this.hora=in.readString();			
	}
	public void setDoctor(String doctor) {
			this.doctor = doctor;
		}
		public void setLugar(String lugar) {
					this.lugar = lugar;
		}
		public void setFecha(String fecha) {
					this.fecha = fecha;
		}
		public void setHora(String hora) {
					this.hora = hora;
		}
	
		public String getFecha() {
			return fecha;
		}
		public String getHora() {
					return hora;
		}
		public String getLugar() {
					return lugar;
		}
		public String getDoctor() {
					return doctor;
		}
	
			
		public int describeContents() {
			return 0;
		}



	public void writeToParcel(Parcel dest, int flags) {
			dest.writeString(getDoctor());
			dest.writeString(getLugar());
			dest.writeString(getFecha());
			dest.writeString(getHora());
		}
	
		public static final Parcelable.Creator<Cita> CREATOR = new Parcelable.Creator<Cita>() {
			public Cita createFromParcel(Parcel in) {
				return new Cita(in);
			}
	
			public Cita[] newArray(int size) {
				return new Cita[size];
			}
		};
	}
