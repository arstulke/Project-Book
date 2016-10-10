package arstulke.projectbook;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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

    @Test
    public void test(){
        assertThat(1, is(1));
    }
}