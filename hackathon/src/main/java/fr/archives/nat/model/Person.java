package fr.archives.nat.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.elasticsearch.common.geo.GeoPoint;

public class Person {

	private Decret decret;

	private String numDossierNat;

	private String nom;

	private String nomNaissance;

	private String prenom;

	// date
	private String dataNaissance;
	private Integer dataNaissanceJour;
	private Integer dataNaissanceMois;
	private Integer dataNaissanceAnnee;
	private String lieuNaissanceCommune;
	private String lieuNaissanceDepartement;
	private String lieuNaissancePays;
	private Location lieuNaissanceLocation;
	private GeoPoint lieuNaissanceGeoPoint;
	private String lieuNaissanceGeo;
	private String origineParent;
	private String origineParentPays;
	private String profession;
	private String lieuResidenceCommune;
	private String lieuResidenceDepartement;
	private String lieuResidencePays;

	public GeoPoint getLieuNaissanceGeoPoint() {
		return lieuNaissanceGeoPoint;
	}
	
	public void setLieuNaissanceGeoPoint(GeoPoint lieuNaissanceGeoPoint) {
		this.lieuNaissanceGeoPoint = lieuNaissanceGeoPoint;
	}
	
	public Decret getDecret() {
		return decret;
	}

	public void setDecret(Decret decret) {
		this.decret = decret;
	}

	public String getNumDossierNat() {
		return numDossierNat;
	}

	public void setNumDossierNat(String numDossierNat) {
		this.numDossierNat = numDossierNat;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNomNaissance() {
		return nomNaissance;
	}

	public void setNomNaissance(String nomNaissance) {
		this.nomNaissance = nomNaissance;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getDataNaissance() {
		return dataNaissance;
	}

	public void setDataNaissance(String dataNaissance) {
		this.dataNaissance = dataNaissance;
	}

	public Integer getDataNaissanceJour() {
		return dataNaissanceJour;
	}

	public void setDataNaissanceJour(Integer dataNaissanceJour) {
		this.dataNaissanceJour = dataNaissanceJour;
	}

	public Integer getDataNaissanceMois() {
		return dataNaissanceMois;
	}

	public void setDataNaissanceMois(Integer dataNaissanceMois) {
		this.dataNaissanceMois = dataNaissanceMois;
	}

	public Integer getDataNaissanceAnnee() {
		return dataNaissanceAnnee;
	}

	public void setDataNaissanceAnnee(Integer dataNaissanceAnnee) {
		this.dataNaissanceAnnee = dataNaissanceAnnee;
	}

	public String getLieuNaissanceCommune() {
		return lieuNaissanceCommune;
	}

	public void setLieuNaissanceCommune(String lieuNaissanceCommune) {
		this.lieuNaissanceCommune = lieuNaissanceCommune;
	}

	public String getLieuNaissanceDepartement() {
		return lieuNaissanceDepartement;
	}

	public void setLieuNaissanceDepartement(String lieuNaissanceDepartement) {
		this.lieuNaissanceDepartement = lieuNaissanceDepartement;
	}

	public String getLieuNaissancePays() {
		return lieuNaissancePays;
	}

	public void setLieuNaissancePays(String lieuNaissancePays) {
		this.lieuNaissancePays = lieuNaissancePays;
	}

	public String getOrigineParent() {
		return origineParent;
	}

	public void setOrigineParent(String origineParent) {
		this.origineParent = origineParent;
	}

	public String getOrigineParentPays() {
		return origineParentPays;
	}

	public void setOrigineParentPays(String origineParentPays) {
		this.origineParentPays = origineParentPays;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getLieuResidenceCommune() {
		return lieuResidenceCommune;
	}

	public void setLieuResidenceCommune(String lieuResidenceCommune) {
		this.lieuResidenceCommune = lieuResidenceCommune;
	}

	public String getLieuResidenceDepartement() {
		return lieuResidenceDepartement;
	}

	public void setLieuResidenceDepartement(String lieuResidenceDepartement) {
		this.lieuResidenceDepartement = lieuResidenceDepartement;
	}

	public String getLieuResidencePays() {
		return lieuResidencePays;
	}

	public void setLieuResidencePays(String lieuResidencePays) {
		this.lieuResidencePays = lieuResidencePays;
	}

	public Location getLieuNaissanceLocation() {
		return lieuNaissanceLocation;
	}

	public void setLieuNaissanceLocation(Location lieuNaissanceLocation) {
		this.lieuNaissanceLocation = lieuNaissanceLocation;
	}

	public void setLieuNaissanceGeo(String lieuNaissanceGeo) {
		this.lieuNaissanceGeo = lieuNaissanceGeo;
	}
	
	public String getLieuNaissanceGeo() {
		return lieuNaissanceGeo;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
