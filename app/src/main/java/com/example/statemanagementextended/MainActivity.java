package com.example.statemanagementextended;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import androidx.lifecycle.ViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;


public class MainActivity extends AppCompatActivity {

    private TextView countTextView;
    private EditText userInputField;
    private CheckBox toggleCheckbox;
    private TextView hiddenTextView;
    private Switch nightModeSwitch;
    private boolean isNightMode;
    private int count;

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button incrementButton = findViewById(R.id.buttonCount);
        incrementButton.setOnClickListener(view -> { // Ustawienie akcji po kliknięciu
            count++;
            countTextView.setText("Licznik: " + count);
        });

        initializeViews();

        setupNightMode();

        setupCheckboxVisibility();

    }

    private void initializeViews() {

        countTextView = findViewById(R.id.CountinView);
        userInputField = findViewById(R.id.input);
        Button incrementButton = findViewById(R.id.buttonCount);
        nightModeSwitch = findViewById(R.id.Switch);
        toggleCheckbox = findViewById(R.id.checkbox);
        hiddenTextView = findViewById(R.id.invisible);
    }

    private void setupNightMode() {
        preferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        isNightMode = preferences.getBoolean("night", false);

        nightModeSwitch.setChecked(isNightMode);
        setNightMode(isNightMode); // Ustawienie trybu nocnego

        nightModeSwitch.setOnClickListener(view -> {
            isNightMode = !isNightMode;
            setNightMode(isNightMode);
            preferencesEditor = preferences.edit();
            preferencesEditor.putBoolean("night", isNightMode);
            preferencesEditor.apply();
        });
    }

    private void setNightMode(boolean nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void setupCheckboxVisibility() {
        hiddenTextView.setVisibility(View.INVISIBLE);
        boolean isTextVisible = preferences.getBoolean("invisibleTextVisible", false); // Sprawdzenie widoczności tekstu
        hiddenTextView.setVisibility(isTextVisible ? View.VISIBLE : View.INVISIBLE); // Ustawienie widoczności
        toggleCheckbox.setChecked(isTextVisible);

        toggleCheckbox.setOnClickListener(view -> {
            hiddenTextView.setVisibility(toggleCheckbox.isChecked() ? View.VISIBLE : View.INVISIBLE);
            preferencesEditor = preferences.edit();
            preferencesEditor.putBoolean("invisibleTextVisible", toggleCheckbox.isChecked()); // Zapisanie stanu checkboxa
            preferencesEditor.apply();
        });
    }
}
