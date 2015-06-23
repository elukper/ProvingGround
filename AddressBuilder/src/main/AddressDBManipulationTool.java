package main;

import main.UtilityClasses.DatabaseUtilityMySQL;

import org.apache.poi.ss.usermodel.Workbook;

public class AddressDBManipulationTool {
	
	private Workbook workbook;
	
	public String resolveNetAddressFromVLAN (Workbook wb, String VLAN) {
		
		int i = DatabaseUtilityMySQL.START_ROW_RBS_DATA_SHEET;
		String NetAddress = "";
		
		for (i=DatabaseUtilityMySQL.START_ROW_RBS_DATA_SHEET; i<wb.getSheet(DatabaseUtilityMySQL.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getLastRowNum(); i++){
			if(wb.getSheet(DatabaseUtilityMySQL.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getRow(i).getCell(DatabaseUtilityMySQL.VLAN_ID_EXCEL_COLUMN).getStringCellValue().equals(VLAN)){
				NetAddress = wb.getSheet(DatabaseUtilityMySQL.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getRow(i).getCell(DatabaseUtilityMySQL.VLAN_ID_EXCEL_COLUMN).getStringCellValue();
				break; 
				}
		
		 }
		
		return NetAddress;
		
	}
	
	
	public void insertRBSIntoDB (String RBS, String VLAN, String VLANType) {
		
	}

}
