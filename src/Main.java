import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        MonthlyReportManager monthlyReportManager = new MonthlyReportManager();
        String[] months = {"Январь", "Ферваль", "Март"};

        YearlyReportManager yearlyReportManager = new YearlyReportManager("resources/y.2021.csv");

        while (true) {
            printMenu();
            int command = scanner.nextInt();

            if (command == 1) {
                for (int i = 1; i < months.length + 1; i++) {
                    monthlyReportManager.loadFile("resources/m.20210" + i + ".csv", months[i-1]);
                }
                System.out.println("Месячные отчёты были успешно считаны.");
            } else if (command == 2) {
                System.out.println("Годовой отчёт был успешно считан.");
            } else if (command == 3) {
                if (monthlyReportManager.monthExpenses.size() != 0 && yearlyReportManager.yearExpenses.size() != 0) {
                    Checker checker = new Checker(monthlyReportManager, yearlyReportManager);
                    checker.check();
                } else {
                    System.out.println("Не могу провести сверку, так как отчёты не были считаны. Пожалуйста, считайте месячные и годовые отчёты.");
                }
            } else if (command == 4) {
                for (int i = 0; i < months.length; i++) {
                    System.out.println("Статистика за месяц - " + months[i]);
                    monthlyReportManager.getMonthStatistics(months[i]);
                }
            } else if (command == 5) {
                yearlyReportManager.getYearStatistics();
            } else if (command == 0) {
                System.out.println("Работа приложения завершена.");
                break;
            } else {
                System.out.println("Введена неподдерживаемая комманда. Попробуйте снова.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Введите команду:");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Завершить работу приложения");
    }
}