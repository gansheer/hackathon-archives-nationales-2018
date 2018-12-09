package fr.archives.nat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import fr.archives.nat.xml.ead.sia.Item;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.archives.nat.model.Decret;
import fr.archives.nat.model.Lieu;
import fr.archives.nat.model.Person;
import fr.archives.nat.xml.ead.sia.C;
import fr.archives.nat.xml.ead.sia.Ead;
import fr.archives.nat.xml.ead.sia.P;
import fr.archives.nat.xml.ead.sia.Unitid;

public class XmlExtend {

	private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRANCE);

	private static final SimpleDateFormat french_dformat = new SimpleDateFormat("MMMM", Locale.FRENCH);

	public List<Person> extendXml(File source) throws IOException, ParseException {
		GeoNames geonames = new GeoNames();
		geonames.toto();
		File cleanedFile = cleanFile(source);
		XmlLoad<Ead> xmlLoader = new XmlLoad<>(cleanedFile, Ead.class);
		Ead ead = xmlLoader.run();
		List<C> decrets = ead.getArchdesc().getDsc().getC();
		List<Person> persons = new ArrayList<>();
		System.out.println("start parsing decrets");

		for (C decret : decrets) {

			Decret decretModel = extractDecret(decret);
			// bla bla

			List<C> decretPersons = decret.getC();

			for (C decretPerson : decretPersons) {

				persons = extractPersons(decretPerson, decretModel);

			}
		}
		System.out.println("nombre de villes = " + geonames.getLieux().size());
		System.out.println("end parsing decrets");
		sendToES(persons);
		return persons;
	}

	private File cleanFile(File file) throws FileNotFoundException, IOException {
		File cleanedFile = new File("/tmp/"+UUID.randomUUID().toString() + ".xml");
		cleanedFile.createNewFile();
		String originContent = IOUtils.toString(new FileInputStream(file));
		String str = Pattern.compile("(<unittitle>)([\\s]*?)(<persname>)([\\S\\s]*?)(</persname>)([\\s]*?)(</unittitle>)").matcher(originContent).replaceAll("$1Persname : $4$7");
		System.out.println(str);
		FileUtils.writeStringToFile(cleanedFile, str);
		return cleanedFile;

	}

	private void sendToES(List<Person> persons) throws IOException {
		ESIndex esIndex = new ESIndex.Builder("http://localhost:9200").build();

		for (Person person : persons) {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(person);
			JsonNode jsonNode = mapper.readTree(json);
			esIndex.createDocument(jsonNode, "persons", "_doc", UUID.randomUUID().toString());
		}
	}

	private List<Person> extractPersons(C decretPerson, Decret decretModel) throws ParseException {
		List<Person> persons = new ArrayList<>();
		Person principalPersonn = new Person();
		principalPersonn.setDecret(decretModel);
		extractPersonNumeroDossier(decretPerson, principalPersonn);
		extractPersonNonPrenom(decretPerson, principalPersonn);
		extractScopeContent(decretPerson, principalPersonn);
		persons.add(principalPersonn);
		return persons;
	}

	private Lieu findLieu(GeoNames geonames, String s) {
		for (Lieu lieu : geonames.getLieux().values()) {
			if (s.contains(lieu.getLieu_commune())) {
				return lieu;
			}
		}

		return null;
	}

	private void extractPersonNumeroDossier(C decretPerson, Person principalPersonn) {
		List<Unitid> unitids = decretPerson.getDid().getUnitid().stream()
				.filter(item -> item.getType().equals("pieces")).collect(Collectors.toList());
		for (Unitid unitId : unitids) {
			Matcher m = XmlPatterns.numDossierSimplePattern.matcher(unitId.getvalue());
			if (m.find()) {
				principalPersonn.setNumDossierNat(m.group(0).trim());
			}
			// TODO tomorrow gafou

		}

	}

	private void extractScopeContent(C decretPerson, Person principalPersonn) throws ParseException {
		if(decretPerson.getScopecontent() == null) {
			return;
		}
		List<Object> contentList = decretPerson.getScopecontent().getPOrList();
		for (Object content : contentList) {
			System.err.println("P");
			String contentString = null;
			if (content instanceof P) {
				contentString = ((P) content).getvalue();
				System.err.println(contentString);
			} else if(content instanceof fr.archives.nat.xml.ead.sia.List){
				List<Item> items = ((fr.archives.nat.xml.ead.sia.List) content).getItem();
				// TODO 
				return;
			} else {
				return;
			}

			// profession
			if (StringUtils.contains("Profession :", contentString)) {
				principalPersonn.setProfession(StringUtils.substringAfter("Profession :", contentString).trim());
			}
			// Naissance
			System.err.println("check naissance " + contentString);
			Matcher naissanceM = XmlPatterns.naissance.matcher(contentString);
			if (naissanceM.find()) {
				System.err.println("naissance");
				extractNaissanceDate(contentString, principalPersonn);
			}
		}

	}

	private void extractNaissanceDate(String fullNaissance, Person principalPersonn) throws ParseException {
		boolean dateNaissanceJourPresent = false;
		boolean dateNaissanceMoisPresent = false;
		boolean dateNaissanceAnneePresent = false;

		if (StringUtils.contains("date inconnue", fullNaissance)) {
			return;
		}

		System.err.println("extract");
		// TODO gafou
		System.err.println("fullNaissance |" + fullNaissance + "|");

		Pattern pjma = Pattern.compile(XmlPatterns.dateJMAPattern);
		Matcher jma = pjma.matcher(fullNaissance);

		Pattern pma = Pattern.compile(XmlPatterns.dateMAPattern);
		Matcher ma = pma.matcher(fullNaissance);

		Pattern pnumeric = Pattern.compile(XmlPatterns.numericPattern);
		Matcher numeric = pnumeric.matcher(fullNaissance);

		Pattern pmois = Pattern.compile(XmlPatterns.moisPattern);
		Matcher mois = pmois.matcher(fullNaissance);
		if (mois.find()) {
			Date moisDate = french_dformat.parse(mois.group(0));
			principalPersonn.setDataNaissanceMois("" + (moisDate.getMonth() + 1));
			dateNaissanceMoisPresent = true;
		}

		if (jma.find()) {
			System.err.println("jma find");
			numeric.find();
			principalPersonn.setDataNaissanceJour(numeric.group(0));
			dateNaissanceJourPresent = true;
			numeric.find();
			principalPersonn.setDataNaissanceAnnee(numeric.group(0));
			dateNaissanceAnneePresent = true;
		} else if (ma.find()) {
			System.err.println("ma find");
			numeric.find();
			principalPersonn.setDataNaissanceAnnee(numeric.group(0));
			dateNaissanceAnneePresent = true;
		}

		if (dateNaissanceJourPresent && dateNaissanceMoisPresent && dateNaissanceAnneePresent) {
			principalPersonn.setDataNaissance(principalPersonn.getDataNaissanceJour() + "/"
					+ principalPersonn.getDataNaissanceMois() + "/" + principalPersonn.getDataNaissanceAnnee());
		}

	}

	private void extractPersonNonPrenom(C decretPerson, Person principalPersonn) {
		// TODO trim
		String personame = decretPerson.getDid().getUnittitle().getvalue();
		String fullName = personame.replace("Persname :", "");
		String fullnom = StringUtils.substringBefore(fullName, ",");
		String nom = fullnom.trim();
		String nomNaissance = "";
		if (fullnom.contains("née")) {
			nom = StringUtils.substringBefore(fullnom, "née");
			nomNaissance = StringUtils.substringAfter(fullnom, ",");
		} else {
			nomNaissance = nom;
		}
		String prenom = StringUtils.substringAfter(fullName, ",").trim();
		principalPersonn.setNom(nom);
		principalPersonn.setNomNaissance(nomNaissance);
		principalPersonn.setPrenom(prenom);
	}

	private Decret extractDecret(C decret) {
		Decret decretModel = new Decret();

		decretModel.setDecretCote(
				decret.getDid().getUnitid().stream().filter(unitid -> unitid.getType().equals("cote-de-consultation"))
						.collect(Collectors.toList()).get(0).getvalue());
		decretModel.setNumDocument(decret.getDid().getUnitid().stream()
				.filter(unitid -> unitid.getType().equals("pieces")).collect(Collectors.toList()).get(0).getvalue());
		String dateText = decret.getDid().getUnitdate().get(0).getvalue();
		LocalDate date = LocalDate.parse(dateText, fmt);

		decretModel.setDecretDate(dateText);

		System.out.println(decretModel.toString());

		return decretModel;
	}
}
