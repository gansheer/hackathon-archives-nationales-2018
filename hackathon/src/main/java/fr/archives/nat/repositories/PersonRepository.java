package fr.archives.nat.repositories;

import fr.archives.nat.domain.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Patrick Allain - 12/9/18.
 */
public interface PersonRepository extends ElasticsearchRepository<Person, String> {

}
