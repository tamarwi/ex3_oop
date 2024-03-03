package image;

import java.awt.*;

// A class for image processing operations.
public class ImageProcessing {
    private final static int MAX_RGB = 255;

    /**
     * Get a padded version of the image with dimensions as the closest power of two.
     *
     * @param image The input image.
     * @return The padded image.
     */
    public static Image getPaddedImage(Image image) {
        assert image.getWidth() % 2 == 0;  // Ensure width is even
        int newImageWidth = getClosestPowerOfTwo(image.getWidth());
        int newImageHeight = getClosestPowerOfTwo(image.getHeight());
        int heightDiff = newImageHeight - image.getHeight();
        int widthDiff = newImageWidth - image.getWidth();

        Color[][] pixelArray = new Color[newImageHeight][newImageWidth];
        // Pad the image with white pixels
        for (int i = 0; i < newImageHeight; i++) {
            for (int j = 0; j < newImageWidth; j++) {
                if ((heightDiff / 2 <= i && i < newImageHeight - heightDiff / 2) &&
                        (widthDiff / 2 <= j && j < newImageWidth - widthDiff / 2)) {
                    pixelArray[i][j] = image.getPixel(i - heightDiff / 2, j - widthDiff / 2);
                } else {
                    pixelArray[i][j] = new Color(ImageProcessing.MAX_RGB, ImageProcessing.MAX_RGB,
                            ImageProcessing.MAX_RGB); // Set as white
                }
            }
        }

        return new Image(pixelArray, newImageWidth, newImageHeight);
    }

    /**
     * Method to get the closest power of two that is bigger than a given number
     *
     * @param num
     * @return the closest power of two that is bigger than num
     */
    private static int getClosestPowerOfTwo(int num) {
        boolean isNumPowerOfTwo = (num & (num - 1)) == 0;
        if (isNumPowerOfTwo) {
            return num;
        }
        return (int) Math.pow(2, 32 - Integer.numberOfLeadingZeros(num - 1));
    }

    /**
     * Get an array of sub-images from the given image.
     *
     * @param image The input image.
     * @param numSubImagesInRow The number of sub-images in each row.
     * @return An array of sub-images.
     */
    public static Image[][] getSubImages(Image image, int numSubImagesInRow) {
        int subImageSize = image.getWidth() / numSubImagesInRow;
        int numSubImagesInCol = image.getHeight() / subImageSize;
        Image[][] subImages = new Image[numSubImagesInCol][numSubImagesInRow];
        // Divide the image into sub-images
        for (int i = 0; i < numSubImagesInCol; ++i) {
            for (int j = 0; j < numSubImagesInRow; ++j) {
                Color[][] pixelArray = new Color[subImageSize][subImageSize];
                for (int x = 0; x < subImageSize; ++x) {
                    for (int y = 0; y < subImageSize; ++y) {
                        pixelArray[x][y] = image.getPixel(i * subImageSize + x, j * subImageSize + y);
                    }
                }
                subImages[i][j] = new Image(pixelArray, subImageSize, subImageSize);
            }
        }
        return subImages;
    }

    /**
     * Calculate the brightness of the given image.
     *
     * @param image The input image.
     * @return The brightness of the image.
     */
    public static double calculateImageBrightness(Image image) {
        double greySum = 0;
        // Calculate brightness by converting pixels to grayscale and summing them up
        for (int i = 0; i < image.getWidth(); ++i) {
            for (int j = 0; j < image.getHeight(); ++j) {
                Color color = image.getPixel(j, i);
                double greyPixel = color.getRed() * 0.2126 + color.getGreen() * 0.7152 +
                        color.getBlue() * 0.0722;
                greySum += greyPixel;
            }
        }
        return greySum / (image.getWidth() * image.getHeight() * ImageProcessing.MAX_RGB);
    }
}