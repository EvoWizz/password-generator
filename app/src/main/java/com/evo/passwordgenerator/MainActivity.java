package com.evo.passwordgenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText charNumber;
    private FloatingActionButton generateButton;
    private Button pButton;
    private int  number=0;
    private TextView generatedPassword;
    private String passwToSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        final View clayout = (View) findViewById(R.id.clayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        charNumber = (EditText) findViewById(R.id.charNumber);
        generatedPassword = (EditText) findViewById(R.id.generatedPassword);
        generatedPassword.setMovementMethod(new ScrollingMovementMethod());
        generateButton = (FloatingActionButton) findViewById(R.id.generateButton);


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

//        generate button in use get input from charNumber(EditText) and use Passw.java, charNumber()

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (charNumber.getText().length() > 0) {
                    number = Integer.parseInt(charNumber.getText().toString());
                    Passw pword = new Passw(number);
                    passwToSave = pword.randomChar();
                    generatedPassword.setText(passwToSave);
                    number = 0;
                    charNumber.setText("");

                } else {
                    Snackbar snackbarNC = Snackbar.make(clayout, "No network connection.", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            });
                    snackbarNC.setActionTextColor(getResources().getColor(R.color.colorPrimary1));
                            snackbarNC.show();

                }
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.theme_changer) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

