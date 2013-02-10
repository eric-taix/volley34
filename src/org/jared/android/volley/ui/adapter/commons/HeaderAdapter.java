/**
 * 
 */
package org.jared.android.volley.ui.adapter.commons;

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
 * An adapter for headers
 * @author eric.taix@gmail.com
 */
public class HeaderAdapter extends BaseAdapter {

	Context context;
	int layout;
	List<Section> sections = new ArrayList<Section>();

	public HeaderAdapter(Context ctx, int layoutId) {
		context = ctx;
		layout = layoutId;
	}

	/**
	 * Return all headers
	 * 
	 * @return
	 */
	public List<Section> getHeaders() {
		return sections;
	}

	/**
	 * Add a header at the end of the list
	 * 
	 * @param header
	 */
	public void add(Section section) {
		sections.add(section);
		notifyDataSetChanged();
	}

	/**
	 * Insert a header at a specific location
	 * 
	 * @param header
	 * @param position
	 */
	public void insert(Section section, int position) {
		sections.add(position, section);
		notifyDataSetChanged();
	}

	/**
	 * Remove a header
	 * 
	 * @param header
	 */
	public void remove(Section section) {
		sections.remove(section);
		notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return sections.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (sections != null && position < sections.size()) {
			sections.get(position);
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
		tv.setText(sections.get(position).title);
		return convertView;
	}

}
