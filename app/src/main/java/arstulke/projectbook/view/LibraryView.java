package arstulke.projectbook.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import arstulke.projectbook.R;
import arstulke.projectbook.activities.LibrariesActivity;
import arstulke.projectbook.controller.MyApplication;
import arstulke.projectbook.controller.Settings;
import arstulke.projectbook.model.Book;
import arstulke.projectbook.model.Library;
import arstulke.projectbook.services.CoverService;

public class LibraryView {
    private Context applicationContext;
    private Resources resources;
    private Application application;
    private Activity activity;

    public LibraryView(Context applicationContext, Resources resources, Application application, Activity activity) {
        this.applicationContext = applicationContext;
        this.resources = resources;
        this.application = application;
        this.activity = activity;
    }


    private Library library;
    private RelativeLayout parent;

    public void show(final Library library, RelativeLayout parent) {
        this.library = library;
        this.parent = parent;

        final BookAdapter adapter = new BookAdapter(activity, R.layout.rowlayout, library);
        final ListView listView = new ListView(activity);
        listView.setAdapter(adapter);
        listView.setId(R.id.book_list);

        setLayout(listView);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookView bookView = new BookView(activity, activity);
                try {
                    bookView.showBook((Book) listView.getItemAtPosition(position), false);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return showOptionsDialog(listView, position);
            }
        });

        parent.addView(listView);
    }

    private void setLayout(ListView listView) {
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        p.addRule(RelativeLayout.BELOW, R.id.searchbar);
        listView.setLayoutParams(p);
        listView.setDivider(new ColorDrawable(resources.getColor(R.color.dividerColor)));
        listView.setDividerHeight(2);
    }

    private boolean showOptionsDialog(final ListView listView, final int position) {
        final ArrayList<String> options = new ArrayList<>();
        Collections.addAll(options, "Löschen");
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, options);

        final Book book = ((Book) listView.getItemAtPosition(position));

        final AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);
        builder.setTitle("Optionen: " + book.getTitle());
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyApplication.libraryManager.getLibrary(MyApplication.libName).removeBook(book.hashCode());
                LibrariesActivity.saveLibraries(MyApplication.libraryManager, application);

                //region refresh
                activity.finish();
                activity.overridePendingTransition(0, 0);
                activity.startActivity(activity.getIntent());
                activity.overridePendingTransition(0, 0);
                //endregion
            }
        });

        AlertDialog ad = builder.create();
        ad.show();
        return true;
    }

    //region BookAdapter
    class BookAdapter extends ArrayAdapter<Book> {
        Context context;
        int layoutResourceId;
        List<Book> data = null;
        private boolean useEthernet;

        BookAdapter(Context context, int layoutResourceId, List<Book> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;

            useEthernet = Settings.useEthernet(application);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View row = inflater.inflate(layoutResourceId, parent, false);

            ImageView imageView = (ImageView) row.findViewById(R.id.row_cover);
            TextView txtTitle = (TextView) row.findViewById(R.id.row_title);
            TextView txtAuthor = (TextView) row.findViewById(R.id.row_author);

            Book book = data.get(position);
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());

            CoverService cs = new CoverService();
            cs.book = book;
            cs.imageView = imageView;
            cs.load(useEthernet);

            data.set(position, book);

            return row;
        }
    }
    //endregion
}
