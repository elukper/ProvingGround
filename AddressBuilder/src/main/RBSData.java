package main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;




@Entity
@Table (name=DatabaseUtil.RBSDATA_SQL_TABLENAME)
public class RBSData {
	
	
	public RBSData (String rbsName) {
		this.rbsName = rbsName;
	}
	
	
	
	public RBSData(String rbsName, int iubVID, int s1vid, int omVID,
			int abisVID, String tcuSIU, int tnAbisVID, int tnIubVID,
			int tnS1VID, int tnOMVID, boolean siuLayer3Used) {
		super();
		this.rbsName = rbsName;
		this.iubVID = iubVID;
		s1VID = s1vid;
		this.omVID = omVID;
		this.abisVID = abisVID;
		this.tcuSIU = tcuSIU;
		this.tnAbisVID = tnAbisVID;
		this.tnIubVID = tnIubVID;
		this.tnS1VID = tnS1VID;
		this.tnOMVID = tnOMVID;
		this.siuLayer3Used = siuLayer3Used;
	}

	public RBSData(String rbsName, int iubVID, int s1vid, int omVID,
			int abisVID, String tcuSIU, boolean siuLayer3Used, String peDevice) {
		super();
		this.rbsName = rbsName;
		this.iubVID = iubVID;
		s1VID = s1vid;
		this.omVID = omVID;
		this.abisVID = abisVID;
		this.tcuSIU = tcuSIU;
		this.siuLayer3Used = siuLayer3Used;
		this.peDevice = peDevice;
	}





	private String rbsName;

	private int iubVID;
	private int s1VID;
	private int omVID;
	private int abisVID;
	
	private String tcuSIU;
	
	private int tnAbisVID;
	private int tnIubVID;
	private int tnS1VID;
	private int tnOMVID;
	
	private boolean siuLayer3Used;
	
	private String peDevice;
	
	@Id
	@Column(name=DatabaseUtil.RBSDATA_SQL_RBSNAME_COLUMN)
	public String getRbsName() {
		return rbsName;
	}

	public void setRbsName(String rbsName) {
		this.rbsName = rbsName;
	}
	
	@Column(name=DatabaseUtil.RBSDATA_SQL_IUB_VID_COLUMN)
	public int getIubVID() {
		return iubVID;
	}

	public void setIubVID(int iubVID) {
		this.iubVID = iubVID;
	}

	@Column(name=DatabaseUtil.RBSDATA_SQL_S1_VID_COLUMN)
	public int getS1VID() {
		return s1VID;
	}

	public void setS1VID(int s1vid) {
		s1VID = s1vid;
	}

	@Column(name=DatabaseUtil.RBSDATA_SQL_OM_VID_COLUMN)
	public int getOmVID() {
		return omVID;
	}

	public void setOmVID(int omVID) {
		this.omVID = omVID;
	}

	@Column(name=DatabaseUtil.RBSDATA_SQL_ABIS_VID_COLUMN)
	public int getAbisVID() {
		return abisVID;
	}

	public void setAbisVID(int abisVID) {
		this.abisVID = abisVID;
	}

	@Column(name=DatabaseUtil.RBSDATA_SQL_SIU_TCU_COLUMN)
	public String getTcuSIU() {
		return tcuSIU;
	}

	public void setTcuSIU(String tcuSIU) {
		this.tcuSIU = tcuSIU;
	}

	@Column(name=DatabaseUtil.RBSDATA_SQL_TN_ABIS_VID_COLUMN)
	public int getTnAbisVID() {
		return tnAbisVID;
	}

	public void setTnAbisVID(int tnAbisVID) {
		this.tnAbisVID = tnAbisVID;
	}

	@Column(name=DatabaseUtil.RBSDATA_SQL_TN_IUB_VID_COLUMN)
	public int getTnIubVID() {
		return tnIubVID;
	}

	public void setTnIubVID(int tnIubVID) {
		this.tnIubVID = tnIubVID;
	}

	@Column(name=DatabaseUtil.RBSDATA_SQL_TN_S1_VID_COLUMN)
	public int getTnS1VID() {
		return tnS1VID;
	}

	public void setTnS1VID(int tnS1VID) {
		this.tnS1VID = tnS1VID;
	}

	@Column(name=DatabaseUtil.RBSDATA_SQL_TN_OM_VID_COLUMN)
	public int getTnOMVID() {
		return tnOMVID;
	}

	public void setTnOMVID(int tnOMVID) {
		this.tnOMVID = tnOMVID;
	}

	@Column(name=DatabaseUtil.RBSDATA_SQL_SIU_LAYER3_COLUMN)
	public boolean isSiuLayer3Used() {
		return siuLayer3Used;
	}

	public void setSiuLayer3Used(boolean siuLayer3Used) {
		this.siuLayer3Used = siuLayer3Used;
	}


	@Column(name=DatabaseUtil.RBSDATA_SQL_PE_DEVICE)
	public String getPeDevice() {
		return peDevice;
	}



	public void setPeDevice(String peDevice) {
		this.peDevice = peDevice;
	}
	
	

	

}



