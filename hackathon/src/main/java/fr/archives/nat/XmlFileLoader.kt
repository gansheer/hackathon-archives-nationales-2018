package fr.archives.nat

import org.xml.sax.EntityResolver
import org.xml.sax.InputSource
import org.xml.sax.helpers.XMLReaderFactory
import java.io.File
import java.net.URL
import java.nio.file.Paths
import javax.xml.bind.JAXBContext
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.sax.SAXSource
import kotlin.reflect.KClass

/**
 * @author Patrick Allain - 12/7/18.
 */
class XmlFileLoader<T : Any>(private val xml: XmlFiles, private val klass: KClass<T>) {

    companion object : KLog() {

        fun getDtd(systemId: String): DtdFiles? {
            val expected = Paths.get(URL(systemId).toURI()).fileName.toString()
            return DtdFiles.values().find { it.fileName == expected }
        }

    }

    fun run(): T {
        try {
            return loadXml3()
                    .also { log.info { "XML File using class $xml has been loaded $it" } }
        } catch (e: Exception) {
            throw log.errorThrow(e) { "Error during parsing XML File : ${xml.location}" }
        }
    }

    private fun loadXml3(): T {

        val ctx = JAXBContext.newInstance(klass.java)
        val unmarshaller = ctx.createUnmarshaller()

        val xmlReader = XMLReaderFactory.createXMLReader()
        xmlReader.setFeature("http://xml.org/sax/features/namespaces", true)
        xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true)
        xmlReader.entityResolver = EntityResolver { _, systemId ->
            getDtd(systemId)?.run { InputSource(file.inputStream()) }
        }

        val inputSource = InputSource(xml.file.inputStream())
        val source = SAXSource(xmlReader, inputSource)

        @Suppress("UNCHECKED_CAST")
        return unmarshaller.unmarshal(source) as T

    }

    private fun loadXmlWithoutLoadingDtd(): T {
        val dbf = DocumentBuilderFactory.newInstance()
        dbf.isValidating = false
        dbf.isNamespaceAware = true
        dbf.setFeature("http://xml.org/sax/features/namespaces", false)
        dbf.setFeature("http://xml.org/sax/features/validation", false)
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false)
        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)

        val db = dbf.newDocumentBuilder()

        val document = db.parse(xml.file)

        val jaxbContext = JAXBContext.newInstance(klass.java)

        @Suppress("UNCHECKED_CAST")
        return jaxbContext.createBinder().unmarshal(document) as T
    }

    enum class XmlFiles(val location: String) {

        FRAN_IR_056040(location = "data/FRAN_IR_056040.xml"),

        FRAN_IR_056870(location = "data/FRAN_IR_056870.xml");

        val file: File = Paths.get("src/main/resources/$location").toAbsolutePath().normalize().toFile()
    }

    enum class DtdFiles(val fileName: String, folder: String) {
        EAD_SIA("ead_sia.dtd", "toolbox/dtd_an/EAD/");

        val file: File = Paths.get("src/main/resources/$folder$fileName").toAbsolutePath().normalize().toFile()

    }

}
