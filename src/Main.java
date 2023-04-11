import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        MonthlyReport januaryReport = null;
        MonthlyReport februaryReport = null;
        MonthlyReport marchReport = null;
        ArrayList<MonthlyReport> monthlyReports = null;


        YearlyReport yearlyReport = new YearlyReport();

        ReportManager reportManager = new ReportManager();
        HashMap<String, String> months = new HashMap<>();
        months.put("january", "Январь");
        months.put("february", "Февраль");
        months.put("march", "Март");

        boolean monthsDataLoaded = false;
        boolean yearDataLoaded = false;

        while (true) {
            printMenu();
            int command = scanUserInputSafely(scanner);

            if (command == 1) {
                januaryReport = new MonthlyReport("resources/m.202101.csv", months.get("january"));
                februaryReport = new MonthlyReport("resources/m.202102.csv", months.get("february"));
                marchReport = new MonthlyReport("resources/m.202103.csv", months.get("march"));
                monthlyReports = new ArrayList<>();
                monthlyReports.add(januaryReport);
                monthlyReports.add(februaryReport);
                monthlyReports.add(marchReport);
                monthsDataLoaded = true;
                System.out.println("Месячные отчёты были успешно считаны.");
            } else if (command == 2) {
                yearlyReport = new YearlyReport("resources/y.2021.csv");
                yearDataLoaded = true;
                System.out.println("Годовой отчёт был успешно считан.");
            } else if (command == 3) {
                if (monthsDataLoaded && yearDataLoaded) {
                    reportManager.check(monthlyReports, yearlyReport);
                } else {
                    System.out.println("Не могу провести сверку, так как отчёты не были считаны. Пожалуйста, считайте месячные и годовые отчёты.");
                }
            } else if (command == 4) {
                if (monthsDataLoaded) {
                    reportManager.getMonthStatistics(januaryReport);
                    reportManager.getMonthStatistics(februaryReport);
                    reportManager.getMonthStatistics(marchReport);
                } else {
                    System.out.println("Для выполнения операции необходимо считать месячные отчёты.");
                }
            } else if (command == 5) {
                if (yearDataLoaded) {
                    reportManager.getYearStatistics(yearlyReport);
                } else {
                    System.out.println("Для выполнения операции необходимо считать годовой отчёт.");
                }
            } else if (command == 0) {
                System.out.println("Работа приложения завершена.");
                break;
            } else {
                System.out.println("Введена неподдерживаемая команда. Попробуйте снова.");
            }
        }
    }

    public static int scanUserInputSafely(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Пожалуйста, введите целое число (если применимо, то из доступного списка вариантов).");
            scanner.next();
            return -1;
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