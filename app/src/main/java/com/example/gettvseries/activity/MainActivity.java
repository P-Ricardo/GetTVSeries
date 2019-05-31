package com.example.gettvseries.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gettvseries.HomeFragment;
import com.example.gettvseries.MyListFragment;
import com.example.gettvseries.R;
import com.example.gettvseries.SearchFragment;
import com.example.gettvseries.UserSettingsFragment;
import com.example.gettvseries.config.ConfigFirebase;
import com.example.gettvseries.helper.FirebaseUsers;
import com.example.gettvseries.helper.Permission;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    // NavDrawer
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;



    private TextView mTextMessage;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private UserSettingsFragment userSettingsFragment;
    private MyListFragment myListFragment;
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


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    homeFragment = new HomeFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameContent, homeFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_search:

                    searchFragment = new SearchFragment();
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.replace(R.id.frameContent, searchFragment);
                    transaction1.commit();
                    return true;
                case R.id.navigation_mylist:

                    myListFragment = new MyListFragment();
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.frameContent, myListFragment);
                    transaction2.commit();
                    return true;
                case R.id.navigation_settings:


                    userSettingsFragment = new UserSettingsFragment();
                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                    transaction3.replace(R.id.frameContent, userSettingsFragment);
                    transaction3.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        authentication = ConfigFirebase.getFirebaseAuthentication();


        Toolbar toolbar = findViewById(R.id.mainToolbar);
        toolbar.setTitle("GetTVSeries()");
        setSupportActionBar(toolbar);

        Permission.validatePermissions(necessaryPermissions, this, 1);

        homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameContent, homeFragment);
        transaction.commit();



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


}
