import java.sql.*;
import java.util.Scanner;
public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "reallyStrongPwd123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/homework";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        Scanner scanner = new Scanner(System.in);
        System.out.print("name:");
        String name = scanner.nextLine();
        System.out.print("email: ");
        String email = scanner.next();
        System.out.print("age:");
        int age = scanner.nextInt();


        String sqlInsert = "INSERT INTO testaccounts(name, email, age) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, email);
        preparedStatement.setInt(3, age);
        preparedStatement.executeUpdate();
        System.out.println("user created!");
        System.out.println("enter the name of the user you are searching for: ");
        String search = scanner.next();
        searchByName(connection, search);
        System.out.println("a list of all users");
        displayAllUsers(connection);
        System.out.println("enter the name you want to delete");
        String deleteName = scanner.next();
        deleteUser(connection, deleteName);
    }

    private static void searchByName(Connection connection, String name) throws SQLException {
        String sqlSelect = "SELECT * FROM testaccounts WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setString(1, name);
        ResultSet searchResult = preparedStatement.executeQuery();

        if (searchResult.next()) {
            System.out.print("this person has an account on this site");
        } else {
            System.out.println("this user doesn't exist on your site");
        }
    }

    private static void displayAllUsers(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from testaccounts");
        while (result.next()) {
            System.out.println(result.getInt("id") + " " + result.getString("name"));
        }
    }

    private static void deleteUser(Connection connection, String name) throws SQLException {
        String sqlDrop = "delete FROM testaccounts WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlDrop);
        preparedStatement.setString(1, name);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("User deleted!");
        } else {
            System.out.println("User not found or deletion failed.");
        }
    }
}