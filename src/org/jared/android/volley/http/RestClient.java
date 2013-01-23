package org.jared.android.volley.http;

import org.jared.android.volley.model.ClubListResponse;
import org.jared.android.volley.model.EquipeDetailResponse;
import org.jared.android.volley.model.EquipesClubResponse;
import org.jared.android.volley.model.EventsResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.googlecode.androidannotations.annotations.rest.Accept;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;
import com.googlecode.androidannotations.api.rest.MediaType;

@Rest(rootUrl="http://webservices.volley34.fr",converters = { FormHttpMessageConverter.class})
@Accept(MediaType.APPLICATION_XML)
public interface RestClient {

	// Liste des clubs
	@Get("/wsEquipes.asmx/GetClub")
	ClubListResponse getClubs();
	
	// Liste des Žquipes d'un clib
	@Get("/wsEquipes.asmx/GetEquipesClub?CodeClub={codeClub}")
	EquipesClubResponse getEquipes(String codeClub);
	
	// DŽtail d'une Žquipe
	@Get("/wsEquipes.asmx/GetEquipeInfo?CodeEquipe={codeEquipe}")
	EquipeDetailResponse getEquipeDetail(String codeEquipe);
	
	// Calendrier futur d'un club
	@Get("/wsCalendriers.asmx/GetCalendrierClub?match=true&tournoi=true&federaux=true&reunion=true&autre=true&datesFutures=true&clubCode={codeClub}")
	EventsResponse getClubCalendar(String codeClub);
	
	// Calendrier futur d'une Žquipe
	@Get("/wsCalendriers.asmx/GetCalendrier?match=true&tournoi=true&federaux=true&reunion=true&autre=true&datesFutures=true&equipeCode={codeEquipe}")
	EventsResponse getCalendar(String codeEquipe);
	
	//----------------------------
	RestTemplate getRestTemplate();

	void setRestTemplate(RestTemplate restTemplate);

}
