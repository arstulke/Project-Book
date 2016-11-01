package arstulke.projectbook.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import arstulke.projectbook.R;
import arstulke.projectbook.controller.MyApplication;
import arstulke.projectbook.controller.Settings;
import arstulke.projectbook.model.Book;
import arstulke.projectbook.model.Library;
import arstulke.projectbook.services.CoverService;
import arstulke.projectbook.utils.MyJSONParser;
import arstulke.projectbook.utils.StyleManager;

@SuppressWarnings("SpellCheckingInspection")
public class BookActivity extends AppCompatActivity {
    Book book = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareActivity();
        Library library = null;

        Intent intent = getIntent();
        System.out.println(intent.hasExtra("libName"));


        if (getIntent().hasExtra("libName")){
            library = MyApplication.libraryManager.getLibrary(getIntent().getStringExtra("libName"));
        }


        if (MyApplication.newBook) {
            try {
                book = (Book) MyJSONParser.parseJSON(getIntent().getStringExtra("newBook"), Book.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MyApplication.book = book;
        } else if (getIntent().hasExtra("libName") && getIntent().hasExtra("book")) {
            book = library.getBookByHashCode(getIntent().getIntExtra("book", -1));
        } else {
            finish();
        }


        //loadBook();

        String libName = (library.getName().length() > 0 ? library.getName() + ": " : "");
        setTitle(libName + book.getTitle());

        show(book, findViewById(R.id.book_layout));
    }

    private void prepareActivity() {
        setContentView(R.layout.activity_book);
        StyleManager.setColors(getWindow());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionBar)));
        }

        WebView webView = (WebView) findViewById(R.id.book_description);
        webView.setBackgroundColor(Color.TRANSPARENT);
    }

    public void show(Book book, View view) {
        TextView title = (TextView) view.findViewById(R.id.book_title);
        TextView author = (TextView) view.findViewById(R.id.book_author);
        TextView isbn = (TextView) view.findViewById(R.id.book_isbn);
        TextView releaseDate = (TextView) view.findViewById(R.id.book_releaseDate);
        WebView description = (WebView) view.findViewById(R.id.book_description);
        TextView publisher = (TextView) view.findViewById(R.id.book_publisher);
        TextView pages = (TextView) view.findViewById(R.id.book_pages);
        ImageView imageView = (ImageView) view.findViewById(R.id.book_cover);

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        isbn.setText(book.getIsbn());
        releaseDate.setText(book.getReleaseDate());
        publisher.setText(book.getPublisher());
        pages.setText("Seitenzahl: " + book.getPages());
        description.loadData(getDescription(book), "text/html; charset=utf8", "utf8");

        loadCover(book, imageView, false);
    }

    public void loadCover(Book book, ImageView imageView) {
        loadCover(book, imageView, true);
    }

    private void loadCover(Book book, ImageView imageView, boolean forceToDownload) {
        CoverService cs = new CoverService();
        cs.book = book;
        cs.imageView = imageView;
        cs.load(Settings.useEthernet(getApplication()) || forceToDownload);
    }

    public String getDescription(Book book) {
        String buyLink = "<br><a href=\"" + book.getBuyLink() + "\">Auf Bücher.de kaufen</a><br><br><a href=\"https://www.amazon.de/s/ref=nb_sb_noss_2?__mk_de_DE=ÅMÅŽÕÑ&url=search-alias%3Daps&field-keywords=" + book.getTitle().replace(" ", "+") + "\">Auf Amazon suchen</a>";
        if (!Settings.showAdvertisementLinks(getApplication())) {
            buyLink = "";
        }
        return "<div style=\"text-align: justify;\">" + book.getDescription() + "<br><br>" + buyLink + "<br><br></div>";
    }

    public void downloadCover(View view) {
        if (!Settings.useEthernet(getApplication())) {
            loadCover(book, (ImageView) findViewById(R.id.book_cover));
        }
    }
}
