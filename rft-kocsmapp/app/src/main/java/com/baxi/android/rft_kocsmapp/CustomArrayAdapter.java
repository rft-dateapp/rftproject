package com.baxi.android.rft_kocsmapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.baxi.android.rft_kocsmapp.model.Pub;

import java.util.List;

/**
 * Created by rendgazd on 2017. 11. 23..
 */

public class CustomArrayAdapter extends ArrayAdapter<Pub> {

    private final Context context;
    private final List<Pub> values;

    public CustomArrayAdapter(Context context, List<Pub> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list_row, parent, false);
        TextView firstLine = (TextView) rowView.findViewById(R.id.nameTextView);
        TextView secondLine = (TextView) rowView.findViewById(R.id.addressTextView);

        firstLine.setText(values.get(position).getName());
        secondLine.setText("Átlagos felhasználói értékelés: " + Double.toString(values.get(position).getCustomerOverallRatings()));


        return rowView;
    }
}
