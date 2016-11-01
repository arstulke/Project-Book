package arstulke.projectbook.activities;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import arstulke.projectbook.R;
import arstulke.projectbook.controller.MyApplication;
import arstulke.projectbook.model.Book;
import arstulke.projectbook.model.Library;
import arstulke.projectbook.model.LibraryManager;
import arstulke.projectbook.utils.MyJSONParser;
import arstulke.projectbook.utils.StyleManager;
import arstulke.projectbook.view.LibraryView;

@SuppressWarnings({"deprecation", "SpellCheckingInspection"})
public class LibrariesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String[] other = new String[]{"Einstellungen", "Tutorial"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MyApplication.libraryManager == null) {
            try {
                MyApplication.libraryManager = new LibraryManager(getApplication());
                MyApplication.libraryManager.loadLibraries("storage");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.libraries, menu);
        menu.clear();

        if (MyApplication.libName != null) {
            menu.add("Buch hinzufügen");
            menu.add("Bibliothek entfernen");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String title = (String) item.getTitle();

        /*if (title.equals("Sortieren")) {
            ArrayList<String> sortModes = new ArrayList<>();
            Collections.addAll(sortModes, "Titel", "Autor", "Seiten");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, sortModes);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setSingleChoiceItems(adapter, 2, new AlertDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (Library.sortType sortMode : Library.sortType.values()) {
                        if (sortMode.getValue() == which) {
                            sortLibrary(sortMode);
                        }
                    }
                    refresh();
                }
            });
            builder.setTitle("Sortieren");

            AlertDialog ad = builder.create();
            ad.show();
        } else */
        if (title.equals("Buch hinzufügen")) {
            Intent intent = new Intent(getApplicationContext(), AddBookActivity.class);
            startActivity(intent);
        } else if (title.equals("Bibliothek entfernen")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bibliothek entfernen");
            builder.setMessage("Möchten Sie wircklich die Bibliothek \"" + MyApplication.libName + "\" unwiderruflich entfernen.");
            builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyApplication.libraryManager.remove(MyApplication.libraryManager.getLibrary(MyApplication.libName));
                    MyApplication.libName = null;

                    saveLibraries(MyApplication.libraryManager, getApplication());
                    show();
                }
            });
            builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (item.getTitle().toString().equals("Bibliothek hinzufügen")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Bitte tragen Sie einen Namen für die neue Bibliothek ein.");
            builder.setTitle("Bibliothek hinzufügen");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            input.setLayoutParams(layoutParams);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (input.getText().length() >= 3) {
                        MyApplication.libName = input.getText().toString();
                        MyApplication.libraryManager.add(new Library(MyApplication.libName));

                        saveLibraries(MyApplication.libraryManager, getApplication());
                        loadNavigationView();
                        loadLibrary();
                    } else {
                        Toast.makeText(getApplicationContext(), "Name muss mind. 3 Zeichen enthalten", Toast.LENGTH_LONG).show();
                    }
                }
            });
            builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            LinkedList<String> a = new LinkedList<>();
            for (Library lib : MyApplication.libraryManager) {
                a.add(lib.getName());
            }
            //noinspection SuspiciousMethodCalls
            if (a.contains(item.getTitle())) {
                MyApplication.libName = (String) item.getTitle();
            } else {
                if (item.getTitle() == other[0]) {
                    openSettings();
                } else if (item.getTitle() == other[1]) {
                    openTutorial(null);
                } else {
                    MyApplication.libName = null;
                }
            }

            loadLibrary();
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MyApplication.libName != null) {
            /*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(findViewById(R.id.searchbar).getWindowToken(), 0);*/
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    private void show() {
        setContentView(R.layout.activity_libraries);

        Library library = MyApplication.libraryManager.getLibrary(MyApplication.libName);

        if (library == null)
            setTitle("P-Book");
        else {
            setTitle(library.getName());
            if (MyApplication.newBook) {
                if (MyApplication.book != null) {
                    MyApplication.libraryManager.getLibrary(MyApplication.libName).add(MyApplication.book);
                    saveLibraries(MyApplication.libraryManager, getApplication());
                    MyApplication.book = null;
                } else {
                    Toast toast;
                    if (MyApplication.searchWithScan) {
                        toast = Toast.makeText(this, "Das Buch konnte nicht gefunden werden. Versuchen Sie es erneut zu scannen.", Toast.LENGTH_LONG);
                    } else {
                        toast = Toast.makeText(this, "Das Buch konnte nicht gefunden werden.", Toast.LENGTH_LONG);
                    }
                    toast.show();
                }
                MyApplication.newBook = false;
            }
        }

        loadLibrary();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        loadNavigationView();

        StyleManager.resources = getResources();
        StyleManager.setColors(getWindow());
        StyleManager.setToolbarColor(toolbar);
    }

    private void loadNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu navigationMenu = navigationView.getMenu();
        navigationMenu.clear();
        SubMenu subMenu = navigationMenu.addSubMenu("Bibliotheken");
        for (Library lib : MyApplication.libraryManager) {
            MenuItem menuItem = subMenu.add(lib.getName());
            menuItem.setIcon(getResources().getDrawable(R.drawable.icon_book));
        }
        MenuItem menuItem = subMenu.add("Bibliothek hinzufügen");
        menuItem.setIcon(getResources().getDrawable(R.drawable.icon_add));

        subMenu = navigationMenu.addSubMenu("Sonstiges");
        menuItem = subMenu.add(other[0]);
        menuItem.setIcon(getResources().getDrawable(R.drawable.icon_gear));
        menuItem = subMenu.add(other[1]);
        menuItem.setIcon(getResources().getDrawable(R.drawable.icon_academy));
    }

    public static void saveLibraries(LibraryManager libraryManager, Application application) {
        String fileName = "LibraryManager.txt";

        try {
            String content = MyJSONParser.parseObject(libraryManager);

            FileOutputStream outputStream = application.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sortLibrary(Library.sortType sortType) {
        Library lib = MyApplication.libraryManager.getLibrary(MyApplication.libName);
        if (lib != null) {
            switch (sortType) {
                case AUTHOR:
                    sortType = Library.sortType.AUTHOR;
                    break;

                case PAGE:
                    sortType = Library.sortType.PAGE;
                    break;

                case TITLE:
                default:
                    sortType = Library.sortType.TITLE;
                    break;
            }
            Collections.sort(lib, Library.getComparator(sortType));
        }
    }

    private void loadLibrary() {
        final Library library = MyApplication.libraryManager.getLibrary(MyApplication.libName);
        if (library != null) {
            this.setTitle(library.getName());
            sortLibrary(Library.sortType.TITLE);

            final RelativeLayout content = (RelativeLayout) findViewById(R.id.cLibraries);
            content.removeAllViews();

            EditText searchBar = getSearchBar();
            searchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    content.removeViewAt(1);
                    if (s.length() == 0)
                        MyApplication.search = "";
                    else
                        MyApplication.search = s.toString();
                    showBookList(MyApplication.search, library, content);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            content.addView(searchBar);

            showBookList(MyApplication.search, library, content);

            invalidateOptionsMenu();
        } else {
            setContentView(R.layout.activity_libraries);
        }
    }

    @NonNull
    private EditText getSearchBar() {
        EditText searchBar = new EditText(this);
        searchBar.setHint("Suchen");
        searchBar.setText(MyApplication.search);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 10;
        layoutParams.bottomMargin = 5;
        layoutParams.rightMargin = 20;
        layoutParams.leftMargin = 20;

        searchBar.setLayoutParams(layoutParams);
        searchBar.setHeight(30);
        searchBar.setTextSize(20);
        searchBar.setMaxLines(1);
        searchBar.setId(R.id.searchbar);
        return searchBar;
    }

    private void showBookList(String search, Library library, RelativeLayout content) {
        Library lib = new Library("Suche");
        if (search != null && search.length() > 0) for (Book book : library) {
            if (book.matches(search)) {
                lib.add(book);
            }
        }
        else {
            lib.addAll(library);
        }

        new LibraryView(this, getResources(), getApplication(), this).show(lib, content);
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

        System.out.println("Open Settings");
    }

    public void openTutorial(View view) {
        MyApplication.libName = null;
        refresh();
        System.out.println("Open Tutorial");
    }

    private void refresh() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}
