package fpoly.junit;

public class JUnitMessage {
    private String message;

    public JUnitMessage(String message) {
        this.message = message;
    }

    public String printMessage() {
        System.out.println(message);
        return message;
    }

    public String printHiMessage() {
        System.out.println("Hi!" + message);
        return "Hi!" + message;
    }
}
