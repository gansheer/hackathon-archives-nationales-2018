package fr.archives.nat.domain;

import fr.archives.nat.model.Decret;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author Patrick Allain - 12/9/18.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "persons", type = "_doc")
public class Person {

    @Id
    private String _id;

//    private Decret decret;
    private String numDossierNat;
    private String nom;
    private String nomNaissance;
    private String prenom;
    private String dataNaissance;
    private String dataNaissanceJour;
    private String dataNaissanceMois;
    private String dataNaissanceAnnee;
    private String lieuNaissanceCommune;
    private String lieuNaissanceDepartement;
    private String lieuNaissancePays;
    private String origineParent;
    private String origineParentPays;
    private String profession;
    private String lieuResidenceCommune;
    private String lieuResidenceDepartement;
    private String lieuResidencePays;

}
