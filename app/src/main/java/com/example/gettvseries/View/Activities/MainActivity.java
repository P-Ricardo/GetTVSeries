package com.example.gettvseries.View.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gettvseries.R;
import com.example.gettvseries.Utils.FirebaseUsers;
import com.example.gettvseries.View.Fragments.PopularMoviesFragment;
import com.example.gettvseries.View.Fragments.SearchByGenreFragment;
import com.example.gettvseries.View.Fragments.SearchFragment;
import com.example.gettvseries.View.Fragments.TopRatedMoviesFragment;
import com.example.gettvseries.View.Fragments.UpcomingMoviesFragment;
import com.example.gettvseries.View.Fragments.UserSettingsFragment;
import com.example.gettvseries.Firebase.ConfigFirebase;
import com.example.gettvseries.Utils.Permission;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // NavDrawer
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    private TextView mTextMessage;

    private SearchFragment searchFragment;
    private UserSettingsFragment userSettingsFragment;

    private FirebaseAuth authentication;

    private CircleImageView circle_profile_image;
    private StorageReference storageReference;
    private String userIdentifier;
    private TextView textProfileName;
    private TextView textProfileEmail;
    private PopularMoviesFragment popularMoviesFragment;
    private FirebaseUser user;
    private View mview;

    private String[] necessaryPermissions = new String[]{

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        circle_profile_image = navigationView.getHeaderView(0).findViewById(R.id.nav_circle_profile_image);
        textProfileName = navigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
        textProfileEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_user_email);
        user = FirebaseUsers.getCurrentUser();
        Uri url = user.getPhotoUrl();

        if (url != null) {

            Glide.with(getApplicationContext())
                    .load(url)
                    .into(circle_profile_image);
        } else {
            circle_profile_image.setImageResource(R.drawable.standard);
        }

        textProfileName.setText(user.getDisplayName());
        textProfileEmail.setText(user.getEmail());

        mTextMessage = findViewById(R.id.message);

        authentication = ConfigFirebase.getFirebaseAuthentication();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        Permission.validatePermissions(necessaryPermissions, this, 1);

        popularMoviesFragment = new PopularMoviesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, popularMoviesFragment);
        transaction.commit();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.exit_app);
            alertDialogBuilder
                    .setMessage(R.string.exit_message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            })

                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu_options, menu);

//        MenuItem searchItem = menu.findItem(R.id.menu_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_search) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, SearchFragment.newInstance())
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout() {

        try {
            authentication.signOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nd_logout) {
            logout();
            finish();
        }

        switch (menuItem.getItemId()) {

            case R.id.nd_popular:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, PopularMoviesFragment.newInstance())
                        .commit();
                break;

            case R.id.nd_top_rated:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, TopRatedMoviesFragment.newInstance())
                        .commit();
                break;

            case R.id.nd_upcoming:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, UpcomingMoviesFragment.newInstance())
                        .commit();
                break;

            case R.id.nd_search_genres:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, SearchByGenreFragment.newInstance())
                        .commit();
                break;

            case R.id.nd_profile:

                userSettingsFragment = new UserSettingsFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, userSettingsFragment)
                        .commit();
                break;

            case R.id.nd_favorites:
                Toast.makeText(MainActivity.this, "Item selected", Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawers();

        return true;
    }

}
