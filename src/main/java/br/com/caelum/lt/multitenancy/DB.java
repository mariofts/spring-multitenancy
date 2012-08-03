package br.com.caelum.lt.multitenancy;

public class DB {
	
	private static String db = "first";

	public static String getDb() {
		return db;
	}

	public static void setDb(String db) {
		DB.db = db;
	}

}
