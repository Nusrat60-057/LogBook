package com.example.logbook2023_3_60_057;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class ActivityAdapter extends ArrayAdapter<ActivityList> {

    private final Context context;
    private final ArrayList<ActivityList> values;

    public ActivityAdapter(@NonNull Context context, @NonNull ArrayList<ActivityList> items) {
        super(context, -1, items);
        this.context = context;
        this.values = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_address, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvPhone = convertView.findViewById(R.id.tvPhone);
        TextView tvDob = convertView.findViewById(R.id.tvDob);

        ActivityList e = values.get(position);
        tvName.setText(e.name);
        tvPhone.setText(e.phone);
        tvDob.setText(e.dob);

        return convertView;
    }
}
