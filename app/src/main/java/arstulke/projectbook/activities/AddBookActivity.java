package arstulke.projectbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.Serializable;

import arstulke.projectbook.R;
import arstulke.projectbook.controller.MyApplication;
import arstulke.projectbook.controller.Settings;
import arstulke.projectbook.model.Book;
import arstulke.projectbook.services.BookService;
import arstulke.projectbook.utils.BookParser;
import arstulke.projectbook.utils.StyleManager;

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton scanBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        StyleManager.resources = getResources();
        StyleManager.setColors(getWindow());

        scanBtn = (ImageButton) findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);
    }

    public void searchBookEditText(View view) {
        EditText editText = (EditText) findViewById(R.id.add_book_isbn);
        String isbn = editText.getText().toString();
        if(isbn.length() == 10 || isbn.length() == 13)
            searchBook(isbn, false);
        else
            Toast.makeText(this, "Die ISBN muss 10 oder 13 Zeichen lang sein und darf nur Zahlen enthalten.", Toast.LENGTH_LONG).show();
    }

    private void searchBook(String isbn, boolean searchWithScan) {
        BookService bs = new BookService(Settings.showHTML(getApplication()), Settings.bookServiceHost(getApplication()));
        bs.load(isbn);

        //noinspection StatementWithEmptyBody
        while (bs.status.equals("RUNNING")) {
        }

        Book book = BookParser.parseJSONToBook(bs.jsonObject);
        if (book != null) {
            try {
                BookParser.saveAndShowBook(book, this);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            MyApplication.book = null;
            Intent intent = new Intent(this, LibrariesActivity.class);
            startActivity(intent);
        }
        MyApplication.newBook = true;
        MyApplication.searchWithScan = searchWithScan;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.scan_button) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            if (scanFormat.equals("EAN_13")) {
                searchBook(scanContent, true);
                Log.i("scanResult", scanContent);
            } else {
                Toast toast = Toast.makeText(this, "Falsches Barcode Format", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
