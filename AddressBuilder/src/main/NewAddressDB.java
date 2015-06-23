package main;

import main.UtilityClasses.DatabaseUtilityMySQL;

import org.apache.poi.ss.usermodel.Workbook;

public class NewAddressDB {
	
	private Workbook workbookinput;
	
	public void BuildNewDB (Workbook wb) { this.workbookinput = wb; } {
		
		int i = DatabaseUtilityMySQL.START_ROW_RBS_DATA_SHEET;
		
		while (workbookinput.getSheet(DatabaseUtilityMySQL.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getRow(i).getCell(DatabaseUtilityMySQL.RBSNAME_EXCEL_COLUMN)!=null){
			
			String RBSName = workbookinput.getSheet(DatabaseUtilityMySQL.EXCEL_IMPORT_SUBNETS_SHEET_DATA).getRow(i).getCell(DatabaseUtilityMySQL.RBSNAME_EXCEL_COLUMN).getStringCellValue();
			
			
			
		}
		
	}
	
	

}
