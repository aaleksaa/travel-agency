package exceptions;

public class PasswordMismatchException extends Exception {
    public PasswordMismatchException() {
        super("Password mismatch!");
    }
}
