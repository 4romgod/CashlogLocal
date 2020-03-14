package com.silverback.lucy.cashlog.Utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class UI {

    /**
     * @param fragment     the fragment to be loaded
     */
    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment, int container){
        if(fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(container, fragment);
            transaction.addToBackStack(""+fragment.toString());
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }
    }       //end loadFragment()


    /**
     * Hides the soft keyboard from the user
     * @param view  The touched view
     * @param context   context of the application
     */
    public static void hideKeyboard(View view, Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }       //end hideKeyboard()


    /**
     * Pops the backstack and returns the user to previous page
     * @param fragmentManager
     */
    public static void popBackStack(FragmentManager fragmentManager){
        if(fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStack();
        }
    }       //end popBackStack()


    public static void progressBarGone(ProgressBar progressBar){
        progressBar.setVisibility(View.GONE);
    }

    public static void progressBarVisible(ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
    }

}       //end class
