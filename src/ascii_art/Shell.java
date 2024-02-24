package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import image.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    private static final List<Character> DEFAULT_CHAR_SET = List.of('0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9');
    private static final int DEFAULT_IMAGE_RESOLUTION = 128;
    private static final AsciiOutput DEFAULT_ASCII_OUTPUT = new ConsoleAsciiOutput();
    /***************** CONSTANTS *****************/
    private static final int COMMAND_WORD_INDEX = 0;
    private static final int COMMAND_PARAMS_INDEX = 1;
    private static final int CHANGE_RESOLUTION_FACTOR = 2;
    private static final int MIN_CHAR_VALUE = 32;
    private static final int MAX_CHAR_VALUE = 126;
    private static final String COMMAND_PROMPT_STRING = "<<<";

    /*********** CLASS FIELDS *******************/
    private List<Character> chars; //TODO: change to CharConverter
    private Image image;
    private AsciiOutput asciiOutput;
    private int imageResolution;

    /*******************************************************/

    public Shell() throws IOException {
        chars = DEFAULT_CHAR_SET;
        image = new Image(DEFAULT_IMAGE);
        asciiOutput = DEFAULT_ASCII_OUTPUT;
        imageResolution = DEFAULT_IMAGE_RESOLUTION;
    }

    public void run() {
        String input = "";
        do {
            System.out.println(COMMAND_PROMPT_STRING);
            commandHandler((input = KeyboardInput.readLine()));
        } while (!input.equals(EXIT_COMMAND));
    }

    private void commandHandler(String userInput) {
        String[] commandWords = userInput.split("\\s+");
        if (commandWords.length == 1) {
            if (commandWords[COMMAND_WORD_INDEX].equals(SHOW_CHARS_COMMAND)) {
                showChars();
            }
            else if (commandWords[COMMAND_WORD_INDEX].equals(RUN_ALGORITHM_COMMAND)) {
                runAsciiArtAlgorithm();
            }
        } else if (commandWords.length == 2) {
            try {
                if (commandWords[COMMAND_WORD_INDEX].equals(ADD_CHAR_COMMAND)) {
                    addChar(commandWords[COMMAND_PARAMS_INDEX]);
                }
                else if (commandWords[COMMAND_WORD_INDEX].equals(REMOVE_CHAR_COMMAND)) {
                    removeChar(commandWords[COMMAND_PARAMS_INDEX]);
                }
                else if (commandWords[COMMAND_WORD_INDEX].equals(CHANGE_IMG_RESOLUTION_COMMAND)) {
                    changeImgResolution(commandWords[COMMAND_PARAMS_INDEX]);
                }
                else if (commandWords[COMMAND_WORD_INDEX].equals(CHANGE_IMG_COMMAND)) {
                    changeImg(commandWords[COMMAND_PARAMS_INDEX]);
                }
                else if (commandWords[COMMAND_WORD_INDEX].equals(CHANGE_OUTPUT_METHOD_COMMAND)) {
                    changeOutputMethod(commandWords[COMMAND_PARAMS_INDEX]);
                }
            } catch (InvalidCharToAddException e) {
                System.out.println("ncjfkd");
            }
        } else {
            System.out.println("Did not execute due to incorrect command.");
        }

    }

    private void showChars() {
        Collections.sort(chars);
        for (char character : chars) {
            System.out.print("%c ".formatted(character));
        }
        System.out.println();
    }

    private void addChar(String charsToAdd) throws InvalidCharToAddException {
        if (chars.equals("all")) {
            for (int i = MIN_CHAR_VALUE; i <= MAX_CHAR_VALUE; i++) {
                chars.add((char) i);
            }
        } else if (chars.equals("space")) {
            chars.add(' ');
        } else if (isCharRange(charsToAdd)) {
            char smaller = charsToAdd.charAt(0) < charsToAdd.charAt(2) ? charsToAdd.charAt(0) : charsToAdd.charAt(2);
            char bigger = charsToAdd.charAt(0) < charsToAdd.charAt(2) ? charsToAdd.charAt(2) : charsToAdd.charAt(0);

            for (int i = smaller; i <= bigger; i++) {
                chars.add((char) i);
            }
        } else if (charsToAdd.length() == 1 && Character.isLetter(charsToAdd.charAt(0))) {
            chars.add(charsToAdd.charAt(0));
        } else {
            throw new InvalidCharToAddException("");
        }
    }

    private boolean isCharRange(String string) {
        if (string.length() != 3) {
            return false;
        }
        return Character.isLetter(string.charAt(0)) && Character.isLetter(string.charAt(2)) &&
                string.charAt(1) == '-';
    }

    private void removeChar(String charsToRemove) throws InvalidCharToRemoveException {
        if (chars.equals("all")) {
            for (int i = MIN_CHAR_VALUE; i < MAX_CHAR_VALUE; i++) {
                chars.remove((char) i);
            }
        } else if (chars.equals("space")) {
            chars.remove(' ');
        } else if (isCharRange(charsToRemove)) {
            char smaller = charsToRemove.charAt(0) < charsToRemove.charAt(2) ?
                    charsToRemove.charAt(0) : charsToRemove.charAt(2);
            char bigger = charsToRemove.charAt(0) < charsToRemove.charAt(2) ?
                    charsToRemove.charAt(2) : charsToRemove.charAt(0);

            for (int i = smaller; i <= bigger; i++) {
                chars.remove((char) i);
            }
        } else if (charsToRemove.length() == 1 && Character.isLetter(charsToRemove.charAt(0))) {
            chars.remove(charsToRemove.charAt(0));
        } else {
            throw new InvalidCharToRemoveException("");
        }
    }

    private void changeImgResolution(String changeResolutionCommand) throws InvalidResolutionException {
        int minCharsInRow = Math.max(1, image.getWidth() / image.getHeight());
        if (changeResolutionCommand.equals("up")) {
            if ((imageResolution * CHANGE_RESOLUTION_FACTOR > image.getWidth()) ||
                    (imageResolution * CHANGE_RESOLUTION_FACTOR < minCharsInRow)) {
                throw new InvalidResolutionException("");
            }
            imageResolution *= CHANGE_RESOLUTION_FACTOR;
        }
        if (changeResolutionCommand.equals("down")) {
            if ((imageResolution / CHANGE_RESOLUTION_FACTOR > image.getWidth()) ||
                    (imageResolution / CHANGE_RESOLUTION_FACTOR < minCharsInRow)) {
                throw new InvalidResolutionException("");
            }
            imageResolution /= CHANGE_RESOLUTION_FACTOR;
        }
        System.out.println("Resolution set to %d".formatted(imageResolution));
    }

    private void changeImg(String changeImgCommand) {
        String[] commandWords = changeImgCommand.split("\\s+");
        try {
            image = new Image(commandWords[1]);
        } catch (IOException e) {
            System.out.println("Did not execute due to problem with image file.");
        }
    }

    private void changeOutputMethod(String changeOutputCommand) {
        String[] commandWords = changeOutputCommand.split("\\s+");
        if (commandWords.length < 2) {
            System.out.println("Did not change output method due to incorrect format.");
            return;
        }

        AsciiOutputFactory asciiOutputFactory = new AsciiOutputFactory();
        asciiOutput = asciiOutputFactory.createAsciiOutput(commandWords[1], new String[]{"out.html", "Courier New"});
    }

    private void runAsciiArtAlgorithm() {
        //AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(); //TODO CHANGE TO CORRECT CTOR
    }

}
