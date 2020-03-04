package com.silverback.lucy.cashlog.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;
import com.silverback.lucy.cashlog.Model.ObjectTemplate.MyDate;
import com.silverback.lucy.cashlog.R;
import com.silverback.lucy.cashlog.Utils.Constants;

import java.util.ArrayList;
import java.util.Date;


public class AdapterListView extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_DATE = 1;
    private static final int ITEM_VIEW_TYPE_COUNT = 2;      //how many view types to handle; date separator and items


    private Context mContext;
    int mResource;

    LayoutInflater inflater;

    ArrayList<Object> items;


    /**
     * <p>This adapter binds a custom view to the listview<br/>
     * Adapter holds list of items to set to the view
     * </p>
     *
     * @param context Context
     * @param resource The resources to be used as a view
     * @param items List of items to set in the listview
     */
    public AdapterListView(@NonNull Context context, int resource, @NonNull ArrayList<Object> items) {
        mContext = context;
        mResource = resource;

        addDateObjects(items);      //add date objects to list of items

        this.items = items;

        inflater = LayoutInflater.from(mContext);
        //inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }       //end AdapterListView()


    //adds the date objects to the list of items
    public void addDateObjects(ArrayList items){
        for (int i = 0; i < items.size(); i++) {
            if (i == 0) {
                Date date1 = new Date();
                items.add(0, new MyDate(date1.getYear(), date1.getMonth(), date1.getDate(), date1.getHours(), date1.getMinutes(), date1.getSeconds()));
            }
            if (i > 0) {

                //last object in the list
                Object lastObjectInList = items.get(i);

                if (lastObjectInList instanceof Item) {
                    Item lastItemInList = (Item) lastObjectInList;

                    Object prevObjectInList = items.get(i-1);
                    if(prevObjectInList instanceof Item){
                        items.add(i, lastItemInList.getDate());
                    }
                }
            }
        }

    }       //end addDateObjects()


    @Override
    public int getCount() {
        return items.size();
    }


    @Nullable
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override   //it now handles 2 view types: item, and date separator
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_COUNT;
    }


    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof Item){
            return TYPE_ITEM;
        }
        else{
            return TYPE_DATE;
        }
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        int rowType = getItemViewType(position);

        // First, let's create a new convertView if needed, of the specified row type
        if(convertView == null){
            switch (rowType){
                case TYPE_DATE: {
                    convertView = inflater.inflate(R.layout.layout_custom_list_view_month, parent, false);
                    break;
                }
                case TYPE_ITEM:{
                    convertView = inflater.inflate(mResource, parent, false);
                    break;
                }

            }       //end switch()

        }       //end if convertView


        // We can now fill the list item view with the appropriate data.
        if(rowType == TYPE_ITEM){

            Item item = (Item) getItem(position);

            //get the text views from the ITEM custom view layout we created
            TextView tvName = (TextView) convertView.findViewById(R.id.nameTv);
            final TextView tvAmount = (TextView) convertView.findViewById(R.id.amountTv);
            TextView tvDescription = (TextView) convertView.findViewById(R.id.decriptionTv);

            //set text views to the item information
            tvName.setText(item.getName());
            tvAmount.setText("R"+item.getAmount());
            tvDescription.setText(item.getDescription());

        }
        else if(rowType == TYPE_DATE){
            MyDate date = (MyDate) getItem(position);

            //get the text views from the DATE custom view layout we created
            TextView tvDate = (TextView) convertView.findViewById(R.id.dateTv);

            tvDate.setText(Constants.months[date.getMonth()] + " " + date.getYear());
        }


        return convertView;

    }       //end getView()




}       //end class()
