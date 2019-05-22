package com.android.project.chefschoice.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.android.project.chefschoice.R;

public class SplashActivity extends AppCompatActivity {
    Thread background;
    private boolean stop = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        background = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5 * 1000);
                } catch (InterruptedException e) {

                } finally {
                    if (!stop) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);

                        finish();
                    } else
                        finish();
                }
            }
        };
        background.start();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            if (background.isAlive()) {
                this.stop = true;
                Toast.makeText(this, "Quitting the application...", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }

    /*
     *
     *
     * TODO
     *
     *
     *
     * */

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelloTextView = findViewById(R.id.hello_textview);
        // restore saved instance state (the text color)
        if (savedInstanceState != null) {
            mHelloTextView.setTextColor(savedInstanceState.getInt("color"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save the current text color
        outState.putInt("color", mHelloTextView.getCurrentTextColor());
    }*/


}

