package arstulke.projectbook.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import arstulke.projectbook.R;

@SuppressWarnings("WeakerAccess")
public class StyleManager {
    public static Resources resources;

    public static void setColors(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(resources.getColor(R.color.statusBar));
            window.setNavigationBarColor(resources.getColor(R.color.navigationBar));
        }
    }

    public static void setToolbarColor(Toolbar toolbar) {
        toolbar.setBackgroundColor(resources.getColor(R.color.actionBar));
    }
}
