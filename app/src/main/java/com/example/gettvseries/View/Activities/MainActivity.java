package com.example.gettvseries.View.Activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gettvseries.R;
import com.example.gettvseries.View.Fragments.PopularMoviesFragment;
import com.example.gettvseries.View.Fragments.SearchByGenreFragment;
import com.example.gettvseries.View.Fragments.SearchFragment;
import com.example.gettvseries.View.Fragments.TopRatedMoviesFragment;
import com.example.gettvseries.View.Fragments.UpcomingMoviesFragment;
import com.example.gettvseries.View.Fragments.UserSettingsFragment;
import com.example.gettvseries.Firebase.ConfigFirebase;
import com.example.gettvseries.Utils.Permission;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // NavDrawer
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;



    private TextView mTextMessage;

    private SearchFragment searchFragment;
    private UserSettingsFragment userSettingsFragment;

    private FirebaseAuth authentication;

    private ImageButton imageButtonCamera, imageButtonGallery;
    private static final int CAMERA_SELECTION = 100;
    private static final int GALLERY_SELECTION = 200;
    private CircleImageView circleImageView;
    private StorageReference storageReference;
    private String userIdentifier;
    private EditText editProfileName;

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
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);


        toggle.syncState();



//        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        authentication = ConfigFirebase.getFirebaseAuthentication();


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("GetTVSeries()");
        setSupportActionBar(toolbar);

        Permission.validatePermissions(necessaryPermissions, this, 1);

//        homeFragment = new HomeFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frameContent, homeFragment);
//        transaction.commit();

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

                logout();
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
//
//            case R.id.nd_search_genres:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, SearchByGenreFragment.newInstance())
//                        .commit();
//                break;

                /***
                 * falta por o resto
                 **/

//            case R.id.nd_logout:
//                break;



        }

        return true;
    }
}
