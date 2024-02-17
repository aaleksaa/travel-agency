package exceptions;

public class EmptyInputException extends Exception {
    public EmptyInputException() {
        super("Fill in all fields!");
    }
}
