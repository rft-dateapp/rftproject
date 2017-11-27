package com.baxi.android.rft_kocsmapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baxi.android.rft_kocsmapp.model.CustomerOpinion;
import com.baxi.android.rft_kocsmapp.model.Pub;

import java.util.List;

/**
 * Created by rendgazd on 2017. 11. 27..
 */

public class CustomOpinionArrayAdapter extends ArrayAdapter<CustomerOpinion> {

    private final Context context;
    private final List<CustomerOpinion> values;

    public CustomOpinionArrayAdapter(Context context, List<CustomerOpinion> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list_row_opinions, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.row_icon);
        TextView firstLine = (TextView) rowView.findViewById(R.id.customerNameTextView);
        TextView secondLine = (TextView) rowView.findViewById(R.id.opinionTextView);
        TextView rating = (TextView) rowView.findViewById(R.id.ratingTextView);

        imageView.setImageResource(R.mipmap.ic_launcher);
        firstLine.setText(values.get(position).getCustomerName());
        secondLine.setText(values.get(position).getOpinion());
        rating.setText(Double.toString(values.get(position).getRating()));


        return rowView;
    }
}
