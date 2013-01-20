/**
 * 
 */
package org.jared.android.volley.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.http.RestClient;
import org.jared.android.volley.model.Equipe;
import org.jared.android.volley.model.EquipesClubResponse;
import org.jared.android.volley.repository.EquipeDAO;
import org.jared.android.volley.repository.VolleyDatabase;
import org.jared.android.volley.ui.EquipeActivity_;
import org.jared.android.volley.ui.adapter.MenuEquipesAdapter;
import org.jared.android.volley.ui.adapter.commons.SectionAdapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/**
 * Fragment pour les favoris
 * @author eric.taix@gmail.com
 */
public class EquipeFragmentProvider extends BaseFragmentProvider {

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
		EquipeActivity_.startActivityForResult(fragment, eq.codeEquipe, SHOW_EQUIPE);
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
	public void doUpdateUI(VolleyDatabase db) {
		List<Equipe> equipes = EquipeDAO.getAllEquipes(db, null);
		if (equipes != null) {
			allAdapter.setEquipes(equipes);
			List<Equipe> favEquipes= getFavoriteEquipes(equipes);
			favoriteAdapter.setEquipes(favEquipes);
			sectionAdapter.notifyDataSetChanged();
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
	public void doSaveToDatabase(Object object, VolleyDatabase db) {
		EquipeDAO.saveAll(db, (List<Equipe>)object, null);
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
