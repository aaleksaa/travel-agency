package exceptions;

public class InvalidDateException extends Exception {
    public InvalidDateException() {
        super("Invalid date input!");
    }
}
