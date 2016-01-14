package com.example.android.muhta;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nir on 1/14/2016.
 */
public class CountryListArrayAdapter extends ArrayAdapter<CountrycodeActivity.Country> {

    private final List<CountrycodeActivity.Country> list;
    private final Activity context;

    static class ViewHolder {
        protected TextView name;
    }

    public CountryListArrayAdapter(Activity context, List<CountrycodeActivity.Country> list) {
        super(context, R.layout.activity_countrycode_row, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_countrycode_row, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(list.get(position).getName());
        return view;
    }
}