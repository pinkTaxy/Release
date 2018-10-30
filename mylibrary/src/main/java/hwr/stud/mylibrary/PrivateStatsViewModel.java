package hwr.stud.mylibrary;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class PrivateStatsViewModel extends ViewModel {
    private MutableLiveData<List<ExpenseListItem>> expenseItems;
    private MutableLiveData<List<DonationListItem>> donationItems;

    public LiveData<List<ExpenseListItem>> getExpenseItems() {
        if (expenseItems == null) {
            expenseItems = new MutableLiveData<List<ExpenseListItem>>();
            loadExpenseItems();
        }
        return expenseItems;
    }

    public LiveData<List<DonationListItem>> getDonationItems() {
        if (donationItems == null) {
            donationItems = new MutableLiveData<List<DonationListItem>>();
            loadExpenseItems();
        }
        return donationItems;
    }

    private void loadExpenseItems() {
        // Do an asynchronous operation to fetch ExpenseItems.
    }
}



