package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;

/**
 * Factory class to create instances of AsciiOutput subclasses.
 */
public class AsciiOutputFactory {
    /**
     * Method to create an instance of AsciiOutput based on the specified output type and parameters.
     * @param outputType The type of output to create. It can be "console" or "html".
     * @param params Parameters required for creating the output. For "console", no parameters are needed.
     *               For "html", parameters should contain the file path and title.
     * @return An instance of AsciiOutput corresponding to the specified output type and parameters.
     */
    public AsciiOutput createAsciiOutput(String outputType, String[] params) {
        AsciiOutput asciiOutput = null;  // Initialize output variable
        switch(outputType) {
            // If the output type is "console", create a ConsoleAsciiOutput instance
            case "console":
                asciiOutput = new ConsoleAsciiOutput();
                break;
            // If the output type is "html", create an HtmlAsciiOutput instance with provided parameters
            case "html":
                asciiOutput = new HtmlAsciiOutput(params[0], params[1]);
                break;
        }
        return asciiOutput;  // Return the created AsciiOutput instance
    }
}
