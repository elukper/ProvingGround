package main.DatabaseBuilders.TableBuilders;


import java.util.List;

import org.hibernate.Session;

import main.SQLDatabaseClasses.RBSData;
import main.SQLDatabaseClasses.RBSSubnetDataAbis;
import main.SQLDatabaseClasses.RBSSubnetDataIub;
import main.SQLDatabaseClasses.RBSSubnetDataOM;
import main.SQLDatabaseClasses.RBSSubnetDataS1;
import main.SQLDatabaseClasses.SubnetData;
import main.SQLDatabaseClasses.Superclasses.RBSSubnetData;
import main.UtilityClasses.DatabaseUtilityMySQL;
import main.UtilityClasses.IPAddressUtility;
import main.UtilityClasses.UtilMySQLConnection;

public class BuildNewAddressTable {
	
	private static int NUMBER_OF_ALLOWED_SAME_VID_ENTRIES_IN_SUBNETDATA = 1;
	
	int vid;
	String ipAddress;
	int netMask;
	private String tableName;
	
	public void buildAddressTable(String tableName, UtilMySQLConnection mySQLConnection){
		
		this.tableName = tableName;
		RBSData rbsData;
		
		this.vid = 0;
		
		List<RBSData> rbsDataList = DatabaseUtilityMySQL.getAllDataFromRBSDataTableOrderBy(this.resolveRBSSubnetDataTableNameToRBSDataColumn(), mySQLConnection.getNewSession());
		
		this.ipAddress = "";
		this.netMask = 0;
		
		for (int i = 0; i<rbsDataList.size(); i++){
			
			rbsData = rbsDataList.get(i);
			
			if(isNewVIDSame(rbsData.getSpecificVid(this.tableName))) {
				
				if(!(isNumOfVIDEntriesAllowed(DatabaseUtilityMySQL.getDataFromSubnetDataTable(DatabaseUtilityMySQL.SUBNETDATA_SQL_VID, ""+vid, mySQLConnection.getNewSession()).size()))){
					System.out.println("Double or no entry for VID: " + vid);
					continue;
				}
				
				this.ipAddress = IPAddressUtility.getIPAddress(DatabaseUtilityMySQL.getDataFromSubnetDataTable(DatabaseUtilityMySQL.SUBNETDATA_SQL_VID, ""+vid,mySQLConnection.getNewSession()).get(0).getSubnet());
				this.netMask = IPAddressUtility.getNetMask(DatabaseUtilityMySQL.getDataFromSubnetDataTable(DatabaseUtilityMySQL.SUBNETDATA_SQL_VID, ""+vid,mySQLConnection.getNewSession()).get(0).getSubnet());
				
			}
			
			ipAddress = IPAddressUtility.incrementIpAddress(ipAddress);
			
			if(isIPAddressBroadcast(ipAddress, netMask)){
				System.out.println("No more available IP addresses, IP address not assigned to: "+rbsData.getRbsName());
				continue;
			}
			
			switch(this.tableName) {
			case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_IUB: DatabaseUtilityMySQL.writeDataToSQLTableSessionIncluded(new RBSSubnetDataIub(rbsData.getRbsName(), rbsData.getPeDevice(), ""+vid, vid, ipAddress, netMask),mySQLConnection.getNewSession()); break;
			case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_ABIS: DatabaseUtilityMySQL.writeDataToSQLTableSessionIncluded(new RBSSubnetDataAbis(rbsData.getRbsName(), rbsData.getPeDevice(), ""+vid, vid, ipAddress, netMask),mySQLConnection.getNewSession()); break;
			case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_OM: DatabaseUtilityMySQL.writeDataToSQLTableSessionIncluded(new RBSSubnetDataOM(rbsData.getRbsName(), rbsData.getPeDevice(), ""+vid, vid, ipAddress, netMask),mySQLConnection.getNewSession()); break;
			case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_S1: DatabaseUtilityMySQL.writeDataToSQLTableSessionIncluded(new RBSSubnetDataS1(rbsData.getRbsName(), rbsData.getPeDevice(), ""+vid, vid, ipAddress, netMask),mySQLConnection.getNewSession()); break;
			default: System.out.println("No match in tables"); break;
			}
			
		}
	
}
	//check and update VID of this class
	private boolean isNewVIDSame(int vid){
		
		boolean returnVal = false;
		
		if(this.vid != vid)
			returnVal = true;
		
		this.vid = vid;
		
		return returnVal;
		
		
	}
	
	private boolean isIPAddressBroadcast(String ipAddress, int subnet){
		
		if (ipAddress.equals(IPAddressUtility.getBroadcastIPAddress(ipAddress, subnet)))
			return true;
		
		return false;
		
	}
	
	//Number of allowed entries for a single VLAN. Should be 1 (1 VLAN = 1 Subnet)
	private boolean isNumOfVIDEntriesAllowed (int vidEntries){
		
		if (vidEntries == this.NUMBER_OF_ALLOWED_SAME_VID_ENTRIES_IN_SUBNETDATA){
			return true;
		}
			
		return false;
		
	}
	
	//returns name of VLAN column in RBSData that matches the name of the RBSSubnetData table (e.g. for table name "DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_IUB" it returns "DatabaseUtilityMySQL.RBSDATA_SQL_IUB_VID_COLUMN"
	private String resolveRBSSubnetDataTableNameToRBSDataColumn(){
		
		switch (this.tableName) {
		
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_IUB: return DatabaseUtilityMySQL.RBSDATA_SQL_IUB_VID_COLUMN;
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_ABIS: return DatabaseUtilityMySQL.RBSDATA_SQL_ABIS_VID_COLUMN;
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_S1: return DatabaseUtilityMySQL.RBSDATA_SQL_S1_VID_COLUMN;
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_OM: return DatabaseUtilityMySQL.RBSDATA_SQL_OM_VID_COLUMN;
		default: return null;
		
		
		}		
	}
	
	}

