package com.silverback.lucy.cashlog.Utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class Validation {

    /**
     * Returns true if editText is empty
     * @param editText     EditText to be validated
     */
    public static boolean isEmpty(EditText editText){
        if(editText.getText().toString().equalsIgnoreCase(null)||editText.getText().toString().equalsIgnoreCase("")){
            editText.setBackgroundColor(Color.RED);
            return true;
        }
        else {
            return false;
        }
    }      //end isEmpty()

    public void changeBG(final EditText editText){

        final Drawable originalBG = editText.getBackground();

        if(isEmpty(editText)){
            editText.setBackgroundColor(Color.RED);
        }

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                editText.setBackground(originalBG);
                return false;
            }
        });

    }       //end changeBG()


}       //end class
