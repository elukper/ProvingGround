package main;

import java.util.List;

import org.apache.lucene.search.Query;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.xmlbeans.impl.piccolo.xml.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

public class DatabaseUtil {
	
	public static final String IMPORTWORKBOOKVALID = "ipRanImportSheet";
	public static final int START_ROW = 3;
	public static final String IMPORT_WB_SHEET_DATA = "RBS Nodes";
	public static final String IUB_VLAN_TYPE = "IUB";
	public static final String S1_VLAN_TYPE = "S1";
	public static final String ABIS_VLAN_TYPE = "Abis";
	
	public static final int RBSNAME_EXCEL_COLUMN = 0;
	public static final int IUB_EXCEL_COLUMN = 1;
	public static final int S1_EXCEL_COLUMN = 2;
	public static final int OM_EXCEL_COLUMN = 3;
	public static final int ABIS_EXCEL_COLUMN = 4;
	public static final int SIU_EXCEL_COLUMN = 5;
	public static final int TN_ABIS_EXCEL_COLUMN = 6;
	public static final int TN_IUB_EXCEL_COLUMN = 7;
	public static final int TN_S1_EXCEL_COLUMN = 8;
	public static final int TN_OM_EXCEL_COLUMN = 9;
	public static final int PE_DEVICE_EXCEL_COLUMN = 10;
	public static final int VLAN_ID_EXCEL_COLUMN = 13;
	public static final int SUBNET_EXCEL_COLUMN = 14;
	
	public static final String SQLDB_SCHEMA_NAME = "AddressStorage";
	public static final String SQLDB_NAME = "AddressStorage";
	
	public static final String RBSDATA_SQL_TABLENAME = "rbsdata";
	public static final String RBSDATA_SQL_RBSNAME_COLUMN = "RBSName";
	public static final String RBSDATA_SQL_SIU_TCU_COLUMN = "TCU_SIU";
	public static final String RBSDATA_SQL_SIU_LAYER3_COLUMN = "SIU_Layer3";
	public static final String RBSDATA_SQL_ABIS_VID_COLUMN = "Abis_VID";
	public static final String RBSDATA_SQL_IUB_VID_COLUMN = "Iub_VID";
	public static final String RBSDATA_SQL_S1_VID_COLUMN = "S1_VID";
	public static final String RBSDATA_SQL_OM_VID_COLUMN = "OM_VID";
	public static final String RBSDATA_SQL_TN_ABIS_VID_COLUMN = "TN_Abis_VID";
	public static final String RBSDATA_SQL_TN_IUB_VID_COLUMN = "TN_Iub_VID";
	public static final String RBSDATA_SQL_TN_S1_VID_COLUMN = "TN_S1_VID";
	public static final String RBSDATA_SQL_TN_OM_VID_COLUMN = "TN_OM_VID";
	public static final String RBSDATA_SQL_PE_DEVICE = "PE3_Device";
	
	public static final String SUBNETDATA_SQL_TABLENAME = "subnetdata";
	public static final String SUBNETDATA_SQL_VID = "VID";
	public static final String SUBNETDATA_SQL_SUBNET = "Subnet";
	
	private static final String MYSQL_NETWORK_ADDRESS = "127.0.0.1:3306";
	private static final String MYSQL_USERNAME = "root";
	private static final String MYSQL_PASSWORD = "Vegas123";
	
	private static SessionFactory sessionFactory;
	
	private static Session session;

	private static Configuration configuration;
	
	private static String configOpenForDatabase;
	
	private static Session getSession() {
		return session;
	}

	private static void setSession(Session session) {
		DatabaseUtil.session = session;
	}

	private static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private static void setSessionFactory(SessionFactory sessions) {
		DatabaseUtil.sessionFactory = sessions;
	}
	
	private static String getConfigSetForDatabase() {
		return configOpenForDatabase;
	}

	private static void setConfigOpenForDatabase(String configOpenForDatabase) {
		DatabaseUtil.configOpenForDatabase = configOpenForDatabase;
	}

	private static Configuration getConfiguration() {
		return configuration;
	}

	private static void setConfiguration(Configuration configuration) {
		DatabaseUtil.configuration = configuration;
	}

	//Populate Database Table, based on "table" keyword (name of the table)
	public static void populateDBTable(String table, Workbook workbook){		
		
		switch (table) {
		case DatabaseUtil.SUBNETDATA_SQL_TABLENAME: updateSubnetDataTable(workbook);
														break;
		default: break;
		}
		
	}
	
	private static void updateSubnetDataTable(Workbook workbook){
		
		setSession(getSessionFactory().openSession());
		
		int i = DatabaseUtil.START_ROW;
		
		Transaction tx = getSession().beginTransaction();
		
		SubnetData subnetData = new SubnetData();
		
		while (workbook.getSheet(DatabaseUtil.IMPORT_WB_SHEET_DATA).getRow(i).getCell(DatabaseUtil.VLAN_ID_EXCEL_COLUMN) != null){	
			subnetData.setVID(Integer.parseInt(workbook.getSheet(DatabaseUtil.IMPORT_WB_SHEET_DATA).getRow(i).getCell(DatabaseUtil.VLAN_ID_EXCEL_COLUMN).getStringCellValue()));
			subnetData.setSubnet(workbook.getSheet(DatabaseUtil.IMPORT_WB_SHEET_DATA).getRow(i).getCell(DatabaseUtil.SUBNET_EXCEL_COLUMN).getStringCellValue());
			//System.out.println(subnetData.getVID()+"   "+subnetData.getSubnet());
			getSession().save(subnetData);
			getSession().flush();
			getSession().clear();
			i++;
				}
		tx.commit();
		
		getSession().close();
		
		
		 }
	
	public static List<SubnetData> getDataFromSubnetDataTable(){
		
		setSession(getSessionFactory().openSession());
		
		FullTextSession fullTextSession = Search.getFullTextSession(getSession());
		
		Transaction tx = fullTextSession.beginTransaction();

		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(SubnetData.class).get();
		
		org.apache.lucene.search.Query query = qb.keyword().onField(DatabaseUtil.SUBNETDATA_SQL_VID).matching("106").createQuery();
		
		// wrap Lucene query in a org.hibernate.Query
		org.hibernate.Query hibQuery =
		    fullTextSession.createFullTextQuery(query, SubnetData.class);
		
		List<SubnetData> subnetList = hibQuery.list();
		
		tx.commit();
		
		getSession().close();
		
		return subnetList;
		
	}
		
	
	public static void initializeConfiguration(String database){
		
		if(getConfiguration()==null || !(database.equals(getConfigSetForDatabase()))){
			
		setConfiguration(new Configuration());
		setConfigOpenForDatabase(database);
		
		getConfiguration().setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		getConfiguration().setProperty("hibernate.connection.url","jdbc:mysql://" + DatabaseUtil.MYSQL_NETWORK_ADDRESS + "/" + database);
		getConfiguration().setProperty("hibernate.connection.username", DatabaseUtil.MYSQL_USERNAME);
		getConfiguration().setProperty("hibernate.connection.password", DatabaseUtil.MYSQL_PASSWORD);
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
		}
		
		
	}
	
	public static void openSessions(String database){
		
		DatabaseUtil.initializeConfiguration(database);
		
		setSessionFactory(getConfiguration().buildSessionFactory());
		
		
	}
	
	public static void closeSessions(){
		
		getSessionFactory().close();
	}




}
