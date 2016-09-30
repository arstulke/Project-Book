package arstulke.projectbook;

import android.content.res.Resources;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

public class ColorManager {

    private final Resources resources;
    public ColorManager(Resources resources) {
        this.resources = resources;
    }

    public void setColors(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(resources.getColor(R.color.statusBar));
            window.setNavigationBarColor(resources.getColor(R.color.navigationBar));
        }
    }

    public void setToolbarColor(Toolbar toolbar) {
        toolbar.setBackgroundColor(resources.getColor(R.color.actionBar));
    }
}
