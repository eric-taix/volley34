package org.jared.android.volley.http;

import org.jared.android.volley.model.ClubList;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Post;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;

@Rest(rootUrl="http://webservices.volley34.fr",converters = { SimpleXmlHttpMessageConverter.class })
@Accept(MediaType.APPLICATION_XML)
public interface RestClient {

	@Post("/wsEquipes.asmx/GetClub")
	ClubList getClubs();
	
	//----------------------------
	RestTemplate getRestTemplate();

	void setRestTemplate(RestTemplate restTemplate);

}
