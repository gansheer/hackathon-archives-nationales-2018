package fr.archives.nat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class GeoNames {
	
	final File allCountries = Paths.get("/home/rocha/Documents/hackathon/allCountries_purged.txt").toFile();

	class GeoLine {
		
		private Integer geonameid  ;       
		private String name       ;       
		private String asciiname ;        
		private String alternatenames;    
		private Double latitude          ;
		private Double longitude;
		private String countryCode;
		public String getCountryCode() {
			return countryCode;
		}
		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}
		public Integer getGeonameid() {
			return geonameid;
		}
		public void setGeonameid(Integer geonameid) {
			this.geonameid = geonameid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAsciiname() {
			return asciiname;
		}
		public void setAsciiname(String asciiname) {
			this.asciiname = asciiname;
		}
		public String getAlternatenames() {
			return alternatenames;
		}
		public void setAlternatenames(String alternatenames) {
			this.alternatenames = alternatenames;
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
		public GeoLine() {
			
		}
		
		public GeoLine(Integer geonameid, String name, String asciiname, String alternatenames, Double latitude,
				Double longitude, String countryCode) {
			this();
			this.geonameid = geonameid;
			this.name = name;
			this.asciiname = asciiname;
			this.alternatenames = alternatenames;
			this.latitude = latitude;
			this.longitude = longitude;
			this.countryCode = countryCode;
		}
		
		public GeoLine copy(String alternames) {
			return new GeoLine(
					this.geonameid,
					this.name,
					this.asciiname,
					this.alternatenames = alternames,
					this.latitude,
					this.longitude,
					this.countryCode
					);
		}
		@Override
		public String toString() {
			return "GeoLine [geonameid=" + geonameid + ", name=" + name + ", asciiname=" + asciiname
					+ ", alternatenames=" + alternatenames + ", latitude=" + latitude + ", longitude=" + longitude
					+ ", countryCode=" + countryCode
					+ "]";
		}
		
		
		
	}
	
	public void toto() {
		try (BufferedReader r = new BufferedReader(new FileReader(allCountries))){
			
			
			r.lines().map(l -> 				l.split("\t"))
			.map(array -> new GeoLine(Integer.valueOf(array[0]), array[1], array[2], array[3], Double.valueOf(array[4]), Double.valueOf(array[5]), array[6]))
			.flatMap(g -> Stream.of(g.getAlternatenames().split(",")).map(newAlterName -> g.copy(newAlterName)))
			.peek(System.out::println)
			.forEach(this::sendToEs);;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendToEs(final GeoLine line) {
		
	}
}
