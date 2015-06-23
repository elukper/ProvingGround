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
	
	private static final String MYSQL_NETWORK_ADDRESS = "127.0.0.1:3306";
	private static final String MYSQL_USERNAME = "root";
	private static final String MYSQL_PASSWORD = "Vegas123";
	
	//Static Variables:
	private static SessionFactory sessionFactory;
	private static Session session;
	private static Configuration configuration;	
	private static String configOpenForDatabase;
	
	
	
	
	//Getters and setters:
	private static Session getSession() {
		return session;
	}

	private static void setSession(Session session) {
		DatabaseUtilityMySQL.session = session;
	}

	private static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private static void setSessionFactory(SessionFactory sessions) {
		DatabaseUtilityMySQL.sessionFactory = sessions;
	}
	
	private static String getConfigSetForDatabase() {
		return configOpenForDatabase;
	}

	private static void setConfigOpenForDatabase(String configOpenForDatabase) {
		DatabaseUtilityMySQL.configOpenForDatabase = configOpenForDatabase;
	}

	private static Configuration getConfiguration() {
		return configuration;
	}

	private static void setConfiguration(Configuration configuration) {
		DatabaseUtilityMySQL.configuration = configuration;
	}

	
	
	//Methods:
	public static void updateRBSDatabaseTable (String table, RBSSubnetData rbsSubnetData){
		//TODO: Throw exception if getSessionFactory()==null;
		setSession(getSessionFactory().openSession());
		Transaction tx = getSession().beginTransaction();
		
		RBSSubnetData newRbsSubnetData = null;
		
		switch(table) {
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_IUB: 	newRbsSubnetData = new RBSSubnetDataIub(rbsSubnetData); break;
		
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_ABIS: newRbsSubnetData = new RBSSubnetDataAbis(rbsSubnetData); break;
			
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_S1: newRbsSubnetData = new RBSSubnetDataS1(rbsSubnetData); break;
				
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_OM: newRbsSubnetData = new RBSSubnetDataOM(rbsSubnetData); break;
																			
		default: break;
		}
		
		if (newRbsSubnetData != null){
		getSession().save(newRbsSubnetData); 
		try { 
			getSession().flush();
			} catch (org.hibernate.exception.ConstraintViolationException e) {
				getSession().clear();
				getSession().update(newRbsSubnetData);
				getSession().flush();
			}
		
		getSession().clear();
		
		tx.commit();
	
		getSession().close();
		
		}
		
	}
	
	//Populate Subnet or RBS Database Table, based on "table" keyword (name of the table)
	public static void populateDBTable(String table, Workbook workbook){		
		
		switch (table) {
		case DatabaseUtilityMySQL.SUBNETDATA_SQL_TABLENAME: populateSubnetDataTable(workbook); break;
		case DatabaseUtilityMySQL.RBSDATA_SQL_TABLENAME: populateRBSDataTable(workbook); break;
		default: break;
		}
		
	}
	
	private static void populateSubnetDataTable(Workbook workbook){
		//TODO: Throw exception if getSessionFactory()==null;
		setSession(getSessionFactory().openSession());
		
		int rowCounter = DatabaseUtilityMySQL.START_ROW_SUBNETS_SHEET;
		
		Transaction tx = getSession().beginTransaction();
		
		SubnetData subnetData = new SubnetData();
		
		try{
		while (workbook.getSheet(DatabaseUtilityMySQL.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getRow(rowCounter).getCell(DatabaseUtilityMySQL.VLAN_ID_EXCEL_COLUMN) != null){	
			
			subnetData.setVID(getIntCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_SUBNETS_SHEET_DATA, DatabaseUtilityMySQL.VLAN_ID_EXCEL_COLUMN, rowCounter));
			subnetData.setSubnet(getStringCellFromExcelWorksheet(workbook, DatabaseUtilityMySQL.EXCEL_IMPORT_SUBNETS_SHEET_DATA, DatabaseUtilityMySQL.SUBNET_EXCEL_COLUMN, rowCounter));
			
			writeDataToSQLTable(subnetData);
			rowCounter++;
				}}
		
		//Catch NullPointer when rowCount reaches Null value row
		catch (java.lang.NullPointerException e){
			getSession().close();
		}
		
		tx.commit();
		if(getSession().isOpen()==true)
			getSession().close();
		
		 }
	
	private static String getStringCellFromExcelWorksheet(Workbook workbook, String worksheet, int column, int row) {
		
		workbook.getSheet(worksheet).getRow(row).getCell(column).setCellType(Cell.CELL_TYPE_STRING);
		return workbook.getSheet(worksheet).getRow(row).getCell(column).getStringCellValue();
		
	}
	
	private static int getIntCellFromExcelWorksheet (Workbook workbook, String worksheet, int column, int row) {
		
		workbook.getSheet(worksheet).getRow(row).getCell(column).setCellType(Cell.CELL_TYPE_NUMERIC);
		return (int)workbook.getSheet(worksheet).getRow(row).getCell(column).getNumericCellValue();
	}
	
	private static void populateRBSDataTable(Workbook workbook) {
		//TODO: Throw exception if getSessionFactory()==null;
		setSession(getSessionFactory().openSession());
		
		int rowCounter = DatabaseUtilityMySQL.START_ROW_RBS_DATA_SHEET;
		
		Transaction tx = getSession().beginTransaction();
		
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
			
			writeDataToSQLTable(rbsData);
			rowCounter++;
			
		}}
		//Catch NullPointer when rowCount reaches Null value row
		catch (java.lang.NullPointerException e){
			getSession().close(); 
			}
		
		tx.commit();
		if(getSession().isOpen()==true)
			getSession().close();
	}
	
	
	private static void initializeHibernateConfiguration(String database){
		
		if(getConfiguration()==null || !(database.equals(getConfigSetForDatabase()))){
			
		setConfiguration(new Configuration());
		setConfigOpenForDatabase(database);
		
		getConfiguration().setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		getConfiguration().setProperty("hibernate.connection.url","jdbc:mysql://" + DatabaseUtilityMySQL.MYSQL_NETWORK_ADDRESS + "/" + database);
		getConfiguration().setProperty("hibernate.connection.username", DatabaseUtilityMySQL.MYSQL_USERNAME);
		getConfiguration().setProperty("hibernate.connection.password", DatabaseUtilityMySQL.MYSQL_PASSWORD);
		getConfiguration().setProperty("hibernate.c3p0.min_siz", "5");
		getConfiguration().setProperty("hibernate.c3p0.max_size", "20");
		getConfiguration().setProperty("hibernate.c3p0.timeout", "1800");
		getConfiguration().setProperty("hibernate.c3p0.max_statements", "50");
		getConfiguration().setProperty("connection.pool_size", "10");
		getConfiguration().setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
		getConfiguration().setProperty("hibernate.search.default.directory_provider", "filesystem");
		getConfiguration().setProperty("hibernate.search.default.indexBase", "/var/lucene/indexes");
		getConfiguration().addAnnotatedClass(RBSData.class);
		getConfiguration().addAnnotatedClass(SubnetData.class);
		getConfiguration().addAnnotatedClass(RBSSubnetDataIub.class);
		getConfiguration().addAnnotatedClass(RBSSubnetDataAbis.class);
		getConfiguration().addAnnotatedClass(RBSSubnetDataOM.class);
		getConfiguration().addAnnotatedClass(RBSSubnetDataS1.class);
		getConfiguration().addAnnotatedClass(RBSSubnetData.class);
		}
		
		
	}
	
	//Returns list of SubnetData classes that 'match' data in a 'column'	
	
	public static List<SubnetData> getDataFromSubnetDataTable(String column, String data){
		
		setSession(getSessionFactory().openSession());
		
		List<SubnetData> subnetList = getSession().createSQLQuery("SELECT * FROM "+DatabaseUtilityMySQL.SUBNETDATA_SQL_TABLENAME+" WHERE "+column+" = '"+data+"'").setResultTransformer(Transformers.aliasToBean(SubnetData.class)).list();
		
		getSession().close();
		
		return subnetList;
		
	}
	
	//Returns list of all SubnetData classes
	
	public static List<SubnetData> getAllDataFromSubnetDataTableOrderBy() {
		
		setSession(getSessionFactory().openSession());
		
		List<SubnetData> subnetList = getSession().createSQLQuery("SELECT * FROM "+DatabaseUtilityMySQL.SUBNETDATA_SQL_TABLENAME).setResultTransformer(Transformers.aliasToBean(SubnetData.class)).list();
		
		getSession().close();
		
		return subnetList;
		
	}
	
	//Returns list of RBSData classes that 'match' data in a 'column'	
	
	public static List<RBSData> getDataFromRBSDataTable(String column, String match){
		
		setSession(getSessionFactory().openSession());
		
		List<RBSData> rbsDataList = getSession().createSQLQuery("SELECT * FROM "+DatabaseUtilityMySQL.RBSDATA_SQL_TABLENAME+" WHERE "+column+" = '"+match+"'").setResultTransformer(Transformers.aliasToBean(RBSData.class)).list();
		
		getSession().close();
		
		return rbsDataList;
		
	}
	
	
	public static List<RBSData> getAllDataFromRBSDataTableOrderBy(String column) {
		
		setSession(getSessionFactory().openSession());
		
		List<RBSData> rbsDataList = getSession().createSQLQuery("SELECT * FROM "+DatabaseUtilityMySQL.RBSDATA_SQL_TABLENAME+" ORDER BY "+column + "").setResultTransformer(Transformers.aliasToBean(RBSData.class)).list();
		
		getSession().close();
		
		return rbsDataList;
		
	}
	
	
	//Returns list of RBSSubnetData that 'match' data in 'column' from 'table'.
	public static List<RBSSubnetData> getDataFromRBSSubnetTable(String table, String column, String match){
		
		setSession(getSessionFactory().openSession());
		
		List<RBSSubnetData> rbsDataList = getSession().createSQLQuery("SELECT * FROM "+table+" WHERE "+column+" = '"+match+"'").setResultTransformer(Transformers.aliasToBean(RBSSubnetData.class)).list();
		
		getSession().close();
		
		return rbsDataList;
		
	}
	

	//initialize config and open SessionFactory session
	public static void openSessions(String database){
		
		DatabaseUtilityMySQL.initializeHibernateConfiguration(database);
		
		setSessionFactory(getConfiguration().buildSessionFactory());
		
		
	}
	
	//close SessionFactory session
	
	public static void closeSessions(){
		
		getSessionFactory().close();
		setSessionFactory(null);
	}

	
	public static void writeToRBSSubnetTable(String table, RBSSubnetData rbsSubnetData){
		
		setSession(getSessionFactory().openSession());
			
		Transaction tx = getSession().beginTransaction();
		
		getSession().save(rbsSubnetData);
		try{
		getSession().flush();
		} catch (ConstraintViolationException e) {
			getSession().clear();
			getSession().update(rbsSubnetData);
			getSession().flush();
		}
		getSession().clear();
		tx.commit();
		getSession().close();
		
		
	}

	private static void writeDataToSQLTable (Object object) {

			getSession().save(object);
			try { 
				getSession().flush();
				} catch (org.hibernate.exception.ConstraintViolationException e) {
					getSession().clear();
					getSession().update(object);
					getSession().flush();
				}
			getSession().clear();
			
		}
	
		
	}
		


