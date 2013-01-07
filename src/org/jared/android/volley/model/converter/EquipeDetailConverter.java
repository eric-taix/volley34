package org.jared.android.volley.model.converter;

import org.jared.android.volley.model.ContactEquipe;
import org.jared.android.volley.model.EquipeDetail;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

public class EquipeDetailConverter implements Converter<EquipeDetail> {

	public EquipeDetail read(InputNode node) {
		EquipeDetail result = new EquipeDetail();
		try {
			result.contactRespChampionnat = new ContactEquipe();
			result.contactRespChampionnat.nom = node.getAttribute("NomResp").getValue();
			result.contactRespChampionnat.mobile = node.getAttribute("MobileResp").getValue();
			result.contactRespChampionnat.telephone = node.getAttribute("TelephoneResp").getValue();
			result.contactRespChampionnat.mail = node.getAttribute("MailResp").getValue();

			result.contactSupplChampionnat = new ContactEquipe();
			result.contactSupplChampionnat.nom = node.getAttribute("NomSuppl").getValue();
			result.contactSupplChampionnat.mobile = node.getAttribute("MobileSuppl").getValue();
			result.contactSupplChampionnat.telephone = node.getAttribute("TelephoneSuppl").getValue();
			result.contactSupplChampionnat.mail = node.getAttribute("MailSuppl").getValue();

			result.contactRespCoupe = new ContactEquipe();
			result.contactRespCoupe.nom = node.getAttribute("NomRespCoupe").getValue();
			result.contactRespCoupe.mobile = node.getAttribute("MobileRespCoupe").getValue();
			result.contactRespCoupe.telephone = node.getAttribute("TelephoneRespCoupe").getValue();
			result.contactRespCoupe.mail = node.getAttribute("MailRespCoupe").getValue();

			result.contactSupplCoupe = new ContactEquipe();
			result.contactSupplCoupe.nom = node.getAttribute("NomSupplCoupe").getValue();
			result.contactSupplCoupe.mobile = node.getAttribute("MobileSupplCoupe").getValue();
			result.contactSupplCoupe.telephone = node.getAttribute("TelephoneSupplCoupe").getValue();
			result.contactSupplCoupe.mail = node.getAttribute("MailSupplCoupe").getValue();
		}
		catch (Exception ex) {
		}
		return result;
	}

	public void write(OutputNode node, EquipeDetail external) {
	}
}
