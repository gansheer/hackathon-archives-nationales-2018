package fr.archives.nat.model;

public class Decret {

	private String numDocument;
	
	private String decretType;
	
	private String decretDate;
	
	private String decretCote;
	
	private String refImage;

	public String getNumDocument() {
		return numDocument;
	}

	public void setNumDocument(String numDocument) {
		this.numDocument = numDocument;
	}

	public String getDecretType() {
		return decretType;
	}

	public void setDecretType(String decretType) {
		this.decretType = decretType;
	}

	public String getDecretDate() {
		return decretDate;
	}

	public void setDecretDate(String decretDate) {
		this.decretDate = decretDate;
	}

	public String getDecretCote() {
		return decretCote;
	}

	public void setDecretCote(String decretCote) {
		this.decretCote = decretCote;
	}

	public String getRefImage() {
		return refImage;
	}

	public void setRefImage(String refImage) {
		this.refImage = refImage;
	}

	@Override
	public String toString() {
		return "Decret [numDocument=" + numDocument + ", decretType=" + decretType + ", decretDate=" + decretDate
				+ ", decretCote=" + decretCote + ", refImage=" + refImage + "]";
	}
	
	
}
