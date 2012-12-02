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
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author eric.taix@gmail.com
 */
public class ClubContactAdapter extends ClubBaseAdapter {

	public ClubContactAdapter(Context ctx) {
		super(ctx);
	}

	public ClubContactAdapter(Context ctx, Club clubP) {
		super(ctx, clubP);
	}
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.club_contact_layout, parent, false);
		}
		ImageView imgPhone = (ImageView) convertView.findViewById(R.id.img_phone);
		TextView phone = (TextView) convertView.findViewById(R.id.phone);
		TextView mail = (TextView) convertView.findViewById(R.id.mail);
		TextView nom = (TextView) convertView.findViewById(R.id.nom);
		ImageView portrait = (ImageView) convertView.findViewById(R.id.portrait);
		
		nom.setText(club.contact);
		if (club.telephone != null && club.telephone.length() > 0) {
			phone.setText(club.telephone);
			phone.setVisibility(View.VISIBLE);
			imgPhone.setVisibility(View.VISIBLE);
		} 
		else {
			phone.setVisibility(View.GONE);
			imgPhone.setVisibility(View.GONE);
		}
		mail.setText(club.mail);
		return convertView;
	}

}
