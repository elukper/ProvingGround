package main.UtilityClasses;

import java.util.List;

import main.SQLDatabaseClasses.RBSSubnetDataAbis;
import main.SQLDatabaseClasses.RBSData;
import main.SQLDatabaseClasses.RBSSubnetDataIub;
import main.SQLDatabaseClasses.RBSSubnetDataOM;
import main.SQLDatabaseClasses.RBSSubnetDataS1;
import main.SQLDatabaseClasses.SubnetData;
import main.SQLDatabaseClasses.Superclasses.RBSSubnetData;

import org.apache.lucene.search.Query;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.xmlbeans.impl.piccolo.xml.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.transform.Transformers;

public final class DatabaseUtilityMySQL {
	
	//Final Static Variables:
	public static final String IMPORTWORKBOOKVALID = "ipRanImportSheet";
	public static final int START_ROW_RBS_DATA_SHEET = 1;
	public static final int START_ROW_SUBNETS_SHEET = 1;
	public static final String EXCEL_IMPORT_SUBNETS_SHEET_DATA = "Subnets";
	public static final String EXCEL_IMPORT_RBS_NODES_SHEET_DATA = "RBS Nodes";
	public static final String IUB_VLAN_TYPE = "IUB";
	public static final String S1_VLAN_TYPE = "S1";
	public static final String ABIS_VLAN_TYPE = "Abis";
	
	public static final int RBSNAME_EXCEL_COLUMN = 0;
	public static final int IUB_EXCEL_COLUMN = 3;
	public static final int S1_EXCEL_COLUMN = 4;
	public static final int OM_EXCEL_COLUMN = 2;
	public static final int ABIS_EXCEL_COLUMN = 5;
	public static final int SIU_EXCEL_COLUMN = 6;
	public static final int TN_ABIS_EXCEL_COLUMN = 8;
	public static final int TN_IUB_EXCEL_COLUMN = 9;
	public static final int TN_S1_EXCEL_COLUMN = 10;
	public static final int TN_OM_EXCEL_COLUMN = 7;
	public static final int PE_DEVICE_EXCEL_COLUMN = 1;
	public static final int VLAN_ID_EXCEL_COLUMN = 0;
	public static final int SUBNET_EXCEL_COLUMN = 1;
	
	public static final String SQLDB_SCHEMA_NAME = "AddressStorage";
	public static final String SQLDB_NAME = "AddressStorage";
	
	public static final String RBSDATA_SQL_TABLENAME = "rbsdata";
	public static final String RBSDATA_SQL_RBSNAME_COLUMN = "rbsName";
	public static final String RBSDATA_SQL_SIU_TCU_COLUMN = "tcuSIU";
	public static final String RBSDATA_SQL_ABIS_VID_COLUMN = "abisVID";
	public static final String RBSDATA_SQL_IUB_VID_COLUMN = "iubVID";
	public static final String RBSDATA_SQL_S1_VID_COLUMN = "s1VID";
	public static final String RBSDATA_SQL_OM_VID_COLUMN = "omVID";
	public static final String RBSDATA_SQL_TN_ABIS_VID_COLUMN = "tnAbisVID";
	public static final String RBSDATA_SQL_TN_IUB_VID_COLUMN = "tnIubVID";
	public static final String RBSDATA_SQL_TN_S1_VID_COLUMN = "tnS1VID";
	public static final String RBSDATA_SQL_TN_OM_VID_COLUMN = "tnOMVID";
	public static final String RBSDATA_SQL_PE_DEVICE = "peDevice";
	
	public static final String RBSSUBNETDATA_SQL_RBSNAME_COLUMN = "rbsData_rbsName";
	public static final String RBSSUBNETDATA_SQL_SUBNET_COLUMN = "subnet";
	public static final String RBSSUBNETDATA_SQL_VLAN_COLUMN = "VLAN";
	public static final String RBSSUBNETDATA_SQL_VID_COLUMN = "VID";
	public static final String RBSSUBNETDATA_SQL_IPADDRESS_COLUMN = "ipAddress";
	public static final String RBSSUBNETDATA_SQL_PEDEVICE_COLUMN = "peDevice";
	
	public static final String RBSSUBNETDATA_SQL_TABLENAME_IUB = "iubData";
	public static final String RBSSUBNETDATA_SQL_TABLENAME_OM = "omData";
	public static final String RBSSUBNETDATA_SQL_TABLENAME_S1 = "s1Data";
	public static final String RBSSUBNETDATA_SQL_TABLENAME_ABIS = "abisData";
	
	public static final String SUBNETDATA_SQL_TABLENAME = "subnetdata";
	public static final String SUBNETDATA_SQL_VID = "VID";
	public static final String SUBNETDATA_SQL_SUBNET = "Subnet";
	
	
	
	//Methods:
	public static void updateRBSDatabaseTable (String table, RBSSubnetData rbsSubnetData, Session session){
		
		RBSSubnetData newRbsSubnetData = null;
		
		switch(table) {
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_IUB: 	newRbsSubnetData = new RBSSubnetDataIub(rbsSubnetData); break;
		
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_ABIS: newRbsSubnetData = new RBSSubnetDataAbis(rbsSubnetData); break;
			
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_S1: newRbsSubnetData = new RBSSubnetDataS1(rbsSubnetData); break;
				
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_OM: newRbsSubnetData = new RBSSubnetDataOM(rbsSubnetData); break;
																			
		default: break;
		}
		
		if (newRbsSubnetData != null){
			writeDataToSQLTableSessionIncluded(newRbsSubnetData, session);
		
		}
		
	}
	
	//Populate Subnet or RBS Database Table, based on "table" keyword (name of the table)
	public static void populateDBTable(String table, Workbook workbook, Session session){		
		
		switch (table) {
		case DatabaseUtilityMySQL.SUBNETDATA_SQL_TABLENAME: populateSubnetDataTable(workbook, session); break;
		case DatabaseUtilityMySQL.RBSDATA_SQL_TABLENAME: populateRBSDataTable(workbook, session); break;
		default: break;
		}
		
	}
	
	private static void populateSubnetDataTable(Workbook workbook, Session session){
		//TODO: Throw exception if getSessionFactory()==null;
		
		int rowCounter = DatabaseUtilityMySQL.START_ROW_SUBNETS_SHEET;
		
		Transaction tx = session.beginTransaction();
		
		SubnetData subnetData = new SubnetData();
		
		try{
		while (workbook.getSheet(DatabaseUtilityMySQL.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getRow(rowCounter).getCell(DatabaseUtilityMySQL.VLAN_ID_EXCEL_COLUMN) != null){	
			
			subnetData.setVID(getIntCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_SUBNETS_SHEET_DATA, DatabaseUtilityMySQL.VLAN_ID_EXCEL_COLUMN, rowCounter));
			subnetData.setSubnet(getStringCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_SUBNETS_SHEET_DATA, DatabaseUtilityMySQL.SUBNET_EXCEL_COLUMN, rowCounter));
			
			writeDataToSQLTableSessionExcluded(subnetData, session);
			rowCounter++;
				}}
		
		//Catch NullPointer when rowCount reaches Null value row
		catch (java.lang.NullPointerException e){
			session.close();
		}
		
		tx.commit();
		if(session.isOpen()==true)
			session.close();
		
		 }
	
	private static String getStringCellFromExcelWorksheet(Workbook workbook, String worksheet, int column, int row) {
		
		workbook.getSheet(worksheet).getRow(row).getCell(column).setCellType(Cell.CELL_TYPE_STRING);
		return workbook.getSheet(worksheet).getRow(row).getCell(column).getStringCellValue();
		
	}
	
	private static int getIntCellFromExcelWorksheet (Workbook workbook, String worksheet, int column, int row) {
		
		workbook.getSheet(worksheet).getRow(row).getCell(column).setCellType(Cell.CELL_TYPE_NUMERIC);
		return (int)workbook.getSheet(worksheet).getRow(row).getCell(column).getNumericCellValue();
	}
	
	private static void populateRBSDataTable(Workbook workbook, Session session) {
		//TODO: Throw exception if getSessionFactory()==null;
		
		int rowCounter = DatabaseUtilityMySQL.START_ROW_RBS_DATA_SHEET;
		
		Transaction tx = session.beginTransaction();
		
		RBSData rbsData = new RBSData();
		
		try {
		while (workbook.getSheet(DatabaseUtilityMySQL.EXCEL_IMPORT_RBS_NODES_SHEET_DATA).getRow(rowCounter).getCell(DatabaseUtilityMySQL.RBSNAME_EXCEL_COLUMN) != null){
			
			rbsData.setRbsName(getStringCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_RBS_NODES_SHEET_DATA, DatabaseUtilityMySQL.RBSNAME_EXCEL_COLUMN, rowCounter));
			
			rbsData.setPeDevice(getStringCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_RBS_NODES_SHEET_DATA, DatabaseUtilityMySQL.PE_DEVICE_EXCEL_COLUMN, rowCounter));
			
			rbsData.setOmVID(getIntCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_RBS_NODES_SHEET_DATA, DatabaseUtilityMySQL.OM_EXCEL_COLUMN, rowCounter));
			
			rbsData.setIubVID(getIntCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_RBS_NODES_SHEET_DATA, DatabaseUtilityMySQL.IUB_EXCEL_COLUMN, rowCounter));
			
			rbsData.setS1VID(getIntCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_RBS_NODES_SHEET_DATA, DatabaseUtilityMySQL.S1_EXCEL_COLUMN, rowCounter));
			
			rbsData.setAbisVID(getIntCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_RBS_NODES_SHEET_DATA, DatabaseUtilityMySQL.ABIS_EXCEL_COLUMN, rowCounter));
			
			rbsData.setTcuSIU(getStringCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_RBS_NODES_SHEET_DATA, DatabaseUtilityMySQL.SIU_EXCEL_COLUMN, rowCounter));
			
			rbsData.setTnOMVID(getIntCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_RBS_NODES_SHEET_DATA, DatabaseUtilityMySQL.TN_OM_EXCEL_COLUMN, rowCounter));
			
			rbsData.setTnIubVID(getIntCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_RBS_NODES_SHEET_DATA, DatabaseUtilityMySQL.TN_IUB_EXCEL_COLUMN, rowCounter));
			
			rbsData.setTnS1VID(getIntCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_RBS_NODES_SHEET_DATA, DatabaseUtilityMySQL.TN_S1_EXCEL_COLUMN, rowCounter));
			
			rbsData.setTnAbisVID(getIntCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_RBS_NODES_SHEET_DATA, DatabaseUtilityMySQL.TN_ABIS_EXCEL_COLUMN, rowCounter));
			
			writeDataToSQLTableSessionExcluded(rbsData, session);
			rowCounter++;
			
		}}
		//Catch NullPointer when rowCount reaches Null value row
		catch (java.lang.NullPointerException e){
			session.close(); 
			}
		
		tx.commit();
		if(session.isOpen()==true)
			session.close();
	}

	
	//Returns list of SubnetData classes that 'match' data in a 'column'	
	
	public static List<SubnetData> getDataFromSubnetDataTable(String column, String data, Session session){
		
		List<SubnetData> subnetList = session.createSQLQuery("SELECT * FROM "+DatabaseUtilityMySQL.SUBNETDATA_SQL_TABLENAME+" WHERE "+column+" = '"+data+"'").setResultTransformer(Transformers.aliasToBean(SubnetData.class)).list();
		
		session.close();
		
		return subnetList;
		
	}
	
	//Returns list of all SubnetData classes
	
	public static List<SubnetData> getAllDataFromSubnetDataTableOrderBy(Session session) {
		
		List<SubnetData> subnetList = session.createSQLQuery("SELECT * FROM "+DatabaseUtilityMySQL.SUBNETDATA_SQL_TABLENAME).setResultTransformer(Transformers.aliasToBean(SubnetData.class)).list();
		
		session.close();
		
		return subnetList;
		
	}
	
	//Returns list of RBSData classes that 'match' data in a 'column'	
	
	public static List<RBSData> getDataFromRBSDataTable(String column, String match, Session session){
		
		List<RBSData> rbsDataList = session.createSQLQuery("SELECT * FROM "+DatabaseUtilityMySQL.RBSDATA_SQL_TABLENAME+" WHERE "+column+" = '"+match+"'").setResultTransformer(Transformers.aliasToBean(RBSData.class)).list();
		
		session.close();
		
		return rbsDataList;
		
	}
	
	
	public static List<RBSData> getAllDataFromRBSDataTableOrderBy(String column, Session session) {
		
		List<RBSData> rbsDataList = session.createSQLQuery("SELECT * FROM "+DatabaseUtilityMySQL.RBSDATA_SQL_TABLENAME+" ORDER BY "+column + "").setResultTransformer(Transformers.aliasToBean(RBSData.class)).list();
		
		session.close();
		
		return rbsDataList;
		
	}
	
	
	//Returns list of RBSSubnetData that 'match' data in 'column' from 'table'.
	public static List<RBSSubnetData> getDataFromRBSSubnetTable(String table, String column, String match, Session session){
		
		List<RBSSubnetData> rbsDataList = session.createSQLQuery("SELECT * FROM "+table+" WHERE "+column+" = '"+match+"'").setResultTransformer(Transformers.aliasToBean(RBSSubnetData.class)).list();
		
		session.close();
		
		return rbsDataList;
		
	}


	//write any object to a table, method handles opening and closing of a session and a transaction (used for a single write to SQL)
	public static void writeDataToSQLTableSessionIncluded(Object object, Session session){
			
		Transaction tx = session.beginTransaction();
		
		session.save(object);
		try{
		session.flush();
		} catch (ConstraintViolationException e) {
			session.clear();
			session.update(object);
			session.flush();
		}
		session.clear();
		tx.commit();
		session.close();
		
		
	}

	//write any object to a table, method doesn't handle opening/closing of sessions or transactions (used in loops when writing multiple entries into SQL)
	private static void writeDataToSQLTableSessionExcluded (Object object, Session session) {

			session.save(object);
			try { 
				session.flush();
				} catch (org.hibernate.exception.ConstraintViolationException e) {
					session.clear();
					session.update(object);
					session.flush();
				}
			session.clear();
			
		}
	
		
	}
		


