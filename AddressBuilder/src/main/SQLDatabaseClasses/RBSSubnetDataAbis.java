package main.SQLDatabaseClasses;

import javax.persistence.Entity;
import javax.persistence.Table;

import main.SQLDatabaseClasses.Superclasses.RBSSubnetData;
import main.UtilityClasses.DatabaseUtilityMySQL;

@Entity
@Table (name=DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_ABIS)
public class RBSSubnetDataAbis extends RBSSubnetData{
	
	//Constructors:
	public RBSSubnetDataAbis(){
		
	}

	public RBSSubnetDataAbis(String rbsData_rbsName, String peDevice,
			String vLAN, int vID, String ipAddress, int subnet) {
		super(rbsData_rbsName, peDevice, vLAN, vID, ipAddress, subnet);
		
	}

	public RBSSubnetDataAbis(String rbsData_rbsName) {
		super(rbsData_rbsName);
		
	}

	public RBSSubnetDataAbis(RBSSubnetData object) {
		super(object);
		
	}

	
	
	

}
