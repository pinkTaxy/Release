package hwr.stud.mylibrary;

public class DonationListItem {

    private String donationItemDate;
    private String donationItemAmount;

    public DonationListItem(String expenseItemAmount, String expenseItemDate) {

        this.donationItemAmount = expenseItemAmount;
        this.donationItemDate = expenseItemDate;
    }

    public String getExpenseItemDate() {
        return donationItemDate;
    }

    public String getExpenseItemAmount() {
        return donationItemAmount;
    }
}
