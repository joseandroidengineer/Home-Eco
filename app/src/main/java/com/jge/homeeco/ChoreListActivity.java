package com.jge.homeeco;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jge.homeeco.Adapters.ChoreAdapter;
import com.jge.homeeco.Database.AppDatabase;
import com.jge.homeeco.Models.Chore;
import com.jge.homeeco.dummy.DummyContent;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
            TextView tv = headerView.findViewById(R.id.chore_master_name);
            tv.setText(choreManagerName);
        }

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
                        "Create Chore",
                        "Cancel",
                        "Chore Cancelled",
                        "Should create chore title",
                        "You must make a chore title!");
                alertDialog.show();
            }
        });
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
        super.onResume();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
        recyclerView.setAdapter(new ChoreAdapter(this, mTwoPane));
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
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
