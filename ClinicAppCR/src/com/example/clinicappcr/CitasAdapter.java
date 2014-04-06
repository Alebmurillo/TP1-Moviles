package com.example.clinicappcr;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CitasAdapter extends BaseAdapter 
{

	static class ViewHolder
		 {
		       TextView tvdoctor;
		       TextView tvfecha;
		       TextView tvhora;
		       TextView tvlugar;	
	    }
	private LayoutInflater inflater=null;
	private static final String TAG = "CustomAdapter";
	private static int convertViewCounter = 0;
	private ArrayList<Cita> data;	

    public CitasAdapter(Context context, ArrayList<Cita> array)
    {   	
		Log.v(TAG, "Constructing CustomAdapter");

        this.data=array;
        this.inflater = LayoutInflater.from(context);
    }
	public int getCount()
	{
		Log.v(TAG, "in getCount()");
		return data.size();
	}
	public Object getItem(int position)
	{
		Log.v(TAG, "in getItem() for position " + position);
		return data.get(position);
	}

	
	public long getItemId(int position)
	{
		Log.v(TAG, "in getItemId() for position " + position);
		return position;
	}

	@Override
	public int getViewTypeCount()
	{
		Log.v(TAG, "in getViewTypeCount()");
		return 1;
	}

	@Override
	public int getItemViewType(int position)
	{
		Log.v(TAG, "in getItemViewType() for position " + position);
		return 0;
	}

	@Override
		public void notifyDataSetChanged()
		{
			super.notifyDataSetChanged();
	}
	
	public View getView(int position, View convertView, ViewGroup parent) 
	{

		ViewHolder holder;
		 
	      Log.v(TAG, "in getView for position " + position + ", convertView is "
	              + ((convertView == null) ? "null" : "being recycled"));
	 
	     if (convertView == null)
	        {
	           convertView = inflater.inflate(R.layout.fila_cita, null);
	 
	          convertViewCounter++;
	            Log.v(TAG, convertViewCounter + " convertViews have been created");
	 
	            holder = new ViewHolder();
	 
	            holder.tvdoctor = (TextView)convertView.findViewById(R.id.view_doctor);
	            holder.tvfecha = (TextView)convertView.findViewById(R.id.view_fecha);
	            holder.tvhora = (TextView)convertView.findViewById(R.id.view_hora);
	            holder.tvlugar = (TextView)convertView.findViewById(R.id.view_lugar);
	    		
	    		 
	            convertView.setTag(holder);
	 
	     } else
	          holder = (ViewHolder) convertView.getTag();
	      //revisar
	      Cita contactoEnFila = (Cita)getItem(position);
	      holder.tvdoctor .setText(contactoEnFila.doctor);
	      holder.tvfecha.setText(contactoEnFila.fecha);
	      holder.tvhora.setText(contactoEnFila.hora);
	      holder.tvlugar.setText(contactoEnFila.lugar);
	 
	      return convertView;		
		
	}
}
	
	