import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MonthlyReportManager {
    ArrayList<MonthExpense> monthExpenses = new ArrayList<>();

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

    public void getMonthStatistics(String month) {
        HashMap<String, Integer> profitableProducts = new HashMap<>();
        HashMap<String, Integer> unprofitableProducts = new HashMap<>();

        for (MonthExpense monthExpense : monthExpenses) {
            if (monthExpense.isExpense) {
                unprofitableProducts.put(monthExpense.title, unprofitableProducts.getOrDefault(monthExpense, 0) + monthExpense.totalPrice);
            } else {
                profitableProducts.put(monthExpense.title, profitableProducts.getOrDefault(monthExpense, 0) + monthExpense.totalPrice);
            }
        }

        String maxProfitTitle = null;
        int maxProfit = 0;
        for (String title : profitableProducts.keySet()) {
            if (maxProfitTitle == null) {
                maxProfitTitle = title;
                maxProfit = profitableProducts.get(title);
                continue;
            }
            if (profitableProducts.get(maxProfitTitle) < profitableProducts.get(title)) {
                maxProfitTitle = title;
                maxProfit = profitableProducts.get(title);
            }
        }
        System.out.println("Самый прибыльный товар - " + maxProfitTitle + ". Прибыль по нему составила " + maxProfit);

        String maxExpenseTitle = null;
        int maxExpense = 0;
        for (String title : unprofitableProducts.keySet()) {
            if (maxExpenseTitle == null) {
                maxExpenseTitle = title;
                maxExpense = unprofitableProducts.get(title);
                continue;
            }
            if (unprofitableProducts.get(maxExpenseTitle) < unprofitableProducts.get(title)) {
                maxExpenseTitle = title;
                maxExpense = unprofitableProducts.get(title);
            }
        }
        System.out.println("Самая большая трата - " + maxExpenseTitle + ". Размер траты " + maxExpense);


    }
}