package org.jared.android.volley;

import org.jared.android.volley.fragment.BirdGridFragment;
import org.jared.android.volley.fragment.MenuFragment_;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;  
import android.view.View;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Activité permettant d'afficher le menu ainsi que le fragment qui correspond au menu courant
 * 
 * @author eric.taix@gmail.com
 */
@EActivity(R.layout.activity_main)
public class MenuActivity extends SlidingFragmentActivity implements RefreshableActivity {
	private Fragment mContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.main_title);
		// Si une instance du contenu a été sauvegardé on l'affiche
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		}
		// Si il n'y a rien on affiche le fragment par défaut (le championnat)
		if (mContent == null) {
			mContent = new BirdGridFragment(0);  
		}
		
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	}

	@AfterViews
	public void afterViews() {
		// On vérifie si on est en mode portrait
		if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			// On affiche l'icon up qui servira à ouvrir le menu
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		else {
			// add a dummy view
			setBehindContentView(new View(this));
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}

		// On affiche le contenu courant
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();
		// On affiche le menu
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment_()).commit();

		// Customisation du SlindingMenu 
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width); 
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindScrollScale(0.5f);
		sm.setFadeDegree(0.5f);
		setSlidingActionBarEnabled(false);
		showIndeterminate(false);
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
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	/**
	 * Méthode permettant d'afficher la progression
	 * @param visible
	 */
	@Override
	public void showIndeterminate(boolean visible) {
		setSupportProgressBarIndeterminateVisibility(visible);
	}
	
	/**
	 * Remplace le contenu courant par le fragment passé en paramètre
	 * @param fragment
	 */
	public void switchContent(final Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}

	public void onBirdPressed(int pos) {
	}

}
