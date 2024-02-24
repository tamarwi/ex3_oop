package ascii_art;

import image.Image;
import image.ImageProcessing;
import image_char_matching.SubImgCharMatcher;

public class AsciiArtAlgorithm{
    private final Image image;
    private final int numCharsInRow;
    private final SubImgCharMatcher matcher;
    public AsciiArtAlgorithm(Image image, int numCharsInRow, SubImgCharMatcher matcher){
        this.image = image;
        this.numCharsInRow = numCharsInRow;
        this.matcher = matcher;
    }

    /**
     *
     * @return
     */
    public char[][] run(){
        Image paddedImage = ImageProcessing.getPaddedImage(this.image);
        Image[][] subImages = ImageProcessing.getSubImages(paddedImage, numCharsInRow);
        assert subImages[0].length == numCharsInRow;
        char[][] asciiArtMatrix = new char[subImages.length][numCharsInRow];
        for(int i=0; i<subImages.length; ++i){
            for(int j=0; j<numCharsInRow; ++j){
                double subImageBrightness = ImageProcessing.calculateImageBrightness(subImages[i][j]);
                asciiArtMatrix[i][j] = this.matcher.getCharByImageBrightness(subImageBrightness);
            }
        }
        return  asciiArtMatrix;
    }
}