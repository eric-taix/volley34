/**
 * 
 */
package org.jared.android.volley.ui.adapter;

import org.jared.android.volley.model.Club;

import android.content.Context;
import android.widget.BaseAdapter;

/**
 * Adpater permettant de gérer les items des clubs<br/>
 * 
 * @author eric.taix@gmail.com
 */
public abstract class ClubBaseAdapter extends BaseAdapter {

	protected Club club;
	protected Context ctx;

	public ClubBaseAdapter(Context ctx) {
		this.ctx = ctx;
	}

	public ClubBaseAdapter(Context ctx, Club clubP) {
		this.ctx = ctx;
		setClub(clubP);
	}

	/**
	 * Fixe le nouveau menu
	 * 
	 * @param menusP
	 */
	public void setClub(Club clubP) {
		club = clubP;
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
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (club != null) {
			return 1;
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
		if (club != null) {
			return club;
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
}
