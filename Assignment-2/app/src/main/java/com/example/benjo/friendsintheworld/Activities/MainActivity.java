package com.example.benjo.friendsintheworld.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.benjo.friendsintheworld.Controller.Controller;
import com.example.benjo.friendsintheworld.Dialogs.DialogAdd;
import com.example.benjo.friendsintheworld.Dialogs.DialogGroups;
import com.example.benjo.friendsintheworld.Dialogs.DialogJoin;
import com.example.benjo.friendsintheworld.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Controller controller;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initController();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };
        mNavigationView.setNavigationItemSelectedListener(new NavigationListener());
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
    }

    private void initController() {
        controller = new Controller(this);
    }

    public void setFragment(Fragment fragment, boolean backStack) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (!fragment.isAdded()) {
            ft.replace(R.id.fragment_container, fragment);
            if (backStack) {
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }

    public void showText(String text) {
        Snackbar.make(findViewById(R.id.main_activity_parent), text, Snackbar.LENGTH_LONG).show();
    }

    private void showDialogAdd() {
        DialogAdd dialogAdd = (DialogAdd) getFragmentManager().findFragmentByTag("dialogAdd");
        if (dialogAdd == null) {
            dialogAdd = new DialogAdd();
        }
        dialogAdd.show(getFragmentManager(), "dialogAdd");
    }

    public void showDialogJoin(String currentGroup) {
        DialogJoin dialogJoin = (DialogJoin) getFragmentManager().findFragmentByTag("dialogJoin");
        if (dialogJoin == null) {
            dialogJoin = new DialogJoin();
        }
        dialogJoin.setGroup(currentGroup);
        dialogJoin.show(getFragmentManager(), "dialogJoin");
    }

    private void showDialogGroups() {
        DialogGroups dialogGroups = (DialogGroups) getFragmentManager().findFragmentByTag("dialogGroups");
        if (dialogGroups == null) {
            dialogGroups = new DialogGroups();
        }
        dialogGroups.show(getFragmentManager(), "dialogGroups");
    }

    public void register(String name, String group) {
        controller.register(name, group);
    }

    public void unsubscribe(String group) {
        controller.unsubscribe(group);
    }

    public ArrayList<String> getMyGroups() {
        return controller.getMyGroups();
    }

    public void showMarkers(String group) {
        controller.addMarkers(group);
    }

    private class NavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.drawer_add_group:
                    showDialogAdd();
                    break;
                case R.id.drawer_gmap:
                    controller.showMap();
                    break;
                case R.id.drawer_my_groups:
                    showDialogGroups();
                    break;
                case R.id.drawer_eng:
                    controller.changeLang("en");
                    break;
                case R.id.drawer_swe:
                    controller.changeLang("sv");
                    break;
            }
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        controller.onPause();
    }
}
