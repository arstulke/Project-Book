package arstulke.projectbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BookActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        Intent intent = getIntent();
        String book = intent.getStringExtra("book");
        setTitle(getString(R.string.app_name) + ": Buch(" + book + ")");
    }
}
