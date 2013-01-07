package org.jared.android.volley.http;

import org.jared.android.volley.model.ClubListResponse;
import org.jared.android.volley.model.EquipeDetailResponse;
import org.jared.android.volley.model.EquipesClubResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;

@Rest(rootUrl="http://webservices.volley34.fr",converters = { FormHttpMessageConverter.class})
@Accept(MediaType.APPLICATION_XML)
public interface RestClient {

	@Get("/wsEquipes.asmx/GetClub")
	ClubListResponse getClubs();
	@Get("/wsEquipes.asmx/GetEquipesClub?CodeClub={codeClub}")
	EquipesClubResponse getEquipes(String codeClub);
	@Get("/wsEquipes.asmx/GetEquipeInfo?CodeEquipe={codeEquipe}")
	EquipeDetailResponse getEquipeDetail(String codeEquipe);
	
	//----------------------------
	RestTemplate getRestTemplate();

	void setRestTemplate(RestTemplate restTemplate);

}
