/**
 * 
 */
package org.jared.android.volley.ui.action;

import org.jared.android.volley.model.Contact;
import org.jared.android.volley.ui.widget.quickaction.Action;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;

/**
 * Action permettant de rajouter un contact
 * 
 * @author eric.taix@gmail.com
 */
public class ContactAction implements Action {

	private Contact contact;

	public ContactAction(Contact contact) {
		this.contact = contact;
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
		intent.putExtra(ContactsContract.Intents.Insert.NAME, contact.getNom());
		if (contact.getMobile() != null && contact.getMobile().length() > 0) {
			intent.putExtra(ContactsContract.Intents.Insert.PHONE, contact.getMobile());
			intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
		}
		if (contact.getTelephone() != null && contact.getTelephone().length() > 0) {
			intent.putExtra(ContactsContract.Intents.Insert.PHONE, contact.getTelephone());
			intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
		}
		if (contact.getMail() != null && contact.getMail().length() > 0) {
			intent.putExtra(ContactsContract.Intents.Insert.EMAIL, contact.getMail());
			intent.putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_OTHER);
		}
		context.startActivity(intent);
	}

}
