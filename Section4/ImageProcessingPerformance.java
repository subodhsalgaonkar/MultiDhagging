package Section4;

import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImageProcessingPerformance {

    public static final String SOURCE_FILE = "./Assets/Flowers.jpg";
    public static final String DEST_FILE = "./out/Flowers.jpg";
    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),  BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();

        // recolorSingleThreaded(originalImage, resultImage);
        recolorMultithreaded(originalImage, resultImage, 1);
        
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println(duration);

        File outputFile = new File(DEST_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);
    }


    public static void recolorMultithreaded(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads){
        List<Thread> threads = new ArrayList<>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadMultiplier = i;

            Thread thread = new Thread(() -> {
                int leftCorner = 0;
                int rightCorner = height * threadMultiplier;
                recolorImage(originalImage, resultImage, leftCorner, rightCorner, width, height);
            });

            threads.add(thread);
        }
        
        for(Thread thread: threads){
            thread.start();
        }

        for(Thread thread: threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Wrapper method for single threaded solution 
     */

    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage){
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }


    /**
     * Method that itterates through the original images and applies the recoloring
     */
    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner, int width, int height){
        
        for(int x = leftCorner; x < leftCorner + width; x++){
            for(int y = topCorner; y < topCorner + height; y++){
                recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }



    /**
     * Method to recolor each individual pixel
     */
    public static void recolorPixel(BufferedImage originImage, BufferedImage resultImage, int x, int y){
        int rgb = originImage.getRGB(x, y);
        
        int red = getRed(rgb);
        int blue = getBlue(rgb);
        int green = getGreen(rgb);

        int newRed;
        int newBlue;
        int newGreen;

        if(isShadeOfGrey(red, green, blue)){
            newRed = Math.max(0, red - 8);
            newGreen =  Math.max(0, green - 50);
            newBlue =  Math.max(0, blue - 10);
        }
        else{
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRGB = createRGBFromColours(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);
    }

    /**
     * Takes target buffered image object, pixel coordinated and rgb value and assigns the rgb value to the pixel in the result image 
     */
    public static void setRGB(BufferedImage image, int x, int y, int rgb){
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }


    /**
     * Takes particular pixels value and determines if it's a shade of grey
     * logic = all components almost same then shade of grey
     */
    public static boolean isShadeOfGrey(int red, int green, int blue){
        return (Math.abs(red - green) < 50) && (Math.abs(red - blue) < 50) && (Math.abs(blue - green) < 50);
        // I added 170 because even black pixels were treated as grey
    }

    /**
     * Takes individual values of red blue and green and builds a compound pixel RGB value out of it.
    */
    public static int createRGBFromColours(int red, int green, int blue){
        int rgb = 0;

        rgb |= blue;
        rgb |= (green << 8); 
        rgb |= (red << 16);

        rgb |= 0xFF000000; // setting alpha to max

        return rgb;
    }

    public static int getBlue(int rgb){
        return rgb & 0x000000FF;
    }

    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }
}
