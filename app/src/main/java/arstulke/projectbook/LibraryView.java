package arstulke.projectbook;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LibraryView {
    private Context applicationContext;
    private Resources resources;
    private Activity activity;

    public LibraryView(Context applicationContext, Resources resources, Activity activity) {
        this.applicationContext = applicationContext;
        this.resources = resources;
        this.activity = activity;
    }

    private ListView listView;
    public void show(List<String> bookList, RelativeLayout parent){
        listView = new ListView(applicationContext);
        ArrayAdapter adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_1, android.R.id.text1, bookList);
        listView.setAdapter(adapter);
        listView.setDivider(new ColorDrawable(resources.getColor(R.color.dividerColor)));
        listView.setDividerHeight(2);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(parent.getContext(), BookActivity.class);
                intent.putExtra("book", (String) parent.getItemAtPosition(position));
                activity.startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            return showOptionsDialog(position);
            }
        });

        parent.addView(listView);
    }

    private boolean showOptionsDialog(int position) {
        ArrayList<String> options = new ArrayList<>();
        Collections.addAll(options, "Bearbeiten", "Löschen");
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_1, android.R.id.text1, options);

        final AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);
        builder.setTitle("Optionen: " + listView.getItemAtPosition(position));
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
