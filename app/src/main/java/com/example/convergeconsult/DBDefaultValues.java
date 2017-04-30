package com.example.convergeconsult;

import android.content.Context;
import android.os.Environment;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.InputStream;

/**
 * Created by khanpar on 3/18/17.
 */

public final class DBDefaultValues {
    private static boolean isStorageAvailable = false;
    private static File file;
    private static InputStream myInput;
    private static POIFSFileSystem myFileSystem;
    private static HSSFWorkbook myWorkBook;
    private static String naStr = "-NA-";
    private static String noStorageStr = "No storage permission";

    public static void initializeValues(Context context) {
        if (!isStorageAvailable && isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
            isStorageAvailable = true;
            try {
                myInput = context.getResources().openRawResource(
                        context.getResources().getIdentifier("readingdata",
                                "raw", context.getPackageName()));
                // Create a POIFSFileSystem object
                myFileSystem = new POIFSFileSystem(myInput);

                // Create a workbook using the File System
                myWorkBook = new HSSFWorkbook(myFileSystem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static String getDipData(Double reading, int sectionNumber) {
        if (!isStorageAvailable)
            return noStorageStr;
        try {
            HSSFSheet mySheet = (sectionNumber == 1) ? myWorkBook.getSheetAt(2) :
                    (sectionNumber == 2) ? myWorkBook.getSheetAt(3) : myWorkBook.getSheetAt(4);
            int rowNumber = findRow(mySheet, reading);
            Cell cell = mySheet.getRow(rowNumber).getCell(1);
            return String.valueOf(cell.getNumericCellValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return naStr;
    }

    private static int findRow(HSSFSheet sheet, Double reading) {
        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                if (cell.getNumericCellValue() == reading) {
                    return row.getRowNum();
                }
            }
        }
        return 0;
    }

    public static String getDensitydata(int row, int col, int sectionNumber) {
        if (!isStorageAvailable)
            return noStorageStr;
        try {
            HSSFSheet mySheet = (sectionNumber == 1) ? myWorkBook.getSheetAt(0) : myWorkBook.getSheetAt(1);
            Row rowNumber = mySheet.getRow(row);
            Cell cell = rowNumber.getCell(col);
            return cell.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return naStr;
    }
}
