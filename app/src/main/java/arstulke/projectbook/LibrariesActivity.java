package arstulke.projectbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EdgeEffect;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

@SuppressWarnings("deprecation")
public class LibrariesActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries);
        setTitle(getString(R.string.app_name) + ": Bibliotheken der BTC AG");
        setColors();

        String[] values = new String[]{"E5", "APW", "Hamburg", "Berlin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView = (ListView) findViewById(R.id.librariesView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openBookList(position);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return showLibraryOptions(position);
            }
        });
    }

    private void setColors() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.navigationBar));
            ActionBar actionBar = this.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.actionBar)));
            }

            
        }
    }

    public void openBookList(int position) {
        String library = (String) listView.getItemAtPosition(position);
        Intent intent = new Intent(LibrariesActivity.this, BooksActivity.class);
        intent.putExtra("library", library);
        LibrariesActivity.this.startActivity(intent);
    }

    public boolean showLibraryOptions(int position) {
        final String library = (String) listView.getItemAtPosition(position);
        final String[] values = new String[]{"Bearbeiten", "Löschen"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Optionen: " + library);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println(adapter.getItem(which));
            }
        });

        AlertDialog ad = builder.create();
        ad.show();

        return true;
    }
}