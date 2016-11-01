package arstulke.projectbook.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.fasterxml.jackson.core.JsonProcessingException;

import arstulke.projectbook.activities.BookActivity;
import arstulke.projectbook.controller.MyApplication;
import arstulke.projectbook.model.Book;
import arstulke.projectbook.utils.MyJSONParser;

@SuppressWarnings("SpellCheckingInspection")
public class BookView extends View {

    private final Activity activity;

    public BookView(Context context, Activity activity) {
        super(context);
        this.activity = activity;
    }

    public void showBook(Book book, boolean newBook) throws JsonProcessingException {
        Intent intent = new Intent(activity, BookActivity.class);

        if (newBook) {
            intent.putExtra("newBook", MyJSONParser.parseObject(book));
        } else {
            intent.putExtra("book", book.hashCode());
        }
        intent.putExtra("libName", MyApplication.libName);

        MyApplication.newBook = newBook;

        activity.startActivity(intent);
    }
}
