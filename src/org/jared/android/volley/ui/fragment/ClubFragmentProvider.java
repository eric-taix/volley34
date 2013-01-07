/**
 * 
 */
package org.jared.android.volley.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.http.RestClient;
import org.jared.android.volley.model.Club;
import org.jared.android.volley.model.ClubListResponse;
import org.jared.android.volley.repository.ClubDAO;
import org.jared.android.volley.repository.VolleyDatabase;
import org.jared.android.volley.ui.ClubActivity;
import org.jared.android.volley.ui.ClubActivity_;
import org.jared.android.volley.ui.adapter.ClubAdapter;
import org.jared.android.volley.ui.adapter.SectionAdapter;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/**
 * Fragment pour les favoris
 * @author eric.taix@gmail.com
 */
public class ClubFragmentProvider extends BaseFragmentProvider {

	private static final int SHOW_CLUB = 0;
	
	private SectionAdapter sectionAdapter;
	private ClubAdapter allAdapter;
	private ClubAdapter favoriteAdapter;
	
	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#getCode()
	 */
	@Override
	public String getCode() {
		return "CLUBS";
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Clubs";
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		Intent intent = new Intent(fragment.getActivity(), ClubActivity_.class);
		intent.putExtra(ClubActivity.EXTRA_CLUB, (Club) sectionAdapter.getItem(position));
		fragment.startActivityForResult(intent, SHOW_CLUB);
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.ContentFragmentProvider#getListAdapter()
	 */
	@Override
	public ListAdapter getListAdapter() {
		sectionAdapter = new SectionAdapter(fragment.getActivity(),  R.layout.list_header);
		allAdapter = new ClubAdapter(fragment.getActivity());
		favoriteAdapter = new ClubAdapter(fragment.getActivity());
		sectionAdapter.addSection("FAVORIS", favoriteAdapter);
		sectionAdapter.addSection("TOUS", allAdapter);
		return sectionAdapter;
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#doUpdateUI(org.jared.android.volley.repository.VolleyDatabase)
	 */
	@Override
	public void doUpdateUI(VolleyDatabase db) {
		List<Club> clubs = ClubDAO.getAllClubs(db);
		if (clubs != null) {
			allAdapter.setClubs(clubs);
			List<Club> favClubs = getFavoriteClubs(clubs);
			favoriteAdapter.setClubs(favClubs);
			sectionAdapter.notifyDataSetChanged();
		} 
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#doGetFromNetwork(org.jared.android.volley.http.RestClient)
	 */
	@Override
	public Object doGetFromNetwork(RestClient client) {
		ClubListResponse clubList = client.getClubs();
		return clubList.clubs;
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#doSaveToDatabase(java.lang.Object, org.jared.android.volley.repository.VolleyDatabase)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doSaveToDatabase(Object object, VolleyDatabase db) {
		ClubDAO.saveAll(db, (List<Club>)object);
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
