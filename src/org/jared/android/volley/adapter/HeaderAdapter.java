/**
 * 
 */
package org.jared.android.volley.adapter;

import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author eric.taix@gmail.com
 */
public class HeaderAdapter extends BaseAdapter {

	Context context;
	int layout;
	List<String> headers = new ArrayList<String>();

	public HeaderAdapter(Context ctx, int layoutId) {
		context = ctx;
		layout = layoutId;
	}

	/**
	 * Retourne la liste des headers
	 * 
	 * @return
	 */
	public List<String> getHeaders() {
		return headers;
	}

	/**
	 * Ajoute un header
	 * 
	 * @param header
	 */
	public void add(String header) {
		headers.add(header);
	}

	/**
	 * Inser un header à la position désirée
	 * 
	 * @param header
	 * @param position
	 */
	public void insert(String header, int position) {
		headers.add(position, header);
	}

	/**
	 * Supprime un header
	 * 
	 * @param header
	 */
	public void remove(String header) {
		headers.remove(header);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return headers.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (headers != null && position < headers.size()) {
			return headers.get(position);
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
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(layout, parent, false);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.list_header_title);
		tv.setText(headers.get(position));
		return convertView;
	}

}
