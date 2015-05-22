package main;

import org.apache.poi.ss.usermodel.Workbook;

public class NewAddressDB {
	
	private Workbook workbookinput;
	
	public void BuildNewDB (Workbook wb) { this.workbookinput = wb; } {
		
		int i = DatabaseUtil.START_ROW_RBS_DATA_SHEET;
		
		while (workbookinput.getSheet(DatabaseUtil.IMPORT_SUBNETS_SHEET_DATA).getRow(i).getCell(DatabaseUtil.RBSNAME_EXCEL_COLUMN)!=null){
			
			String RBSName = workbookinput.getSheet(DatabaseUtil.IMPORT_SUBNETS_SHEET_DATA).getRow(i).getCell(DatabaseUtil.RBSNAME_EXCEL_COLUMN).getStringCellValue();
			
			
			
		}
		
	}
	
	

}
