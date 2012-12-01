/**
 * 
 */
package org.jared.android.volley.adapter;

import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.model.Club;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adpater permettant de gérer les items des clubs<br/>
 * 
 * @author eric.taix@gmail.com
 */
public class ClubAdapter extends BaseAdapter {

	// ============== Inner Class ==============

	/**
	 * ViewHolder permettant d'afficher les informations du club
	 * 
	 * @author eric.taix@gmail.com
	 */
	class ClubHolder {
		private TextView titleCourt;
		private TextView title;
		private TextView nbEquipes;
		private ImageView favoriteImg;

		public ClubHolder(View view) {
			titleCourt = (TextView) view.findViewById(R.id.title_court);
			title = (TextView) view.findViewById(R.id.title);
			nbEquipes = (TextView) view.findViewById(R.id.nb);
			favoriteImg = (ImageView) view.findViewById(R.id.favorite);
			view.setTag(this);
		}

		public void update(Club club) {
			titleCourt.setText(club.nomCourt);
			title.setText(club.nom);
			nbEquipes.setText("" + club.nbEquipes);
			favoriteImg.setVisibility((club.favorite ? View.VISIBLE : View.INVISIBLE));
		}

	}

	// =========================================

	private List<Club> clubs;
	private Context ctx;

	public ClubAdapter(Context ctx) {
		this.ctx = ctx;
	}

	public ClubAdapter(Context ctx, List<Club> clubsP) {
		this.ctx = ctx;
		setClubs(clubsP);
	}

	/**
	 * Fixe le nouveau menu
	 * 
	 * @param menusP
	 */
	public void setClubs(List<Club> clubsP) {
		clubs = clubsP;
		notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (clubs != null) {
			return clubs.size();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (clubs != null && position < clubs.size()) {
			return clubs.get(position);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ClubHolder holder = null;
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.club_item, parent, false);
			holder = new ClubHolder(convertView);
		}
		else {
			holder = (ClubHolder)convertView.getTag();
		}
		Club club = (Club) getItem(position);
		holder.update(club);
		return convertView;
	}
}
