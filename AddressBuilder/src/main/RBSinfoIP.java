package main;

public class RBSinfoIP {
	
	public RBSinfoIP () {
		
	}
	
	public RBSinfoIP (String RBSName){
		this.rbsName = RBSName;
	}
	
	public RBSinfoIP (String RBSName, String PEDevice){
		this.rbsName = RBSName;
		this.peDeviceName = PEDevice;
	}
	
	public RBSinfoIP (String RBSName, String PEDevice, String vlanName, int vlanID, String ipAddress){
		this.rbsName=RBSName;
		this.peDeviceName=PEDevice;
		this.vlanName=vlanName;
		this.vlanID=vlanID;
		this.ipAddress=ipAddress;
	}

	private String peDeviceName = "";
	private String rbsName;
	private String vlanName = "";
	private int vlanID;
	private String ipAddress = "";
	
	

}
