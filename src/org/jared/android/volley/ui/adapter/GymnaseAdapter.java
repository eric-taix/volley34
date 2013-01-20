/**
 * 
 */
package org.jared.android.volley.ui.adapter;

import org.jared.android.volley.R;
import org.jared.android.volley.model.Gymnase;
import org.jared.android.volley.utils.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter permettant d'afficher l'adresse du gymnase ainsi qu'une carte
 * @author eric.taix@gmail.com
 */
public class GymnaseAdapter extends BaseAdapter {

	private Context ctx;
	private Gymnase gymnase;
	
	public GymnaseAdapter(Context ctx) {
		this.ctx = ctx;
	}
	
	public void setGymnase(Gymnase gymnase) {
		this.gymnase = gymnase;
		notifyDataSetChanged();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return (gymnase != null ? 1 : 0);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return gymnase;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.gymnase_layout, parent, false);
		}
		TextView n = (TextView) convertView.findViewById(R.id.id_nom);
		TextView q = (TextView) convertView.findViewById(R.id.id_quartier);
		TextView a = (TextView) convertView.findViewById(R.id.id_adresse);
		TextView c = (TextView) convertView.findViewById(R.id.id_cp);
		TextView v = (TextView) convertView.findViewById(R.id.id_ville);
		n.setText(gymnase.nom);
		Views.showOrGone(q, gymnase.quartier);
		Views.showOrGone(a, gymnase.adresse);
		Views.showOrGone(c, gymnase.cp);
		Views.showOrGone(v, gymnase.ville);
		return convertView;
	}

}
