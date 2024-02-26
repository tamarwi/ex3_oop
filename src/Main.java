import ascii_art.Shell;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            Shell s = new Shell();
            s.run();
        } catch(IOException e) {
            System.out.println("Failed");
        }
    }
}