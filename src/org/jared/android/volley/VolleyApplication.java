package org.jared.android.volley;


import java.util.ArrayList;
import java.util.List;

import org.jared.android.volley.http.RestClient;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Application;

import com.googlecode.androidannotations.annotations.EApplication;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Application de Volley34
 * @author eric.taix@gmail.com
 */
@EApplication
public class VolleyApplication extends Application {

	@RestService
	public RestClient restClient;
	
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
		// Init du template REST afin de prendre en compte la stratégie d'annotation
		// ATTENTION: l'instance du RestClient est partagée et doit l'être !!! 
		// Donc il ne faut pas instancier un autre RestClient
		RestTemplate template = restClient.getRestTemplate();
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		Strategy strategy = new AnnotationStrategy();
		Serializer serializer = new Persister(strategy);
		SimpleXmlHttpMessageConverter simpleConverter = new SimpleXmlHttpMessageConverter(serializer);
		converters.add(simpleConverter);
		template.setMessageConverters(converters); 
	}
}
