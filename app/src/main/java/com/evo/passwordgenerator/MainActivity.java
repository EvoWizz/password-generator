package com.evo.passwordgenerator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.internal.ThemeSingleton;


public class MainActivity extends AppCompatActivity  {

    private Context context;
    private EditText charNumber;
    private FloatingActionButton generateButton;
    private Button clipbutton;
    private Button clearbutton;
    private int  number=0;
    private TextView generatedPassword;
    private String passwToSave;
    private static final String PRIVATE_PREF = "myapp";
    private static final String VERSION_KEY = "version_number";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        final View clayout = findViewById(R.id.clayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu);
        setSupportActionBar(toolbar);

        charNumber = (EditText) findViewById(R.id.charNumber);
        generatedPassword = (EditText) findViewById(R.id.generatedPassword);
        generatedPassword.setMovementMethod(new ScrollingMovementMethod());
        generateButton = (FloatingActionButton) findViewById(R.id.generateButton);
        clipbutton = (Button) findViewById(R.id.clipboard_button);
        clearbutton = (Button) findViewById(R.id.clear_button);
        context = getApplicationContext();


        generatedPassword.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        init();
        //        generate button in use get input from charNumber(EditText) and use Passw.java, charNumber()

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (charNumber.getText().length() > 0) {
                    number = Integer.parseInt(charNumber.getText().toString());
                    PassAlpha pword = new PassAlpha(number);
                    passwToSave = pword.randomChar(context);
                    generatedPassword.setText(passwToSave);
                    number = 0;

                } else {
                    Snackbar snackbarNC = Snackbar.make(clayout, getString(R.string.valuesnackbarnc), Snackbar.LENGTH_LONG)
                            .setAction(android.R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            });
                    snackbarNC.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbarNC.show();

                }
            }
        });


        clipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(generatedPassword.getText().length() > 0){
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Text Label", generatedPassword.getText().toString());
                clipboard.setPrimaryClip(clip);
                    Snackbar snackbarCB = Snackbar.make(clayout, getString(R.string.clipboard), Snackbar.LENGTH_LONG)
                            .setAction(android.R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            });
                    snackbarCB.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbarCB.show();
                } else {
                    Snackbar snackbarCBf = Snackbar.make(clayout, getString(R.string.clipboard_empty), Snackbar.LENGTH_LONG)
                            .setAction(android.R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            });
                    snackbarCBf.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbarCBf.show();
                }
            }
        });

        clearbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (charNumber.getText().length() > 0 || generatedPassword.getText().length() > 0) {
                    charNumber.setText("");
                    generatedPassword.setText("");

                } else {
                    Snackbar snackbarCB = Snackbar.make(clayout, getString(R.string.valuesnackbarcb), Snackbar.LENGTH_LONG)
                            .setAction(android.R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            });
                    snackbarCB.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    snackbarCB.show();

                }
            }
        });


    }


    private void init() {
        SharedPreferences sharedPref = getSharedPreferences(PRIVATE_PREF, Context.MODE_PRIVATE);
        int currentVersionNumber = 0;
        int savedVersionNumber = sharedPref.getInt(VERSION_KEY, 0);

        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            currentVersionNumber = pi.versionCode;
        } catch (Exception e) {}

        if (currentVersionNumber > savedVersionNumber) {
            showChangelog();

            SharedPreferences.Editor editor   = sharedPref.edit();

            editor.putInt(VERSION_KEY, currentVersionNumber);
            editor.commit();
        }
    }


    public void showChangelog() {
        int accentColor = ThemeSingleton.get().widgetColor;
        if (accentColor == 0)
            accentColor = ContextCompat.getColor(this, R.color.colorAccent);
        ChangelogDialog.create(false, accentColor)
                .show(getSupportFragmentManager(), "changelog");
    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent about = new Intent(this, AboutActivity.class);
                startActivity(about);
                return true;
            case R.id.reporter:
                Intent reporter = new Intent(this, GHReporter.class);
                startActivity(reporter);
                return true;
            case R.id.activitylauncher:
                Intent alauncher = new Intent(this, MainActivityTest.class);
                startActivity(alauncher);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

