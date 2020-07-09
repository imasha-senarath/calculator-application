package com.imasha.ds.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView settingBtn;

    Switch aSwitch;
    SharedPreferences sharedPreferences;

    TextView creditBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences("CalculatorPreferences", Context.MODE_PRIVATE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settingBtn = findViewById(R.id.setting_btn);
        settingBtn.setVisibility(View.GONE);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        aSwitch = findViewById(R.id.switch1);
        checkNightMode();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SaveNightModeState("night");

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SaveNightModeState("light");
                }
            }
        });

        creditBtn = findViewById(R.id.credit_button);
        creditBtn.setPaintFlags(creditBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        creditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupCreditDialog();
            }
        });
    }

    private void PopupCreditDialog()
    {
        final Dialog creditDialog = new Dialog(this);
        creditDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        creditDialog.setContentView(R.layout.credit_dialog);
        creditDialog.setTitle("Credit Window");
        creditDialog.show();

        Button closeBtn = creditDialog.findViewById(R.id.credit_close_button);
        closeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                creditDialog.dismiss();
            }
        });
    }

    private void SaveNightModeState(String appTheme) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AppTheme", appTheme);
        editor.apply();
    }

    private void checkNightMode(){
        if(sharedPreferences.getString("AppTheme", "light").equals("night")){
            aSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            aSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
