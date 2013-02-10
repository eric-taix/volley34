/**
 * 
 */
package org.jared.android.volley.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.R;
import org.jared.android.volley.model.Contact;
import org.jared.android.volley.ui.action.ContactAction;
import org.jared.android.volley.ui.action.MailAction;
import org.jared.android.volley.ui.action.PhoneAction;
import org.jared.android.volley.ui.action.SmsAction;
import org.jared.android.volley.ui.widget.quickaction.Action;
import org.jared.android.volley.ui.widget.quickaction.ActionItem;
import org.jared.android.volley.ui.widget.quickaction.QuickAction;
import org.jared.android.volley.utils.MD5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * This adapter aims to adapt a Personn contact (its name, phone number, email) and also provides a button to be in touch with (phone call, email, SMS)
 * @author eric.taix@gmail.com
 */
public class ContactAdapter extends BaseAdapter {

	private static final int ID_MAIL = 0;
	private static final int ID_PHONE = 1;
	private static final int ID_SMS = 2;
	private static final int ID_CONTACT = 3;
	//private static final int ID_SHARE = 4;

	protected List<Contact> contacts;
	protected Context ctx;

	// Options for the ImageLoader
	private static DisplayImageOptions avatarOptions = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_unknown)
			.showImageForEmptyUri(R.drawable.ic_unknown).cacheInMemory().build();

	/**
	 * Constructor which initialize the maximum number of items with the default value
	 * 
	 * @param ctx
	 * @param maxItems
	 */
	public ContactAdapter(Context ctx) {
		this.ctx = ctx;
	}

	/**
	 * Remove all contacts
	 */
	public void clear() {
		if (this.contacts != null) {
			this.contacts.clear();
		}
	}

	/**
	 * Add a contact
	 * 
	 * @param contacts
	 */
	public void addContact(Contact contact) {
		if (this.contacts == null) {
			this.contacts = new ArrayList<Contact>();
		}
		this.contacts.add(contact);
		notifyDataSetChanged();
	}

	/**
	 * Create a QuickAction instance according to the contact parameter
	 * @param contact
	 * @return
	 */
	private QuickAction createQuickAction(Contact contact) {
		// CrŽation du menu pour le contact
		QuickAction quickAction = new QuickAction(this.ctx);
		if (contact.getMail() != null && contact.getMail().length() > 0) {
			ActionItem mailAction = new ActionItem(ID_MAIL, "Envoyer un email", ctx.getResources().getDrawable(R.drawable.ic_mail), new MailAction(
					contact.getMail(), contact.getNom()));
			quickAction.addActionItem(mailAction);
		}
		if (contact.getMobile() != null && contact.getMobile().length() > 0) {
			ActionItem phoneAction = new ActionItem(ID_PHONE, "TŽlŽphoner", ctx.getResources().getDrawable(R.drawable.ic_phone), new PhoneAction(
					contact.getMobile()));
			ActionItem smsAction = new ActionItem(ID_SMS, "Envoyer un SMS", ctx.getResources().getDrawable(R.drawable.ic_sms), new SmsAction(contact.getMobile()));
			quickAction.addActionItem(phoneAction);
			quickAction.addActionItem(smsAction);
		}
		else if (contact.getTelephone() != null && contact.getTelephone().length() > 0) {
			ActionItem phoneAction = new ActionItem(ID_PHONE, "TŽlŽphoner", ctx.getResources().getDrawable(R.drawable.ic_phone), new PhoneAction(
					contact.getTelephone()));
			quickAction.addActionItem(phoneAction);
		}
		if ((contact.getMobile() != null && contact.getMobile().length() > 0) || (contact.getMail() != null && contact.getMail().length() > 0)) {
			ActionItem contactAction = new ActionItem(ID_CONTACT, "Ajouter aux contacts", ctx.getResources().getDrawable(R.drawable.ic_address_book),
					new ContactAction(contact));
			//ActionItem shareAction = new ActionItem(ID_SHARE, "Partager", ctx.getResources().getDrawable(R.drawable.ic_share), new ShareAction(contact));
			quickAction.addActionItem(contactAction);
			//quickAction.addActionItem(shareAction);
		}
		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				ActionItem actionItem = quickAction.getActionItem(pos);
				Action action = actionItem.getAction();
				if (action != null) {
					action.execute(ctx);
				}
			}
		});
		return quickAction;
	}

	/**
	 * Set the new list of contacts
	 * 
	 * @param contacts
	 */
	public void setContact(List<Contact> contacts) {
		this.contacts = contacts;
		notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.BaseAdapter#areAllItemsEnabled()
	 */
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.BaseAdapter#isEnabled(int)
	 */
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (contacts != null) {
			return contacts.size();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (contacts != null && position < contacts.size()) {
			return contacts.get(position);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@SuppressLint("DefaultLocale")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.club_contact_layout, parent, false);
		}
		ImageView imgPhone = (ImageView) convertView.findViewById(R.id.img_phone);
		TextView phone = (TextView) convertView.findViewById(R.id.phone);
		ImageView imgMail = (ImageView) convertView.findViewById(R.id.img_mail);
		TextView mail = (TextView) convertView.findViewById(R.id.mail);
		TextView nom = (TextView) convertView.findViewById(R.id.nom);
		ImageView avatar = (ImageView) convertView.findViewById(R.id.portrait);

		// On rŽcupre l'ŽlŽment ˆ afficher
		Contact contact = contacts.get(position);
		if (contact != null) {
			// Le button et l'action sur ce bouton
			Button button = (Button) convertView.findViewById(R.id.button_contact);
			final View anchorView = convertView;
			final QuickAction quickAction = createQuickAction(contact);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					quickAction.show(anchorView);
				}
			});
			// Les informations ˆ afficher
			nom.setText(contact.getNom());
			if ((contact.getTelephone() != null && contact.getTelephone().trim().length() > 0)
					|| (contact.getMobile() != null && contact.getMobile().trim().length() > 0)) {
				String t = contact.getMobile();
				if (t == null || t.trim().length() == 0) {
					t = contact.getTelephone();
				}
				phone.setText(t);
				phone.setVisibility(View.VISIBLE);
				imgPhone.setVisibility(View.VISIBLE);
			}
			else {
				phone.setVisibility(View.GONE);
				imgPhone.setVisibility(View.GONE);
			}

			if (contact.getMail() != null) {
				mail.setText(contact.getMail());
				mail.setVisibility(View.VISIBLE);
				imgMail.setVisibility(View.VISIBLE);
				// Calculate the MD5 of the email
				String md5 = MD5.calcMD5(contact.getMail().trim().toLowerCase());
				String url = "http://www.gravatar.com/avatar/" + md5 + "?d=mm";
				ImageLoader imageLoader = ImageLoader.getInstance();
				imageLoader.displayImage(url, avatar, avatarOptions);
			}
			else {
				mail.setVisibility(View.VISIBLE);
				imgMail.setVisibility(View.VISIBLE);
			}
		}
		return convertView;
	}

}
