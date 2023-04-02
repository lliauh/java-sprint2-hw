public class MonthExpense {
    public String title;
    boolean isExpense;
    int quantity;
    int itemPrice;
    int totalPrice;
    String month;

    MonthExpense(String title, boolean isExpense, int quantity, int itemPrice, String month) {
        this.title = title;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.totalPrice = quantity * itemPrice;
        this.month = month;
    }
}
