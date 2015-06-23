package main.NewDBBuilders;

import main.UtilityClasses.DatabaseUtilityMySQL;

import org.apache.poi.ss.usermodel.Workbook;

public class BuildNewDatabase extends Thread{
	
	//Constructors:
	public BuildNewDatabase(Workbook workbook,String database){
		this.database = database;
		this.workbook = workbook;
		
	}
	
	//Variables:
	private Workbook workbook;
	private String database;
	
	//Methods:
	public void run() {
		
		//ToDo: create database based on scheme
		
		DatabaseUtilityMySQL.openSessions(this.database);
		
		DatabaseUtilityMySQL.populateDBTable(DatabaseUtilityMySQL.SUBNETDATA_SQL_TABLENAME, workbook);
		
		DatabaseUtilityMySQL.populateDBTable(DatabaseUtilityMySQL.RBSDATA_SQL_TABLENAME, workbook);
		
		
		
	}

}
