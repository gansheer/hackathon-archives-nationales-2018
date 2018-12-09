package fr.archives.nat.controllers;

import fr.archives.nat.domain.Person;
import fr.archives.nat.repositories.PersonRepository;
import lombok.Builder;
import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Patrick Allain - 12/8/18.
 */
@Controller
public class IndexController {

    /** The class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private final PersonRepository personRepository;

    public IndexController(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @SafeVarargs
    static Map<String, Object> mapOf(final Tuple2<String, Object>... items) {
        return Stream.of(items).collect(Collectors.toMap(Tuple2::getT1, Tuple2::getT2));
    }

    @GetMapping("")
    public ModelAndView home(@ModelAttribute("form") SearchDecreeRequest form) {
        return new ModelAndView("pages/index", mapOf(
                Tuples.of("person", "Amélie "),
                Tuples.of("form", form))
        );
    }

    @GetMapping("/search")
    public ModelAndView search(final @ModelAttribute("form") SearchDecreeRequest form, final BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/");
        }
//        final Page<String> fakeData = new PageImpl<>(
//                IntStream.range(0, 20).mapToObj(i -> "res-" + i).collect(Collectors.toList()),
//                PageRequest.of(5, 10),
//                100
//        );
        final Page<Person> search = personRepository.search(
                QueryBuilders.matchQuery("nom", "BENTZ"),
                Pageable.unpaged());
        LOGGER.info("Info of persons : {}", search);
        return new ModelAndView("pages/search", mapOf(
                Tuples.of("results", search),
                Tuples.of("form", form))
        );
    }

    @GetMapping("/result/{id}")
    public ModelAndView result(@PathVariable("id") final String id) {
        return new ModelAndView("pages/result",
                "result",
                personRepository.findById(id));
    }

    @GetMapping("/credits")
    public ModelAndView credits() {
        return new ModelAndView("pages/credits", mapOf(
                Tuples.of("credits", Arrays.asList(
                        "Gaëlle Fournier",
                        "Romain Chauveau",
                        "Edouard Vasseur",
                        "Marion Ville",
                        "Amélie Collin",
                        "Patrick Alain",
                        "A tous le personnel des Archives Nationales")),
                Tuples.of("tools", Arrays.asList(
                        CreditTool.builder()
                                .name("ElasticSearch")
                                .webSite("https://www.elastic.co/")
                                .build(),
                        CreditTool.builder()
                                .name("Spring Boot")
                                .webSite("https://spring.io/projects/spring-boot")
                                .build(),
                        CreditTool.builder()
                                .name("Thymeleaf")
                                .webSite("https://www.thymeleaf.org/")
                                .build(),
                        CreditTool.builder()
                                .name("Font Awesome")
                                .webSite("https://fontawesome.com/")
                                .build()))
        ));
    }

    @Data
    @Builder
    static class CreditTool {

        private String name;

        private String webSite;

    }

}
