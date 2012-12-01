/**
 * 
 */
package org.jared.android.volley.fragment;

import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.RefreshableActivity;
import org.jared.android.volley.adapter.ClubAdapter;
import org.jared.android.volley.adapter.SectionAdapter;
import org.jared.android.volley.http.RestClient;
import org.jared.android.volley.model.Club;
import org.jared.android.volley.model.ClubList;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.rest.RestService;

/**
 * Le fragment permettant d'afficher les clubs
 * 
 * @author eric.taix@gmail.com
 */
@EFragment(R.layout.list)
public class ClubFragment extends ListFragment {

	@RestService
	RestClient restClient;
	// L'adapteur qui g�re l'affichage de tous les clubs
	private ClubAdapter allAdapter;
	// L'adapteur qui g�re l'affichage des clubs favoris
	private ClubAdapter favoriteAdapter;
	// L'adapter g�n�ral
	private SectionAdapter sectionAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		allAdapter = new ClubAdapter(getActivity());
		favoriteAdapter = new ClubAdapter(getActivity());
		sectionAdapter = new SectionAdapter(this.getActivity());
		sectionAdapter.addSection("CLUBS FAVORIS", favoriteAdapter);
		sectionAdapter.addSection("TOUS LES CLUBS", allAdapter);
		getListView().setCacheColorHint(getResources().getColor(R.color.transparent));
		// On positionne un divider plus "sympa"
		int[] colors = { 0, 0xFF777777, 0 };
		getListView().setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		getListView().setDividerHeight(1);
		setListAdapter(sectionAdapter);
		showIndeterminate(true);
		getClubs();
		getActivity().setTitle("Clubs");
	}

	@Background
	void getClubs() {
		ClubList clubList = restClient.getClubs();
		updateClubs(clubList.clubs);
	}

	@UiThread
	void updateClubs(List<Club> clubs) {
		allAdapter.setClubs(clubs);
		List<Club> favClubs = getFavoriteClubs(clubs);
		if (favClubs == null || favClubs.size() == 0) {
			sectionAdapter.removeSection("CLUBS FAVORIS");
		}
		else {
			sectionAdapter.insertSection("CLUBS FAVORIS", favoriteAdapter, 0);
			favoriteAdapter.setClubs(favClubs);
		}
		sectionAdapter.notifyDataSetChanged();
		showIndeterminate(false);
	}

	/**
	 * M�thode permettant d'afficher ou de cacher la progression d'une action �x�cut�e en t�che de fond
	 * 
	 * @param visible
	 */
	private void showIndeterminate(boolean visible) {
		// On r�cup�re les clubs en t�che de fond
		Activity parentActivity = getActivity();
		if (parentActivity != null && parentActivity instanceof RefreshableActivity) {
			((RefreshableActivity) parentActivity).showIndeterminate(visible);
		}
	}

	/**
	 * Retourne uniquement la liste des clubs en favoris
	 * 
	 * @param allClubs
	 * @return
	 */
	private List<Club> getFavoriteClubs(List<Club> allClubs) {
		List<Club> retain = new ArrayList<Club>(allClubs.size());
		for (Club club : allClubs) {
			if (club.favorite) {
				retain.add(club);
			}
		}
		return retain;
	}
}
