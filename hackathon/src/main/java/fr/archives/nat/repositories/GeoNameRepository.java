package fr.archives.nat.repositories;

import fr.archives.nat.domain.GeoName;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Patrick Allain - 12/8/18.
 */
public interface GeoNameRepository extends ElasticsearchRepository<GeoName, String> {

}
