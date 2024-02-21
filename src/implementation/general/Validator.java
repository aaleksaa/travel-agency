package implementation.general;

import exceptions.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
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

public class Validator {
    public static void areInputsEmpty(TextInputControl[] inputs) throws EmptyInputException {
        for (TextInputControl input : inputs)
            if (input.getText().isEmpty())
                throw new EmptyInputException();
    }

    public static boolean validateNumberFormat(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNegative(String number) {
        return Double.parseDouble(number) < 0;
    }

    public static void isPriceValid(String number) throws InvalidInputException {
        if (!validateNumberFormat(number) || isNegative(number))
            throw new InvalidInputException("Invalid price input!");
    }

    public static void isUsernameAvailable(List<User> users, String username) throws InvalidInputException {
        if (users.stream().anyMatch(user -> user.isUsernameMatching(username)))
            throw new InvalidInputException("Username " + username + " is taken!");
    }

    public static void passwordMatch(String password, String confirmPassword) throws PasswordMismatchException {
        if (!password.equals(confirmPassword))
            throw new PasswordMismatchException();
    }

    public static void isDateSelected(LocalDate date) throws EmptyInputException {
        if (date == null)
            throw new EmptyInputException();
    }


    public static void resetInputs(TextInputControl[] inputs) {
        for (TextInputControl input : inputs)
            input.clear();
    }

    public static void isTripDateAfterArrivalDate(LocalDate date1, LocalDate date2) throws InvalidDateException {
        if (!date1.isBefore(date2))
            throw new InvalidDateException();
    }

    public static void areChoiceBoxesSelected(ChoiceBox<String> cb1, ChoiceBox<String> cb2) throws EmptyInputException {
        if (Transport.fromString(cb1.getValue()) == null || RoomType.fromString(cb2.getValue()) == null)
            throw new EmptyInputException();
    }


    public static void isReserved(List<Reservation> reservations, Client client, Arrangement arr) throws UnsuccessfulReservationException {
        for (Reservation reservation : reservations)
            if (reservation.isReservedByClient(client, arr))
                throw new UnsuccessfulReservationException("Arrangement is already reserved!");
    }

    public static void checkBalanceForTransaction(BankAccount bankAccount, double amount) throws UnsuccessfulReservationException {
        if (bankAccount.getBalance() < amount)
            throw new UnsuccessfulReservationException("Insufficient balance for transaction!");
    }
}
