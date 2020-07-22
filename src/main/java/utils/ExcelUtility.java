package main.java.utils;

/**
 * @author lionel.mangoua
 * date: 07/07/20
 */

import main.java.Engine.DriverFactory;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ExcelUtility extends DriverFactory {

    //region <Method reads data from the spreadsheet and store it in a 2D array>
    public static String[][] readExcelFile (String sheetName) {

        try {
            /*
             *Creating a Workbook from an Excel file (.xls or .xlsx)
             *And make a temporary copy to prevent changes to the one under
             *version control.
             */
            String originalName, tmpName;
            originalName = xlsxFilePath();
            tmpName = originalName.replace(".xls", "-tmp.xls"); // also covers .xlsx files
            Files.copy(
                    Paths.get(originalName),
                    Paths.get(tmpName),
                    StandardCopyOption.REPLACE_EXISTING
            );

            Workbook workbook = WorkbookFactory.create(new File(tmpName)); // Create Excel Instance

            //Get the data from the sheet you want to read
            Sheet sheet = workbook.getSheet(sheetName); // Get Sheet named as sheetName value

            //Find number of rows in excel file
            int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
            int numRows = sheet.getLastRowNum() + 1;
            int numCols = sheet.getRow(0).getLastCellNum();
            excelData = new String[numRows][numCols];

            //Create a loop over all the rows of excel file to read it
            for (int i = 0; i < rowCount + 1; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    DataFormatter fmt = new DataFormatter();
                    String valueAsSeenInExcel = fmt.formatCellValue(row.getCell(j));
                    excelData[i][j] = valueAsSeenInExcel;
                }
            }

            workbook.close();
            return excelData;
        }
        catch(IOException e) {
            log("Something went wrong reading the excel sheet --- " + e, "ERROR", "text");
            return excelData;
        }
    }
    //endregion
}
