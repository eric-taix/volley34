/**
 * 
 */
package org.jared.android.volley.fragment;

import org.jared.android.volley.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragment;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.viewpagerindicator.TabPageIndicator;

/**
 * Le fragment permettant d'afficher les championnats
 * 
 * @author eric.taix@gmail.com
 */
@EFragment(R.layout.tabs_layout)
public class ChampionnatFragment extends SherlockFragment {

	@ViewById(R.id.pager)
	ViewPager pager;
	
	@ViewById(R.id.indicator)
	TabPageIndicator indicator;
	
	@AfterViews
	public void afterView() {
		FragmentPagerAdapter adapter = new GoogleMusicAdapter(getFragmentManager());
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
	}

	private static final String[] CONTENT = new String[] { "4x4 MASCULIN", "4x4 MIXTE","6x6 MASCULIN","6x6 MIXTE","6x6 FEMININ"};

	class GoogleMusicAdapter extends FragmentPagerAdapter {
		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return new BirdGridFragment(position);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return CONTENT[position % CONTENT.length];
		}

		@Override
		public int getCount() {
			return CONTENT.length;
		}
	}
}
