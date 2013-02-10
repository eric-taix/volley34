package org.jared.android.volley.ui.adapter.commons;

import android.widget.Adapter;

/**
 * A section contains a title, an adapter and a flag to know if this section is visible or not
 * @author eric.taix@gmail.com
 */
public class Section {

	public int id;
	public String title;
	public Adapter adapter;
	public boolean visible = true;
	
	public Section(int id, String title, Adapter adapter) {
		this.id = id;
		this.title = title;
		this.adapter = adapter;
	}
}
