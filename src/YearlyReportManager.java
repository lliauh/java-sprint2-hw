import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class YearlyReportManager {

    ArrayList<YearExpense> yearExpenses = new ArrayList<>();

    public YearlyReportManager(String path) {
        List<String> content = readFileContents(path);
        for (int i = 1; i < content.size(); i++) {
            String line = content.get(i);
            String[] parts = line.split(",");
            int month = Integer.parseInt(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            boolean isExpense = Boolean.parseBoolean(parts[2]);

            YearExpense yearExpense = new YearExpense(month, amount, isExpense);
            yearExpenses.add(yearExpense);
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

    public void getYearStatistics() {
        HashMap<Integer, Integer> incomes = new HashMap<>();
        HashMap<Integer, Integer> expenses = new HashMap<>();

        for (YearExpense yearExpense : yearExpenses) {
            if (yearExpense.isExpense) {
                expenses.put(yearExpense.month, expenses.getOrDefault(yearExpense.month, 0) + yearExpense.amount);
            } else {
                incomes.put(yearExpense.month, incomes.getOrDefault(yearExpense.month, 0) + yearExpense.amount);
            }
        }

        System.out.println("Годовой отчёт за 2021 год:");
        System.out.println("Прибыль помесячно:");
        for (Integer month : incomes.keySet()) {
            System.out.println(month + " - " + (incomes.get(month) - expenses.get(month)));
        }

        int totalExpense = 0;
        for (Integer expense : expenses.keySet()) {
            totalExpense += expenses.get(expense);
        }
        int avgExpense = totalExpense / expenses.size();
        System.out.println("Средний ежемесячный расход - " + avgExpense);

        int totalIncome = 0;
        for (Integer income : incomes.keySet()) {
            totalIncome += incomes.get(income);
        }
        int avgIncome = totalIncome / incomes.size();
        System.out.println("Средний ежемесячный доход - " + avgIncome);

    }
}