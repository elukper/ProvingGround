package main;

import org.apache.poi.ss.usermodel.Workbook;

public class AddressDBManipulationTool {
	
	private Workbook workbook;
	
	public String resolveNetAddressFromVLAN (Workbook wb, String VLAN) {
		
		int i = DatabaseUtil.START_ROW_RBS_DATA_SHEET;
		String NetAddress = "";
		
		for (i=DatabaseUtil.START_ROW_RBS_DATA_SHEET; i<wb.getSheet(DatabaseUtil.IMPORT_SUBNETS_SHEET_DATA).getLastRowNum(); i++){
			if(wb.getSheet(DatabaseUtil.IMPORT_SUBNETS_SHEET_DATA).getRow(i).getCell(DatabaseUtil.VLAN_ID_EXCEL_COLUMN).getStringCellValue().equals(VLAN)){
				NetAddress = wb.getSheet(DatabaseUtil.IMPORT_SUBNETS_SHEET_DATA).getRow(i).getCell(DatabaseUtil.VLAN_ID_EXCEL_COLUMN).getStringCellValue();
				break; 
				}
		
		 }
		
		return NetAddress;
		
	}
	
	
	public void insertRBSIntoDB (String RBS, String VLAN, String VLANType) {
		
	}

}
