package arstulke.projectbook;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LibraryView.class, LibrariesActivity.class})
public class RenderTester {

    @Test
    public void showBookList() throws Exception {
        Context applicationContext = mock(Context.class);
        Resources resources = mock(Resources.class);
        Activity activity = mock(Activity.class);
        LibraryView libraryView = new LibraryView(applicationContext, resources, activity);
        ListView listView = mock(ListView.class);
        ArrayAdapter adapter = mock(ArrayAdapter.class);
        whenNew(ListView.class).withAnyArguments().thenReturn(listView);
        whenNew(ArrayAdapter.class).withAnyArguments().thenReturn(adapter);


        //given
        List<String> bookList = Arrays.asList("Buch1", "Buch2");
        RelativeLayout parent = mock(RelativeLayout.class);

        //When
        libraryView.show(bookList, parent);

        //Then
        ArgumentCaptor<ArrayAdapter> adapterCaptor = ArgumentCaptor.forClass(ArrayAdapter.class);
        verify(listView).setAdapter(adapterCaptor.capture());
        verifyNew(ArrayAdapter.class).withArguments(any(Context.class), anyInt(), anyInt(), eq(bookList));
        verify(parent).addView(listView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void openDefaultLayout() throws Exception {
        //given
        LibrariesActivity defaultActivity = mock(LibrariesActivity.class);

        Toolbar toolbar = mock(Toolbar.class);
        DrawerLayout drawer = mock(DrawerLayout.class);
        ActionBarDrawerToggle toggle = mock(ActionBarDrawerToggle.class);
        when(defaultActivity.findViewById(R.id.toolbar)).thenReturn(toolbar);
        when(defaultActivity.findViewById(R.id.drawer_layout)).thenReturn(drawer);
        whenNew(ActionBarDrawerToggle.class).withArguments(defaultActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close).thenReturn(toggle);

        NavigationView navigationView = mock(NavigationView.class);
        when(defaultActivity.findViewById(R.id.nav_view)).thenReturn(navigationView);

        //when
        defaultActivity.onCreate(null, null);

        //then
        verify(defaultActivity).setSupportActionBar(toolbar);

        verify(drawer).setDrawerListener(toggle);
        verify(toggle).syncState();

        verify(navigationView).setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) any());
    }
}