/**
 * 
 */
package org.jared.android.volley.ui.adapter;

import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.model.Equipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author eric.taix@gmail.com
 */
public class ClubEquipeAdapter extends BaseAdapter {

	// Liste des Žquipes
	private List<Equipe> equipes;
	// Le contexte courant
	private Context ctx;
	
	public ClubEquipeAdapter(Context ctx) {
		this.ctx = ctx;
	}
	
	/**
	 * Fixe la liste des Žquipes
	 * @param equipes
	 */
	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
		notifyDataSetChanged();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.club_equipe_layout, parent, false);
		}
		Equipe equipe = (Equipe) getItem(position);
		TextView nomTv = (TextView) convertView.findViewById(R.id.nom);
		nomTv.setText(equipe.nomEquipe);
		return convertView;
	}

	@Override
	public int getCount() {
		return (equipes != null ? equipes.size() : 0);
	}

	@Override
	public Object getItem(int position) {
		if (equipes == null) {
			return null;
		}
		return equipes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
