/**
 * 
 */
package org.jared.android.volley.ui.adapter;

import org.jared.android.volley.R;
import org.jared.android.volley.model.Club;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Gre les informations d'un club tel que son nom, le site et propose un bouton pour naviguer vers le site le cas ŽchŽant
 * @author eric.taix@gmail.com
 */
@SuppressLint("DefaultLocale")
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
		Button button = (Button) convertView.findViewById(R.id.buttonWeb);
		button.setOnClickListener(new OnClickListener() {
			@SuppressLint("DefaultLocale")
			@Override
			public void onClick(View v) {
				String url = club.urlSiteWeb;
				if (!url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://")) {
					url = "http://" + url;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				ctx.startActivity(intent);				
			}
		});
		tv.setText(club.nom);
		if (club.urlSiteWeb != null && club.urlSiteWeb.length() > 0) {
			button.setVisibility(View.VISIBLE);
		}
		else {
			button.setVisibility(View.GONE);
		}
		return convertView;
	}

}
