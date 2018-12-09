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
	public void testPattern() {
		String source = "Naissance : 5 avril 1856 (Rosheim, Bas-Rhin, France)";

		Pattern p = Pattern.compile(XmlPatterns.dateJMAPattern);
		Matcher m = p.matcher(source);
		assertTrue(m.find());
		System.out.println(XmlPatterns.all_mois);
		Pattern pmois = Pattern.compile(XmlPatterns.moisPattern);
		Matcher mois = pmois.matcher(source);
		assertTrue(mois.find());
	}

	@Test
	public void extendXmlBentzTest() throws IOException, ParseException {

		URL sourceUrl = GeoNames.class.getClassLoader().getResource("samples/person-bentz.xml");
		System.err.println(sourceUrl);
		XmlExtend xmlExtend = new XmlExtend();
		List<Person> persons = xmlExtend.extendXml(new File(sourceUrl.getFile()));
		assertTrue(persons.size() == 1);
		System.out.println(persons.get(0));
	}
}
