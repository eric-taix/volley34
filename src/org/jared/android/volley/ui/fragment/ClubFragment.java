/**
 * 
 */
package org.jared.android.volley.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.http.RestClient;
import org.jared.android.volley.model.Club;
import org.jared.android.volley.model.ClubList;
import org.jared.android.volley.repository.ClubDAO;
import org.jared.android.volley.repository.VolleyDatabase;
import org.jared.android.volley.ui.ClubActivity;
import org.jared.android.volley.ui.ClubActivity_;
import org.jared.android.volley.ui.RefreshableActivity;
import org.jared.android.volley.ui.adapter.ClubAdapter;
import org.jared.android.volley.ui.adapter.SectionAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

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

	private static final int SHOW_CLUB = 0;
	
	@RestService
	RestClient restClient;
	// L'adapteur qui gère l'affichage de tous les clubs
	private ClubAdapter allAdapter;
	// L'adapteur qui gère l'affichage des clubs favoris
	private ClubAdapter favoriteAdapter;
	// L'adapter général
	private SectionAdapter sectionAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		allAdapter = new ClubAdapter(getActivity());
		favoriteAdapter = new ClubAdapter(getActivity());
		sectionAdapter = new SectionAdapter(this.getActivity(), R.layout.list_header);
		sectionAdapter.addSection("CLUBS FAVORIS", favoriteAdapter);
		sectionAdapter.addSection("TOUS LES CLUBS", allAdapter);
		getListView().setCacheColorHint(getResources().getColor(R.color.transparent));
		// On positionne un divider plus "sympa"
		int[] colors = { 0, 0xFF777777, 0 };
		getListView().setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		getListView().setDividerHeight(1);
		setListAdapter(sectionAdapter);
		showIndeterminate(true);
		updateUI();
		getActivity().setTitle("Clubs");
		// En tâche de fond on interroge le serveur
		showIndeterminate(true);
		updateClubsFromNetWork();
	}

	/**
	 * Met à jour les données en interrogeant le serveur
	 */
	@Background
	void updateClubsFromNetWork() {
		try {
			ClubList clubList = restClient.getClubs();
			VolleyDatabase db = new VolleyDatabase(getActivity());
			ClubDAO.saveAll(db, clubList.clubs);
		}
		finally {
			updateClubsFromDB();
		}
	}

	@UiThread
	void updateClubsFromDB() { 
		updateUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.ListFragment#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), ClubActivity_.class);
		intent.putExtra(ClubActivity.EXTRA_CLUB, (Club) l.getAdapter().getItem(position));
		startActivityForResult(intent, SHOW_CLUB);
	}

	/**
	 * Met à jour l'interface graphique
	 * 
	 * @param clubs 
	 */
	void updateUI() {
		VolleyDatabase db = new VolleyDatabase(getActivity());
		List<Club> clubs = ClubDAO.getAllClubs(db);
		if (clubs != null) {
			allAdapter.setClubs(clubs);
			List<Club> favClubs = getFavoriteClubs(clubs);
			favoriteAdapter.setClubs(favClubs);
			sectionAdapter.notifyDataSetChanged();
		} 
		showIndeterminate(false);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SHOW_CLUB) {
			updateClubsFromDB();
		}
	}

	/**
	 * Méthode permettant d'afficher ou de cacher la progression d'une action éxécutée en tâche de fond
	 * 
	 * @param visible
	 */
	private void showIndeterminate(boolean visible) {
		// On récupère les clubs en tâche de fond
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
