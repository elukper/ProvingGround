package main;

public class IPAddressManipulator {
	
	public static final String getIPAddress(String NetAddress) { 
				
		String[] split = NetAddress.split("/");
		
		return  split[0];
		
	}
	
	public static final int getSubnet(String NetAddress) {
		
		String[] split = NetAddress.split("/");
		
		return  Integer.parseInt(split[1]);
	}
	
	public static final String getBroadcastIPAddress (String IPAddress, int subnet){
		
		final int intMax = 0xFFFFFFFF;
		
		IPAddress ipAddress = new IPAddress(IPAddress);
		
		int wildmask = (intMax >>> subnet);
		
		int broadcast = (ipAddress.getValue() | wildmask);
		
		IPAddress broadcastAddress = new IPAddress(broadcast);
		
		return broadcastAddress.toString();
	}
	
	public static final String incrementIpAddress (String IpAddress) {
		
		IPAddress ipAddress = new IPAddress(IpAddress);
		
		return (ipAddress.next()).toString();
		
	}

}
