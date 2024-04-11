package magic.store;

import magic.store.data.models.ItemModel;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;


public class WorkflowFourPresentationDAL {
    private static WorkflowFourDAL GetDAL() throws SQLException {
        Scanner credentialScanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        // String input
        String userName = credentialScanner.nextLine();
        System.out.print("Enter password: ");
        Console console = System.console();
        var password = credentialScanner.nextLine();
        return new WorkflowFourDAL(userName, new String(password));
    }

    public static void main(String[] args) throws SQLException, IOException {
        var dal = GetDAL();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to your Magic Store, monthly sales");
        System.out.println("Month to repo (1-12):");
        String month = scanner.nextLine();
        dal.generateMonthlyReport(month);
    }

}