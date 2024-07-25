package com.juaracoding;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExportDataTest {
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testExportDataToExcel() throws InterruptedException, IOException {
        driver.get("http://yourwebsite.com/exportDataPage");

        // Trigger the export data functionality (the details here depend on your specific webpage)
        WebElement exportButton = driver.findElement(By.id("exportButton"));
        exportButton.click();

        // Wait for the download to complete
        Thread.sleep(5000); // Adjust wait time as necessary

        // Assume the file is downloaded to a default location, e.g., Downloads folder
        Path downloadPath = Paths.get(System.getProperty("user.home"), "Downloads", "exported_data.xlsx");
        Assert.assertTrue(Files.exists(downloadPath), "Exported Excel file not found.");

        // Verify the contents of the exported Excel file
        verifyExcelFile(downloadPath.toString(), List.of(
                List.of("Header1", "Header2", "Header3"),
                List.of("Data1", "Data2", "Data3")
        ));
    }

    private void verifyExcelFile(String filePath, List<List<String>> expectedData) throws IOException {
        FileInputStream excelFile = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 0; i < expectedData.size(); i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < expectedData.get(i).size(); j++) {
                Cell cell = row.getCell(j);
                String cellValue = cell.getStringCellValue();
                Assert.assertEquals(cellValue, expectedData.get(i).get(j),
                        "Mismatch at row " + i + ", column " + j);
            }
        }

        workbook.close();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

