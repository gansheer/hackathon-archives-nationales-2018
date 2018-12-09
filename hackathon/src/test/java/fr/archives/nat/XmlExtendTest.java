package fr.archives.nat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import fr.archives.nat.model.Person;

public class XmlExtendTest {

	@Test
	public void extendXmlTest() throws IOException {

		final XmlFiles source = XmlFiles.FRAN_IR_056040;
		List<Person> persons = XmlExtend.extendXml(source.getFile());
	}
	
	@Test
	public void testPattern() {
		String source = "Naissance : 5 avril 1856 (Rosheim, Bas-Rhin, France)";

		Matcher m = XmlPatterns.dateNaissanceJMAPattern.matcher(source);
		assertTrue(m.find());
	}

	@Test
	public void extendXmlBentzTest() throws IOException {

		URL sourceUrl = GeoNames.class.getClassLoader().getResource("samples/person-bentz.xml");
		System.err.println(sourceUrl);
		List<Person> persons = XmlExtend.extendXml(new File(sourceUrl.getFile()));
		assertTrue(persons.size() == 1);
		System.out.println(persons.get(0));
	}
}
