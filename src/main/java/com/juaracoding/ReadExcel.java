package com.juaracoding;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ReadExcel {
    public static void main(String[] args) throws IOException {
        String pathExcel = "C:\\Users\\Lenovo\\Downloads\\exported.xlsx";
        verifyExcelFile(pathExcel,List.of(
                List.of("username", "email", "address"),
                List.of("username1", "email1@email.com", "myaddress")
        ));
    }

    private static void verifyExcelFile(String filePath, List<List<String>> expectedData) throws IOException {
        FileInputStream excelFile = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 0; i < expectedData.size(); i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < expectedData.get(i).size(); j++) {
                Cell cell = row.getCell(j);
                String cellValue = cell.getStringCellValue();
                System.out.println(cellValue);
                System.out.println(expectedData.get(i).get(j));
                // Assert.assertEquals(cellValue, expectedData.get(i).get(j),
                //        "Mismatch at row " + i + ", column " + j);
            }
        }

        workbook.close();
    }
}
