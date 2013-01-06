/**
 * 
 */
package org.jared.android.volley.ui.action;

import org.jared.android.volley.ui.widget.quickaction.Action;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Action permettant d'envoyer un SMS
 * 
 * @author eric.taix@gmail.com
 */
public class SmsAction implements Action {
	
	private String phoneNumber;

	public SmsAction(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jared.android.volley.widget.quickaction.Action#execute(android.content.Context)
	 */
	@Override
	public void execute(Context context) {
		String uri =  "sms:"+phoneNumber;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(uri));
		context.startActivity(Intent.createChooser(intent, "Envoyer un SMS..."));
	}

}
