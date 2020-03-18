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
import java.util.List;


public class AdapterListViewHistory extends ArrayAdapter<Item> {

    private Context mContext;
    int mResource;

    LayoutInflater inflater;

    List<Item> items = new ArrayList<>();

    public AdapterListViewHistory(@NonNull Context context, int resource) {
        super(context, resource);
        mContext = context;
        mResource = resource;

        inflater = LayoutInflater.from(mContext);
        //inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }       //end AdapterListView()


    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return items.size();
    }


    @Nullable
    @Override
    public Item getItem(int position) {
        return items.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(mResource, parent, false);

        Item item = (Item) getItem(position);

        //get the text views from the custom view layout we created
        TextView tvName = (TextView) convertView.findViewById(R.id.nameTv);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.amountTv);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.descriptionTv);

        //when money out item is added
        if(item.getAmount()<0){
            tvAmount.setTextColor(Color.RED);
        }

        //set text views to the item information
        tvName.setText(item.getName());
        tvAmount.setText("R"+item.getAmount());
        tvDescription.setText(item.getDescription());

        return convertView;
    }       //end getView()



}       //end class()
