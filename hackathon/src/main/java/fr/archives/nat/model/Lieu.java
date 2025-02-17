package fr.archives.nat.model;

public class Lieu {

	private String lieu_commune;
	private String lieu_departement;
	private String lieu_pays;
	private Double latitude;
	private Double longitude;
	
	public String getLieu_commune() {
		return lieu_commune;
	}
	public void setLieu_commune(String lieu_commune) {
		this.lieu_commune = lieu_commune;
	}
	public String getLieu_departement() {
		return lieu_departement;
	}
	public void setLieu_departement(String lieu_departement) {
		this.lieu_departement = lieu_departement;
	}
	public String getLieu_pays() {
		return lieu_pays;
	}
	public void setLieu_pays(String lieu_pays) {
		this.lieu_pays = lieu_pays;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
	
	@Override
	public String toString() {
		return "Lieu [lieu_commune=" + lieu_commune + ", lieu_departement=" + lieu_departement + ", lieu_pays="
				+ lieu_pays + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
	public Lieu(String lieu_commune, String lieu_departement, String lieu_pays) {
		super();
		this.lieu_commune = lieu_commune;
		this.lieu_departement = lieu_departement;
		this.lieu_pays = lieu_pays;
	}
	
	public Lieu(String lieu_commune, String lieu_departement, String lieu_pays, Double longitude, Double latitude) {
		this(lieu_commune, lieu_departement, lieu_pays);
		this.longitude = longitude;
		this.latitude =latitude;
	}
	
	
}
