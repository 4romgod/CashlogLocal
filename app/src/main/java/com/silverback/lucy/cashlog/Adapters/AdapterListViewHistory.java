package com.silverback.lucy.cashlog.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.silverback.lucy.cashlog.Model.POJO.Item;
import com.silverback.lucy.cashlog.Model.POJO.MyDate;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Utils.Constants;

import java.util.ArrayList;


public class AdapterListViewHistory extends ArrayAdapter<Item> {

    private Context mContext;
    int mResource;


    public AdapterListViewHistory(@NonNull Context context, int resource, @NonNull ArrayList<Item> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }       //end AdapterListView()


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get the information
        String type = getItem(position).getType();
        String name = getItem(position).getName();
        float amount = getItem(position).getAmount();
        String description = getItem(position).getDescription();
        MyDate date = getItem(position).getDate();

        //create the item object with the information
        Item item = new Item(type, name, amount, description, date);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        //get the text views from the custom view layout we created
        TextView tvName = (TextView) convertView.findViewById(R.id.nameTv);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.amountTv);
        TextView tvDate = (TextView) convertView.findViewById(R.id.dateTv);

        //when money out item is added
        if(amount<0){
            tvAmount.setTextColor(Color.RED);
        }

        //set text views to the item information
        tvName.setText(name);
        tvAmount.setText("R"+amount);
        tvDate.setText(date.getDay() + " " + Constants.months[date.getMonth()] +" "+date.getYear()+" - "+date.getHours());

        return convertView;
    }       //end getView()



}       //end class()
