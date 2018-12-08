package fr.archives.nat;

<<<<<<< 19ab2facd31c67f1b7ebe6f45f454bf5b67c3a4a
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
=======
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
>>>>>>> Init personn extract

import fr.archives.nat.XmlLoad.XmlFiles;
import fr.archives.nat.model.Decret;
import fr.archives.nat.model.Lieu;
import fr.archives.nat.model.Person;
import fr.archives.nat.xml.ead.sia.C;
import fr.archives.nat.xml.ead.sia.Ead;

public class XmlExtend {
	
	private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRANCE);
	private static GeoNames geonames = new GeoNames();

	private static final SimpleDateFormat french_dformat = new SimpleDateFormat("MMMM", Locale.FRENCH);

	public static void extendXml() {
		geonames.toto();
		final XmlFiles source = XmlFiles.FRAN_IR_056040;
		XmlLoad<Ead> xmlLoader = new XmlLoad<>(source, Ead.class);
		Ead ead = xmlLoader.run();
		List<C> decrets = ead.getArchdesc().getDsc().getC();
		
		System.out.println("start parsing decrets");

		for (C decret : decrets) {

			Decret decretModel = extractDecret(decret);
			// bla bla

			List<C> decretPersons = decret.getC();

			for (C decretPerson : decretPersons) {

				List<Person> persons = extractPersons(decretPerson, decret);

			}
		}
		System.out.println("nombre de villes = " + geonames.getLieux().size());
		System.out.println("end parsing decrets");
		String testPhrase = "Lieu de résidence : Périgueux, Dordogne";
		Lieu lieu = findLieu(testPhrase);
		
		System.out.println(lieu.toString());
		
//		System.out.println(ead.toString());
	}

	private static List<Person> extractPersons(C decretPerson, C decret) {
		Person principalPersonn = new Person();
		extractPersonNumeroDossier(decretPerson, principalPersonn);
		extractPersonNonPrenom(decretPerson, principalPersonn);
		extractScopeContent(decretPerson, principalPersonn);
		return null;
	}
	
	private static Lieu findLieu(String s) {
		for(Lieu lieu : geonames.getLieux().values()) {
			if(s.contains(lieu.getLieu_commune())) {
				return lieu;
			}
		}
		
		return null;
	}

	private static void extractScopeContent(C decretPerson, Person principalPersonn) {
		List<Object> contentList = decretPerson.getScopecontent().getPOrList();
		for (Object content : contentList) {
			String contentString = (String) content;
			// profession
			if (StringUtils.contains("Profession :", contentString)) {
				principalPersonn.setProfession(StringUtils.substringAfter("Profession :", contentString));
			}
			// Naissance
			if (StringUtils.contains("Naissance:", contentString)) {
				String fullNaissance = StringUtils.substringAfter("Naissance :", contentString);
				extractNaissanceDate(fullNaissance, principalPersonn);
			}
		}

	}

	private static void extractNaissanceDate(String fullNaissance, Person principalPersonn) {
		boolean dateNaissanceJourPresent = false;
		boolean dateNaissanceMoisPresent = false;
		boolean dateNaissanceAnneePresent = false;

		if (StringUtils.contains("date inconnue", fullNaissance)) {
			return;
		}

		String fullNaissanceClean = RegExUtils.replaceFirst(fullNaissance, "en ", "");
		fullNaissanceClean = RegExUtils.replaceFirst(fullNaissance, "vers ", "");

		String[] datas = StringUtils.split(fullNaissanceClean, " ");
		// first data
		String firstData = datas[0];
		if (StringUtils.isNumeric(firstData)) {
			Integer firstDataNumeric = Integer.valueOf(firstData);
			if (firstDataNumeric < 32) {
				principalPersonn.setDataNaissanceJour(firstData);
				dateNaissanceJourPresent = true;
			} else {
				principalPersonn.setDataNaissanceAnnee(firstData);
				dateNaissanceAnneePresent = true;
			}
		} else {
			try {
				Month month = Month.valueOf(firstData.toUpperCase());
				principalPersonn.setDataNaissanceMois("" + month.getValue());
				dateNaissanceMoisPresent = true;
			} catch (IllegalArgumentException e) {
				// TODO
			}
		}

		// seconde data
		String secondData = datas[1];
		if (dateNaissanceJourPresent) {
			try {
				Month month = Month.valueOf(secondData.toUpperCase());
				principalPersonn.setDataNaissanceMois("" + month.getValue());
				dateNaissanceMoisPresent = true;
			} catch (IllegalArgumentException e) {
				// TODO
			}

		} else if (dateNaissanceMoisPresent) {
			if (StringUtils.isNumeric(secondData)) {
				principalPersonn.setDataNaissanceAnnee(secondData);
				dateNaissanceAnneePresent = true;
			}

		}
		// third data
		String thirdData = datas[2];
		if (dateNaissanceJourPresent && dateNaissanceMoisPresent) {
			if (StringUtils.isNumeric(thirdData)) {
				principalPersonn.setDataNaissanceAnnee(thirdData);
				dateNaissanceAnneePresent = true;
			}
		}

		if (dateNaissanceJourPresent && dateNaissanceMoisPresent && dateNaissanceAnneePresent) {
			principalPersonn.setDataNaissance(principalPersonn.getDataNaissanceJour() + "/"
					+ principalPersonn.getDataNaissanceMois() + "/" + principalPersonn.getDataNaissanceAnnee());
		}

	}

	private static void extractPersonNonPrenom(C decretPerson, Person principalPersonn) {
		// TODO trim
		String personame = decretPerson.getDid().getUnittitle().getvalue();
		String fullName = personame.replace("<personame>", "");
		fullName = personame.replace("</personame>", "");
		String fullnom = StringUtils.substringBefore(fullName, ",");
		String nom = fullnom;
		String nomNaissance = "";
		if (fullnom.contains("née")) {
			nom = StringUtils.substringBefore(fullnom, "née");
			nomNaissance = StringUtils.substringAfter(fullnom, ",");
		}
		String prenom = StringUtils.substringBefore(fullName, ",");
		principalPersonn.setNom(nom);
		principalPersonn.setNomNaissance(nomNaissance);
		principalPersonn.setPrenom(prenom);
	}

	private static Decret extractDecret(C decret) {
		Decret decretModel = new Decret();

		decretModel.setDecretCote(decret.getDid().getUnitid().stream().filter(unitid -> unitid.getType().equals("cote-de-consultation")).collect(Collectors.toList()).get(0).getvalue());
		decretModel.setNumDocument(decret.getDid().getUnitid().stream().filter(unitid -> unitid.getType().equals("pieces")).collect(Collectors.toList()).get(0).getvalue());
		String dateText = decret.getDid().getUnitdate().get(0).getvalue();
		LocalDate date = LocalDate.parse(dateText, fmt);

		decretModel.setDecretDate(dateText);
		
		System.out.println(decretModel.toString());
		
		return decretModel;
	}
}
