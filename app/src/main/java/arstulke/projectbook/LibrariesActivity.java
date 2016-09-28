package arstulke.projectbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LibrariesActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries);

        setTitle(getString(R.string.app_name) + ": Bibliotheken der BTC AG");

        String[] values = new String[] { "E5", "APW", "Hamburg", "Berlin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView = (ListView) findViewById(R.id.librariesView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openBookList(position);
            }
        });
        listView.setOnDragListener(new AdapterView.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });
    }

    public void openBookList(int position){
        String library = (String) listView.getItemAtPosition(position);
        Intent intent = new Intent(LibrariesActivity.this, BooksActivity.class);
        intent.putExtra("library", library);
        LibrariesActivity.this.startActivity(intent);
    }
}