package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;

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

    /*********** CLASS FIELDS *******************/
    private List<Character> chars;
    private String image;
    private AsciiOutput asciiOutput;
    private int imageResolution;
    /*******************************************************/

    public Shell() {
        chars = DEFAULT_CHAR_SET;
        image = DEFAULT_IMAGE;
        asciiOutput = DEFAULT_ASCII_OUTPUT;
        imageResolution = DEFAULT_IMAGE_RESOLUTION;
    }

    public void run() {
        KeyboardInput scanner = KeyboardInput.getObject();
        String input = "";
        do {
            System.out.println("<<<");
            commandHandler((input = KeyboardInput.readLine()));
        } while (!input.equals(EXIT_COMMAND));
    }

    private void commandHandler(String userInput) {
        switch (userInput.split("\\s+")[0]) {
            case (SHOW_CHARS_COMMAND):
                showChars();
                break;
            case (ADD_CHAR_COMMAND):
                addChar(userInput);
                break;
            case (REMOVE_CHAR_COMMAND):
                removeChar(userInput);
                break;
            case (CHANGE_IMG_RESOLUTION_COMMAND):
                changeImgResolution();
                break;
            case (CHANGE_IMG_COMMAND):
                changeImg();
                break;
            case (CHANGE_OUTPUT_METHOD_COMMAND):
                changeOutputMethod();
                break;
            case (RUN_ALGORITHM_COMMAND):
                //run algorithm;
                break;
            default:
                System.out.println("Did not execute due to incorrect command.");
        }
    }

    private void showChars() {
        Collections.sort(chars);
        for(char character: chars){
            System.out.print("%c ".formatted(character));
        }
        System.out.println();
    }

    private void addChar(String toAddCommand) throws InvalidCharToAddException{
        String[] commandWords = toAddCommand.split("\\s+");
        if(commandWords.length != 2){
            throw new InvalidCharToAddException("");
        }



    }

    private void removeChar(String toRemove) {
    }

    private void changeImgResolution() {
    }

    private void changeImg() {
    }

    private void changeOutputMethod() {
    }

    private void runAsciiArtAlgorithm() {

        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(); //TODO CHANGE TO CORRECT CTOR
    }

}
