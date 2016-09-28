package arstulke.projectbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        TextView tv = (TextView) this.findViewById(R.id.textView);
        int visibility;
        if(tv.getVisibility() == VISIBLE) {
            visibility = INVISIBLE;
        } else {
            visibility = VISIBLE;
        }
        tv.setVisibility(visibility);
    }
}