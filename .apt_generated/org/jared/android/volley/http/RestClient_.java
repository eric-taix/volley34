//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package org.jared.android.volley.http;

import java.util.Collections;
import java.util.HashMap;
import org.jared.android.volley.model.ClubListResponse;
import org.jared.android.volley.model.EquipeDetailResponse;
import org.jared.android.volley.model.EquipesClubResponse;
import org.jared.android.volley.model.EventsResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestClient_
    implements RestClient
{

    private RestTemplate restTemplate;
    private String rootUrl;

    public RestClient_() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
        rootUrl = "http://webservices.volley34.fr";
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Override
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public EquipesClubResponse getEquipes(String codeClub) {
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("codeClub", codeClub);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/xml")));
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        return restTemplate.exchange(rootUrl.concat("/wsEquipes.asmx/GetEquipesClub?CodeClub={codeClub}"), HttpMethod.GET, requestEntity, EquipesClubResponse.class, urlVariables).getBody();
    }

    @Override
    public EquipeDetailResponse getEquipeDetail(String codeEquipe) {
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("codeEquipe", codeEquipe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/xml")));
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        return restTemplate.exchange(rootUrl.concat("/wsEquipes.asmx/GetEquipeInfo?CodeEquipe={codeEquipe}"), HttpMethod.GET, requestEntity, EquipeDetailResponse.class, urlVariables).getBody();
    }

    @Override
    public ClubListResponse getClubs() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/xml")));
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        return restTemplate.exchange(rootUrl.concat("/wsEquipes.asmx/GetClub"), HttpMethod.GET, requestEntity, ClubListResponse.class).getBody();
    }

    @Override
    public EventsResponse getClubCalendar(String codeClub) {
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("codeClub", codeClub);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/xml")));
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        return restTemplate.exchange(rootUrl.concat("/wsCalendriers.asmx/GetCalendrierClub?match=true&tournoi=true&federaux=true&reunion=true&autre=true&datesFutures=true&clubCode={codeClub}"), HttpMethod.GET, requestEntity, EventsResponse.class, urlVariables).getBody();
    }

    @Override
    public EventsResponse getCalendar(String codeEquipe) {
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("codeEquipe", codeEquipe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/xml")));
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        return restTemplate.exchange(rootUrl.concat("/wsCalendriers.asmx/GetCalendrier?match=true&tournoi=true&federaux=true&reunion=true&autre=true&datesFutures=true&equipeCode={codeEquipe}"), HttpMethod.GET, requestEntity, EventsResponse.class, urlVariables).getBody();
    }

}
