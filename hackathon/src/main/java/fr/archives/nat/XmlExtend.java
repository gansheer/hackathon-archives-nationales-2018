package fr.archives.nat;

import fr.archives.nat.XmlLoad.XmlFiles;
import fr.archives.nat.xml.ead.sia.Ead;

public class XmlExtend {

	public static void extendXml() {
		final XmlFiles source = XmlFiles.FRAN_IR_056040;
		XmlLoad<Ead> xmlLoader = new XmlLoad<Ead>(source, Ead.class);
		Ead ead = (Ead) xmlLoader.run();
		System.out.println(ead.toString());
	}	
}
