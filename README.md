# Selenium TestNG Excel Export and Image Upload Verification

This project demonstrates how to use Selenium WebDriver, TestNG, and Apache POI to perform an automated test that verifies data export functionality by downloading an Excel file and checking its contents.

JavaCV is an open-source library that provides wrappers for commonly used computer vision libraries like OpenCV, FFmpeg, and others. It simplifies the process of using these libraries in Java applications by offering a consistent API and handling the complexities of native code integration.

**Use Cases**
- Computer Vision: Perform image processing tasks such as face detection, object tracking, and feature extraction.
- Video Processing: Handle video input and output, manipulate frames, and perform real-time video capture.
- Machine Learning: Utilize machine learning models for various tasks including classification, pattern recognition, and more.

## Prerequisites

- Java Development Kit (JDK) 17
- Maven
- Chrome Browser
- ChromeDriver executable matching the installed Chrome version

## Project Setup

1. **Clone the repository**:
    ```sh
    git clone https://github.com/koswara-dev/selenium-upload-export.git
    cd selenium-upload-export
    ```

2. **Configure ChromeDriver**:
   Download the ChromeDriver executable from [here](https://sites.google.com/a/chromium.org/chromedriver/downloads) and place it in an accessible path. Update the path in the test script if necessary.

3. **Add Dependencies**:
   Ensure that the `pom.xml` contains the following dependencies:

    ```xml
    <dependencies>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>3.141.59</version>
        </dependency>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>7.4.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.bytedeco</groupId>
            <artifactId>javacv-platform</artifactId>
            <version>1.5.9</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>5.2.5</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>5.2.5</version>
        </dependency>
    </dependencies>
    ```

4. **Build the project**:
    ```sh
    mvn clean install
    ```

## Running the Tests

To run the tests, execute the following command from the root directory of the project:

```sh
mvn test
```

## Buy me a coffe

If you like this project and want to support its further development, buy me a coffee!

[![Buy Me a Coffee](https://www.buymeacoffee.com/assets/img/guidelines/download-assets-sm-1.svg)](https://www.buymeacoffee.com/kudajengke404)