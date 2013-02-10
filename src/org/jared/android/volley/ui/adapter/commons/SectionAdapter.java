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
 * Adpater which aims to have sections with different kinds of adapters
 * @author eric.taix@gmail.com
 */
public class SectionAdapter extends BaseAdapter {

	public final Map<Integer, Section> sections = new LinkedHashMap<Integer, Section>();
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
		headers.registerDataSetObserver(observer);
	}

	public void addSection(Section section) {
		this.headers.add(section);
		this.sections.put(section.id, section);
		section.adapter.registerDataSetObserver(observer);
	}

	public void insertSection(Section section, int position) {
		this.headers.insert(section, position);
		this.sections.put(section.id, section);
		section.adapter.registerDataSetObserver(observer);
	}

	public void setSectionVisibility(int sectionId, boolean visible) {
		Section sec = this.sections.get(sectionId);
		if (sec != null) {
			sec.visible = visible;
		}
		notifyDataSetChanged();
	}

	public void renameSection(int sectionId, String newHeader) {
		Section section = this.sections.get(sectionId);
		if (section != null) {
			section.title = newHeader;
		}
		notifyDataSetChanged();
	}

	public Object getItem(int position) {
		for (Section section : sections.values()) {
			if (section.visible) {
				Adapter adapter = section.adapter;
				int size = adapter.getCount() + 1;

				// Verify if the position is within a section
				if (position == 0) return section.title;
				if (position < size) return adapter.getItem(position - 1);

				// Otherwise go to the next section
				position -= size;
			}
		}
		return null;
	}

	public int getCount() {
		// Add all items count for each visible sections and add one for each sections
		int total = 0;
		for (Section section : sections.values()) {
			if (section.visible) {
				Adapter adapter = section.adapter;
				total += adapter.getCount() + 1;
			}
		}
		return total;
	}

	public int getViewTypeCount() {
		// Add all adapter's view type count and add one (for all headers) even if a section is not visible
		int total = 1;
		for (Section section : sections.values()) {
			Adapter adapter = section.adapter;
			total += adapter.getViewTypeCount();
		}
		return total;
	}

	public int getItemViewType(int position) {
		int result = -1;
		int type = 1;
		for (Section section : sections.values()) {
			Adapter adapter = section.adapter;
			if (section.visible) {
				int size = adapter.getCount() + 1;

				// Verify if the position is within a section
				if (position == 0) {
					result = TYPE_SECTION_HEADER;
					break;
				}
				if (position < size) {
					result = type + adapter.getItemViewType(position - 1);
					break;
				}

				// Otherwise go to the next section
				position -= size;
			}
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
		for (Section section : sections.values()) {
			if (section.visible) {
				Adapter adapter = section.adapter;
				int size = adapter.getCount() + 1;

				// Verify if the position is within a section
				if (position == 0) return false;
				if (position < size) {
					if (adapter instanceof BaseAdapter) {
						return ((BaseAdapter) adapter).isEnabled(position - 1);
					}
					return true;
				}
				// Otherwise go to the next section
				position -= size;
			}
		}
		return true;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int sectionnum = 0;
		for (Section section : sections.values()) {
			if (section.visible) {
				Adapter adapter = section.adapter;

				int size = adapter.getCount() + 1;

				// Verify if the position is within a section
				if (position == 0) return headers.getView(sectionnum, convertView, parent);
				if (position < size) return adapter.getView(position - 1, convertView, parent);

				// Otherwise go to the next section
				position -= size;

			}
			sectionnum++;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
