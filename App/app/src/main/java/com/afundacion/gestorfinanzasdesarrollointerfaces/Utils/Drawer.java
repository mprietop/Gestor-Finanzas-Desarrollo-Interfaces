package com.afundacion.gestorfinanzasdesarrollointerfaces.Utils;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.afundacion.gestorfinanzasdesarrollointerfaces.R;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Screens.History;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Screens.StartScreen;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Screens.Stats;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Screens.Login;
import com.afundacion.gestorfinanzasdesarrollointerfaces.Screens.Transaction;
import com.google.android.material.navigation.NavigationView;

public class Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));

        setupDrawer(toolbar);
        setupNavigationView();
        //setupHeader();
    }

    private void setupDrawer(Toolbar toolbar) {
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        drawerLayout.addDrawerListener(this);
    }

    /*
    private void setupHeader() {
        View header = navigationView.getHeaderView(0);
        header.findViewById(R.id.header_title).setOnClickListener(view -> Toast.makeText(
                HomeActivity.this,
                getString(R.string.title_click),
                Toast.LENGTH_SHORT).show());
    }
    */

    private void setupNavigationView() {
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //@Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int title = getTitle(menuItem);
        showFragment(title, menuItem.toString());
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private int getTitle(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_inicio:
                return R.string.menu_inicio;
            case R.id.nav_agregar:
                return R.string.menu_agregar;
            case R.id.nav_historial:
                return R.string.menu_historial;
            case R.id.nav_estadisticas:
                return R.string.menu_estadisticas;
            case R.id.nav_logout:
                return R.string.menu_logout;
            default:
                throw new IllegalArgumentException("menu option not implemented!!");
        }
    }

    private void showFragment(@StringRes int title, String name) {
        Fragment fragment = null;
        switch (name) {
            case "Inicio":
                fragment = StartScreen.newInstance();
                break;
            case "Agregar":
                fragment = Transaction.newInstance();
                break;
            case "Historial":
                fragment = History.newInstance();
                break;
            case "Estadisticas":
                fragment = Stats.newInstance();
                break;
            case "Cerrar sesión":
                SharedPreferences prefs = getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
                .replace(R.id.home_content, fragment)
                .commit();
        setTitle(getString(title));
    }


    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }



}
