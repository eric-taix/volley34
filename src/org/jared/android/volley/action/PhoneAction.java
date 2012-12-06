/**
 * 
 */
package org.jared.android.volley.action;

import org.jared.android.volley.widget.quickaction.Action;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Action permettant d'appeler un numéro
 * 
 * @author eric.taix@gmail.com
 */
public class PhoneAction implements Action {
	
	private String phoneNumber;

	public PhoneAction(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jared.android.volley.widget.quickaction.Action#execute(android.content.Context)
	 */
	@Override
	public void execute(Context context) {
		String uri = "tel:" + phoneNumber;
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse(uri));
		context.startActivity(Intent.createChooser(intent, "Appeler..."));
	}

}
