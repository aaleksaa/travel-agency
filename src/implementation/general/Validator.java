package implementation.general;

import exceptions.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextInputControl;
import models.entities.Arrangement;
import models.entities.Reservation;
import models.entities.BankAccount;
import models.entities.Client;
import models.entities.User;
import models.enums.RoomType;
import models.enums.Transport;

import java.time.LocalDate;
import java.util.List;

/**
 * The Validator class provides methods for input validation and error handling.
 */
public class Validator {
    /**
     * Checks if any of the text input controls are empty.
     *
     * @param inputs the array of text input controls to be checked.
     * @throws EmptyInputException if any input control is empty.
     */
    public static void areInputsEmpty(TextInputControl[] inputs) throws EmptyInputException {
        for (TextInputControl input : inputs)
            if (input.getText().isEmpty())
                throw new EmptyInputException();
    }

    /**
     * Validates if a string represents a valid number format.
     *
     * @param number the string to be validated.
     * @return true if the string represents a valid number, false otherwise.
     */
    public static boolean validateNumberFormat(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a number is negative.
     *
     * @param number the string representation of the number to be checked.
     * @return true if the number is negative, false otherwise.
     */
    public static boolean isNegative(String number) {
        return Double.parseDouble(number) < 0;
    }

    /**
     * Validates if a price input is valid.
     *
     * @param number the string representation of the price to be validated.
     * @throws InvalidInputException if the price input is invalid.
     */
    public static void isPriceValid(String number) throws InvalidInputException {
        if (!validateNumberFormat(number) || isNegative(number))
            throw new InvalidInputException("Invalid price input!");
    }

    /**
     * Checks if a username is available.
     *
     * @param users    the list of existing users.
     * @param username the username to be checked.
     * @throws InvalidInputException if the username is already taken.
     */
    public static void isUsernameAvailable(List<User> users, String username) throws InvalidInputException {
        if (users.stream().anyMatch(user -> user.isUsernameMatching(username)))
            throw new InvalidInputException("Username " + username + " is taken!");
    }

    /**
     * Checks if two passwords match.
     *
     * @param password        the first password.
     * @param confirmPassword the second password for confirmation.
     * @throws PasswordMismatchException if the passwords do not match.
     */
    public static void passwordMatch(String password, String confirmPassword) throws PasswordMismatchException {
        if (!password.equals(confirmPassword))
            throw new PasswordMismatchException();
    }

    /**
     * Checks if a date is selected.
     *
     * @param date the date to be checked.
     * @throws EmptyInputException if no date is selected.
     */
    public static void isDateSelected(LocalDate date) throws EmptyInputException {
        if (date == null)
            throw new EmptyInputException();
    }

    /**
     * Resets the input controls by clearing their text.
     *
     * @param inputs the array of text input controls to be reset.
     */
    public static void resetInputs(TextInputControl[] inputs) {
        for (TextInputControl input : inputs)
            input.clear();
    }

    /**
     * Checks if the trip date is after the arrival date.
     *
     * @param date1 the trip date.
     * @param date2 the arrival date.
     * @throws InvalidDateException if the trip date is after the arrival date.
     */
    public static void isTripDateAfterArrivalDate(LocalDate date1, LocalDate date2) throws InvalidDateException {
        if (!date1.isBefore(date2))
            throw new InvalidDateException();
    }

    /**
     * Checks if both choice boxes have selections.
     *
     * @param cb1 the first choice box.
     * @param cb2 the second choice box.
     * @throws EmptyInputException if any choice box does not have a selection.
     */
    public static void areChoiceBoxesSelected(ChoiceBox<String> cb1, ChoiceBox<String> cb2) throws EmptyInputException {
        if (Transport.fromString(cb1.getValue()) == null || RoomType.fromString(cb2.getValue()) == null)
            throw new EmptyInputException();
    }

    /**
     * Checks if an arrangement is already reserved by the client.
     *
     * @param reservations the list of reservations.
     * @param client       the client making the reservation.
     * @param arr          the arrangement to be reserved.
     * @throws UnsuccessfulReservationException if the arrangement is already reserved by the client.
     */
    public static void isReserved(List<Reservation> reservations, Client client, Arrangement arr) throws UnsuccessfulReservationException {
        for (Reservation reservation : reservations)
            if (reservation.isReservedByClient(client, arr))
                throw new UnsuccessfulReservationException("Arrangement is already reserved!");
    }

    /**
     * Checks if there's sufficient balance for a transaction.
     *
     * @param bankAccount the bank account.
     * @param amount      the amount to be transacted.
     * @throws UnsuccessfulReservationException if there's insufficient balance for the transaction.
     */
    public static void checkBalanceForTransaction(BankAccount bankAccount, double amount) throws UnsuccessfulReservationException {
        if (bankAccount.getBalance() < amount)
            throw new UnsuccessfulReservationException("Insufficient balance for transaction!");
    }
}
