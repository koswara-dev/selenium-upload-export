package com.juaracoding;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageUploadTest {
    WebDriver driver;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testImageUpload() throws InterruptedException, AWTException {
        driver.get("http://yourwebsite.com/profile");

        // Locate the file upload element
        WebElement uploadElement = driver.findElement(By.id("profileImageUpload"));

        // Provide the path of the image file to upload
        String imagePath = "path/to/your/image.jpg";
        uploadElement.sendKeys(imagePath);

        // Wait for the upload to complete
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("uploadedProfileImage")));

        // Capture the uploaded image from the webpage
        WebElement uploadedImageElement = driver.findElement(By.id("uploadedProfileImage"));

        // Use Actions to perform right-click and select 'Save Image As...'
        Actions actions = new Actions(driver);
        actions.contextClick(uploadedImageElement).perform();

        // Simulate pressing down arrow key and Enter (this part might vary based on browser behavior)
        Robot robot = new Robot();
        // Wait some time for context menu to appear (adjust if necessary)
        Thread.sleep(2000);

        // Move down to Save As option (number of steps depends on the browser)
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // Wait to ensure the image has been downloaded
        Thread.sleep(5000); // Adjust wait time as necessary

        // Assuming the image is downloaded to a default location, e.g., Downloads folder
        Path downloadPath = Paths.get(System.getProperty("user.home"), "Downloads", "image.jpg");
        Assert.assertTrue(Files.exists(downloadPath), "Downloaded image not found.");

        // Compare the original image and the downloaded image using OpenCV
        boolean isSameImage = compareImages(imagePath, downloadPath.toString());
        Assert.assertTrue(isSameImage, "Uploaded image does not match the original image.");
    }

    private boolean compareImages(String img1Path, String img2Path) {
        Mat img1 = Imgcodecs.imread(img1Path);
        Mat img2 = Imgcodecs.imread(img2Path);

        if (img1.size().equals(img2.size())) {
            // Use OpenCV functions to compare images
            Mat diff = new Mat();
            Core.absdiff(img1, img2, diff);
            double nonZeroPixels = Core.countNonZero(diff);
            return nonZeroPixels == 0;
        }
        return false;
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
