package com.jge.homeeco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class OnboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final EditText editText = findViewById(R.id.editText);
        FloatingActionButton FAB = findViewById(R.id.fab);

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ChoreListActivity.class);
                i.putExtra("choreMasterName",editText.getText().toString());
                startActivity(i);
            }
        });

    }
}
