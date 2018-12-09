package fr.archives.nat.model;

public enum TypeLieu {
	CITY("CITY", "P", null),
	REGION("REGION", "A", "ADM1"),
	DEPT("DEPT", "A", "ADM2");
	
	private final String typeCode;
	private final String geoCode;
	private final String geoSubCode;

	TypeLieu(String typeCode, String geoCode, String geoSubCode) {
		this.typeCode = typeCode;
		this.geoCode = geoCode;
		this.geoSubCode = geoSubCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public String getGeoCode() {
		return geoCode;
	}

	public String getGeoSubCode() {
		return geoSubCode;
	}
	
	public static TypeLieu findTypeLieu(String geoCode, String geoSubCode) {
		for(TypeLieu lieu : TypeLieu.values()) {
			if(lieu.getGeoCode().equals(geoCode)) {
				if((lieu.getGeoSubCode() == null) || (lieu.getGeoSubCode().equals(geoSubCode))) {
					return lieu;
				}
			}
		}
		
		return null;
	}
}
