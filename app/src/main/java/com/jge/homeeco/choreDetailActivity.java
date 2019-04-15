package com.jge.homeeco;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

/**
 * An activity representing a single chore detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link choreListActivity}.
 */
public class choreDetailActivity extends AppCompatActivity {

    private TextView textViewDescription;
    private EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chore_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewDescription = findViewById(R.id.chore_detail);
                editTextDescription = findViewById(R.id.editable_chore_detail);
                if (textViewDescription.getVisibility() == textViewDescription.VISIBLE){
                    editTextDescription.setVisibility(View.VISIBLE);
                    editTextDescription.setText(textViewDescription.getText());
                    textViewDescription.setVisibility(View.GONE);
                }else{
                    textViewDescription.setVisibility(View.VISIBLE);
                    textViewDescription.setText(editTextDescription.getText());
                    editTextDescription.setVisibility(View.GONE);
                    Snackbar.make(view, "Text Updated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(choreDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(choreDetailFragment.ARG_ITEM_ID));
            arguments.putString("choreTitle", getIntent().getStringExtra("choreTitle"));
            arguments.putBundle("bundleChore", getIntent().getBundleExtra("bundleChore"));
            choreDetailFragment fragment = new choreDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.chore_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, choreListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
