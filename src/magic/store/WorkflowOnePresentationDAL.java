package magic.store;

import java.io.Console;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class WorkflowOnePresentationDAL {
    public static WorkflowOneDAL GetDAL() throws SQLException {
        Scanner credentialScanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        // String input
        String userName = credentialScanner.nextLine();
        System.out.print("Enter password: ");
        Console console = System.console();
        var password = credentialScanner.nextLine();
        return new WorkflowOneDAL(userName, new String(password));
    }
    public static void main(String[] args) throws SQLException {
        var dal = GetDAL();
        System.out.println("Welcome to your Magic Store, please provide item to register");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        dal.registerItem(line);
        System.out.println("Item registered successfully");
    }}