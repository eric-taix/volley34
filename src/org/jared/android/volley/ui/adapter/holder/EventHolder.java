/**
 * 
 */
package org.jared.android.volley.ui.adapter.holder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jared.android.volley.R;
import org.jared.android.volley.model.Event;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Holder de base pour les Events
 * @author eric.taix@gmail.com
 */
@SuppressLint("SimpleDateFormat")
public class EventHolder {
	TextView title;
	TextView date;
	TextView place;
	ImageView action;
	ImageView type;
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public EventHolder(View view) {
		title = (TextView) view.findViewById(R.id.id_nom);
		date = (TextView) view.findViewById(R.id.id_date);
		place = (TextView) view.findViewById(R.id.id_place);
		action = (ImageView) view.findViewById(R.id.id_image_action);
		type = (ImageView) view.findViewById(R.id.id_type);
		view.setTag(this);
	}

	public void update(Context ctx, Event event) {
		title.setText(event.eventName);
		try {
			Date startDate = format.parse(event.eventStartDate);
			date.setText(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(startDate));
		}
		catch (ParseException e) {
		}

		place.setText(event.eventPlace);
		action.setVisibility(event.eventType.equals(Event.TYPE_MATCH) ? View.VISIBLE : View.INVISIBLE);
		int color = ctx.getResources().getColor(R.color.yellow);
		if (event.eventType.equals(Event.TYPE_MATCH)) {
			color = ctx.getResources().getColor(R.color.red);
		}
		else if (event.eventType.equals(Event.TYPE_TOURNOI)) {
			color = ctx.getResources().getColor(R.color.green);
		}
		else if (event.eventType.equals(Event.TYPE_REUNION) || event.eventType.equals(Event.TYPE_AUTRES)) {
			color = ctx.getResources().getColor(R.color.yellow);
		}
		else if (event.eventType.equals(Event.TYPE_REUNION)) {
			color = ctx.getResources().getColor(R.color.yellow);
		}
		type.setBackgroundColor(color);
	}
}
