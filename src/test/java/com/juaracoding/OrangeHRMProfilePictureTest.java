package com.juaracoding;

import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class OrangeHRMProfilePictureTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @Test
    public void verifyChangeProfilePicture() throws AWTException, InterruptedException, IOException {
        // Login
        driver.findElement(By.id("txtUsername")).sendKeys("Admin");
        driver.findElement(By.id("txtPassword")).sendKeys("admin123");
        driver.findElement(By.id("btnLogin")).click();

        // Navigate to My Info > Change Profile Picture
        WebElement myInfoMenu = driver.findElement(By.id("menu_pim_viewMyDetails"));
        myInfoMenu.click();

        Thread.sleep(2000); // wait for the page to load

        WebElement changePhotoLink = driver.findElement(By.id("empPic"));
        changePhotoLink.click();

        Thread.sleep(2000); // wait for modal to appear

        // Upload a new profile picture
        WebElement fileInput = driver.findElement(By.id("photofile"));

        // Using Robot to upload the file
        StringSelection profilePicPath = new StringSelection("path/to/new/profile/picture.jpg");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(profilePicPath, null);

        Robot robot = new Robot();
        robot.setAutoDelay(1000);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        Thread.sleep(2000); // wait for upload

        driver.findElement(By.id("btnSave")).click();

        Thread.sleep(2000); // wait for save

        // Verification: Compare the old and new profile pictures using JavaCV
        String imgSrcAfterUpdate = driver.findElement(By.id("empPic")).getAttribute("src");

        URL url = new URL(imgSrcAfterUpdate);
        File updatedImageFile = new File("updatedImage.jpg");
        ImageIO.write(ImageIO.read(url), "jpg", updatedImageFile);

        // Load the old and new images
        Mat oldImage = opencv_imgcodecs.imread("path/to/old/profile/picture.jpg");
        Mat newImage = opencv_imgcodecs.imread(updatedImageFile.getAbsolutePath());

        Assert.assertNotNull(oldImage, "Old image is null!");
        Assert.assertNotNull(newImage, "New image is null!");

        // Convert images to grayscale
        Mat grayOldImage = new Mat();
        Mat grayNewImage = new Mat();
        opencv_imgproc.cvtColor(oldImage, grayOldImage, opencv_imgproc.COLOR_BGR2GRAY);
        opencv_imgproc.cvtColor(newImage, grayNewImage, opencv_imgproc.COLOR_BGR2GRAY);

        // Compute absolute difference between the images
        Mat diff = new Mat();
        opencv_core.absdiff(grayOldImage, grayNewImage, diff);

        // Sum up all the differences
        Scalar sumDiff = opencv_core.sumElems(diff);

        double totalDifference = sumDiff.get(0) + sumDiff.get(1) + sumDiff.get(2);

        // Assert if the total difference is above a threshold (i.e., images are different)
        double threshold = 1000.0; // You may need to adjust this threshold
        Assert.assertTrue(totalDifference > threshold, "Profile picture did not change!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

