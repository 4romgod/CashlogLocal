package com.silverback.lucy.cashlog.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.silverback.lucy.cashlog.R;

/**
 * The first activity the app opens. Introduction to the app
 */
public class ActivitySplash extends AppCompatActivity {

    private static final String TAG = "ActivitySplash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, "onCreate: Splash Activity started");

        //blink();

        Thread thread = new Thread(){

            @Override
            public void run() {
                Log.d(TAG, "run: method to run the thread");
                Log.d(TAG, "run: method to run the thread");
                try {
                    //time allocated to the splash
                    sleep(3000);        //appear for 5 seconds
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                finally {
                    //where app should go next
                    Intent mainIntent = new Intent(ActivitySplash.this, ActivityMain.class);
                    startActivity(mainIntent);
                }       //end finally()
            }       //end run()
        };
        thread.start();

    }       //end onCreate()


    //make a blinking effect
    public void blink(){
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.screen);
        ObjectAnimator animator = ObjectAnimator.ofInt(layout, "backgroundColor",
                Color.rgb(82, 143, 118), Color.rgb(17, 128, 14), Color.rgb(82, 143, 118));

        animator.setDuration(1000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }       //end blink()


    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }       //end onPause()


}       //end class
