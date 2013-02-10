/**
 * 
 */
package org.jared.android.volley.ui.fragment;

import org.jared.android.volley.R;
import org.jared.android.volley.http.RestClient;

import com.googlecode.androidannotations.annotations.EFragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/**
 * Fragment pour les favoris
 * @author eric.taix@gmail.com
 */
@EFragment(R.layout.list_layout)
public class CoupeFragment extends ContentFragment {

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#getCode()
	 */
	@Override
	public String getCode() {
		return "COUPE";
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Coupe";
	}
	
	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.ContentFragmentProvider#getListAdapter()
	 */
	@Override
	public ListAdapter getListAdapter() {
		return null;
	}
	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#doUpdateUI(org.jared.android.volley.repository.VolleyDatabase)
	 */
	@Override
	public void doUpdateUI() {
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#doGetFromNetwork(org.jared.android.volley.http.RestClient)
	 */
	@Override
	public Object doGetFromNetwork(RestClient client) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#doSaveToDatabase(java.lang.Object, org.jared.android.volley.repository.VolleyDatabase)
	 */
	@Override
	public void doSaveToDatabase(Object oject) {
	}

}
