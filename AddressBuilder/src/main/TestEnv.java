package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import main.SQLDatabaseClasses.RBSData;
import main.SQLDatabaseClasses.RBSSubnetDataIub;
import main.SQLDatabaseClasses.SubnetData;
import main.SQLDatabaseClasses.Superclasses.RBSSubnetData;
import main.UtilityClasses.DatabaseUtilityMySQL;
import main.UtilityClasses.DatabaseUtilityTables;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class TestEnv extends Thread{
	
	public void run() {
		
		Workbook workbookinput = null;
		Workbook workbookoutput;
		
		
		
		try {
			workbookinput = new XSSFWorkbook(new FileInputStream("C:/personal/Java/input_31.xlsx"));
		} catch (EncryptedDocumentException e) { System.out.println("read error"); e.printStackTrace();
		} catch (FileNotFoundException e) { System.out.println("read error"); e.printStackTrace();
		} catch (IOException e) { System.out.println("read error"); e.printStackTrace();
		}
		
		
		if (workbookinput.getSheet(DatabaseUtilityMySQL.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getRow(0).getCell(0).getStringCellValue().equals("VLAN ID")) {
			System.out.println("radi");
		} else System.out.println("krivi file");
		
		FileOutputStream fileOut = null;
		
		/**int j = 0;
		while(workbookinput.getSheet(DatabaseUtil.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getRow(j)!=null){
			workbookinput.getSheet(DatabaseUtil.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getRow(j).getCell(0).setCellType(Cell.CELL_TYPE_STRING); 
			System.out.println("Cell Data: " + workbookinput.getSheet(DatabaseUtil.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getRow(j).getCell(0).getStringCellValue());
			j++;
		}
		
	/**	File excel = new File("C:/personal/Java/input_31.xlsx");
		
		try {
			fileOut = new FileOutputStream(excel);
		} catch (FileNotFoundException e) { System.out.println("write error"); e.printStackTrace();
		}
		
		
		if (fileOut != null){
		try {
			workbookinput.write(fileOut);
		} catch (IOException e) { System.out.println("write error"); e.printStackTrace(); }
		
	    try {
			fileOut.close();
		} catch (IOException e) { System.out.println("write error"); e.printStackTrace(); }
		} 
            
		if (fileOut != null) {
                try {
                	fileOut.flush();
                	fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        
	
		/**
		try {
			workbookinput = new XSSFWorkbook(new FileInputStream("C:/personal/Java/input_31.xlsx"));
		} catch (EncryptedDocumentException e) { System.out.println("read error"); e.printStackTrace();
		} catch (FileNotFoundException e) { System.out.println("read error"); e.printStackTrace();
		} catch (IOException e) { System.out.println("read error"); e.printStackTrace();
		}
		
		j=0;
		while(workbookinput.getSheet(DatabaseUtil.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getRow(j)!=null){
			System.out.println("Cell Data: " + workbookinput.getSheet(DatabaseUtil.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getRow(j).getCell(0).getStringCellValue()); j++;
		}
		
	/**	System.out.println(IPAddressManipulator.getBroadcastIPAddress("192.168.114.64", 27)); **/
		
		DatabaseUtilityMySQL.openSessions("AddressStorage");
		
		if(workbookinput != null){
		DatabaseUtilityMySQL.populateDBTable(DatabaseUtilityMySQL.SUBNETDATA_SQL_TABLENAME, workbookinput);
		
		DatabaseUtilityMySQL.populateDBTable(DatabaseUtilityMySQL.RBSDATA_SQL_TABLENAME, workbookinput);
		}
		
		RBSSubnetData rbsSubnetData = new RBSSubnetData("TEST", "Router", "TEST_VLAN", 105, "192.168.50.1", 24);
		
		DatabaseUtilityMySQL.updateRBSDatabaseTable(DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_IUB, rbsSubnetData);
		
		List<RBSSubnetData> rbsSList = DatabaseUtilityMySQL.getDataFromRBSSubnetTable(DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_IUB, "vLAN", "TEST_VLAN");
		
		System.out.println("Output from Iub database: "+rbsSList.get(0).getIpAddress()+" "+rbsSList.get(0).getVLAN()+" "+rbsSList.get(0).getRbsData_rbsName());
		
		//RBSIubSubnetData rbsIubSubnetData = new RBSIubSubnetData(
		//DatabaseUtil.closeSessions();
		
		//DatabaseUtil.openSessions("AddressStorage");
		
		List<SubnetData> subnetData = DatabaseUtilityMySQL.getDataFromSubnetDataTable(DatabaseUtilityMySQL.SUBNETDATA_SQL_SUBNET, "192.168.12.0/24");
		
		for (int i=0; i<subnetData.size(); i++)
		System.out.println("From Database: "+subnetData.get(i).getVID() + "  " + subnetData.get(i).getSubnet());
		
		List<RBSData> rbsData = DatabaseUtilityMySQL.getDataFromRBSDataTable(DatabaseUtilityMySQL.RBSDATA_SQL_RBSNAME_COLUMN, "PRIJEKO_BRDO");
		
		for (int i=0; i<rbsData.size(); i++)
		System.out.println("From Database: "+rbsData.get(i).getRbsName() + "  " + rbsData.get(i).getPeDevice());
		
		
		
		
		
		/**Configuration cfg = new Configuration();
		cfg.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		cfg.setProperty("hibernate.connection.url","jdbc:mysql://127.0.0.1:3306/AddressStorage");
		cfg.setProperty("hibernate.connection.username", "root");
		cfg.setProperty("hibernate.connection.password", "Vegas123");
		cfg.setProperty("hibernate.c3p0.min_siz", "5");
		cfg.setProperty("hibernate.c3p0.max_size", "20");
		cfg.setProperty("hibernate.c3p0.timeout", "1800");
		cfg.setProperty("hibernate.c3p0.max_statements", "50");
		cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
		cfg.addAnnotatedClass(RBSData.class);
		
		SessionFactory sessions = cfg.buildSessionFactory();
    
		
		Session session = sessions.openSession();
		
		System.out.println("Connection established: " + session.isConnected());
		
		RBSData rbs = new RBSData("test", 100, 200, 300, 400, "SIU_45", false,"Cisco_3600");
		
		Transaction tx = session.beginTransaction();
		
		session.save(rbs);
		//session.delete(rbs);
		session.flush();
		session.clear();
		
		tx.commit();
		
		session.close();
		
		System.out.println("Connection established: " + session.isConnected());
		
		sessions.close();**/
		
		DatabaseUtilityTables databaseUtilityTables = new DatabaseUtilityTables();
		
		databaseUtilityTables.createAddressTable(DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_OM);
		
		DatabaseUtilityMySQL.closeSessions();

	}}


