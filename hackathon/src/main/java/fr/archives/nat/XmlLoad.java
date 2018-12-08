package fr.archives.nat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XmlLoad<T> {

	private final XmlFiles xml;
	private final Class<T> klass;

	public XmlLoad(XmlFiles xml, Class<T> klass) {
		super();
		this.xml = xml;
		this.klass = klass;
	}

	private static Optional<DtdFiles> getDtd(String systemId) {
		String expected;
		try {
			expected = Paths.get(new URL(systemId).toURI()).getFileName().toString();
			return Stream.of(DtdFiles.values()).filter(i -> i.getFileName().equals(expected)).findFirst();
		} catch (MalformedURLException | URISyntaxException e) {
			throw new RuntimeException(e);
		}

	}

	@SuppressWarnings("unchecked")
	public T run() {
		try {
			JAXBContext ctx = JAXBContext.newInstance(this.klass);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();

			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setFeature("http://xml.org/sax/features/namespaces", true);
			xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
			xmlReader.setEntityResolver(new EntityResolver() {

				@Override
				public InputSource resolveEntity(String arg0, String systemId) throws SAXException, IOException {
					try {
						return getDtd(systemId).map(i -> {
							try (FileInputStream fis = new FileInputStream(i.getFile())) {
								return new InputSource(fis);
							} catch (IOException e) {
								throw new RuntimeException(e);

							}
						}).orElseThrow(() -> new RuntimeException("Oups"));
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

				}

			});

			try(FileInputStream fis = new FileInputStream(xml.getFile())) {
				InputSource inputSource = new InputSource();
				SAXSource source = new SAXSource(xmlReader, inputSource);
	
				return (T) unmarshaller.unmarshal(source);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	enum XmlFiles {

		FRAN_IR_056040("data/FRAN_IR_056040.xml"),

		FRAN_IR_056870("data/FRAN_IR_056870.xml");

		private String location;
		private File file;

		XmlFiles(String location) {
			this.location = location;
			this.file = Paths.get("src/main/resources/"+location).toAbsolutePath().normalize().toFile();
		}

		public File getFile() {
			return file;
		}

		public String getLocation() {
			return location;
		}

	}

	enum DtdFiles {
		EAD_SIA("ead_sia.dtd", "toolbox/dtd_an/EAD/");

		private String fileName;
		private String folder;
		private File file;

		DtdFiles(String fileName, String folder) {
			this.fileName = fileName;
			this.folder = folder;
			this.file = Paths.get("src/main/resources/"+folder+fileName).toAbsolutePath().normalize().toFile();
		}

		public String getFileName() {
			return fileName;
		}

		public String getFolder() {
			return folder;
		}

		public File getFile() {
			return file;
		}

	}

}
