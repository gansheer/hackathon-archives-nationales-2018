package fr.archives.nat;

import fr.archives.nat.domain.GeoName;
import fr.archives.nat.repositories.GeoNameRepository;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * @author Patrick Allain - 12/8/18.
 */
@DisplayName("ES 6")
class Es6Tests {

    @Nested
    class ConnectionEsTest {

        @Test
        @DisplayName("Ensure connection exist")
        void ensure_connection_exist() throws UnknownHostException {
            final Settings settings = Settings.builder()
                    .put("cluster.name", "docker-cluster")
                    .build();
            final TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getLocalHost(), 9300));
            client.listedNodes();

            System.out.printf("Nodes : %s \n", client.listedNodes());
            final ElasticsearchTemplate template = new ElasticsearchTemplate(client);

            template.createIndex("ensure-connection-" + UUID.randomUUID().toString());
        }

    }

    @SpringBootTest
    @ExtendWith(SpringExtension.class)
    @Nested
    class PushEsTests {

        private final GeoNameRepository geoNameRepository;

        public PushEsTests(@Autowired GeoNameRepository geoNameRepository) {
            this.geoNameRepository = geoNameRepository;
        }

        @Test
        @DisplayName("Push fake document to ES Cluster")
        void pushToElasticSearch() {
            geoNameRepository.save(GeoName.builder()
                    .name("TEST_NAME")
                    .alternateName("TEST_ALTERNATENAME")
                    .asciiName("TEST_ASCIINAME")
                    .geonameId(-1)
                    .latitude(-1.0)
                    .longitude(-1.0)
                    .build()
            );
        }
    }
}
