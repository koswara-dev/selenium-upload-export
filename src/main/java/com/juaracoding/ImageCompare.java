package com.juaracoding;

import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Scalar;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

public class ImageCompare {

    public static void main(String[] args) {
        // 1. Using JavaX ImageIO
        try {
            // Load the images to be compared
            BufferedImage img1 = ImageIO.read(new File("C:\\Users\\Lenovo\\Pictures\\172918.jpg"));
            BufferedImage img2 = ImageIO.read(new File("C:\\Users\\Lenovo\\Pictures\\172918.jpg"));

            boolean result = compareImages(img1, img2);

            if(result) {
                System.out.println("The images are identical.");
            } else {
                System.out.println("The images are different.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Using JavaCV (OpenCV)
        // Load the old and new images
        Mat oldImage = opencv_imgcodecs.imread("C:\\Users\\Lenovo\\Pictures\\172918.jpg");
        // Resize the Image
        Mat newImage = opencv_imgcodecs.imread("C:\\Users\\Lenovo\\Pictures\\172918 - Copy.jpg");

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
        double threshold = 0.0; // You may need to adjust this threshold
        System.out.println(totalDifference);
        if(totalDifference == threshold){
            System.out.println("True");
        } else {
            System.out.println("False");
        }
    }

    // JavaX ImageIO
    public static boolean compareImages(BufferedImage img1, BufferedImage img2) {
        // Check if dimensions are equal
        if (img1.getWidth() != img1.getWidth() || img1.getHeight() != img1.getHeight()) {
            return false;
        }

        // Compare pixels
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                Color color1 = new Color(img1.getRGB(x, y));
                Color color2 = new Color(img2.getRGB(x, y));

                if (!color1.equals(color2)) {
                    return false;
                }
            }
        }
        return true;
    }
}
