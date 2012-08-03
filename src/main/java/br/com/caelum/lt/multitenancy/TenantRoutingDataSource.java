package br.com.caelum.lt.multitenancy;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class TenantRoutingDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		
		/*
		 * here goes the logic for changing db... 
		 * 
		 * you cann get the logged user, read a file, etc.
		 * 
		 * I'll use a static string... :P
		 */
		return DB.getDb();
	}

}
