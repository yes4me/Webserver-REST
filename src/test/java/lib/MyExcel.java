/* ===========================================================================
Created:	2015/08/15 - https://github.com/yes4me/
Author:		Thomas Nguyen - thomas_ejob@hotmail.com
Purpose:	My library for Excel files
Requires: FROM: Apache POI - https://poi.apache.org/download.html
	poi-3.12-20150511.jar
	poi-ooxml-3.12-20150511.jar
	poi-ooxml-schemas-3.12-20150511.jar
	xmlbeans-2.6.0.jar
=========================================================================== */

package lib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

public class MyExcel {
	public boolean readExcel(String file) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		Workbook wb;
		try {
			wb = WorkbookFactory.create(fis);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		Sheet sheet			= wb.getSheet("Sheet1");
		for (Row row : sheet) { 
			System.out.println(row.getCell(0).getStringCellValue()+" : "+row.getCell(1).getStringCellValue());
		}
		return true;
	}
	public static void main(String[] args) {
		MyExcel myExcel = new MyExcel();
		myExcel.readExcel("test.xlsx");
	}
}