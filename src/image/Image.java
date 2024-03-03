package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 *
 * @author Dan Nirel
 */
public class Image {
    // Array to hold pixel colors
    private final Color[][] pixelArray;
    // Dimensions of the image
    private final int width;
    private final int height;

    /**
     * Constructor to create an Image object from a file
     *
     * @param filename
     * @throws IOException
     */
    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();

        // Populate the pixelArray with colors from the BufferedImage
        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j] = new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * Constructor to create an Image object from a pixel array
     *
     * @param pixelArray
     * @param width
     * @param height
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Getter method to get the width of the image
     *
     * @return width of the image
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter method to get the height of the image
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Method to get the color of a pixel at a given position
     *
     * @param x row of pixel
     * @param y column of pixel
     * @return color of the pixel
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    /**
     * Method to save the image to a file
     *
     * @param fileName name of file to save
     */
    public void saveImage(String fileName) {
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        // Save the BufferedImage to a file
        File outputfile = new File(fileName + ".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
