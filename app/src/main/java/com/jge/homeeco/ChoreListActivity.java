package com.jge.homeeco;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jge.homeeco.Adapters.ChoreAdapter;
import com.jge.homeeco.Adapters.PersonAdapter;
import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.Models.DarkWeather;
import com.jge.homeeco.Models.Person;
import com.jge.homeeco.Models.Prize;
import com.jge.homeeco.ViewModels.ChoreViewModel;
import com.jge.homeeco.ViewModels.PersonViewModel;
import com.jge.homeeco.dummy.DummyContent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Chores. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ChoreDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ChoreListActivity extends AppCompatActivity implements ListItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private AppDatabase mChoreDatabase;
    private ArrayList<Person> people;
    public static DarkWeather darkWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        /*****Navigation Drawer*****/
        DrawerLayout drawer = findViewById(R.id.drawer_layoutA);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /***Navigation Drawer*****/
        String choreManagerName = getIntent().getStringExtra("choreMasterName");

        if(choreManagerName != null){
            toolbar.setTitle(choreManagerName);
            View headerView = navigationView.getHeaderView(0);
            TextView tv = navigationView.findViewById(R.id.chore_master_name);
            tv.setText(choreManagerName);
        }

        LinearLayout ll = navigationView.findViewById(R.id.add_person);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.createPersonAlertDialog(
                        "Add Person",
                        ChoreListActivity.this,
                        PersonDetailActivity.class,
                        "Create Person",
                        "Cancel",
                        "Person creation cancelled",
                        "Should Create Person",
                        "You must add a person's name!" ).show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        if (findViewById(R.id.chore_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder alertDialog = Utilities.createAlertDialog(
                        "New chore",
                        ChoreListActivity.this,
                        ChoreDetailActivity.class,
                        "Create Chore",
                        "Cancel",
                        "Chore Cancelled",
                        "Should create chore title",
                        "You must make a chore title!");
                alertDialog.show();
            }
        });
        new NetworkDarkWeatherCall().execute(Utilities.BASE_URL);


        //RecyclerView personList = findViewById(R.id.chore_list);
        //setUpPersonAdapterAndRecyclerView(personList);


    }

    @Override
    protected void onResume() {
        final ChoreAdapter  choreAdapter = new ChoreAdapter(this, mTwoPane);
        mChoreDatabase = AppDatabase.getInstance(this);
        ChoreViewModel choreViewModel = ViewModelProviders.of(this).get(ChoreViewModel.class);
        choreViewModel.getChores().observe(this, new Observer<List<Chore>>() {
            @Override
            public void onChanged(@Nullable List<Chore> chores) {
                ArrayList<Chore> choreArrayList = new ArrayList<>(chores);
                choreAdapter.setChoreData(choreArrayList, getApplicationContext());
            }
        });
        RecyclerView recyclerView = findViewById(R.id.chore_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        choreAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(choreAdapter);

        RecyclerView personList = findViewById(R.id.person_list);
        setUpPersonAdapterAndRecyclerView(personList);
        super.onResume();
    }

    private void setUpPersonAdapterAndRecyclerView(final RecyclerView recyclerView){
        final PersonAdapter personAdapter = new PersonAdapter(this);
        mChoreDatabase = AppDatabase.getInstance(this);
        PersonViewModel personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);

        personViewModel.getPersons().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(@Nullable List<Person> people) {
                ArrayList<Person> personArrayList = new ArrayList<>(people);
                personAdapter.setPersonData(personArrayList);
                people = personArrayList;
                personAdapter.notifyDataSetChanged();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        personAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(personAdapter);
    }

    @Override
    public void onListItemClick(Chore choreIndexClicked) {
        Bundle b = new Bundle();
        b.putParcelable("bundleChore", choreIndexClicked);

        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putBundle("bundleChore", b);
            ChoreDetailFragment fragment = new ChoreDetailFragment();
            fragment.setArguments(arguments);
            this.getSupportFragmentManager().beginTransaction().replace(R.id.chore_detail_container, fragment).commit();
        } else {
            Intent intent = new Intent(this, ChoreDetailActivity.class);

            intent.putExtra("bundleChore",b);
            Toast.makeText(this,""+choreIndexClicked.getTitle(), Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    @Override
    public void onListItemClick(Person personIndexClicked) {
        Intent intent = new Intent(this, PersonDetailActivity.class);
        intent.putExtra("person",personIndexClicked);
        startActivity(intent);
    }

    @Override
    public void onListItemClick(Prize prizeItemClicked) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.add_person) {


        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layoutA);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private class NetworkDarkWeatherCall extends AsyncTask<String, Integer, JSONObject> {
        public Gson gson;

        @Override
        protected JSONObject doInBackground(String... strings) {
            String response = null;
            JSONObject jsonObject = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = Utilities.convertStreamToString(in);
                jsonObject= new JSONObject(response);
            } catch (MalformedURLException e) {
                Log.e(Utilities.TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(Utilities.TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(Utilities.TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(Utilities.TAG, "Exception: " + e.getMessage());
            }
            return jsonObject;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
            JSONObject jObj = null;
            try {
                jObj = jsonObject.getJSONObject("currently");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            darkWeather = gson.fromJson(jObj.toString(), DarkWeather.class);
            Log.e("TAG: ", darkWeather.getIcon());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layoutA);
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chore_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this,"Should navigate to settings",Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_weather) {
            Toast.makeText(this,"The current temperature is: "+Math.round(darkWeather.getTemperature())+" degrees Fahrenheit",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ChoreListActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ChoreDetailFragment.ARG_ITEM_ID, item.id);
                    ChoreDetailFragment fragment = new ChoreDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.chore_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ChoreDetailActivity.class);
                    intent.putExtra(ChoreDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ChoreListActivity parent,
                                      List<DummyContent.DummyItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chore_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
