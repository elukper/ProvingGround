package main.SQLDatabaseClasses;

import javax.persistence.Entity;
import javax.persistence.Table;

import main.SQLDatabaseClasses.Superclasses.RBSSubnetData;
import main.UtilityClasses.DatabaseUtilityMySQL;

@Entity
@Table (name=DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_OM)
public class RBSSubnetDataOM extends RBSSubnetData{

	//Constructors:
	public RBSSubnetDataOM() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RBSSubnetDataOM(RBSSubnetData object) {
		super(object);
		// TODO Auto-generated constructor stub
	}

	public RBSSubnetDataOM(String rbsData_rbsName, String peDevice,
			String vLAN, int vID, String ipAddress, int subnet) {
		super(rbsData_rbsName, peDevice, vLAN, vID, ipAddress, subnet);
		// TODO Auto-generated constructor stub
	}

	public RBSSubnetDataOM(String rbsData_rbsName) {
		super(rbsData_rbsName);
		// TODO Auto-generated constructor stub
	}
	
	

}
