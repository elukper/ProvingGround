package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
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
		Workbook workbookoutput = new XSSFWorkbook();
		
		
		
		try {
			workbookinput = new XSSFWorkbook(new FileInputStream("C:/personal/Java/input_21.xlsm"));
		} catch (EncryptedDocumentException e) { System.out.println("read error"); e.printStackTrace();
		} catch (FileNotFoundException e) { System.out.println("read error"); e.printStackTrace();
		} catch (IOException e) { System.out.println("read error"); e.printStackTrace();
		}
		
		
		if (workbookinput.getSheet("variables").getRow(0).getCell(0).getStringCellValue().equals(DatabaseUtil.IMPORTWORKBOOKVALID)) {
			System.out.println("radi");
		} else System.out.println("krivi file"); FileOutputStream fileOut = null;
		
		try {
			fileOut = new FileOutputStream("C:/personal/Java/output_21.xlsx");
		} catch (FileNotFoundException e) { System.out.println("write error"); e.printStackTrace();
		}
		
		if (fileOut != null){
		try {
			workbookoutput.write(fileOut);
		} catch (IOException e) { System.out.println("write error"); e.printStackTrace(); }
		
	    try {
			fileOut.close();
		} catch (IOException e) { System.out.println("write error"); e.printStackTrace(); }
		}
		
		System.out.println(IPAddressManipulator.getBroadcastIPAddress("192.168.114.64", 27));
		
		DatabaseUtil.openSessions("AddressStorage");
		
		if(workbookinput != null)
		DatabaseUtil.populateDBTable(DatabaseUtil.SUBNETDATA_SQL_TABLENAME, workbookinput);
		
		//DatabaseUtil.populateDBTable(DatabaseUtil.RBSDATA_SQL_TABLENAME, workbookinput);
		
		//DatabaseUtil.closeSessions();
		
		//DatabaseUtil.openSessions("AddressStorage");
		
		List<SubnetData> subnetData = DatabaseUtil.getDataFromSubnetDataTable(DatabaseUtil.SUBNETDATA_SQL_SUBNET, "192.168.12.0/24");
		
		for (int i=0; i<subnetData.size(); i++)
		System.out.println("From Database: "+subnetData.get(i).getVID() + "  " + subnetData.get(i).getSubnet());
		
		List<RBSData> rbsData = DatabaseUtil.getDataFromRBSDataTable(DatabaseUtil.RBSDATA_SQL_RBSNAME_COLUMN, "PRIJEKO_BRDO");
		
		for (int i=0; i<subnetData.size(); i++)
		System.out.println("From Database: "+subnetData.get(i).getVID() + "  " + subnetData.get(i).getSubnet());
		
		DatabaseUtil.closeSessions();
		
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

	}

}
