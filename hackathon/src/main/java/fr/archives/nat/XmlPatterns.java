package fr.archives.nat;

import java.util.regex.Pattern;

public class XmlPatterns {
	public static final Pattern numDossierSimplePattern = Pattern.compile("\\d+\\sX\\s\\d+");

	
	public static final Pattern naissance = Pattern.compile("(n|N)aissance");
	public static String mois_01 = "janvier";
	public static String mois_02 = "février";
	public static String mois_03 = "mars";
	public static String mois_04 = "avril";
	public static String mois_05 = "mai";
	public static String mois_06 = "juin";
	public static String mois_07 = "juillet";
	public static String mois_08 = "aout";
	public static String mois_09 = "septembre";
	public static String mois_10 = "octobre";
	public static String mois_11 = "novembre";
	public static String mois_12 = "décembre";
	public static String all_mois =  mois_01 + "|" + mois_02 + "|" + mois_03 + "|" + mois_04 + "|" + mois_05 + "|"
			+ mois_06 + "|" + mois_07 + "|" + mois_08 + "|" + mois_09 + "|" + mois_10 + "|" + mois_11 + "|" + mois_12
			;

	public static final String dateJMAPattern = "\\d+\\s(" + all_mois + ")\\s\\d+";
	public static final String dateMAPattern = "("+all_mois + ")\\s\\d+";
	public static final String moisPattern = all_mois;
	public static final String numericPattern = "\\d+";

}
