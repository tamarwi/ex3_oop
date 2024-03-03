package ascii_art;

import image.Image;
import image.ImageProcessing;
import image_char_matching.SubImgCharMatcher;

// Class representing an algorithm for generating ASCII art from an image.
public class AsciiArtAlgorithm {
    private final Image image;              // The input image
    private final int numCharsInRow;        // Number of characters in each row of the output ASCII art
    private final SubImgCharMatcher matcher; // Character matcher for mapping brightness to characters

    // Constructor to initialize the algorithm with the input image, number of characters in a row, and character matcher.
    public AsciiArtAlgorithm(Image image, int numCharsInRow, SubImgCharMatcher matcher) {
        this.image = image;
        this.numCharsInRow = numCharsInRow;
        this.matcher = matcher;
    }

    // Method to run the ASCII art generation algorithm.
    public char[][] run() {
        // Pad the input image
        Image paddedImage = ImageProcessing.getPaddedImage(this.image);
        // Divide the padded image into sub-images
        Image[][] subImages = ImageProcessing.getSubImages(paddedImage, numCharsInRow);
        // Assert that the number of characters in each row matches the specified number
        assert subImages[0].length == numCharsInRow;
        // Initialize a matrix to store the resulting ASCII art
        char[][] asciiArtMatrix = new char[subImages.length][numCharsInRow];
        // Iterate over each sub-image
        for (int i = 0; i < subImages.length; ++i) {
            for (int j = 0; j < numCharsInRow; ++j) {
                // Calculate the brightness of the sub-image
                double subImageBrightness = ImageProcessing.calculateImageBrightness(subImages[i][j]);
                // Map the brightness to a character using the matcher and store it in the matrix
                asciiArtMatrix[i][j] = this.matcher.getCharByImageBrightness(subImageBrightness);
            }
        }
        // Return the resulting ASCII art matrix
        return asciiArtMatrix;
    }
}