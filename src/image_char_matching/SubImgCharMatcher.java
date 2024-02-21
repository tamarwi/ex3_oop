public class SubImgCharMatcher{
    private ArrayList<Pair<double, char>> sortedCharBrightnessPairList;

    public SubImgCharMatcher(char[] charset){
        this.sortedCharBrightnessPairList = new ArrayList<Pair<double, char>>();
        for(char c: charset){
            addChar(c);
        }
    }

    public char getCharByImageBrightness(double brightness){
        int right = this.sortedCharBrightnessPairList.size();
        int left = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Check if brightness is present at mid
            if (this.sortedCharBrightnessPairList.get(mid).getKey() == brightness)
                return this.sortedCharBrightnessPairList.get(mid).getValue();

            // If brightness greater, ignore left half
            if (this.sortedCharBrightnessPairList.get(mid).getKey() < brightness)
                left = mid + 1;

                // If brightness is smaller, ignore right half
            else
                right = mid - 1;
        }
        double leftDistance = Math.abs(brightness - this.sortedCharBrightnessPairList.get(left).getKey());
        double rightDistance = Math.abs(brightness - this.sortedCharBrightnessPairList.get(right).getKey());
        if(leftDistance < rightDistance){
            return this.sortedCharBrightnessPairList.get(left).getValue();
        }
        else{
            return this.sortedCharBrightnessPairList.get(right).getValue();
        }
    }

    public void addChar(char c){
        double brightness = calculateNonLinearBrightness(c);
        Pair<double, char> brightnessCharPair = new Pair<>(brightness, c);
        this.sortedCharBrightnessPairList.add(brightnessCharPair);
        Collections.sort(this.sortedCharBrightnessPairList);
    }

    public void removeChar(char c){
        double brightness = calculateNonLinearBrightness(c);
        Pair<double, char> brightnessCharPair = new Pair<>(brightness, c);
        assert this.sortedCharBrightnessPairList.remove(brightnessCharPair); //TODO: remove assert
    }

    private double calculateNonLinearBrightness(char c){
        boolean[][] arr = CharConverter.convertToBoolArray(c);
        int numTrue = 0;
        for(int i=0; i<CharConverter.DEFAULT_PIXEL_RESOLUTION; ++i){
            for(int j=0; j<CharConverter.DEFAULT_PIXEL_RESOLUTION; ++j){
                if(arr[i][j]){
                    ++numTrue;
                }
            }
        }

        return numTrue / (CharConverter.DEFAULT_PIXEL_RESOLUTION * CharConverter.DEFAULT_PIXEL_RESOLUTION);
    }
}