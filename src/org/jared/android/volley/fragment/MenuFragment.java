package org.jared.android.volley.fragment;

import java.util.HashMap;
import java.util.Map;

import org.jared.android.volley.MenuActivity;
import org.jared.android.volley.R;
import org.jared.android.volley.adapter.Menu;
import org.jared.android.volley.adapter.MenuAdapter;
import org.jared.android.volley.adapter.SectionAdapter;

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
@EFragment(value = R.layout.list)
public class MenuFragment extends ListFragment {

	public final static String ITEM_TITLE = "title";
	public final static String ITEM_CAPTION = "caption";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Cr�e les listes qui forment le menu
		SectionAdapter adapter = new SectionAdapter(this.getActivity());
		adapter.addSection("MENU", new MenuAdapter(getActivity(), new Menu[] { new Menu("Championnat", R.drawable.ic_championnat),
				new Menu("Coupe", R.drawable.ic_coupe), new Menu("Equipes", R.drawable.ic_equipes), new Menu("Clubs", R.drawable.ic_club) }));
		adapter.addSection("OUTILS", new MenuAdapter(getActivity(), new Menu[] { new Menu("R�glages", R.drawable.ic_reglages),
				new Menu("A propos", R.drawable.ic_apropos) }));
		getListView().setBackgroundColor(getResources().getColor(R.color.background_dark));
		getListView().setCacheColorHint(getResources().getColor(R.color.transparent));
		// On positionne un divider plus "sympa"
		int[] colors = { 0, 0xFF777777, 0 };
		getListView().setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		getListView().setDividerHeight(1);
		setListAdapter(adapter);
	}

	public Map<String, ?> createItem(String title, String caption) {
		Map<String, String> item = new HashMap<String, String>();
		item.put(ITEM_TITLE, title);
		item.put(ITEM_CAPTION, caption);
		return item;
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = new BirdGridFragment(position);
		if (newContent != null) switchFragment(newContent);
	}

	/**
	 * M�thode permettant de changer le fragment courant en d�legant � l'activit� parent
	 * 
	 * @param fragment
	 */
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null) return;

		if (getActivity() instanceof MenuActivity) {
			MenuActivity ma = (MenuActivity) getActivity();
			ma.switchContent(fragment);
		}
	}

}
