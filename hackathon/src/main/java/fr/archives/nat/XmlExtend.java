package fr.archives.nat;

import java.util.List;

import fr.archives.nat.XmlLoad.XmlFiles;
import fr.archives.nat.model.Decret;
import fr.archives.nat.model.Person;
import fr.archives.nat.xml.ead.sia.C;
import fr.archives.nat.xml.ead.sia.Ead;

public class XmlExtend {

	public static void extendXml() {
		final XmlFiles source = XmlFiles.FRAN_IR_056040;
		XmlLoad<Ead> xmlLoader = new XmlLoad<>(source, Ead.class);
		Ead ead = xmlLoader.run();
		List<C> decrets = ead.getArchdesc().getDsc().getC();
		
		for(C decret : decrets) {
			
			Decret decretModel = extractDecret(decret);
			// bla bla
			
			List<C> decretPersons  = decret.getC();
			
			for(C decretPerson : decretPersons) {
				
				List<Person> persons = extractPersons(decretPerson,decret);
				
				
			}
		}
		
		System.out.println(ead.toString());
	}

	private static List<Person> extractPersons(C decretPerson, C decret) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Decret extractDecret(C decret) {
		Decret decretModel = new Decret();
		//decretModel.setNumDocument(decret.getDid().getUnitid().stream().filter(unitid -> ));
		return decretModel;
	}	
}
