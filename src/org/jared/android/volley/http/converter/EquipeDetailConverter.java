package org.jared.android.volley.http.converter;

import org.jared.android.volley.model.ContactEquipe;
import org.jared.android.volley.model.EquipeDetail;
import org.jared.android.volley.model.Gymnase;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

public class EquipeDetailConverter implements Converter<EquipeDetail> {

	public EquipeDetail read(InputNode node) {
		EquipeDetail result = new EquipeDetail();
		try {
			// Les contacts
			result.contactRespChampionnat = new ContactEquipe();
			result.contactRespChampionnat.setNom(node.getAttribute("NomResp").getValue());
			result.contactRespChampionnat.setMobile(node.getAttribute("MobileResp").getValue());
			result.contactRespChampionnat.setTelephone(node.getAttribute("TelephoneResp").getValue());
			result.contactRespChampionnat.setMail(node.getAttribute("MailResp").getValue());

			result.contactSupplChampionnat = new ContactEquipe();
			result.contactSupplChampionnat.setNom(node.getAttribute("NomSuppl").getValue());
			result.contactSupplChampionnat.setMobile(node.getAttribute("MobileSuppl").getValue());
			result.contactSupplChampionnat.setTelephone(node.getAttribute("TelephoneSuppl").getValue());
			result.contactSupplChampionnat.setMail(node.getAttribute("MailSuppl").getValue());

			result.contactRespCoupe = new ContactEquipe();
			result.contactRespCoupe.setNom(node.getAttribute("NomRespCoupe").getValue());
			result.contactRespCoupe.setMobile(node.getAttribute("MobileRespCoupe").getValue());
			result.contactRespCoupe.setTelephone(node.getAttribute("TelephoneRespCoupe").getValue());
			result.contactRespCoupe.setMail(node.getAttribute("MailRespCoupe").getValue());

			result.contactSupplCoupe = new ContactEquipe();
			result.contactSupplCoupe.setNom(node.getAttribute("NomSupplCoupe").getValue());
			result.contactSupplCoupe.setMobile(node.getAttribute("MobileSupplCoupe").getValue());
			result.contactSupplCoupe.setTelephone(node.getAttribute("TelephoneSupplCoupe").getValue());
			result.contactSupplCoupe.setMail(node.getAttribute("MailSupplCoupe").getValue());
			
			// Les gymnases
			result.gymnaseChampionnat = new Gymnase();
			result.gymnaseChampionnat.gps = node.getAttribute("GPSGymnase").getValue();
			result.gymnaseChampionnat.telephone = node.getAttribute("TelGymnase").getValue();
			result.gymnaseChampionnat.ville = node.getAttribute("VilleGymnase").getValue();
			result.gymnaseChampionnat.quartier = node.getAttribute("QuartierGymnase").getValue();
			result.gymnaseChampionnat.cp = node.getAttribute("CPGymnase").getValue();
			result.gymnaseChampionnat.adresse = node.getAttribute("AdresseGymnase").getValue();
			result.gymnaseChampionnat.nomComplet = node.getAttribute("NomCompletGymnase").getValue();
			result.gymnaseChampionnat.nom = node.getAttribute("NomGymnase").getValue();
			result.gymnaseChampionnat.heure = node.getAttribute("Heure").getValue();
			result.gymnaseChampionnat.jour = node.getAttribute("JourSemaine").getValue();
			
			result.gymnaseCoupe = new Gymnase();
			result.gymnaseCoupe.gps = node.getAttribute("GPSGymnaseCoupe").getValue();
			result.gymnaseCoupe.telephone = node.getAttribute("TelGymnaseCoupe").getValue();
			result.gymnaseCoupe.ville = node.getAttribute("VilleGymnaseCoupe").getValue();
			result.gymnaseCoupe.quartier = node.getAttribute("QuartierGymnaseCoupe").getValue();
			result.gymnaseCoupe.cp = node.getAttribute("CPGymnaseCoupe").getValue();
			result.gymnaseCoupe.adresse = node.getAttribute("AdresseGymnaseCoupe").getValue();
			result.gymnaseCoupe.nomComplet = node.getAttribute("NomCompletGymnaseCoupe").getValue();
			result.gymnaseCoupe.nom = node.getAttribute("NomGymnaseCoupe").getValue();
			result.gymnaseCoupe.heure = node.getAttribute("HeureCoupe").getValue();
			result.gymnaseCoupe.jour = node.getAttribute("JourSemaineCoupe").getValue();
			
			
		}
		catch (Exception ex) {
		}
		return result;
	}

	public void write(OutputNode node, EquipeDetail external) {
	}
}
