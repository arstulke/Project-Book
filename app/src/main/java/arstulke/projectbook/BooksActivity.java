package arstulke.projectbook;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BooksActivity extends AppCompatActivity {

    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        setColors();

        Intent intent = getIntent();
        String library = intent.getStringExtra("library");
        setTitle(getString(R.string.app_name) + ": Bücherliste(" + library + ")");
        String[] values = new String[] { "Cucumber", "Think Java", "Think Java", "Think Java", "Think Java", "Think Java", "Think Java", "Think Java", "Grundkurs Programmieren in Java"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView = (ListView) findViewById(R.id.booksView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String book = (String) listView.getItemAtPosition(position);
                Intent intent = new Intent(BooksActivity.this, BookActivity.class);
                intent.putExtra("book", book);
                BooksActivity.this.startActivity(intent);
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
}
