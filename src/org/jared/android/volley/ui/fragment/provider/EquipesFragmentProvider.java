/**
 * 
 */
package org.jared.android.volley.ui.fragment.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.http.RestClient;
import org.jared.android.volley.model.Equipe;
import org.jared.android.volley.model.EquipesClubResponse;
import org.jared.android.volley.ui.adapter.MenuEquipesAdapter;
import org.jared.android.volley.ui.adapter.commons.SectionAdapter;
import org.jared.android.volley.ui.fragment.EquipeFragment;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.j256.ormlite.dao.Dao;

/**
 * Fragment pour les favoris
 * @author eric.taix@gmail.com
 */
public class EquipesFragmentProvider extends BaseFragmentProvider {

	private static final int SHOW_EQUIPE = 0;
	
	private SectionAdapter sectionAdapter;
	private MenuEquipesAdapter allAdapter;
	private MenuEquipesAdapter favoriteAdapter;
	

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#getCode()
	 */
	@Override
	public String getCode() {
		return "EQUIPES";
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Equipes";
	}
	
	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Equipe eq = (Equipe) sectionAdapter.getItem(position);
		EquipeFragment.showEquipe(fragment, eq.codeEquipe, SHOW_EQUIPE);
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.ContentFragmentProvider#getListAdapter()
	 */
	@Override
	public ListAdapter getListAdapter() {
		sectionAdapter = new SectionAdapter(fragment.getActivity(),  R.layout.list_header);
		allAdapter = new MenuEquipesAdapter(fragment.getActivity());
		favoriteAdapter = new MenuEquipesAdapter(fragment.getActivity());
		sectionAdapter.addSection("FAVORIS", favoriteAdapter);
		sectionAdapter.addSection("TOUS", allAdapter);
		return sectionAdapter;
	}
	
	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#doUpdateUI(org.jared.android.volley.repository.VolleyDatabase)
	 */
	@Override
	public void doUpdateUI() {
		try {
			Dao<Equipe, String> equipeDao = getHelper().getDao(Equipe.class);
			List<Equipe> equipes = equipeDao.queryForAll();
			if (equipes != null) {
				allAdapter.setEquipes(equipes);
				List<Equipe> favEquipes= getFavoriteEquipes(equipes);
				favoriteAdapter.setEquipes(favEquipes);
				sectionAdapter.notifyDataSetChanged();
			}
		}
		catch (SQLException e) {
		Log.e("Volley34", "Error while retreiving team list");
		}
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#doGetFromNetwork(org.jared.android.volley.http.RestClient)
	 */
	@Override
	public Object doGetFromNetwork(RestClient client) {
		EquipesClubResponse response = client.getEquipes("");
		return response.equipes;
	}

	/* (non-Javadoc)
	 * @see org.jared.android.volley.ui.fragment.MenuBaseFragment#doSaveToDatabase(java.lang.Object, org.jared.android.volley.repository.VolleyDatabase)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doSaveToDatabase(Object object) {
		try {
			Dao<Equipe, String> equipeDao = getHelper().getDao(Equipe.class);
			List<Equipe> oldEquipes = equipeDao.queryForAll();
			List<Equipe> newEquipes = (List<Equipe>)object;
			// Delete all old teams
			equipeDao.delete(oldEquipes);
			// Set the favorite flag (which does not exist in the REST response)
			for (Equipe equipe : newEquipes) {
				int index = oldEquipes.indexOf(equipe);
				if (index != -1) {
					Equipe oldEquipe = oldEquipes.get(index);
					equipe.favorite = oldEquipe.favorite;
				}
				equipeDao.create(equipe);
			}
		}
		catch (SQLException e) {
			Log.e("Volley34", "Error while retreiving team list");
		}
	}
	
	/**
	 * Retourne uniquement la liste des clubs en favoris
	 * 
	 * @param allClubs
	 * @return
	 */
	private List<Equipe> getFavoriteEquipes(List<Equipe> allEquipes) {
		List<Equipe> retain = new ArrayList<Equipe>(allEquipes.size());
		for (Equipe equipe : allEquipes) {
			if (equipe.favorite) {
				retain.add(equipe);
			}
		}
		return retain;
	}

}
