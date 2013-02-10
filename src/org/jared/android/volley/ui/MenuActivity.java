package org.jared.android.volley.ui;

import org.jared.android.volley.R;
import org.jared.android.volley.ui.fragment.FavorisFragment_;
import org.jared.android.volley.ui.fragment.MenuFragment_;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.actionbarsherlock.view.MenuItem;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Main activity which manages the menu and the detail content
 * 
 * @author eric.taix@gmail.com
 */
@EActivity(R.layout.activity_main)
public class MenuActivity extends SlidingFragmentActivity {
	private boolean landScape = false;
	@ViewById(R.id.content_frame)
	View content;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.main_title);
	}

	@AfterViews
	public void afterViews() {
		// Verify if we are in portrait or landscape
		if (findViewById(R.id.menu_frame) == null) {
			landScape = false;
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			// On affiche l'icon up qui servira ˆ ouvrir le menu
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		else {
			landScape = true;
			// Add a fake wiew behind because we don't need it : the sliding menu we be always visible
			setBehindContentView(new View(this));
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}

		// Display the current content : nothing
		switchContent(null, new FavorisFragment_());
		// Display the menu
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment_()).commit();

		// Customize ht SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0.5f);
		sm.setFadeDegree(0.5f);
		sm.setSelectorEnabled(true);
		sm.setSelectorDrawable(R.drawable.ic_club);
		setSlidingActionBarEnabled(false);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	if (!landScape && getSupportFragmentManager().getBackStackEntryCount() == 0) {
				if (!getSlidingMenu().isMenuShowing()) {
					getSlidingMenu().showMenu(true);
					return true; 
				}
			}
	    	 return super.onKeyUp(keyCode, event);
	    }
	    return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	/**
	 * Replace any existing fragment with this one inside the frame
	 * @param fragment
	 */
	public void switchContent(View source, final Fragment fragment) {
		// Execute the transaction,
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.replace(R.id.content_frame, fragment);
		ft.addToBackStack("");
		ft.commit();

		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}
}
