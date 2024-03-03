package image_char_matching;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A class for matching characters to image brightness.
 */
public class SubImgCharMatcher {
    // List of characters sorted by brightness
    private final ArrayList<CharBrightness> sortedCharBrightnessList;
    private double minBrightness;   // Minimum brightness among characters
    private double maxBrightness;   // Maximum brightness among characters

    /**
     * Constructor to initialize the SubImgCharMatcher with a given character set.
     * @param charset array of characters that should be used to convert image to ascii art
     */
    public SubImgCharMatcher(char[] charset) {
        // Initialize character brightness list
        this.sortedCharBrightnessList = new ArrayList<CharBrightness>();
        this.minBrightness = 1;   // Initialize minimum brightness to highest possible value
        this.maxBrightness = -1;  // Initialize maximum brightness to lowest possible value
        for (char c : charset) {
            addChar(c);  // Add each character to the list
        }
    }

    // Method to get the character associated with a given image brightness.
    public char getCharByImageBrightness(double brightness) {
        int right = this.sortedCharBrightnessList.size();
        int left = 0;
        // Binary search to find the character with brightness closest to the given brightness
        while (left <= right) {
            int mid = left + (right - left) / 2;
            double midBrightness = this.sortedCharBrightnessList.get(mid).getLinearBrightness();

            // Check if brightness is present at mid
            if (midBrightness == brightness)
                return this.sortedCharBrightnessList.get(mid).getCharacter();

            // If brightness greater, ignore left half
            if (midBrightness < brightness)
                left = mid + 1;

                // If brightness is smaller, ignore right half
            else
                right = mid - 1;
        }
        double leftBrightness = this.sortedCharBrightnessList.get(left).getLinearBrightness();
        double rightBrightness = this.sortedCharBrightnessList.get(right).getLinearBrightness();
        double leftDistance = Math.abs(brightness - leftBrightness);
        double rightDistance = Math.abs(brightness - rightBrightness);

        // This loop ensures that the lower character is chosen if there are characters with equal brightness
        while (right > 0 && this.sortedCharBrightnessList.get(right - 1).getLinearBrightness() ==
                this.sortedCharBrightnessList.get(right).getLinearBrightness()) {
            --right;
        }
        if (leftDistance < rightDistance) {
            return this.sortedCharBrightnessList.get(left).getCharacter();
        } else if (leftDistance > rightDistance) {
            return this.sortedCharBrightnessList.get(right).getCharacter();
        }
        return this.sortedCharBrightnessList.get(left).getCharacter();
    }

    // Method to add a character to the list of sorted characters.
    public void addChar(char c) {
        CharBrightness charBrightness = new CharBrightness(c);
        if (this.sortedCharBrightnessList.contains(charBrightness)) {
            return; // If character already exists, return
        }

        int pos = Collections.binarySearch(this.sortedCharBrightnessList, charBrightness);
        if (pos < 0) {
            this.sortedCharBrightnessList.add(-pos - 1, charBrightness); // Add character in sorted order
        }

        // Update min and max brightness, and linearize brightness list if necessary
        if (charBrightness.getNonLinearBrightness() > this.maxBrightness ||
                charBrightness.getNonLinearBrightness() < this.minBrightness) {
            linearizeBrightnessList();
        } else {
            charBrightness.updateLinearBrightness(minBrightness, maxBrightness);
        }
    }

    // Method to remove a character from the list of sorted characters.
    public void removeChar(char c) {
        CharBrightness charBrightness = new CharBrightness(c);
        this.sortedCharBrightnessList.remove(charBrightness); // Remove character from the list

        // Update min and max brightness, and linearize brightness list if necessary
        if (charBrightness.getNonLinearBrightness() == this.maxBrightness ||
                charBrightness.getNonLinearBrightness() == this.minBrightness) {
            linearizeBrightnessList();
        }
    }

    // Method to linearize the brightness list.
    private void linearizeBrightnessList() {
        // Update min and max brightness
        this.minBrightness = this.sortedCharBrightnessList.getFirst().getNonLinearBrightness();
        this.maxBrightness = this.sortedCharBrightnessList.getLast().getNonLinearBrightness();
        // Update linear brightness of all characters
        for (CharBrightness charBrightness : this.sortedCharBrightnessList) {
            charBrightness.updateLinearBrightness(minBrightness, maxBrightness);
        }
    }

    // Method to get a sorted list of characters alphabetically.
    public ArrayList<Character> getSortedCharAlphabeticallyList() {
        ArrayList<Character> copyCharList = new ArrayList<Character>();
        // Copy characters from sorted list to new list
        for (CharBrightness charBrightness : sortedCharBrightnessList) {
            copyCharList.add(charBrightness.getCharacter());
        }

        Collections.sort(copyCharList); // Sort the list alphabetically
        return copyCharList;
    }
}