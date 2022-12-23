package com.example.patrollingapp.PatrolOfficer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.patrollingapp.AcquistsLocationFragment;
import com.example.patrollingapp.AddAcquistsFragment;
import com.example.patrollingapp.AdminDashboard;
import com.example.patrollingapp.AdminDashboardFragment;
import com.example.patrollingapp.CompletedFragment;
import com.example.patrollingapp.InformFragment;
import com.example.patrollingapp.Login;
import com.example.patrollingapp.PatrolFragment;
import com.example.patrollingapp.R;
import com.example.patrollingapp.VisitedFragmentAdmin;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;

    String name;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ImageView menuicon, startupicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //HookS
        menuicon = findViewById(R.id.menu_icon);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        name = getIntent().getStringExtra("name");

        navigationDrawer();

    }

    //Navigation Drawer Functions
    private void navigationDrawer() {

        //Navigation Drawer
        navigationView.bringToFront();
        ;
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuicon.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        item.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (id) {
            case R.id.nav_home:
                replaceFragment(new PatrolOfficerDashboardFragment());
                break;
            case R.id.nav_newly_added:
                replaceFragment(new InformFragment());
                break;
            case R.id.nav_new_image:
                replaceFragment(new NewImageFragment());
                break;
            case R.id.nav_retrieve_image:
                replaceFragment(new RetrieveImageFragment());
                break;
            case R.id.nav_visited:
                replaceFragment(new PatrolFragment());
                break;
            case R.id.nav_completed:
                replaceFragment(new VisitedFragmentAdmin());
                break;
            case R.id.nav_add_acquists:
                replaceFragment(new AddAcquistsFragment());
                break;
            case R.id.nav_acquists_location:
                replaceFragment(new AcquistsLocationFragment());
                break;
            case R.id.nav_logout:

                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();

                Toast.makeText(Dashboard.this, "User Logged-out successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Dashboard.this, Login.class);
                startActivity(intent);

                finish();
            default:
                return false;

        }

        return true;
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }

}