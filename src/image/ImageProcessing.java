package image;

import java.awt.*;

public class ImageProcessing{
    private final static int MAX_RGB = 255;

    /**
     *
     * @param image
     * @return
     */
    public static Image getPaddedImage(Image image){
        assert image.getWidth() % 2 == 0;
        int newImageWidth = getClosestPowerOfTwo(image.getWidth());
        int newImageHeight = getClosestPowerOfTwo(image.getHeight());
        int heightDiff = newImageHeight - image.getHeight();
        int widthDiff = newImageWidth - image.getWidth();

        Color[][] pixelArray = new Color[newImageHeight][newImageWidth];
        for (int i = 0; i < newImageHeight; i++) {
            for (int j = 0; j < newImageWidth; j++) {
                if((heightDiff/2 <= i && i <= newImageHeight - heightDiff/2) &&
                        (widthDiff/2 <= j && j <= newImageWidth - widthDiff/2)){
                    pixelArray[i][j]=image.getPixel(i-heightDiff/2, j-widthDiff/2);
                }
                else{
                    pixelArray[i][j] = new Color(ImageProcessing.MAX_RGB, ImageProcessing.MAX_RGB,
                            ImageProcessing.MAX_RGB);
                }
            }
        }

        return new Image(pixelArray, newImageWidth, newImageHeight);
    }

    private static int getClosestPowerOfTwo(int num){
        boolean isNumPowerOfTwo = (num & (num - 1)) == 0;
        if(isNumPowerOfTwo){
            return num;
        }
        return (int)Math.pow(2, 32 - Integer.numberOfLeadingZeros(num - 1));
    }

    /**
     *
     * @param image
     * @param numSubImagesInRow
     * @return
     */
    public static Image[][] getSubImages(Image image, int numSubImagesInRow){
        int subImageSize = image.getWidth() / numSubImagesInRow;
        int numSubImagesInCol = image.getHeight() / subImageSize;
        Image[][] subImages = new Image[numSubImagesInCol][numSubImagesInRow];
        for(int i=0; i < numSubImagesInCol;++i){
            for(int j=0; j < numSubImagesInRow;++j){
                Color[][] pixelArray = new Color[subImageSize][subImageSize];
                for(int x=0; x < subImageSize;++x){
                    for(int y=0; y < subImageSize;++y){
                        pixelArray[x][y] = image.getPixel(i*subImageSize+x,j*subImageSize+y);
                    }
                }
                subImages[i][j] = new Image(pixelArray, subImageSize, subImageSize);
            }
        }
        return subImages;
    }

    /**
     *
     * @param image
     * @return
     */
    public static double calculateImageBrightness(Image image){
        double greySum = 0;
        for(int i=0; i < image.getWidth(); ++i){
            for(int j=0; j < image.getHeight(); ++j){
                Color color = image.getPixel(j, i);
                double greyPixel = color.getRed() * 0.2126 + color.getGreen() * 0.7152 +
                        color.getBlue() * 0.0722;
                greySum += greyPixel;
            }
        }
        return greySum / (image.getWidth() * image.getHeight() * ImageProcessing.MAX_RGB);
    }
}