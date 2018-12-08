package fr.archives.nat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

/**
 * @author Patrick Allain - 12/8/18.
 */
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

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Integer getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(final Integer geonameId) {
        this.geonameId = geonameId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAsciiName() {
        return asciiName;
    }

    public void setAsciiName(final String asciiName) {
        this.asciiName = asciiName;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(final String alternateName) {
        this.alternateName = alternateName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String id;

        private Integer geonameId;

        private String name;

        private String asciiName;

        private String alternateName;

        private Double latitude;

        private Double longitude;



        public Builder geonameId(Integer geonameId) {
            this.geonameId = geonameId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder asciiName(String asciiName) {
            this.asciiName = asciiName;
            return this;
        }

        public Builder alternateName(String alternateName) {
            this.alternateName = alternateName;
            return this;
        }

        public Builder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public GeoName build() {
            final GeoName geoName = new GeoName();
            geoName.setId(UUID.randomUUID().toString() + "-" + geonameId);
            geoName.setGeonameId(geonameId);
            geoName.setName(name);
            geoName.setAsciiName(asciiName);
            geoName.setAlternateName(alternateName);
            geoName.setLatitude(latitude);
            geoName.setLongitude(longitude);
            return geoName;
        }
    }
}
