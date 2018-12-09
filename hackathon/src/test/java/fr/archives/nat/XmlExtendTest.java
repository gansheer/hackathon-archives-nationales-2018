package fr.archives.nat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import fr.archives.nat.model.Person;

public class XmlExtendTest {

	@Test
	public void extendXmlTest() throws IOException, ParseException {

		final XmlFiles source = XmlFiles.FRAN_IR_056040;
		XmlExtend xmlExtend = new XmlExtend();
		List<Person> persons = xmlExtend.extendXml(source.getFile());
	}

	@Test
	public void extendXmlBentzTest() throws IOException, ParseException {

		URL sourceUrl = GeoNames.class.getClassLoader().getResource("samples/person-bentz.xml");
		System.err.println(sourceUrl);
		XmlExtend xmlExtend = new XmlExtend();
		List<Person> persons = xmlExtend.extendXml(new File(sourceUrl.getFile()));
		assertTrue(persons.size() == 1);
		assertTrue("BENTZ".equals(persons.get(0).getNom()));
		assertTrue("Joseph".equals(persons.get(0).getPrenom()));
		assertTrue("5".equals(persons.get(0).getDataNaissanceJour()));
		assertTrue("4".equals(persons.get(0).getDataNaissanceMois()));
		assertTrue("1856".equals(persons.get(0).getDataNaissanceAnnee()));
		assertTrue("1652 X 84".equals(persons.get(0).getNumDossierNat()));
		System.out.println(persons.get(0));
	}
}
