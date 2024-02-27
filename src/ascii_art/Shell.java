package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

/**
 * Represents a shell for interacting with ASCII art generation and manipulation.
 * Users can input commands to perform various operations such as adding or removing characters,
 * changing image resolution, selecting output methods, and running the ASCII art algorithm.
 *
 * @author OOP staff
 * @see SubImgCharMatcher
 * @see AsciiOutput
 */
public class Shell {
    // COMMAND CONSTANTS

    /**
     * Command constant for exiting the shell.
     */
    private static final String EXIT_COMMAND = "exit";

    /**
     * Command constant for displaying available characters.
     */
    private static final String SHOW_CHARS_COMMAND = "chars";

    /**
     * Command constant for adding characters.
     */
    private static final String ADD_CHAR_COMMAND = "add";

    /**
     * Command constant for removing characters.
     */
    private static final String REMOVE_CHAR_COMMAND = "remove";

    /**
     * Command constant for changing the image resolution.
     */
    private static final String CHANGE_IMG_RESOLUTION_COMMAND = "res";

    /**
     * Command constant for changing the image.
     */
    private static final String CHANGE_IMG_COMMAND = "image";

    /**
     * Command constant for changing the output method.
     */
    private static final String CHANGE_OUTPUT_METHOD_COMMAND = "output";

    /**
     * Command constant for running the ASCII art algorithm.
     */
    private static final String RUN_ALGORITHM_COMMAND = "asciiArt";

    // DEFAULT VALUES CONSTANTS

    /**
     * Default image file.
     */
    private static final String DEFAULT_IMAGE = "cat.jpeg";

    /**
     * Default character set used for ASCII art.
     */
    private static final char[] DEFAULT_CHAR_SET = {'0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'};

    /**
     * Default resolution for the image.
     */
    private static final int DEFAULT_IMAGE_RESOLUTION = 128;

    /**
     * Default ASCII output method.
     */
    private static final AsciiOutput DEFAULT_ASCII_OUTPUT = new ConsoleAsciiOutput();

    // CONSTANTS
    /**
     * Number of params for the commands that need params.
     */
    private static final int NUMBER_PARAMS_COMMAND_WITH_PARAMS = 2;

    /**
     * Index of the command word in the user input.
     */
    private static final int COMMAND_WORD_INDEX = 0;

    /**
     * Index of the command parameters in the user input.
     */
    private static final int COMMAND_PARAMS_INDEX = 1;

    /**
     * Factor by which the image resolution changes.
     */
    private static final int CHANGE_RESOLUTION_FACTOR = 2;

    /**
     * Minimum ASCII character value.
     */
    private static final int MIN_CHAR_VALUE = 32;

    /**
     * Maximum ASCII character value.
     */
    private static final int MAX_CHAR_VALUE = 126;

    /**
     * Prompt string indicating user input.
     */
    private static final String COMMAND_PROMPT_STRING = "<<< ";

    /**
     * Index of the first character in a character range parameter.
     */
    private static final int FIRST_CHAR_RANGE_INDEX = 0;

    /**
     * Index of the second character in a character range parameter.
     */
    private static final int SECOND_CHAR_RANGE_INDEX = 2;

    /**
     * Length of a character range parameter.
     */
    private static final int CHAR_RANGE_PARAM_LENGTH = 3;
    //ERROR MESSAGES
    private static final String INCORRECT_COMMAND_ERROR_MSG = "Did not execute due to incorrect command.";
    private static final String ADD_COMMAND_INCORRECT_FORMAT_ERROR_MSG =
            "Did not add due to incorrect format.";
    private static final String REMOVE_COMMAND_INCORRECT_FORMAT_ERROR_MSG =
            "Did not remove due to incorrect format.";

    private static final String IMG_COMMAND_ERROR_MSG = "Did not execute due to problem with image file.";
    private static final String RES_COMMAND_BOUNDARIES_ERROR_MSG =
            "Did not change resolution due to exceeding boundaries.";
    private static final String RES_COMMAND_INCORRECT_FORMAT_ERROR_MSG =
            "Did not change resolution due to incorrect format.";

    // CLASS FIELDS

    /**
     * Matcher for the characters used in ASCII art.
     */
    private final SubImgCharMatcher chars;

    /**
     * Image used for ASCII art generation.
     */
    private Image image;

    /**
     * Output method for displaying ASCII art.
     */
    private AsciiOutput asciiOutput;

    /**
     * Resolution of the image used for ASCII art generation.
     */
    private int imageResolution;

    /**
     * Constructor for the Shell class.
     * Initializes the shell with default settings.
     *
     * @throws IOException if there is an issue with file input/output.
     */
    public Shell() throws IOException {
        chars = new SubImgCharMatcher(DEFAULT_CHAR_SET);
        image = new Image(DEFAULT_IMAGE);
        asciiOutput = DEFAULT_ASCII_OUTPUT;
        imageResolution = DEFAULT_IMAGE_RESOLUTION;
    }

    /**
     * Runs the shell, accepting user input and executing commands until the exit command is given.
     */
    public void run() {
        String input = "";
        System.out.print(COMMAND_PROMPT_STRING);
        commandHandler((input = KeyboardInput.readLine()));
        while (!input.equals(EXIT_COMMAND)) {
            System.out.print(COMMAND_PROMPT_STRING);
            commandHandler((input = KeyboardInput.readLine()));
        }
    }

    /**
     * Handles the user input by parsing commands and executing corresponding actions.
     *
     * @param userInput the user input to be processed.
     */
    private void commandHandler(String userInput) {
        String[] commandWords = userInput.split("\\s+");

        if (commandWords.length == 1) {
            noParamCommandHandler(commandWords);
        } else if (commandWords.length == NUMBER_PARAMS_COMMAND_WITH_PARAMS) {
            try {
                paramCommandHandler(commandWords);
            } catch (InvalidParamsException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println(INCORRECT_COMMAND_ERROR_MSG);
        }
    }

    /**
     * Handles commands that do not require parameters.
     *
     * @param commandWords the command to be processed.
     */
    private void noParamCommandHandler(String[] commandWords) {
        if (commandWords[COMMAND_WORD_INDEX].equals(SHOW_CHARS_COMMAND)) {
            showChars();
        } else if (commandWords[COMMAND_WORD_INDEX].equals(RUN_ALGORITHM_COMMAND)) {
            runAsciiArtAlgorithm();
        } else {
            System.out.println(INCORRECT_COMMAND_ERROR_MSG);
        }
    }

    /**
     * Handles commands that require parameters.
     *
     * @param commandWords the command and its parameters to be processed.
     * @throws InvalidParamsException if the parameters provided are invalid.
     */
    private void paramCommandHandler(String[] commandWords) throws InvalidParamsException {
        switch (commandWords[COMMAND_WORD_INDEX]) {
            case ADD_CHAR_COMMAND -> addChar(commandWords[COMMAND_PARAMS_INDEX]);
            case REMOVE_CHAR_COMMAND -> removeChar(commandWords[COMMAND_PARAMS_INDEX]);
            case CHANGE_IMG_RESOLUTION_COMMAND -> changeImgResolution(commandWords[COMMAND_PARAMS_INDEX]);
            case CHANGE_IMG_COMMAND -> changeImg(commandWords[COMMAND_PARAMS_INDEX]);
            case CHANGE_OUTPUT_METHOD_COMMAND -> changeOutputMethod(commandWords[COMMAND_PARAMS_INDEX]);
            default -> System.out.println(INCORRECT_COMMAND_ERROR_MSG);
        }
    }

    /**
     * Displays the available characters.
     */
    private void showChars() {
        for (char character : chars.getSortedCharAlphabeticallyList()) {
            System.out.print("%c ".formatted(character));
        }
        System.out.println();
    }

    /**
     * Adds characters based on user input.
     *
     * @param charsToAdd the characters to be added.
     * @throws InvalidParamsException if the parameters provided are invalid.
     */
    private void addChar(String charsToAdd) throws InvalidParamsException {
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
            throw new InvalidParamsException(ADD_COMMAND_INCORRECT_FORMAT_ERROR_MSG);
        }
    }

    /**
     * Checks if a given string represents a character range.
     *
     * @param string the string to be checked.
     * @return true if the string represents a character range, false otherwise.
     */
    private boolean isCharRange(String string) {
        if (string.length() != CHAR_RANGE_PARAM_LENGTH) {
            return false;
        }
        return isInCharRange(string.charAt(FIRST_CHAR_RANGE_INDEX)) &&
                isInCharRange(string.charAt(SECOND_CHAR_RANGE_INDEX)) &&
                string.charAt(1) == '-';
    }

    /**
     * Checks if a given character is within the valid character range.
     *
     * @param c the character to be checked.
     * @return true if the character is within the valid range, false otherwise.
     */
    private boolean isInCharRange(char c) {
        return (c <= MAX_CHAR_VALUE) && (c >= MIN_CHAR_VALUE);
    }

    /**
     * Adds or removes characters within a specified range.
     *
     * @param charsToAdd the range of characters to be added or removed.
     * @param isAddition true if characters are to be added, false if they are to be removed.
     */
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

    /**
     * Removes characters based on user input.
     *
     * @param charsToRemove the characters to be removed.
     * @throws InvalidParamsException if the parameters provided are invalid.
     */
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
            throw new InvalidParamsException(REMOVE_COMMAND_INCORRECT_FORMAT_ERROR_MSG);
        }
    }

    /**
     * Changes the image resolution based on user input.
     *
     * @param changeResolutionCommand the command to change the resolution.
     * @throws InvalidParamsException if the parameters provided are invalid.
     */
    private void changeImgResolution(String changeResolutionCommand) throws InvalidParamsException {
        int minCharsInRow = Math.max(1, image.getWidth() / image.getHeight());
        if (changeResolutionCommand.equals("up")) {
            if ((imageResolution * CHANGE_RESOLUTION_FACTOR > image.getWidth()) ||
                    (imageResolution * CHANGE_RESOLUTION_FACTOR < minCharsInRow)) {
                throw new InvalidParamsException(RES_COMMAND_BOUNDARIES_ERROR_MSG);
            }
            imageResolution *= CHANGE_RESOLUTION_FACTOR;
        } else if (changeResolutionCommand.equals("down")) {
            if ((imageResolution / CHANGE_RESOLUTION_FACTOR > image.getWidth()) ||
                    (imageResolution / CHANGE_RESOLUTION_FACTOR < minCharsInRow)) {
                throw new InvalidParamsException(RES_COMMAND_BOUNDARIES_ERROR_MSG);
            }
            imageResolution /= CHANGE_RESOLUTION_FACTOR;
        } else {
            throw new InvalidParamsException(RES_COMMAND_INCORRECT_FORMAT_ERROR_MSG);
        }
        System.out.println("Resolution set to " + Integer.toString(imageResolution));
    }

    /**
     * Changes the image based on user input.
     *
     * @param changeImgCommand the command to change the image.
     */
    private void changeImg(String changeImgCommand) {
        try {
            image = new Image(changeImgCommand);
        } catch (IOException e) {
            System.out.println(IMG_COMMAND_ERROR_MSG);
        }
    }

    /**
     * Changes the output method based on user input.
     *
     * @param changeOutputCommand the command to change the output method.
     */
    private void changeOutputMethod(String changeOutputCommand) {
        AsciiOutputFactory asciiOutputFactory = new AsciiOutputFactory();
        asciiOutput = asciiOutputFactory.createAsciiOutput(changeOutputCommand, new String[]{
                "out.html", "Courier New"});
    }

    /**
     * Runs the ASCII art algorithm and outputs the result.
     */
    private void runAsciiArtAlgorithm() {
        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(image, imageResolution, chars);
        asciiOutput.out(asciiArtAlgorithm.run());
    }
}
