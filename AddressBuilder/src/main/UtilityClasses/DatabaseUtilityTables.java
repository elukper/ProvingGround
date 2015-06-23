package main.UtilityClasses;


import java.util.List;

import main.SQLDatabaseClasses.RBSData;
import main.SQLDatabaseClasses.RBSSubnetDataAbis;
import main.SQLDatabaseClasses.RBSSubnetDataIub;
import main.SQLDatabaseClasses.RBSSubnetDataOM;
import main.SQLDatabaseClasses.RBSSubnetDataS1;
import main.SQLDatabaseClasses.SubnetData;
import main.SQLDatabaseClasses.Superclasses.RBSSubnetData;

public class DatabaseUtilityTables {
	
	
	private static int NUMBER_OF_ALLOWED_SAME_VID_ENTRIES_IN_SUBNETDATA = 1;
	
	int vid;
	String ipAddress;
	int netMask;
	
	public void createAddressTable(String tableName){
		
		RBSData rbsData;
		
		this.vid = 0;
		
		List<RBSData> rbsDataList = DatabaseUtilityMySQL.getAllDataFromRBSDataTableOrderBy(this.resolveRBSSubnetDataTableNameToRBSDataColumn(tableName));
		
		this.ipAddress = "";
		this.netMask = 0;
		
		for (int i = 0; i<rbsDataList.size(); i++){
			
			rbsData = rbsDataList.get(i);
			
			if(isNewVIDSame(rbsData.getSpecificVid(tableName))) {
				
				if(!(isNumOfVIDEntriesAllowed(DatabaseUtilityMySQL.getDataFromSubnetDataTable(DatabaseUtilityMySQL.SUBNETDATA_SQL_VID, ""+vid).size()))){
					System.out.println("Double or no entry for VID: " + vid);
					continue;
				}
				
				this.ipAddress = IPAddressUtility.getIPAddress(DatabaseUtilityMySQL.getDataFromSubnetDataTable(DatabaseUtilityMySQL.SUBNETDATA_SQL_VID, ""+vid).get(0).getSubnet());
				this.netMask = IPAddressUtility.getNetMask(DatabaseUtilityMySQL.getDataFromSubnetDataTable(DatabaseUtilityMySQL.SUBNETDATA_SQL_VID, ""+vid).get(0).getSubnet());
				
			}
			
			ipAddress = IPAddressUtility.incrementIpAddress(ipAddress);
			
			if(isIPAddressBroadcast(ipAddress, netMask)){
				System.out.println("No more available IP addresses, IP address not assigned to: "+rbsData.getRbsName());
				continue;
			}
			
			switch(tableName) {
			case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_IUB: DatabaseUtilityMySQL.writeToRBSSubnetTable(tableName, new RBSSubnetDataIub(rbsData.getRbsName(), rbsData.getPeDevice(), ""+vid, vid, ipAddress, netMask)); break;
			case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_ABIS: DatabaseUtilityMySQL.writeToRBSSubnetTable(tableName, new RBSSubnetDataAbis(rbsData.getRbsName(), rbsData.getPeDevice(), ""+vid, vid, ipAddress, netMask)); break;
			case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_OM: DatabaseUtilityMySQL.writeToRBSSubnetTable(tableName, new RBSSubnetDataOM(rbsData.getRbsName(), rbsData.getPeDevice(), ""+vid, vid, ipAddress, netMask)); break;
			case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_S1: DatabaseUtilityMySQL.writeToRBSSubnetTable(tableName, new RBSSubnetDataS1(rbsData.getRbsName(), rbsData.getPeDevice(), ""+vid, vid, ipAddress, netMask)); break;
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
	private String resolveRBSSubnetDataTableNameToRBSDataColumn(String tableName){
		
		switch (tableName) {
		
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_IUB: return DatabaseUtilityMySQL.RBSDATA_SQL_IUB_VID_COLUMN;
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_ABIS: return DatabaseUtilityMySQL.RBSDATA_SQL_ABIS_VID_COLUMN;
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_S1: return DatabaseUtilityMySQL.RBSDATA_SQL_S1_VID_COLUMN;
		case DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_OM: return DatabaseUtilityMySQL.RBSDATA_SQL_OM_VID_COLUMN;
		default: return null;
		
		
		}		
	}
	
	}

