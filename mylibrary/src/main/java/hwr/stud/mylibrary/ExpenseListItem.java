package hwr.stud.mylibrary;

public class ExpenseListItem {

    private String expenseItemDate;
    private String expenseItemDescription;
    private String expenseItemAmount;

    public ExpenseListItem(String expenseItemAmount, String expenseItemDate, String expenseItemDescription) {

        this.expenseItemAmount = expenseItemAmount;
        this.expenseItemDate = expenseItemDate;
        this.expenseItemDescription = expenseItemDescription;
    }

    public String getExpenseItemDate() {
        return expenseItemDate;
    }

    public String getExpenseItemDescription() {
        return expenseItemDescription;
    }

    public String getExpenseItemAmount() {
        return expenseItemAmount;
    }
}
