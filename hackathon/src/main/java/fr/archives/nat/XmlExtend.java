package fr.archives.nat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import fr.archives.nat.XmlLoad.XmlFiles;
import fr.archives.nat.model.Decret;
import fr.archives.nat.model.Person;
import fr.archives.nat.xml.ead.sia.C;
import fr.archives.nat.xml.ead.sia.Ead;

public class XmlExtend {
	
	private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRANCE);
	private static GeoNames geonames = new GeoNames();

	public static void extendXml() {
		geonames.toto();
		final XmlFiles source = XmlFiles.FRAN_IR_056040;
		XmlLoad<Ead> xmlLoader = new XmlLoad<>(source, Ead.class);
		Ead ead = xmlLoader.run();
		List<C> decrets = ead.getArchdesc().getDsc().getC();
		
		System.out.println("start parsing decrets");
		
		for(C decret : decrets) {
			
			Decret decretModel = extractDecret(decret);
			// bla bla
			
			List<C> decretPersons  = decret.getC();
			
			for(C decretPerson : decretPersons) {
				
				List<Person> persons = extractPersons(decretPerson,decret);
				
				
			}
		}
		System.out.println("nombre de villes = " + geonames.getLieux().size());
		System.out.println("end parsing decrets");
		String testPhrase = "Lieu de résidence : Périgueux, Dordogne";
		
//		System.out.println(ead.toString());
	}

	private static List<Person> extractPersons(C decretPerson, C decret) {
		// TODO Auto-generated method stub
		return null;
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
