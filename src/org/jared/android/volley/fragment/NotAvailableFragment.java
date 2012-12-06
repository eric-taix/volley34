/**
 * 
 */
package org.jared.android.volley.fragment;

import org.jared.android.volley.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.googlecode.androidannotations.annotations.EFragment;

/**
 * Fragment permettant d'afficher le fait qu'une fonctionnalité n'existe pas
 * 
 * @author eric.taix@gmail.com
 */
@EFragment(R.layout.notavailable_layout)
public class NotAvailableFragment extends Fragment {

	public static final String TITLE = "title";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(getArguments().getString(TITLE));
	}

}
