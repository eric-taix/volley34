package org.jared.android.volley.ui.fragment;

import org.jared.android.volley.R;
import org.jared.android.volley.model.Menu;
import org.jared.android.volley.ui.MenuActivity;
import org.jared.android.volley.ui.adapter.commons.MenuAdapter;
import org.jared.android.volley.ui.adapter.commons.Section;
import org.jared.android.volley.ui.adapter.commons.SectionAdapter;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.EFragment;

/**
 * Fragment permettant d'afficher le menu
 * 
 * @author eric.taix@gmail.com
 */
@EFragment(R.layout.list)
public class MenuFragment extends ListFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Crée les listes qui forment le menu
		SectionAdapter adapter = new SectionAdapter(this.getActivity(), R.layout.menu_header);
		adapter.addSection(new Section(1, "MENU", new MenuAdapter(getActivity(), new Menu[] { new Menu("Favoris", R.drawable.ic_star_enabled), new Menu("Championnat", R.drawable.ic_championnat),
				new Menu("Coupe", R.drawable.ic_coupe), new Menu("Equipes", R.drawable.ic_equipes), new Menu("Clubs", R.drawable.ic_club) })));
		adapter.addSection(new Section(2, "OUTILS", new MenuAdapter(getActivity(), new Menu[] { new Menu("Réglages", R.drawable.ic_reglages),
				new Menu("A propos", R.drawable.ic_apropos) })));
		getListView().setBackgroundColor(getResources().getColor(R.color.background_menu));
		getListView().setCacheColorHint(getResources().getColor(R.color.transparent));
		// On positionne un divider plus "sympa"
		int[] colors = { 0, 0xFF777777, 0 };
		getListView().setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		getListView().setDividerHeight(1);
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		Bundle args = new Bundle();
		switch(position) {
		case 1:
			newContent = new FavorisFragment_();
			break;
		case 2:
			newContent = new ChampionnatFragment_();
			break;
		case 3:
			newContent = new CoupeFragment_();
			break;
		case 4:
			newContent = new EquipesFragment_();
			break;
		case 5:
			newContent = new ClubsFragment_();
			break;
		case 6:
			newContent = new DefaultFragment_();
			break;
		case 7:
			newContent = new DefaultFragment_();
			break;
		default: 
			newContent = new DefaultFragment_();
		}
		newContent.setArguments(args);
		if (newContent != null) switchFragment(v, newContent);
	}

	/**
	 * Méthode permettant de changer le fragment courant en délegant à l'activité parent
	 * 
	 * @param fragment
	 */
	private void switchFragment(View source, Fragment fragment) {
		if (getActivity() == null) return;

		if (getActivity() instanceof MenuActivity) {
			MenuActivity ma = (MenuActivity) getActivity();
			ma.switchContent(source, fragment);
		}
	}

}
