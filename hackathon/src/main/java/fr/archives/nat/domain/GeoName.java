package fr.archives.nat.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

/**
 * @author Patrick Allain - 12/8/18.
 */
@Data
@Builder
@Document(indexName = "geoname-index", type = "geoname")
public class GeoName {

    @Id
    private String id;

    private Integer geonameId;

    private String name;

    private String asciiName;

    private String alternateName;

    private Double latitude;

    private Double longitude;


}
