package arstulke.projectbook;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

@SuppressWarnings("deprecation")
public class LibrariesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String[] options = new String[]{"Einstellungen", "Tutorial"};
    private String[] libs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries);
        setTitle("P-Book");

        loadLibrary();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu navigationMenu = navigationView.getMenu();
        navigationMenu.clear();
        SubMenu subMenu = navigationMenu.addSubMenu("Bibliotheken");
        libs = new String[]{"E5", "APW"};
        for (String lib : libs) {
            subMenu.add(lib);
        }

        subMenu = navigationMenu.addSubMenu("Sonstiges");
        for (String option : options) {
            subMenu.add(option);
        }

        ColorManager colorManager = new ColorManager(getResources());
        colorManager.setColors(getWindow());
        colorManager.setToolbarColor(toolbar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        System.out.println(((MyApplication) getApplicationContext()).lib);

        getMenuInflater().inflate(R.menu.libraries, menu);
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

    @SuppressWarnings({"StatementWithEmptyBody", "SuspiciousMethodCalls"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        LinkedList<String> a = new LinkedList<>();
        Collections.addAll(a, libs);
        if (a.contains(item.getTitle())) {
            ((MyApplication) getApplication()).lib = (String) item.getTitle();
        } else {
            if (item.getTitle() == options[0]) {
                openSettings();
            } else if (item.getTitle() == options[1]) {
                openTutorial(null);
            } else {
                ((MyApplication) getApplication()).lib = null;
            }
        }

        loadLibrary();

        return true;
    }

    private void loadLibrary() {
        final String selectedLibrary = ((MyApplication) getApplication()).lib;
        if (selectedLibrary != null) {
            RelativeLayout content = (RelativeLayout) findViewById(R.id.cLibraries);
            content.removeAllViews();

            ArrayList<String> arrayList = new ArrayList<>();
            Collections.addAll(arrayList, "Buch1", "Buch2", "Buch3", "Buch4");
            LibraryView lv = new LibraryView(this, getResources(), this);
            lv.show(arrayList, content);

            this.setTitle(selectedLibrary);
        }
    }

    private void openSettings() {
        ///Settings
        System.out.println("Open Settings");
    }

    public void openTutorial(View view) {
        /////tutorial
        System.out.println("Open Tutorial");
    }
}
