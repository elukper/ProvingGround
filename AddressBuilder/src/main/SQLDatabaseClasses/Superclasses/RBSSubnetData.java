package main.SQLDatabaseClasses.Superclasses;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import main.UtilityClasses.DatabaseUtilityMySQL;

@MappedSuperclass
public class RBSSubnetData {
	
	//Constructors
	public RBSSubnetData () {
		
	}
	
	public RBSSubnetData (String rbsData_rbsName) {
		this.rbsData_rbsName = rbsData_rbsName;
	}

	public RBSSubnetData(String rbsData_rbsName, String peDevice, String vLAN,
			int vID, String ipAddress, int subnet) {
		super();
		this.peDevice = peDevice;
		this.rbsData_rbsName = rbsData_rbsName;
		this.VLAN = vLAN;
		this.VID = vID;
		this.ipAddress = ipAddress;
		this.subnet = subnet;
	}
	
	public RBSSubnetData(RBSSubnetData object){
		setIpAddress(object.getIpAddress());
		setPeDevice(object.getPeDevice());
		setRbsData_rbsName(object.getRbsData_rbsName());
		setSubnet(object.getSubnet());
		setVID(object.getVID());
		setVLAN(object.getVLAN());
	}
	
	public void insertDataFromRBSSubnetDataObject (RBSSubnetData object){
		setIpAddress(object.getIpAddress());
		setPeDevice(object.getPeDevice());
		setRbsData_rbsName(object.getRbsData_rbsName());
		setSubnet(object.getSubnet());
		setVID(object.getVID());
		setVLAN(object.getVLAN());
	}


	//Variables
	private String peDevice;
	private String rbsData_rbsName;
	private String VLAN;
	private int VID;
	private String ipAddress = "";
	private int subnet;
	
	//Getters and setters
	@Id
	@Column(name=DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_RBSNAME_COLUMN)
	public String getRbsData_rbsName() {
		return rbsData_rbsName;
	}

	public void setRbsData_rbsName(String rbsData_rbsName) {
		this.rbsData_rbsName = rbsData_rbsName;
	}
	
	@Column(name=DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_SUBNET_COLUMN)
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	public int getSubnet() {
		return subnet;
	}

	public void setSubnet(int subnet) {
		this.subnet = subnet;
	}

	@Column(name=DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_PEDEVICE_COLUMN)
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	public String getPeDevice() {
		return peDevice;
	}

	public void setPeDevice(String peDevice) {
		this.peDevice = peDevice;
	}

	@Column(name=DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_VLAN_COLUMN)
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	public String getVLAN() {
		return VLAN;
	}

	public void setVLAN(String vLAN) {
		VLAN = vLAN;
	}

	@Column(name=DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_VID_COLUMN)
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	public int getVID() {
		return VID;
	}

	public void setVID(int vID) {
		VID = vID;
	}

	@Column(name=DatabaseUtilityMySQL.RBSSUBNETDATA_SQL_IPADDRESS_COLUMN)
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	

}
