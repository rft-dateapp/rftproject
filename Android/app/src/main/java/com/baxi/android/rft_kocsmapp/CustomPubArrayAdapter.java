package com.baxi.android.rft_kocsmapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baxi.android.rft_kocsmapp.model.Pub;

import java.util.List;

/**
 * Created by rendgazd on 2017. 11. 23..
 */

public class CustomPubArrayAdapter extends ArrayAdapter<Pub> {

    private final Context context;
    private final List<Pub> values;

    public CustomPubArrayAdapter(Context context, List<Pub> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list_row, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.row_icon);
        TextView firstLine = (TextView) rowView.findViewById(R.id.pubNameTextView);
        TextView secondLine = (TextView) rowView.findViewById(R.id.opinionTextView);

        imageView.setImageResource(R.mipmap.ic_launcher);
        firstLine.setText(values.get(position).getName());
        secondLine.setText(String.format("Átlagos felhasználói értékelés: %.1f", values.get(position).getCustomerOverallRatings()));


        return rowView;
    }
}
