package arstulke.projectbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by ARSTULKE on 29.09.2016.
 */

public class LibraryView {

    private Context applicationContext;
    private Resources resources;
    private Activity activity;
    private RelativeLayout parent;

    public LibraryView(Context applicationContext, Resources resources, Activity activity) {
        this.applicationContext = applicationContext;
        this.resources = resources;
        this.activity = activity;
    }

    private Context getApplicationContext(){
        return applicationContext;
    }

    private Activity getActivity() {
        return activity;
    }

    private Resources getResources() {
        return resources;
    }

    public void show(List<String> bookList, RelativeLayout parent){
        ListView listView = new ListView(getApplicationContext());
        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, bookList);
        listView.setAdapter(adapter);
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.dividerColor)));
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
                throw new UnsupportedOperationException();
            }
        });

        parent.addView(listView);
        this.parent = parent;
    }

    public RelativeLayout getParent(){
        return parent;
    }
}
