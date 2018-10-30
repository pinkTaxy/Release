package hwr.stud.mylibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExpensesListAdapter extends ArrayAdapter<ExpenseListItem>{

    int viewItemLayout;
    Context context;

    public ExpensesListAdapter(Context context, int layout, ArrayList<ExpenseListItem> expenseArray) {

        super(context, layout, expenseArray);

        this.context = context;
        this.viewItemLayout = layout;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {

        View view = contentView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(viewItemLayout, null);
        }
        ExpenseListItem expenseListItem = getItem(position);
        if (expenseListItem != null) {

            TextView expenseAmount = (TextView) view.findViewById(R.id.expenseAmount);
            TextView expenseDescription = (TextView) view.findViewById(R.id.expenseDescription);
            TextView expenseDate = (TextView) view.findViewById(R.id.expenseDate);

            expenseAmount.setText(expenseListItem.getExpenseItemAmount());
            expenseDate.setText(expenseListItem.getExpenseItemDate());
            expenseDescription.setText(expenseListItem.getExpenseItemDescription());
        }

        return view;
    }



}
