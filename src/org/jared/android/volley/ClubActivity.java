/**
 * 
 */
package org.jared.android.volley;

import org.jared.android.volley.action.ContactAction;
import org.jared.android.volley.action.MailAction;
import org.jared.android.volley.action.PhoneAction;
import org.jared.android.volley.action.ShareAction;
import org.jared.android.volley.action.SmsAction;
import org.jared.android.volley.adapter.ClubContactAdapter;
import org.jared.android.volley.adapter.ClubInformationAdapter;
import org.jared.android.volley.adapter.SectionAdapter;
import org.jared.android.volley.model.Club;
import org.jared.android.volley.widget.quickaction.Action;
import org.jared.android.volley.widget.quickaction.ActionItem;
import org.jared.android.volley.widget.quickaction.QuickAction;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.ViewById;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * ActivitŽ permettant d'afficher un club
 * 
 * @author eric.taix@gmail.com
 */
@EActivity(value = R.layout.club_detail_layout)
public class ClubActivity extends SherlockActivity implements OnItemClickListener {

	public static final String EXTRA_CLUB = "CLUB";

	public static final int ID_MAIL = 0;
	public static final int ID_PHONE = 1;
	public static final int ID_SMS = 2;
	public static final int ID_CONTACT = 3;
	public static final int ID_SHARE = 4;

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
	// Options de l'ImageLoader pour les logo des clubs: cache mŽmoire + cache disque
	private static DisplayImageOptions logoOptions = new DisplayImageOptions.Builder().showStubImage(R.drawable.empty).showImageForEmptyUri(R.drawable.empty)
			.cacheInMemory().build();

	private QuickAction quickAction;

	/*
	 * (non-Javadoc)
	 * 
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
			// On affiche le logo du club en t‰che de fond
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(currentClub.urlLogo, logo, logoOptions);
		}
		else {
			logo.setVisibility(View.INVISIBLE);
		}
		favorite.setImageDrawable(currentClub.favorite ? getResources().getDrawable(R.drawable.ic_star_enabled) : getResources().getDrawable(
				R.drawable.ic_star_disabled));
		listView.setOnItemClickListener(this);
		// CrŽation du menu pour le contact
		quickAction = new QuickAction(this);
		if (currentClub.mail != null && currentClub.mail.length() > 0) {
			ActionItem mailAction = new ActionItem(ID_MAIL, "Envoyer un email", getResources().getDrawable(R.drawable.ic_mail), new MailAction(currentClub.mail, currentClub.nom));
			quickAction.addActionItem(mailAction);
		}
		if (currentClub.telephone != null && currentClub.telephone.length() > 0) {
			ActionItem phoneAction = new ActionItem(ID_PHONE, "TŽlŽphoner", getResources().getDrawable(R.drawable.ic_phone), new PhoneAction(currentClub.telephone));
			ActionItem smsAction = new ActionItem(ID_SMS, "Envoyer un SMS", getResources().getDrawable(R.drawable.ic_sms),  new SmsAction(currentClub.telephone));
			quickAction.addActionItem(phoneAction);
			quickAction.addActionItem(smsAction);
		}
		if ((currentClub.telephone != null && currentClub.telephone.length() > 0) || (currentClub.mail != null && currentClub.mail.length() > 0)) {
			ActionItem contactAction = new ActionItem(ID_CONTACT, "Ajouter aux contacts", getResources().getDrawable(R.drawable.ic_address_book), new ContactAction(currentClub));
			ActionItem shareAction = new ActionItem(ID_SHARE, "Partager", getResources().getDrawable(R.drawable.ic_share), new ShareAction(currentClub));
			quickAction.addActionItem(contactAction);
			quickAction.addActionItem(shareAction);
		}

 

		// Mise en place du listener sur le quick action
		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				ActionItem actionItem = quickAction.getActionItem(pos);
				Action action = actionItem.getAction();
				executeAction(action);
			}
		});
	}

	@Background
	public void executeAction(Action action) {
		if (action != null) {
			action.execute(ClubActivity.this);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Le site Web
		if (position == 1) {
			if (currentClub.urlSiteWeb != null && currentClub.urlSiteWeb.length() > 0) {
				String url = currentClub.urlSiteWeb;
				if (!url.toLowerCase().startsWith("http://") || !url.toLowerCase().startsWith("https://")) {
					url = "http://" + url;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				startActivity(intent);
			}
		}
		// Le contact
		else if (position == 3) {
			quickAction.show(view);
		}
		// Une des Žquipes
		else {

		}
	}

}
