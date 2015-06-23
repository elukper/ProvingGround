package main.UtilityClasses;


public class IPAddressUtility {
	
	//Input example: 192.168.0.1/24 (can be 192.168.0.1); Output example: 192.168.0.0
	public static final String getIPAddress(String NetAddress) { 
				
		String[] split = NetAddress.split("/");
		
		return  split[0];
		
	}
	
	//Input example: 192.168.0.1/24; output example: 24
	public static final int getNetMask(String NetAddress) {
		
		String[] split = NetAddress.split("/");
		
		return  Integer.parseInt(split[1]);
	}
	
	//Input example: 192.168.0.1, 24; Output example: 192.168.0.255
	public static final String getBroadcastIPAddress (String IPAddress, int subnet){
		
		final int intMax = 0xFFFFFFFF;
		
		IPAddress ipAddress = new IPAddress(IPAddress);
		
		int wildmask = (intMax >>> subnet);
		
		int broadcast = (ipAddress.getValue() | wildmask);
		
		IPAddress broadcastAddress = new IPAddress(broadcast);
		
		return broadcastAddress.toString();
	}
	
	//Input example: 192.168.0.1; Output example: 192.168.0.2
	public static final String incrementIpAddress (String IpAddress) {
		
		IPAddress ipAddress = new IPAddress(IpAddress);
		
		return (ipAddress.next()).toString();
		
	}

}
