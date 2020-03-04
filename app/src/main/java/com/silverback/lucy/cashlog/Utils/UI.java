package com.silverback.lucy.cashlog.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import androidx.fragment.app.FragmentManager;

public class UI {


    public static void hideKeyboard(View view, Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }       //end hideKeyboard()

    public static void popBackStack(FragmentManager fragmentManager){
        fragmentManager.popBackStack();
    }


    public static void progressBarGone(ProgressBar progressBar){
        progressBar.setVisibility(View.GONE);
    }

    public static void progressBarVisible(ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
    }

}       //end class
