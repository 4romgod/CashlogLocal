package com.silverback.lucy.cashlog.Utils;

import com.silverback.lucy.cashlog.Model.ObjectTemplate.Item;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sorting {

    public ArrayList sortedList(ArrayList items){

        //sort by year
        Comparator<Item> comYear = new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getDate().getYear()-o2.getDate().getYear();
            }
        };
        Collections.sort(items, comYear);


        //sort by months
        Comparator<Item> comMonth = new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getDate().getMonth()-o2.getDate().getMonth();
            }
        };
        Collections.sort(items, comMonth);


        //sort by day
        Comparator<Item> comDay = new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getDate().getDay()-o2.getDate().getDay();
            }
        };
        Collections.sort(items, comDay);


        //sort by hours
        Comparator<Item> comHours = new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getDate().getHours()-o2.getDate().getHours();
            }
        };
        Collections.sort(items, comHours);


        //sort by minutes
        Comparator<Item> comMin = new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getDate().getMin()-o2.getDate().getMin();
            }
        };
        Collections.sort(items, comMin);


        //sort by seconds
        Comparator<Item> comSec = new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getDate().getSec()-o2.getDate().getSec();
            }
        };
        Collections.sort(items, comSec);


        return items;
    }       //end sort()



}       //end class
