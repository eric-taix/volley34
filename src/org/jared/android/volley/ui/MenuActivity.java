package org.jared.android.volley.ui;

import org.jared.android.volley.R;
import org.jared.android.volley.ui.fragment.ContentFragment_;
import org.jared.android.volley.ui.fragment.MenuFragment_;
import org.jared.android.volley.ui.fragment.provider.ChampionnatFragmentProvider;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
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
public class MenuActivity extends SlidingFragmentActivity implements RefreshableActivity {
	private Fragment mContent;
	private boolean landScape = false; 
	@ViewById(R.id.content_frame)
	View content;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.main_title);
		
		// Si il n'y a rien on affiche le fragment par défaut (le championnat)
		if (mContent == null) { 
			mContent = new ContentFragment_(); 
			((ContentFragment_)mContent).setProvider(new ChampionnatFragmentProvider());
			Bundle args = new Bundle();
			mContent.setArguments(args); 
		}
		 
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	}

	@AfterViews
	public void afterViews() {
		// On vérifie si on est en mode portrait
		if (findViewById(R.id.menu_frame) == null) {
			landScape = false;
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			// On affiche l'icon up qui servira à ouvrir le menu
	 		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		else {
			landScape = true;
			// Vue bidon derrière car aucun besoin d'avoir cette vue
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
		sm.setSelectorEnabled(true);
		sm.setSelectorDrawable(R.drawable.ic_club);
		setSlidingActionBarEnabled(false);
		showIndeterminate(false);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	if (!landScape) {
				if (!getSlidingMenu().isMenuShowing()) {
					getSlidingMenu().showMenu(true);
					return true; 
				}
			}
	    	finish();
	    	return true;
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
	 * Replace any existing fragment with this one inside the frame
	 * @param fragment
	 */
	public void switchContent(View source, final Fragment fragment) {
		mContent = fragment;
		//Animation.fadeOut(content, 200);
		// Execute the transaction, 
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
//        ft.setCustomAnimations(
//                R.anim.card_flip_right_in, R.anim.card_flip_right_out,
//                R.anim.card_flip_left_in, R.anim.card_flip_left_out);
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
		//Animation.fadeIn(content, 1000); 
        
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
	}
}
