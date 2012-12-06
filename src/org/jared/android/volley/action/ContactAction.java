/**
 * 
 */
package org.jared.android.volley.action;

import org.jared.android.volley.model.Club;
import org.jared.android.volley.widget.quickaction.Action;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;

/**
 * Action permettant de rajouter un contact
 * 
 * @author eric.taix@gmail.com
 */
public class ContactAction implements Action {

	private Club club;

	public ContactAction(Club club) {
		this.club = club;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jared.android.volley.widget.quickaction.Action#execute(android.content.Context)
	 */
	@Override
	public void execute(Context context) {
		Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
		intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
		intent.putExtra(ContactsContract.Intents.Insert.NAME, club.contact);
		intent.putExtra(ContactsContract.Intents.Insert.COMPANY, club.nom);
		if (club.telephone != null && club.telephone.length() > 0) {
			intent.putExtra(ContactsContract.Intents.Insert.PHONE, club.telephone);
			intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
		}
		if (club.mail != null && club.mail.length() > 0) {
			intent.putExtra(ContactsContract.Intents.Insert.EMAIL, club.mail);
			intent.putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_OTHER);
		}
		context.startActivity(intent);
	}

}
