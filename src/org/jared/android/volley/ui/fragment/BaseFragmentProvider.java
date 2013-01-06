package org.jared.android.volley.ui.fragment;

import android.support.v4.app.Fragment;

/**
 * Classe de base pour les ContentFragmentProvider
 */
public abstract class BaseFragmentProvider implements ContentFragmentProvider {

	Fragment fragment;
	
	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.ContentFragmentProvider#init(android.content.Context)
	 */
	@Override
	public void init(Fragment frag) {
		fragment = frag;
	}
}
