package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;

public class AsciiOutputFactory {
    public AsciiOutput createAsciiOutput(String outputType, String[] params){
        AsciiOutput asciiOutput = null;
        switch(outputType){
            case "console":
                asciiOutput = new ConsoleAsciiOutput();
                break;
            case "html":
                asciiOutput = new HtmlAsciiOutput(params[0], params[1]);
                break;
        }
        return asciiOutput;
    }
}
