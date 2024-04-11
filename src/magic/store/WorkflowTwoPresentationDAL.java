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


public class WorkflowTwoPresentationDAL {
    private static WorkflowTwoDAL GetDAL() throws SQLException {
        Scanner credentialScanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        // String input
        String userName = credentialScanner.nextLine();
        System.out.print("Enter password: ");
        Console console = System.console();
        var password = credentialScanner.nextLine();
        return new WorkflowTwoDAL(userName, new String(password));
    }

    public static void main(String[] args) throws SQLException, IOException {
        var dal = GetDAL();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to your Magic Store, please provide your search filters");
        System.out.println("Danger Level (1-5)");
        String dangerLevel = scanner.nextLine();
        System.out.println("Magic Type");
        String magicType = scanner.nextLine();
        System.out.println("Price:");
        String price = scanner.nextLine();
        String isGreater = "";
        if (!price.trim().isEmpty()) {
            System.out.println("Are you looking for items greater or equal to " + price + " (yes or any character):");
            isGreater = scanner.nextLine();
        }
        List<ItemModel> rows = dal.searchByFilters(dangerLevel, magicType, price, isGreater);
        printRows(rows);
        System.out.println("Do you want to save your search (yes or not):");
        String shouldSave = scanner.nextLine();
        if (shouldSave.trim().equalsIgnoreCase("yes")) {
            saveSearch(rows);
        }
        System.out.println("Item registered successfully");
    }

    private static void printRows(List<ItemModel> rows) {
        System.out.printf("| %-50s | %-100s | %-10s | %-11s | %-12s | %-10s |%n", "Name", "Description", "Quantity", "Magic Type", "Danger Level", "Price");
        for (ItemModel model : rows) {
            System.out.printf(
                    "| %-50s | %-100s | %-10s | %-11s | %-12s | %-10s |%n",
                    model.getItemName(), model.getDescription(), model.getQuantity(), model.getMagicName(),
                    model.getDangerLevel(), String.format("%.2f", model.getSellPrice()));
        }

    }

    private static void saveSearch(List<ItemModel> rows) throws IOException {
        var file = new File("search-" + UUID.randomUUID() + ".txt");
        try(var writer = new FileWriter(file)) {
            writer.write(String.format("| %-50s | %-100s | %-10s | %-11s | %-12s | %-10s |%n", "Name", "Description", "Quantity", "Magic Type", "Danger Level", "Price"));
            for (ItemModel model : rows) {
                writer.write(String.format(
                        "| %-50s | %-100s | %-10s | %-11s | %-12s | %-10s |%n",
                        model.getItemName(), model.getDescription(), model.getQuantity(), model.getMagicName(),
                        model.getDangerLevel(), String.format("%.2f", model.getSellPrice())
                ));
            }
        }
        System.out.println("Your saved search can be found at " + file);
    }
}