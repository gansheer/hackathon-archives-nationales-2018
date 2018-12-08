package fr.archives.nat.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author Patrick Allain - 12/8/18.
 */
@Data
@Builder
@Document(indexName = "geoname-index", shards = 1, replicas = 0, refreshInterval = "-1")
public class GeoName {

    private Integer geonameId;

    private String name;

    private String asciiName;

    private String alternateName;

    private Double latitude;

    private Double longitude;

}
