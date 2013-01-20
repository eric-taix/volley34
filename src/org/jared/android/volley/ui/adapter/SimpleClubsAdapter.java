/**
 * 
 */
package org.jared.android.volley.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.model.ClubInformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Affiche des clubs de façon simple (le nom uniquement)
 * @author eric.taix@gmail.com
 */
public class SimpleClubsAdapter extends BaseAdapter {

	// Les clubs
	private List<ClubInformation> clubs;
	// Le contexte courant
	private Context ctx;
	
	public SimpleClubsAdapter(Context ctx) {
		this.ctx = ctx;
	}
	
	/**
	 * Fixe la liste des équipes
	 * @param equipes
	 */
	public void setClubs(List<ClubInformation> clubs) {
		this.clubs = clubs;
		notifyDataSetChanged();
	}
	
	/**
	 * Fixe la liste des équipes
	 * @param equipes
	 */
	public void setClub(ClubInformation club) {
		this.clubs = new ArrayList<ClubInformation>();
		clubs.add(club);
		notifyDataSetChanged();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.simple_name_layout, parent, false);
		}
		ClubInformation club = (ClubInformation) getItem(position);
		TextView nomTv = (TextView) convertView.findViewById(R.id.nom);
		nomTv.setText(club.nom);
		return convertView;
	}

	@Override
	public int getCount() {
		return (clubs != null ? clubs.size() : 0);
	}

	@Override
	public Object getItem(int position) {
		if (clubs == null) {
			return null;
		}
		return clubs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
