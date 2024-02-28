package image_char_matching;

public class CharBrightness implements CompaCrable<CharBrightness>{
    private char character;
    private double nonLinearBrightness;
    private double linearBrightness;

    public CharBrightness(char character){
        this.character = character;
        this.nonLinearBrightness = calculateNonLinearBrightness(character);
        this.linearBrightness = -1;
    }

    public void updateLinearBrightness(double minBrightness, double maxBrightness){
        this.linearBrightness = (this.nonLinearBrightness - minBrightness) / (maxBrightness - minBrightness);
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
        return (double) (numTrue) / (CharConverter.DEFAULT_PIXEL_RESOLUTION *
                CharConverter.DEFAULT_PIXEL_RESOLUTION);
    }

    public char getCharacter() {
        return character;
    }

    public double getNonLinearBrightness() {
        return nonLinearBrightness;
    }

    public double getLinearBrightness() {
        return linearBrightness;
    }

    @Override
    public int compareTo(CharBrightness other){
        double subtraction = (this.nonLinearBrightness - other.nonLinearBrightness);
        if(subtraction == 0){
            return 0;
        }
        else if(subtraction < 0){
            return -1;
        }
        return 1;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof CharBrightness)){
            return false;
        }
        return ((CharBrightness)o).character == this.character;
    }
}
