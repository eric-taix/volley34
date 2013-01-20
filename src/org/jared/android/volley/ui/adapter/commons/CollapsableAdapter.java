/**
 * 
 */
package org.jared.android.volley.ui.adapter.commons;

import org.jared.android.volley.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Un adapter permettant de limiter le nombre d'items visible au départ puis de pouvoir les afficher / cacher
 * @author eric.taix@gmail.com
 */
public class CollapsableAdapter extends BaseAdapter {

	private static final String DEFAULT_COLLAPSE  ="Réduire";
	private static final String DEFAULT_EXPAND = "Tout afficher";
	
	private static final int DEFAULT_MAX_ITEMS = 3;
	// Le contexte d'éxécution 
	private Context ctx;
	// L'adapter qui en dessous affiche les différents éléments
	private BaseAdapter childAdapter;
	// L'adapter parent (optionnel) qui contient le CollapsableAdapter
	private BaseAdapter parentAdapter;
	// Le nombre maximum d'items en mode collapsed
	private int maxItems;
	// L'état du mode
	private boolean collapsed = true;
	// Les textes à afficher
	private String expandText = DEFAULT_EXPAND;
	private String collapseText = DEFAULT_COLLAPSE;

	/**
	 * Constructeur permettant d'initialiser le nb max d'items à la valeur par défaut
	 * 
	 * @param ctx
	 * @param maxItems
	 */
	public CollapsableAdapter(Context ctx, BaseAdapter childAdapter) {
		this(ctx, childAdapter, null);
	}

	/**
	 * Constructeur permettant d'initialiser le nb max d'items à la valeur par défaut
	 * 
	 * @param ctx
	 * @param maxItems
	 */
	public CollapsableAdapter(Context ctx, BaseAdapter childAdapter, BaseAdapter parentAdapter) {
		this(ctx, childAdapter, parentAdapter, DEFAULT_MAX_ITEMS);
	}

	/**
	 * Fixe les textes à afficher 
	 * @param expandText
	 * @param collapseText
	 */
	public void setTexts(String expandText, String collapseText) {
		this.expandText = expandText;
		this.collapseText = collapseText;
	}
	
	/**
	 * Constructeur permettant d'initialiser le nb max d'items
	 * 
	 * @param ctx
	 * @param maxItems
	 */
	public CollapsableAdapter(Context ctx, BaseAdapter childAdapter, BaseAdapter parentAdapter, int maxItems) {
		this.ctx = ctx;
		this.childAdapter = childAdapter;
		this.parentAdapter = parentAdapter;
		this.maxItems = maxItems;
		// On ajoute un observer lorsque les données sont du child sont modifiées
		this.childAdapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				childDataSetChanged();
			}
		});
	}

	private void childDataSetChanged() {
		notifyDataSetChanged();
		if (parentAdapter != null) {
			parentAdapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * Affiche la totalité des items présents dans le childAdapter
	 */
	public void expand() {
		collapsed = false;
		childDataSetChanged();
	}

	/**
	 * Réduit pour n'afficher que les maxItems Items
	 */
	public void collapse() {
		collapsed = true;
		childDataSetChanged();
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (childAdapter.getCount() <= maxItems) {
			return childAdapter.getCount();
		}
		if (collapsed && childAdapter.getCount() > maxItems) {
			return maxItems + 1;
		}
		return childAdapter.getCount() + 1;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (childAdapter.getCount() <= maxItems) {
			return childAdapter.getItem(position);
		}
		if (position < getCount()-1) {
			return childAdapter.getItem(position);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		if (childAdapter.getCount() <= maxItems) {
			return position;
		}
		if (collapsed && position == maxItems) {
			return childAdapter.getCount() + 1;
		}
		return position;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getItemViewType(position) == 0) {
			if (convertView == null) {
				LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = li.inflate(R.layout.list_control_layout, parent, false);
				final TextView tv = (TextView) convertView.findViewById(R.id.expand_title);
				tv.setText(expandText);
				final ImageView im = (ImageView) convertView.findViewById(R.id.id_arrow);
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (CollapsableAdapter.this.collapsed) {
							CollapsableAdapter.this.expand();
							tv.setText(collapseText);
							im.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_top_arrow));
						}
						else {
							CollapsableAdapter.this.collapse();
							tv.setText(expandText);
							im.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_bottom_arrow));
						}
					}
				});
			}
			return convertView;
		}
		else {
			convertView = childAdapter.getView(position, convertView, parent);
			return convertView;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(int position) {
		if (childAdapter.getCount() <= maxItems) {
			return childAdapter.getItemViewType(position) + 1;
		}
		if (collapsed) {
			if (position < maxItems) {
				return childAdapter.getItemViewType(position) + 1;
			}
			else {
				return 0;
			}
		}
		if (position < childAdapter.getCount()) {
			return childAdapter.getItemViewType(position) + 1;
		}
		else {
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.BaseAdapter#getViewTypeCount()
	 */
	@Override
	public int getViewTypeCount() {
		return childAdapter.getViewTypeCount() + 1;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.BaseAdapter#areAllItemsEnabled()
	 */
	@Override
	public boolean areAllItemsEnabled() {
		return childAdapter.areAllItemsEnabled();
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.BaseAdapter#isEnabled(int)
	 */
	@Override
	public boolean isEnabled(int position) {
		if (childAdapter.getCount() <= maxItems) {
			return childAdapter.isEnabled(position);
		}
		if ((collapsed && position < maxItems) || (!collapsed && position < childAdapter.getCount())) {
			return childAdapter.isEnabled(position);
		}
		else {
			return true;
		}
	}

}
