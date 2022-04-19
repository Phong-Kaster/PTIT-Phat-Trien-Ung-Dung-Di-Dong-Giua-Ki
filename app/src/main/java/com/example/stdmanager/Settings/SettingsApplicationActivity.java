package com.example.stdmanager.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.example.stdmanager.R;

public class SettingsApplicationActivity extends AppCompatActivity {

    SwitchCompat switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_application);
        setControl();
        setEvent();
    }

    private void setControl()
    {
        switchButton = findViewById(R.id.darkModeSwitch);
    }

    private void setEvent()
    {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            switchButton.setChecked(true);
            setTheme(R.style.Theme_STDManager_Dark);
        } else {
            switchButton.setChecked(false);
            setTheme(R.style.Theme_STDManager);
        }

        /*
        * MODE_NIGHT_NO = light mode
        * MODE_NIGHT_YES = dark mode
        * */
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !switchButton.isChecked()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        });
    }
}