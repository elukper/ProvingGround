package main.SQLDatabaseClasses;

import javax.persistence.Entity;
import javax.persistence.Table;

import main.SQLDatabaseClasses.Superclasses.RBSSubnetData;
import main.UtilityClasses.DatabaseUtilityMySQL;

@Entity
@Table (name=DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_TABLENAME_S1)
public class RBSSubnetDataS1 extends RBSSubnetData{

	//Constructors:
	public RBSSubnetDataS1() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RBSSubnetDataS1(RBSSubnetData object) {
		super(object);
		// TODO Auto-generated constructor stub
	}

	public RBSSubnetDataS1(String rbsData_rbsName, String peDevice,
			String vLAN, int vID, String ipAddress, int subnet) {
		super(rbsData_rbsName, peDevice, vLAN, vID, ipAddress, subnet);
		// TODO Auto-generated constructor stub
	}

	public RBSSubnetDataS1(String rbsData_rbsName) {
		super(rbsData_rbsName);
		// TODO Auto-generated constructor stub
	}
	
	

}
