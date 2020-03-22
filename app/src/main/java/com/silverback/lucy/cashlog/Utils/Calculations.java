package com.silverback.lucy.cashlog.Utils;

import com.silverback.lucy.cashlog.Model.POJO.Item;

import java.util.List;

public class Calculations {

    public static double getTotal(List<Item> items){
        double total = 0;

        for (Item item: items) {
            total = total + item.getAmount();
        }

        return total;
    }


    public static double getBalance(List<Item> items){
        double total = 0;

        for (Item item: items) {
            if(item.getType().equalsIgnoreCase("MoneyOut")){
                total = total - item.getAmount();
            }
            else{
                total = total + item.getAmount();
            }

        }

        return total;
    }

}
