package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

public class Shell {
    /*********** COMMAND CONSTANTS ********************/
    private static final String EXIT_COMMAND = "exit";
    private static final String SHOW_CHARS_COMMAND = "chars";
    private static final String ADD_CHAR_COMMAND = "add";
    private static final String REMOVE_CHAR_COMMAND = "remove";
    private static final String CHANGE_IMG_RESOLUTION_COMMAND = "res";
    private static final String CHANGE_IMG_COMMAND = "image";
    private static final String CHANGE_OUTPUT_METHOD_COMMAND = "output";
    private static final String RUN_ALGORITHM_COMMAND = "asciiArt";

    /*********** DEFAULT VALUES CONSTANTS *******************/
    private static final String DEFAULT_IMAGE = "cat.jpeg";
    private static final char[] DEFAULT_CHAR_SET = {'0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'};
    private static final int DEFAULT_IMAGE_RESOLUTION = 128;
    private static final AsciiOutput DEFAULT_ASCII_OUTPUT = new ConsoleAsciiOutput();
    /***************** CONSTANTS *****************/
    private static final int COMMAND_WORD_INDEX = 0;
    private static final int COMMAND_PARAMS_INDEX = 1;
    private static final int CHANGE_RESOLUTION_FACTOR = 2;
    private static final int MIN_CHAR_VALUE = 32;
    private static final int MAX_CHAR_VALUE = 126;
    private static final String COMMAND_PROMPT_STRING = "<<<";
    private static final int FIRST_CHAR_RANGE_INDEX = 0;
    private static final int SECOND_CHAR_RANGE_INDEX = 2;
    private static final int CHAR_RANGE_PARAM_LENGTH = 3;

    /*********** CLASS FIELDS *******************/
    private final SubImgCharMatcher chars;
    private Image image;
    private AsciiOutput asciiOutput;
    private int imageResolution;

    /*******************************************************/

    public Shell() throws IOException {
        chars = new SubImgCharMatcher(DEFAULT_CHAR_SET);
        image = new Image(DEFAULT_IMAGE);
        asciiOutput = DEFAULT_ASCII_OUTPUT;
        imageResolution = DEFAULT_IMAGE_RESOLUTION;
    }

    public void run() {
        String input = "";
        System.out.print(COMMAND_PROMPT_STRING);
        commandHandler((input = KeyboardInput.readLine()));
        while (!input.equals(EXIT_COMMAND)) {
            System.out.print(COMMAND_PROMPT_STRING);
            commandHandler((input = KeyboardInput.readLine()));
        }
    }

    private void commandHandler(String userInput) {
        String[] commandWords = userInput.split("\\s+");
        boolean executed = false;
        executed = noParamCommandHandler(commandWords);
        if (!executed) {
            try {
                paramCommandHandler(commandWords);
            } catch (InvalidParamsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean noParamCommandHandler(String[] commandWords) {
        boolean correctNumberOfParams = commandWords.length == 1;
        boolean executed = false;

        if (commandWords[COMMAND_WORD_INDEX].equals(SHOW_CHARS_COMMAND)) {
            showChars();
            executed = true;
        } else if (commandWords[COMMAND_WORD_INDEX].equals(RUN_ALGORITHM_COMMAND)) {
            runAsciiArtAlgorithm();
            executed = true;
        } else if (correctNumberOfParams && !executed) {
            System.out.println("Did not execute due to incorrect command.");
        }
        return executed;
    }

    private void paramCommandHandler(String[] commandWords) throws InvalidParamsException {
        switch (commandWords[COMMAND_WORD_INDEX]) {
            case ADD_CHAR_COMMAND -> addChar(commandWords);
            case REMOVE_CHAR_COMMAND -> removeChar(commandWords[COMMAND_PARAMS_INDEX]);
            case CHANGE_IMG_RESOLUTION_COMMAND -> changeImgResolution(commandWords[COMMAND_PARAMS_INDEX]);
            case CHANGE_IMG_COMMAND -> changeImg(commandWords[COMMAND_PARAMS_INDEX]);
            case CHANGE_OUTPUT_METHOD_COMMAND -> changeOutputMethod(commandWords[COMMAND_PARAMS_INDEX]);
            default -> System.out.println("Did not execute due to incorrect command.");
        }
    }

    private void showChars() {
        for (char character : chars.getSortedCharAlphabeticallyList()) {
            System.out.print("%c ".formatted(character));
        }
        System.out.println();
    }

    private void addChar(String[] commandParams) throws InvalidParamsException {
        if (commandParams.length != 2) {
            throw new InvalidParamsException("Did not add due to incorrect format.");
        }
        String charsToAdd = commandParams[COMMAND_PARAMS_INDEX];
        if (charsToAdd.equals("all")) {
            for (int i = MIN_CHAR_VALUE; i <= MAX_CHAR_VALUE; i++) {
                chars.addChar((char) i);
            }
        } else if (charsToAdd.equals("space")) {
            chars.addChar(' ');
        } else if (isCharRange(charsToAdd)) {
            addRemoveCharRange(charsToAdd, true);
        } else if (charsToAdd.length() == 1 && (charsToAdd.charAt(0) >= MIN_CHAR_VALUE) &&
                (charsToAdd.charAt(0) <= MAX_CHAR_VALUE)) {
            chars.addChar(charsToAdd.charAt(0));
        } else {
            throw new InvalidParamsException("Did not add due to incorrect format.");
        }
    }

    private boolean isCharRange(String string) {
        if (string.length() != CHAR_RANGE_PARAM_LENGTH) {
            return false;
        }
        return isInCharRange(string.charAt(FIRST_CHAR_RANGE_INDEX)) &&
                isInCharRange(string.charAt(SECOND_CHAR_RANGE_INDEX)) &&
                string.charAt(1) == '-';
    }

    private boolean isInCharRange(char c) {
        return (c <= MAX_CHAR_VALUE) && (c >= MIN_CHAR_VALUE);
    }

    private void addRemoveCharRange(String charsToAdd, boolean isAddition) {
        char smaller = charsToAdd.charAt(FIRST_CHAR_RANGE_INDEX) <
                charsToAdd.charAt(SECOND_CHAR_RANGE_INDEX) ? charsToAdd.charAt(FIRST_CHAR_RANGE_INDEX) :
                charsToAdd.charAt(SECOND_CHAR_RANGE_INDEX);
        char bigger = charsToAdd.charAt(FIRST_CHAR_RANGE_INDEX) <
                charsToAdd.charAt(SECOND_CHAR_RANGE_INDEX) ? charsToAdd.charAt(SECOND_CHAR_RANGE_INDEX) :
                charsToAdd.charAt(FIRST_CHAR_RANGE_INDEX);

        for (int i = smaller; i <= bigger; i++) {
            if (isAddition) {
                chars.addChar((char) i);
            } else {
                chars.removeChar((char) i);
            }
        }
    }

    private void removeChar(String charsToRemove) throws InvalidParamsException {
        if (charsToRemove.equals("all")) {
            for (int i = MIN_CHAR_VALUE; i < MAX_CHAR_VALUE; i++) {
                chars.removeChar((char) i);
            }
        } else if (charsToRemove.equals("space")) {
            chars.removeChar(' ');
        } else if (isCharRange(charsToRemove)) {
            addRemoveCharRange(charsToRemove, false);
        } else if (charsToRemove.length() == 1 && (isInCharRange(charsToRemove.charAt(0)))) {
            chars.removeChar(charsToRemove.charAt(0));
        } else {
            throw new InvalidParamsException("Did not remove due to incorrect format.");
        }
    }

    private void changeImgResolution(String changeResolutionCommand) throws InvalidParamsException {
        int minCharsInRow = Math.max(1, image.getWidth() / image.getHeight());
        if (changeResolutionCommand.equals("up")) {
            if ((imageResolution * CHANGE_RESOLUTION_FACTOR > image.getWidth()) ||
                    (imageResolution * CHANGE_RESOLUTION_FACTOR < minCharsInRow)) {
                throw new InvalidParamsException("Did not change resolution due to exceeding boundaries.");
            }
            imageResolution *= CHANGE_RESOLUTION_FACTOR;
        } else if (changeResolutionCommand.equals("down")) {
            if ((imageResolution / CHANGE_RESOLUTION_FACTOR > image.getWidth()) ||
                    (imageResolution / CHANGE_RESOLUTION_FACTOR < minCharsInRow)) {
                throw new InvalidParamsException("Did not change resolution due to exceeding boundaries.");
            }
            imageResolution /= CHANGE_RESOLUTION_FACTOR;
        } else {
            throw new InvalidParamsException("Did not change resolution due to incorrect format.");
        }
        System.out.println("Resolution set to " + Integer.toString(imageResolution));
    }

    private void changeImg(String changeImgCommand) {
        try {
            image = new Image(changeImgCommand);
        } catch (IOException e) {
            System.out.println("Did not execute due to problem with image file.");
        }
    }

    private void changeOutputMethod(String changeOutputCommand) {
        AsciiOutputFactory asciiOutputFactory = new AsciiOutputFactory();
        asciiOutput = asciiOutputFactory.createAsciiOutput(changeOutputCommand, new String[]{
                "out.html", "Courier New"});
    }

    private void runAsciiArtAlgorithm() {
        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(image, imageResolution, chars);
        asciiOutput.out(asciiArtAlgorithm.run());
    }

}
