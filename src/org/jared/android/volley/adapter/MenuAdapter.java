/**
 * 
 */
package org.jared.android.volley.adapter;

import org.jared.android.volley.R;
import org.jared.android.volley.model.Menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adpater permettant de gérer les items de menus.<br/>
 * Comme il s'agit d'un menu statique pas de ViewHolder
 * @author eric.taix@gmail.com
 */
public class MenuAdapter extends BaseAdapter {

	private Menu[] menus;
	private Context ctx;
	
	public MenuAdapter(Context ctx) {
		this.ctx = ctx;
	}
	
	public MenuAdapter(Context ctx, Menu[] menusP) {
		this.ctx = ctx;
		setMenus(menusP);
	}
	
	/**
	 * Fixe le nouveau menu
	 * @param menusP
	 */
	public void setMenus(Menu[] menusP) {
		menus = menusP;
		notifyDataSetChanged();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (menus != null) {
			return menus.length;
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (menus != null && position < menus.length) {
			return menus[position];
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
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.menu_layout, parent, false);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.textView);
		ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);
		Menu menu = (Menu) getItem(position);
		tv.setText(menu.title);
		iv.setImageResource(menu.resourceId);
		return convertView;
	}

}
