import java.util.HashMap;
import java.util.ArrayList;

public class ReportManager {
    public ArrayList<MonthlyReport> monthlyReports;
    public YearlyReport yearlyReport;


    public void check(ArrayList<MonthlyReport> monthlyReports, YearlyReport yearlyReport) {

        //Приводим помесячную статистику в удобный для сравнения формат
        HashMap<String, Integer> monthsProfit = new HashMap<>();
        HashMap<String, Integer> monthsExpenses = new HashMap<>();

        for (MonthlyReport monthlyReport : monthlyReports) {
            monthsExpenses.put(monthlyReport.month, monthlyReport.calculateTotalExpense());
            monthsProfit.put(monthlyReport.month, monthlyReport.calculateTotalProfit());
        }


        //Приводим годовую статистику в удобный для сравнения формат
        HashMap<String, Integer> yearProfit = new HashMap<>();
        HashMap<String, Integer> yearExpenses = new HashMap<>();
        String[] months = {"Январь", "Февраль", "Март", "Апрель", " Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        HashMap<Integer, String> monthNames = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            monthNames.put(i + 1, months[i]);
        }

        for (int monthNumber : monthNames.keySet()) {
            for (YearExpense yearExpense : yearlyReport.yearExpenses) {
                if (yearExpense.month == monthNumber) {
                    if (yearExpense.isExpense) {
                        yearExpenses.put(monthNames.get(monthNumber), yearExpense.amount);
                    } else {
                        yearProfit.put(monthNames.get(monthNumber), yearExpense.amount);
                    }
                }
            }
        }

        //Сравним месячную с годовой табличкой и выдадим результаты
        boolean checkFailed = false;
        boolean monthCheckFailed = false;

        for (String month : months) {
            if (monthsProfit.containsKey(month) && monthsExpenses.containsKey(month)) {
                if (monthsProfit.get(month).intValue() != yearProfit.get(month).intValue()) {
                    checkFailed = true;
                    monthCheckFailed = true;
                } else if (monthsExpenses.get(month).intValue() != yearExpenses.get(month).intValue()) {
                    checkFailed = true;
                    monthCheckFailed = true;
                }
            }
            if (monthCheckFailed) {
                System.out.println("Обнаружены расхождения между месячным и годовым отчетом в месяце - " + month);
                System.out.println("Итоговая прибыль в месячном отчете - " + monthsProfit.get(month));
                System.out.println("Итоговая прибыль в годовом отчете - " + yearProfit.get(month));
                System.out.println("Итоговый расход в месячном отчете - " + monthsExpenses.get(month));
                System.out.println("Итоговый расход в годовом отчете - " + yearExpenses.get(month));
                monthCheckFailed = false;
            }
        }
        if (!checkFailed) {
            System.out.println("Сверка прошла успешно, ошибки не были обнаружены.");
        }
    }

    public void getMonthStatistics(MonthlyReport monthlyReport) {
        HashMap<String, Integer> profitableProducts = new HashMap<>();
        for (MonthExpense monthExpense : monthlyReport.profit) {
            profitableProducts.put(monthExpense.title, profitableProducts.getOrDefault("monthExpense", 0) + monthExpense.totalPrice);
        }

        HashMap<String, Integer> unprofitableProducts = new HashMap<>();
        for (MonthExpense monthExpense: monthlyReport.expenses) {
            unprofitableProducts.put(monthExpense.title, profitableProducts.getOrDefault("monthExpense", 0) + monthExpense.totalPrice);
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

        System.out.println("Статистика за месяц - " + monthlyReport.month);
        System.out.println("Самый прибыльный товар - " + maxProfitTitle + ". Прибыль по нему составила " + maxProfit);
        System.out.println("Самая большая трата - " + maxExpenseTitle + ". Размер траты " + maxExpense);
    }

    public void getYearStatistics(YearlyReport yearlyReport) {
        HashMap<Integer, Integer> incomes = new HashMap<>();
        HashMap<Integer, Integer> expenses = new HashMap<>();

        for (YearExpense yearExpense : yearlyReport.yearExpenses) {
            if (yearExpense.isExpense) {
                expenses.put(yearExpense.month, expenses.getOrDefault("yearExpense.month", 0) + yearExpense.amount);
            } else {
                incomes.put(yearExpense.month, incomes.getOrDefault("yearExpense.month", 0) + yearExpense.amount);
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