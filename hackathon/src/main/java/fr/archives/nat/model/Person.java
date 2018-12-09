package fr.archives.nat.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Person {

	private Decret decret;
	
	private String numDossierNat;
	
	private String nom;
	
	private String nomNaissance;
	
	private String prenom;
	
	// date
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
	public String getDataNaissanceJour() {
		return dataNaissanceJour;
	}
	public void setDataNaissanceJour(String dataNaissanceJour) {
		this.dataNaissanceJour = dataNaissanceJour;
	}
	public String getDataNaissanceMois() {
		return dataNaissanceMois;
	}
	public void setDataNaissanceMois(String dataNaissanceMois) {
		this.dataNaissanceMois = dataNaissanceMois;
	}
	public String getDataNaissanceAnnee() {
		return dataNaissanceAnnee;
	}
	public void setDataNaissanceAnnee(String dataNaissanceAnnee) {
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
	
	@Override
	public String toString() {
		   return ToStringBuilder.reflectionToString(this);
	}
}
