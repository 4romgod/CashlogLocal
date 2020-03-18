package com.silverback.lucy.cashlog.Utils;

//THIS CLASS CONTAINS THINGS THAT NEVER CHANGE, AND REUSABLE WITHOUT CREATING OBJECTS

import android.content.res.Resources;

import com.silverback.lucy.cashlog.R;

public class Constants {

    public static final String moneyIn = Resources.getSystem().getString(R.string.type_money_in);
    public static final String moneyOut = Resources.getSystem().getString(R.string.type_money_out);


    public static final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public static final String[] weeks = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

}       //end class
