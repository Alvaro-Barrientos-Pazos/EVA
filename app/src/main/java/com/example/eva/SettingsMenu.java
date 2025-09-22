package com.example.eva;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsMenu extends AppCompatActivity {

    final String SETTINGS_FILENAME = "settings.xml";

    private EditText nameField;
    private EditText emailField;
    private EditText ageField;
    private Button saveButton;
    private CheckBox isAdvertising;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.settings_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameField     = findViewById(R.id.nameField);
        emailField    = findViewById(R.id.emailField);
        ageField      = findViewById(R.id.ageField);
        saveButton    = findViewById(R.id.saveButton);
        isAdvertising = findViewById(R.id.advertisingCheckBox);

        loadSettings();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
                Toast.makeText(SettingsMenu.this, R.string.settingsSavedMsg,Toast.LENGTH_SHORT).show();
            }
        });
    }


    protected boolean loadSettings(){
        SharedPreferences settingsFile = getSharedPreferences(SETTINGS_FILENAME, MODE_PRIVATE);

        if (settingsFile.contains("nick")) {
            String name = settingsFile.getString("nick", "");
            String email = settingsFile.getString("email", "");
            int age = settingsFile.getInt("age", 0);
            boolean advertising = settingsFile.getBoolean("advertising", false);

            nameField.setText(name);
            emailField.setText(email);
            ageField.setText(age > 0 ? String.valueOf(age) : "");
            isAdvertising.setChecked(advertising);

            return true;
        }

        return false;
    }


    protected void saveSettings(){
        String name = nameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        int age = Integer.parseInt(ageField.getText().toString());

        SharedPreferences settingsFile = getSharedPreferences (SETTINGS_FILENAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = settingsFile.edit();
        editor.putString("nick",name);
        editor.putString("email",email);
        editor.putInt("age",age);
        editor.putBoolean("advertising",isAdvertising.isChecked());

        editor.apply();
    }

}