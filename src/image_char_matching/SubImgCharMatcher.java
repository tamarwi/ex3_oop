package image_char_matching;

import java.util.ArrayList;
import java.util.Collections;

public class SubImgCharMatcher{
    private final ArrayList<CharBrightness> sortedCharBrightnessList;
    private double minBrightness;
    private double maxBrightness;

    public SubImgCharMatcher(char[] charset){
        this.sortedCharBrightnessList = new ArrayList<CharBrightness>();
        this.minBrightness = 1;
        this.maxBrightness = -1;
        for(char c: charset){
            addChar(c);
        }
    }

    public char getCharByImageBrightness(double brightness){
        int right = this.sortedCharBrightnessList.size();
        int left = 0;
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

        // This makes sure that the lower character is chosen - because there might be some letters with equal
        while(right > 0 && this.sortedCharBrightnessList.get(right-1).getLinearBrightness() ==
                this.sortedCharBrightnessList.get(right).getLinearBrightness()){
            --right;
        }
        if(leftDistance < rightDistance){
            return this.sortedCharBrightnessList.get(left).getCharacter();
        }
        else if(leftDistance > rightDistance){
            return this.sortedCharBrightnessList.get(right).getCharacter();
        }
        return this.sortedCharBrightnessList.get(left).getCharacter();
    }

    public void addChar(char c){
        CharBrightness charBrightness = new CharBrightness(c);
        if(this.sortedCharBrightnessList.contains(charBrightness))
        {
            return;
        }

        int pos = Collections.binarySearch(this.sortedCharBrightnessList, charBrightness);
        if (pos < 0) {
            this.sortedCharBrightnessList.add(-pos-1, charBrightness);
        }

        if(charBrightness.getNonLinearBrightness() > this.maxBrightness ||
                charBrightness.getNonLinearBrightness() < this.minBrightness){
            linearizeBrightnessList();
        }else{
            charBrightness.updateLinearBrightness(minBrightness, maxBrightness);
        }
    }

    public void removeChar(char c){
        CharBrightness charBrightness = new CharBrightness(c);
        this.sortedCharBrightnessList.remove(charBrightness);

        if(charBrightness.getNonLinearBrightness() == this.maxBrightness ||
                charBrightness.getNonLinearBrightness() == this.minBrightness){
            linearizeBrightnessList();
        }
    }

    private void linearizeBrightnessList(){
        //should be used to increase efficiency
        this.minBrightness = this.sortedCharBrightnessList.getFirst().getNonLinearBrightness();
        this.maxBrightness = this.sortedCharBrightnessList.getLast().getNonLinearBrightness();
        for(CharBrightness charBrightness: this.sortedCharBrightnessList){
            charBrightness.updateLinearBrightness(minBrightness, maxBrightness);
        }
    }

    public ArrayList<Character> getSortedCharAlphabeticallyList(){

        ArrayList<Character> copyCharList = new ArrayList<Character>();
        for(CharBrightness charBrightness: sortedCharBrightnessList){
            copyCharList.add(charBrightness.getCharacter());
        }

        Collections.sort(copyCharList); // Sort the list
        return copyCharList;
    }
}