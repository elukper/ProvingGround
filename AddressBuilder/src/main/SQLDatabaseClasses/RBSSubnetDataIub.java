package main.SQLDatabaseClasses;

import javax.persistence.Entity;
import javax.persistence.Table;

import main.SQLDatabaseClasses.Superclasses.RBSSubnetData;
import main.UtilityClasses.DatabaseUtilityMySQL;

@Entity
@Table (name=DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_IUB)
public class RBSSubnetDataIub extends RBSSubnetData{

	//Constructors:
	public RBSSubnetDataIub() {
	}

	public RBSSubnetDataIub(String rbsData_rbsName, String peDevice,
			String vLAN, int vID, String ipAddress, int subnet) {
		super(rbsData_rbsName, peDevice, vLAN, vID, ipAddress,subnet);
		
	}

	public RBSSubnetDataIub(String rbsData_rbsName) {
		super(rbsData_rbsName);
	}

	public RBSSubnetDataIub(RBSSubnetData object) {
		super(object);
		
	}
	
	
	

}
