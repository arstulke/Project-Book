package arstulke.projectbook;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LibraryView.class})
public class RenderTester {

    private LibraryView libraryView;
    private List<String> bookList;
    private RelativeLayout parent;
    private ListView listView;
    private ArrayAdapter adapter;

    @Before
    public void setup() throws Exception {
        Context applicationContext = mock(Context.class);
        Resources resources = mock(Resources.class);
        Activity activity = mock(Activity.class);
        libraryView = new LibraryView(applicationContext, resources, activity);
        listView = mock(ListView.class);
        adapter = mock(ArrayAdapter.class);
        whenNew(ListView.class).withAnyArguments().thenReturn(listView);
        whenNew(ArrayAdapter.class).withAnyArguments().thenReturn(adapter);
    }

    @Test
    public void showBookList() throws Exception {
        //Given
        bookList = Arrays.asList("Buch1", "Buch2");
        parent = mock(RelativeLayout.class);

        //When
        libraryView.show(bookList, parent);

        //Then
        ArgumentCaptor<ArrayAdapter> adapterCaptor = ArgumentCaptor.forClass(ArrayAdapter.class);
        verify(listView).setAdapter(adapterCaptor.capture());
        verifyNew(ArrayAdapter.class).withArguments(any(Context.class), anyInt(), anyInt(), eq(bookList));
        verify(parent).addView(listView);
    }
}