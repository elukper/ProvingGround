package main.DatabaseBuilders;

import main.UtilityClasses.DatabaseUtilityMySQL;
import main.UtilityClasses.UtilMySQLConnection;

import org.apache.poi.ss.usermodel.Workbook;

public class BuildNewDatabase extends Thread{
	
	//Constructors:
	public BuildNewDatabase(Workbook workbook,UtilMySQLConnection mySQLConnection){
		this.mySQLConnection = mySQLConnection;
		this.workbook = workbook;
		
	}
	
	//Variables:
	private Workbook workbook;
	private UtilMySQLConnection mySQLConnection;
	
	//Methods:
	public void run() {
		
		//ToDo: create database based on scheme
		
		
		DatabaseUtilityMySQL.populateDBTable(DatabaseUtilityMySQL.SUBNETDATA_SQL_TABLENAME, workbook, mySQLConnection.getNewSession());
		
		DatabaseUtilityMySQL.populateDBTable(DatabaseUtilityMySQL.RBSDATA_SQL_TABLENAME, workbook, mySQLConnection.getNewSession());
		
		
		
	}

}
