package database;

import interfaces.RowMapper;
import models.arrangement.Accommodation;
import models.arrangement.Arrangement;
import models.arrangement.RoomType;
import models.arrangement.Transport;
import models.reservation.Reservation;
import models.user.Admin;
import models.user.BankAccount;
import models.user.Client;
import models.user.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static String DB_user = "root";
    private static String DB_password = "";
    private static String connectionUrl;
    private static int port = 3306;
    private static String DB_name = "agencija";
    private static Connection connection;

    public static void DBConnect() throws SQLException /*, ClassNotFoundException*/ {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        connectionUrl = "jdbc:mysql://localhost" + ":" + port + "/" + DB_name;
        connection = DriverManager.getConnection(connectionUrl, DB_user, DB_password);
    }

    public static <T> List<T> getObjects(String tableName, RowMapper<T> rowMapper) throws SQLException {
        DBConnect();
        List<T> objects = new ArrayList<>();

        ResultSet resultSet = null;
        Statement statement = connection.createStatement();
        String sqlQuery = "SELECT * FROM " + tableName;
        resultSet = statement.executeQuery(sqlQuery);

        while (resultSet.next())
            objects.add(rowMapper.mapRow(resultSet));

        statement.close();
        connection.close();

        return objects;
    }

    public static List<Client> getClients() throws SQLException {
        return getObjects("klijent", resultSet ->
                new Client(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                )
        );
    }

    public static List<Admin> getAdmins() throws SQLException {
        return getObjects("admin", resultSet ->
                new Admin(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                )
        );
    }

    public static List<BankAccount> getBankAccounts() throws SQLException {
        return getObjects("bankovni_racun", resultSet ->
                new BankAccount(
                        resultSet.getInt(1),
                        resultSet.getString(3),
                        resultSet.getString(2),
                        resultSet.getDouble(4)
                )
        );
    }

    public static List<Accommodation> getAccommodations() throws SQLException {
        return getObjects("smjestaj", resultSet ->
                new Accommodation(
                        resultSet.getInt(1),
                        Integer.parseInt(resultSet.getString(3)),
                        resultSet.getString(2),
                        RoomType.fromString(resultSet.getString(4)),
                        Double.parseDouble(resultSet.getString(5))
                )
        );
    }

    private static Accommodation getAccommodationByID(int id) throws SQLException {
        List<Accommodation> accommodations = getAccommodations();
        for (Accommodation accommodation : accommodations)
            if (accommodation.getId() == id)
                return accommodation;
        return null;
    }

    private static Client getClientByID(int id) throws SQLException {
        List<Client> clients = getClients();
        for (Client client : clients)
            if (client.getId() == id)
                return client;
        return null;
    }

    private static Arrangement getArrangementByID(int id) throws SQLException {
        List<Arrangement> arrangements = getArrangements();
        for (Arrangement arrangement : arrangements)
            if (arrangement.getId() == id)
                return arrangement;
        return null;
    }

    public static List<Arrangement> getArrangements() throws SQLException {
        return getObjects("aranzman", resultSet ->
                new Arrangement(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        Transport.fromString(resultSet.getString(4)),
                        LocalDate.parse(resultSet.getString(5)),
                        LocalDate.parse(resultSet.getString(6)),
                        Double.parseDouble(resultSet.getString(7)),
                        getAccommodationByID(resultSet.getInt(8))
                )
        );
    }

    public static List<Reservation> getReservations() throws SQLException {
        return getObjects("rezervacija", resultSet ->
                new Reservation(
                        getClientByID(resultSet.getInt(1)),
                        getArrangementByID(Integer.parseInt(resultSet.getString(2))),
                        null,
                        Double.parseDouble(resultSet.getString(3)),
                        Double.parseDouble(resultSet.getString(4))
                )
        );
    }

    public static BankAccount getAgencyBankAccount() throws SQLException {
        List<BankAccount> bankAccounts = getBankAccounts();
        for (BankAccount bankAccount : bankAccounts)
            if (bankAccount.isAgencyBankAccount())
                return bankAccount;
        return null;
    }

    public static List<User> getUsers() throws SQLException {
        List<Client> clients = getClients();
        List<Admin> admins = getAdmins();
        List<User> users = new ArrayList<>();
        users.addAll(admins);
        users.addAll(clients);
        return users;
    }

    public static void updateBalance(int id, double newBalance) throws SQLException {
        DBConnect();
        String sqlUpdate = "UPDATE bankovni_racun SET stanje=? where id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
        preparedStatement.setDouble(1, newBalance);
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();
        connection.close();
    }
}
