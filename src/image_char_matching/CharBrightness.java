package image_char_matching;

/**
 * A class representing the brightness of a character, with methods to calculate brightness
 * and update linear brightness based on a given range.
 */
public class CharBrightness implements Comparable<CharBrightness> {
    private char character;                 // The character associated with this brightness
    private double nonLinearBrightness;     // Non-linear brightness of the character
    private double linearBrightness;        // Linear brightness of the character

    /**
     * Constructor to initialize CharBrightness object with a character.
     *
     * @param character The character to associate with this brightness.
     */
    public CharBrightness(char character) {
        this.character = character;
        this.nonLinearBrightness = calculateNonLinearBrightness(character);  // Calculate non-linear brightness
        this.linearBrightness = -1;    // Default value until updated
    }


    /**
     * Method to update linear brightness based on given min and max brightness values.
     *
     * @param minBrightness
     * @param maxBrightness
     */
    public void updateLinearBrightness(double minBrightness, double maxBrightness) {
        this.linearBrightness = (this.nonLinearBrightness - minBrightness) / (maxBrightness - minBrightness);
    }

    /**
     * Method to calculate non-linear brightness of a character.
     *
     * @param c character to calculate its brightness
     * @return the non-linear brightness of character c
     */
    private double calculateNonLinearBrightness(char c) {
        boolean[][] arr = CharConverter.convertToBoolArray(c);
        int numTrue = 0;
        // Count the number of true values (bright pixels) in the character matrix
        for (int i = 0; i < CharConverter.DEFAULT_PIXEL_RESOLUTION; ++i) {
            for (int j = 0; j < CharConverter.DEFAULT_PIXEL_RESOLUTION; ++j) {
                if (arr[i][j]) {
                    ++numTrue;
                }
            }
        }
        // Calculate brightness as the ratio of bright pixels to total pixels
        return (double) (numTrue) / (CharConverter.DEFAULT_PIXEL_RESOLUTION * CharConverter.DEFAULT_PIXEL_RESOLUTION);
    }

    /**
     * Getter method for the character associated with this brightness.
     *
     * @return the character associated with this brightness.
     */
    public char getCharacter() {
        return character;
    }

    /**
     * Getter method for the non-linear brightness of the character.
     *
     * @return the non-linear brightness of the character.
     */
    public double getNonLinearBrightness() {
        return nonLinearBrightness;
    }

    /**
     * Getter method for the linear brightness of the character.
     *
     * @return the linear brightness of the character.
     */
    public double getLinearBrightness() {
        return linearBrightness;
    }

    /**
     * Comparison method to compare brightness of characters.
     *
     * @param other the object to be compared.
     * @return -1 if other is bigger then this, 0 if they are equal, then 1
     */
    @Override
    public int compareTo(CharBrightness other) {
        double subtraction = (this.nonLinearBrightness - other.nonLinearBrightness);
        // If brightness is equal, compare characters
        if (subtraction == 0) {
            if (this.character < other.character) {
                return -1;
            } else if (this.character > other.character) {
                return 1;
            }
            return 0;
        }
        // Otherwise, return comparison based on brightness difference
        else if (subtraction < 0) {
            return -1;
        }
        return 1;
    }

    /**
     * Method to check equality between CharBrightness objects.
     *
     * @param o
     * @return true if this object equals to o
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CharBrightness)) {
            return false;
        }
        return ((CharBrightness) o).character == this.character;
    }
}