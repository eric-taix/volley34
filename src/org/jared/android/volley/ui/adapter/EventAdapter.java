/**
 * 
 */
package org.jared.android.volley.ui.adapter;

import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.model.Event;
import org.jared.android.volley.ui.adapter.holder.EventHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Adapter pour les Events
 * @author eric.taix@gmail.com
 */
public class EventAdapter extends BaseAdapter {
	
	private List<Event> events;
	private Context ctx;
	
	public EventAdapter(Context ctx) {
		this.ctx = ctx;
	}
	
	/**
	 * Met ˆ jour la liste des events
	 * @param events
	 */
	public void setEvents(List<Event> events) {
		this.events = events;
		notifyDataSetChanged();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#areAllItemsEnabled()
	 */
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#isEnabled(int)
	 */
	@Override
	public boolean isEnabled(int position) {
		if (events != null) {
			Event evt = events.get(position);
			// Seuls les matchs et les tournois sont enable
			if (evt.eventType != null && (evt.eventType.equals(Event.TYPE_MATCH))) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#getViewTypeCount()
	 */
	@Override
	public int getViewTypeCount() {
		return 1;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (events != null) {
			return events.size();
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (events != null) {
			return events.get(position);
		}
		return null;
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
		EventHolder holder;
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.event_layout, parent, false);
			// Le holder qui se stocke tout seul dans le tag
			holder = new EventHolder(convertView);
		}
		else {
			holder = (EventHolder) convertView.getTag();
		}
		Event evt = (Event) getItem(position);
		holder.update(ctx, evt);
		return convertView;
	}

}
