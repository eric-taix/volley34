/**
 * 
 */
package org.jared.android.volley.adapter;

import org.jared.android.volley.R;
import org.jared.android.volley.model.Club;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author eric.taix@gmail.com
 */
public class ClubInformationAdapter extends ClubBaseAdapter {

	public ClubInformationAdapter(Context ctx) {
		super(ctx);
	}

	public ClubInformationAdapter(Context ctx, Club clubP) {
		super(ctx, clubP);
	}
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.club_information_layout, parent, false);
		}
		TextView tv = (TextView)convertView.findViewById(R.id.nom);
		View imgWeb = convertView.findViewById(R.id.web);
		View txtWeb = convertView.findViewById(R.id.urlWeb);
		tv.setText(club.nom);
		if (club.urlSiteWeb != null && club.urlSiteWeb.length() > 0) {
			imgWeb.setVisibility(View.VISIBLE);
			txtWeb.setVisibility(View.VISIBLE);
		}
		else {
			imgWeb.setVisibility(View.GONE);
			txtWeb.setVisibility(View.GONE);
		}
		return convertView;
	}

}
