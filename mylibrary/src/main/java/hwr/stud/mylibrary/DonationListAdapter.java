package hwr.stud.mylibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DonationListAdapter extends ArrayAdapter<DonationListItem> {

    int viewItemLayout;
    Context context;

    public DonationListAdapter(Context context, int layout, ArrayList<DonationListItem> donationArray) {

        super(context, layout, donationArray);

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
        DonationListItem donationListItem = getItem(position);
        if (donationListItem != null) {

            TextView donationAmount = (TextView) view.findViewById(R.id.donationAmount);
            TextView donationDate = (TextView) view.findViewById(R.id.donationDate);

            donationAmount.setText(donationListItem.getExpenseItemAmount());
            donationDate.setText(donationListItem.getExpenseItemDate());
        }

        return view;
    }


}
