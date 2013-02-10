/**
 * 
 */
package org.jared.android.volley.ui.fragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.http.RestClient;
import org.jared.android.volley.model.Club;
import org.jared.android.volley.model.ClubListResponse;
import org.jared.android.volley.ui.adapter.MenuClubsAdapter;
import org.jared.android.volley.ui.adapter.commons.Section;
import org.jared.android.volley.ui.adapter.commons.SectionAdapter;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.googlecode.androidannotations.annotations.EFragment;
import com.j256.ormlite.dao.Dao;

/**
 * Fragment for the clubs list
 * @author eric.taix@gmail.com
 */
@EFragment(R.layout.list_layout)
public class ClubsFragment extends ContentFragment {

	private SectionAdapter sectionAdapter;
	private MenuClubsAdapter allAdapter;
	private MenuClubsAdapter favoriteAdapter;

	/*
	 * (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#getCode()
	 */
	@Override
	public String getCode() {
		return "CLUBS";
	}

	/*
	 * (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Clubs";
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		ClubFragment.showClub(this, ((Club) sectionAdapter.getItem(position)).code, 0);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.ContentFragmentProvider#getListAdapter()
	 */
	@Override
	public ListAdapter getListAdapter() {
		sectionAdapter = new SectionAdapter(this.getActivity(), R.layout.list_header);
		allAdapter = new MenuClubsAdapter(this.getActivity());
		favoriteAdapter = new MenuClubsAdapter(this.getActivity());
		sectionAdapter.addSection(new Section(1,"FAVORIS", favoriteAdapter));
		sectionAdapter.addSection(new Section(2,"TOUS", allAdapter));
		return sectionAdapter;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#doUpdateUI(org.jared.android.volley.repository.VolleyDatabase)
	 */
	@Override
	public void doUpdateUI() {
		try {
			Dao<Club, String> clubDao = getHelper().getDao(Club.class);
			List<Club> clubs = clubDao.queryForAll();
			if (clubs != null) {
				allAdapter.setClubs(clubs);
				List<Club> favClubs = getFavoriteClubs(clubs);
				favoriteAdapter.setClubs(favClubs);
				sectionAdapter.notifyDataSetChanged();
			}
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while retrieving clubs from DB", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#doGetFromNetwork(org.jared.android.volley.http.RestClient)
	 */
	@Override
	public Object doGetFromNetwork(RestClient client) {
		ClubListResponse clubList = client.getClubs();
		return clubList.clubs;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#doSaveToDatabase(java.lang.Object, org.jared.android.volley.repository.VolleyDatabase)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doSaveToDatabase(Object object) {
		try {
			Dao<Club, Integer> clubDao = getHelper().getDao(Club.class);
			List<Club> oldClubs = clubDao.queryForAll();
			List<Club> newClubs = (List<Club>) object;
			// Delete all old clubs
			clubDao.delete(oldClubs);
			// Set the favorite flag (which does not exist in the REST response)
			for (Club club : newClubs) {
				int index = oldClubs.indexOf(club);
				if (index != -1) {
					Club oldClub = oldClubs.get(index);
					club.favorite = oldClub.favorite;
				}
				clubDao.create(club);
			}
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while saving clubs to DB", e);
		}
	}

	/**
	 * Return the list of clubs which are our favorites
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
