package com.example.gettvseries.View.Activities;

import android.Manifest;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

import org.w3c.dom.Text;

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

    private ImageButton imageButtonCamera, imageButtonGallery;
    private static final int CAMERA_SELECTION = 100;
    private static final int GALLERY_SELECTION = 200;
    private CircleImageView circle_profile_image;
    private StorageReference storageReference;
    private String userIdentifier;
    private TextView textProfileName;
    private PopularMoviesFragment popularMoviesFragment;
    private FirebaseUser user;

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

        /*toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();*/

        /*
        circle_profile_image = findViewById(R.id.nav_circle_profile_image);
        textProfileName = findViewById(R.id.nav_user_name);
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
        */


//        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        authentication = ConfigFirebase.getFirebaseAuthentication();


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("GetTVSeries()");
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
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu_options, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case R.id.menuLogout:

                //logout();
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(){

        try {
            authentication.signOut();
        }catch (Exception e){

            e.printStackTrace();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.nd_popular:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, PopularMoviesFragment.newInstance())
                        .commit();
                drawerLayout.closeDrawers();
                break;

            case R.id.nd_top_rated:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, TopRatedMoviesFragment.newInstance())
                        .commit();
                drawerLayout.closeDrawers();
                break;

            case R.id.nd_upcoming:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, UpcomingMoviesFragment.newInstance())
                        .commit();
                drawerLayout.closeDrawers();
                break;

            case R.id.nd_search_genres:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, SearchByGenreFragment.newInstance())
                        .commit();
                drawerLayout.closeDrawers();
                break;

            case R.id.nd_profile:

                userSettingsFragment = new UserSettingsFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, userSettingsFragment )
                        .commit();
                drawerLayout.closeDrawers();
                //Toast.makeText(MainActivity.this,"Item selected", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nd_favorites:
                drawerLayout.closeDrawers();
                Toast.makeText(MainActivity.this,"Item selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nd_logout:

                logout();
                finish();
                break;

        }

        return true;
    }
}
