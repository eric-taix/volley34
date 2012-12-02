package org.jared.android.volley;


import com.googlecode.androidannotations.annotations.EApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

/**
 * Application de Volley34
 * @author eric.taix@gmail.com
 */
@EApplication
public class VolleyApplication extends Application {

	/**
	 * Creation de l'application
	 */
	public void onCreate() {
		super.onCreate();
		// On initialise l'ImageLoader
		ImageLoader imageLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).
				defaultDisplayImageOptions(DisplayImageOptions.createSimple()).build();
		imageLoader.init(config);
	}
}
