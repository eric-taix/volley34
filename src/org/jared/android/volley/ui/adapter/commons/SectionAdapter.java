/**
 * 
 */
package org.jared.android.volley.ui.adapter.commons;

import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

/**
 * Adapter permettant d'avoir des sections
 * @author eric.taix@gmail.com
 */
public class SectionAdapter extends BaseAdapter {

	public final Map<String, Adapter> sections = new LinkedHashMap<String, Adapter>();
	public final HeaderAdapter headers;
	public final static int TYPE_SECTION_HEADER = 0;
	private DataSetObserver observer;

	public SectionAdapter(Context context, int headerLayout) {
		headers = new HeaderAdapter(context, headerLayout);
		observer = new DataSetObserver() {
			@Override
			public void onChanged() {
				notifyDataSetChanged();
			}
		};
	}

	public void addSection(String section, Adapter adapter) {
		this.headers.add(section);
		this.sections.put(section, adapter);
		adapter.registerDataSetObserver(observer);
	}

	public void insertSection(String section,  Adapter adapter, int position) {
		this.headers.insert(section, position);
		this.sections.put(section, adapter);
		adapter.registerDataSetObserver(observer);
	}

	public void removeSection(String section) {
		this.headers.remove(section);
		Adapter adapter = this.sections.remove(section);
		adapter.unregisterDataSetObserver(observer);
	}
	
	public Object getItem(int position) {
		for (String header : headers.getHeaders()) {
			Adapter adapter = sections.get(header);
			int size = adapter.getCount() + 1;

			// VŽrifie si la position est dans la section
			if (position == 0) return header;
			if (position < size) return adapter.getItem(position - 1);

			// Sinon on passe ˆ la prochaine section
			position -= size;
		}
		return null;
	}

	public int getCount() {
		// Somme des items dans chaque section + 1 pour chaque section
		int total = 0;
		for (Adapter adapter : this.sections.values())
			total += adapter.getCount() + 1;
		return total;
	}

	public int getViewTypeCount() {
		// Les headers compte pour 1 + tous les autres types
		int total = 1;
		for (Adapter adapter : this.sections.values())
			total += adapter.getViewTypeCount();
		return total;
	}

	public int getItemViewType(int position) {
		int result = -1;
		int type = 1;
		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// VŽrifie si la position est dans la section
			if (position == 0) {
				result = TYPE_SECTION_HEADER;
				break;
			}
			if (position < size) {
				result = type + adapter.getItemViewType(position - 1);
				System.out.println("==>"+adapter+" getVT="+result);
				break;
			}

			// Sinon on passe ˆ la prochaine section
			position -= size;
			type += adapter.getViewTypeCount();
		}
		return result;
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isEnabled(int position) {
		if (getItemViewType(position) == TYPE_SECTION_HEADER) {
			return false;
		}
		for (String header : headers.getHeaders()) {
			Adapter adapter = sections.get(header);
			int size = adapter.getCount() + 1;

			// VŽrifie si la position est dans la section
			if (position == 0) return false;
			if (position < size) {
				if (adapter instanceof BaseAdapter) {
					return ((BaseAdapter)adapter).isEnabled(position - 1);
				}
				return true;
			}
			// Sinon on passe ˆ la prochaine section
			position -= size;
		}
		return true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int sectionnum = 0;
		for (String header : headers.getHeaders()) {
			Adapter adapter = sections.get(header);
			
			int size = adapter.getCount() + 1;

			// VŽrifie si la position est dans la section
			if (position == 0) return headers.getView(sectionnum, convertView, parent);
			if (position < size) return adapter.getView(position - 1, convertView, parent);

			// Sinon on passe ˆ la prochaine section
			position -= size;
			sectionnum++;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
