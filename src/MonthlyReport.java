import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MonthlyReport {
    String month;
    ArrayList<MonthExpense> monthExpenses = new ArrayList<>();
    ArrayList<MonthExpense> expenses = new ArrayList<>();
    ArrayList<MonthExpense> profit = new ArrayList<>();

    public MonthlyReport() {
    }

    public MonthlyReport(String path, String month) {
        this.month = month;
        loadFile(path, month);

        for (MonthExpense monthExpense : monthExpenses) {
            if (monthExpense.isExpense) {
                expenses.add(monthExpense);
            } else {
                profit.add(monthExpense);
            }
        }
    }

    public int calculateTotalProfit() {
        int totalProfit = 0;
        for (MonthExpense monthExpense : profit) {
            totalProfit += monthExpense.totalPrice;
        }
        return totalProfit;
    }

    public int calculateTotalExpense() {
        int totalExpense = 0;
        for (MonthExpense monthExpense : expenses) {
            totalExpense += monthExpense.totalPrice;
        }
        return totalExpense;
    }

    public void loadFile(String path, String month) {
        List<String> content = readFileContents(path);
        for (int i = 1; i < content.size(); i++) {
            String line = content.get(i);
            String[] parts = line.split(",");
            String title = parts[0];
            boolean isExpense = Boolean.parseBoolean(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            int itemPrice = Integer.parseInt(parts[3]);

            MonthExpense monthExpense = new MonthExpense(title, isExpense, quantity, itemPrice, month);
            monthExpenses.add(monthExpense);

        }
    }
    List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }
}