package fr.archives.nat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XmlLoad<T> {

    private final File xml;

    private final Class<T> klass;

    public XmlLoad(final File xml, final Class<T> klass) {
        this.xml = xml;
        this.klass = klass;
    }

    private static Optional<DtdFiles> getDtd(String systemId) {
        String expected;
        try {
            final URI uri = new URL(systemId).toURI();
            expected = Paths.get(uri).getFileName().toString();
            return Stream.of(DtdFiles.values())
                    .filter(i -> i.getFileName().equals(expected))
                    .findFirst();
        } catch (final MalformedURLException | URISyntaxException e) {
            throw new RuntimeException("Error during retrieve dtd for systemId : " + systemId, e);
        }
    }

    public T run() {
        try {
            final JAXBContext ctx = JAXBContext.newInstance(this.klass);
            final Unmarshaller unmarshaller = ctx.createUnmarshaller();

            final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            xmlReader.setFeature("http://xml.org/sax/features/namespaces", true);
            xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
            xmlReader.setEntityResolver((unused, systemId) -> {
                final DtdFiles f = getDtd(systemId).orElseThrow(() -> new RuntimeException("DTD not found"));
                try (final FileInputStream fis = new FileInputStream(f.getFile())) {
                    final String collect = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))
                            .lines()
                            .collect(Collectors.joining());
                    return new InputSource(new StringReader(collect));
                }
            });

            return loadObj(unmarshaller, xmlReader);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    private T loadObj(final Unmarshaller unmarshaller, final XMLReader xmlReader) throws IOException, JAXBException {
        try (final FileInputStream fis = new FileInputStream(xml)) {
            final InputSource inputSource = new InputSource(fis);
            final SAXSource source = new SAXSource(xmlReader, inputSource);

            @SuppressWarnings("unchecked")
            final T obj = (T) unmarshaller.unmarshal(source);
            return obj;
        }
    }

    enum DtdFiles {
        EAD_SIA("ead_sia.dtd", "toolbox/dtd_an/EAD/");

        private final String fileName;

        private final File file;

        DtdFiles(String fileName, String folder) {
            this.fileName = fileName;
            this.file = Paths.get("src/main/resources/" + folder + fileName).toAbsolutePath().normalize().toFile();
        }

        public String getFileName() {
            return fileName;
        }

        public File getFile() {
            return file;
        }

    }

}
