package main.SQLDatabaseClasses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import main.UtilityClasses.DatabaseUtilityMySQL;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table (name=DatabaseUtilityMySQL.SUBNETDATA_SQL_TABLENAME)
public class SubnetData {
	
	public SubnetData() {
		
	}
	
	public SubnetData(int vID, String subnet) {
		super();
		VID = vID;
		this.subnet = subnet;
	}

	private int VID;
	
	private String subnet;

	@Id
	@Column(name=DatabaseUtilityMySQL.SUBNETDATA_SQL_VID)
	public int getVID() {
		return VID;
	}

	public void setVID(int vID) {
		VID = vID;
	}

	@Column(name=DatabaseUtilityMySQL.SUBNETDATA_SQL_SUBNET)
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	public String getSubnet() {
		return subnet;
	}

	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}
	
	

}
