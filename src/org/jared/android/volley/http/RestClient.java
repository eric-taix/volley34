package org.jared.android.volley.http;

import org.jared.android.volley.model.ClubList;
import org.jared.android.volley.model.EquipeClubList;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;

@Rest(rootUrl="http://webservices.volley34.fr",converters = { SimpleXmlHttpMessageConverter.class , FormHttpMessageConverter.class})
@Accept(MediaType.APPLICATION_XML)
public interface RestClient {

	@Get("/wsEquipes.asmx/GetClub")
	ClubList getClubs();
	@Get("/wsEquipes.asmx/GetEquipesClub?CodeClub={codeClub}")
	EquipeClubList getEquipes(String codeClub);
	@Get("/wsEquipes.asmx/GetEquipeInfo?CodeEquipe={codeEquipe}")
	void getEquipeDetail(String codeEquipe);
	
	//----------------------------
	RestTemplate getRestTemplate();

	void setRestTemplate(RestTemplate restTemplate);

}
