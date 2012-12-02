/**
 * 
 */
package org.jared.android.volley;

import org.jared.android.volley.adapter.ClubContactAdapter;
import org.jared.android.volley.adapter.ClubInformationAdapter;
import org.jared.android.volley.adapter.SectionAdapter;
import org.jared.android.volley.model.Club;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * ActivitŽ permettant d'afficher un club
 * @author eric.taix@gmail.com
 */
@EActivity(value = R.layout.club_detail_layout)
public class ClubActivity extends SherlockActivity {
 
	public static final String EXTRA_CLUB = "CLUB";
	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.logo)
	ImageView logo;
	@ViewById(R.id.favorite)
	ImageView favorite;
	
	@ViewById(R.id.listView)
	ListView listView;
	
	// Le club courant
	private Club currentClub;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ActionBar ab = getSupportActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		setTitle("Club");
		// Si l'activitŽ est lancŽe avec des extras (ce qui devrait tre le cas systŽmatiquement)
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Parcelable clubToShow = extras.getParcelable(EXTRA_CLUB);
			if (clubToShow != null) {
				currentClub = (Club) clubToShow;
			}
		}
	}

	@OptionsItem(android.R.id.home)
	boolean backHome() {
		finish();
		return true;
	}

	@AfterViews
	public void afterViews() {
		ClubContactAdapter contactAdapter = new ClubContactAdapter(this, currentClub);
		ClubInformationAdapter informationAdapter = new ClubInformationAdapter(this, currentClub);
		SectionAdapter sectionAdapter = new SectionAdapter(this, R.layout.list_header);
		sectionAdapter.addSection("INFORMATIONS", informationAdapter);
		sectionAdapter.addSection("CONTACT", contactAdapter);
		listView.setCacheColorHint(getResources().getColor(R.color.transparent));
		// On positionne un divider plus "sympa"
		int[] colors = { 0, 0xFF777777, 0 };
		listView.setDivider(new GradientDrawable(Orientation.RIGHT_LEFT, colors));
		listView.setDividerHeight(1);
		listView.setAdapter(sectionAdapter);
		// On fixe les infos
		title.setText(currentClub.nomCourt);
		if (currentClub.urlLogo != null && currentClub.urlLogo.length() > 0) {
			logo.setVisibility(View.VISIBLE);
			
		}
		else {
			logo.setVisibility(View.INVISIBLE);
		}
		favorite.setVisibility(currentClub.favorite ? View.VISIBLE : View.INVISIBLE);
	}
}
