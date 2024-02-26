import ascii_art.Shell;

public class Main {
    public static void main(String[] args) {
        try{
            Shell s = new Shell();
            s.run();
        } catch(Exception e) {
            System.out.println("Failed");
        }
    }
}