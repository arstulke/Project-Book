package arstulke.projectbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BooksActivity extends AppCompatActivity {

    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        Intent intent = getIntent();
        String library = intent.getStringExtra("library");

        setTitle(getString(R.string.app_name) + ": Bücherliste(" + library + ")");

        String[] values = new String[] { "Cucumber", "Think Java", "Grundkurs Programmieren in Java"};
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
}
