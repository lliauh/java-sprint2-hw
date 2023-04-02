import java.util.HashMap;

public class Checker {
    public MonthlyReportManager monthlyReportManager;
    public YearlyReportManager yearlyReportManager;

    public Checker(MonthlyReportManager monthlyReportManager, YearlyReportManager yearlyReportManager) {
        this.monthlyReportManager = monthlyReportManager;
        this.yearlyReportManager = yearlyReportManager;
    }

    public void check() {
        String[] months = {"Январь", "Ферваль", "Март", "Апрель", " Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        HashMap<Integer, String> monthNames = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            monthNames.put(i + 1, months[i]);
        }

        //Приводим помесячную статистику в удобный для сравнения формат
        HashMap<String, Integer[]> monthStats = new HashMap<>();

        for (String month : months) {
            int totalProfit = 0;
            int totalExpense = 0;
            for (MonthExpense monthExpense : monthlyReportManager.monthExpenses) {
                if (monthExpense.month.equals(month)) {
                    if (monthExpense.isExpense) {
                        totalExpense += monthExpense.totalPrice;
                    } else {
                        totalProfit += monthExpense.totalPrice;
                    }
                }
            }
            monthStats.put(month, new Integer[]{totalProfit, totalExpense});
        }

        //Приводим годовую статистику в удобный для сравнения формат
        HashMap<String, Integer[]> yearStats = new HashMap<>();

        for (int monthNumber : monthNames.keySet()) {
            int totalProfit = 0;
            int totalExpense = 0;
            for (YearExpense yearExpense : yearlyReportManager.yearExpenses) {
                if (yearExpense.month == monthNumber) {
                    if (yearExpense.isExpense) {
                        totalExpense += yearExpense.amount;
                    } else {
                        totalProfit += yearExpense.amount;
                    }
                }
            }
            yearStats.put(monthNames.get(monthNumber), new Integer[]{totalProfit, totalExpense});
        }

        //Сравним месячную с годовой табличкой и выдадим результаты
        boolean checkFailed = false;
        boolean monthCheckFailed = false;
        for (String month : monthStats.keySet()) {
            for (int i = 0; i < monthStats.get(month).length; i++) {
                if (monthStats.get(month)[i] != yearStats.get(month)[i]) {
                    checkFailed = true;
                    monthCheckFailed = true;
                }
            }
            if (monthCheckFailed) {
                System.out.println("Обнаружены расхождения между месячным и годовым отчетом в месяце - " + month);
                System.out.println("Итоговая прибыль в месячном отчете - " + monthStats.get(month)[0]);
                System.out.println("Итоговая прибыль в годовом отчете - " + yearStats.get(month)[0]);
                System.out.println("Итоговый расход в месячном отчете - " + monthStats.get(month)[1]);
                System.out.println("Итоговый расход в годовом отчете - " + yearStats.get(month)[1]);
                monthCheckFailed = false;
            }
        }
        if (!checkFailed) {
            System.out.println("Сверка прошла успешно, ошибки не были обнаружены.");
        }
    }
}