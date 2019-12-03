package com.example.ecomapp;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private long backPressedTime;
    private CardView cardView;
    RecyclerView mrecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mdatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //RecyclerView
        mrecyclerView = findViewById(R.id.recyclerView);

        mrecyclerView.setHasFixedSize(true);
        //set layout as LinearLayout
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //send Query to FirebaseDatabase
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mrecyclerView.setLayoutManager(gridLayoutManager);

        mfirebaseDatabase = FirebaseDatabase.getInstance();

        mdatabaseReference = mfirebaseDatabase.getReference("Research");
        mrecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void firebaseSearch (String searchText){
        Query firebaseSearchQuery = mdatabaseReference.orderByChild("title").startAt(searchText).endAt(searchText + "\uf8ff");
        FirebaseRecyclerAdapter<sample, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<sample, ViewHolder>(
                sample.class,
                R.layout.row,
                ViewHolder.class,
                firebaseSearchQuery

        ) {

            @Override
            protected void populateViewHolder(ViewHolder viewHolder, sample sample, int i) {
                viewHolder.setDetails(getApplicationContext(),sample.getTitle(),sample.getImage(),sample.getDescription(),sample.getGeneric(),sample.getType());
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup Child, int viewType) {
                ViewHolder viewHolder = super.onCreateViewHolder(Child,viewType);

                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //get data from firebase at the position clicked
                        String mPostTitle = getItem(position).getTitle();
                        String mPostDesc = getItem(position).getDescription();
                        String mImageView = getItem(position).getImage();

                        //pass this data to new activity
                        Intent intent  = new Intent(getApplicationContext(), ItemDetailsActivity.class);
                        intent.putExtra("image",mImageView);// put title
                        intent.putExtra("title",mPostTitle);// put description
                        intent.putExtra("description",mPostDesc);// put image Url
                        startActivity(intent);// startActivity
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        //TODO do your own implementaion on long item click

                    }


                });
                return viewHolder;
            }

        };
        mrecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {

        super.onStart();
        FirebaseRecyclerAdapter<sample, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<sample, ViewHolder>(
                sample.class,
                R.layout.row,
                ViewHolder.class,
                mdatabaseReference
        ) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, sample sample, int i) {
                viewHolder.setDetails(getApplicationContext(),sample.getTitle(),sample.getImage(),sample.getDescription(),sample.getGeneric(),sample.getType());
            }


            @Override
            public ViewHolder onCreateViewHolder(ViewGroup Child, int viewType) {
                ViewHolder viewHolder  = super.onCreateViewHolder(Child,viewType);

                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //get data from firebase at the position clicked
                        String mPostTitle = getItem(position).getTitle();
                        String mPostDesc = getItem(position).getDescription();
                        String mImageView = getItem(position).getImage();

                        //pass this data to new activity
                        Intent intent  = new Intent(getApplicationContext(), ItemDetailsActivity.class);
                        intent.putExtra("image",mImageView);// put title
                        intent.putExtra("title",mPostTitle);// put description
                        intent.putExtra("description",mPostDesc);// put image Url
                        startActivity(intent);// startActivity
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        //TODO do your own implementaion on long item click

                    }


                });
                return viewHolder;
            }
        };
        mrecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        moveTaskToBack(true);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        MenuItem item1 = menu.findItem(R.id.action_addcart);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (id == R.id.action_search){
            return true;
        }
        if (id == R.id.action_addcart) {
            startActivity(new Intent(getApplicationContext(), AddCartActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivitylogin();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            startActivityAccount();

        } else if (id == R.id.nav_slideshow) {
            startActivityAbout();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.action_settings) {
            startActivity();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    private boolean startActivity() {
        FirebaseAuth.getInstance().signOut();
        moveTaskToBack(true);
        finish();
        return true;
    }
    private boolean startActivityAbout() {
        FirebaseFirestore.getInstance();
        FirebaseDatabase.getInstance();
        FirebaseAuth.getInstance();
        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        finish();
        return true;

    }
    private boolean startActivityAccount() {
        FirebaseFirestore.getInstance();
        FirebaseAuth.getInstance();
        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
        finish();
        return true;

    }
    private boolean startActivitylogin() {
        FirebaseAuth.getInstance();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
        return true;

    }



}
