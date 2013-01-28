package org.jared.android.volley.ui.fragment;

import org.jared.android.volley.repository.VolleyDatabaseHelper;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import android.support.v4.app.Fragment;

/**
 * Base class for ContentFragmentProvider
 */
public abstract class BaseFragmentProvider implements ContentFragmentProvider {

	Fragment fragment;
	private VolleyDatabaseHelper databaseHelper = null;
	
	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.ContentFragmentProvider#init(android.content.Context)
	 */
	@Override
	public void init(Fragment frag) {
		fragment = frag;
	}
	
	/**
	 * Retrieve the DatabaseHelper and create it if needed (can not be injected by AA)
	 */
	protected VolleyDatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(fragment.getActivity(), VolleyDatabaseHelper.class);
		}
		return databaseHelper;
	}
}
