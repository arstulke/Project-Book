package arstulke.projectbook.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import arstulke.projectbook.model.Book;
import arstulke.projectbook.model.Library;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LibraryView.class})
public class LibraryViewTest {

    private Context context;

    @Before
    public void setup() {
        context = Mockito.mock(Context.class);
    }

    @Test
    public void showBookList() throws Exception {
        Activity activity = Mockito.mock(Activity.class);
        Resources resources = Mockito.mock(Resources.class);
        Application application = Mockito.mock(Application.class);
        LibraryView libraryView = new LibraryView(context, resources, application, activity);
        ListView listView = Mockito.mock(ListView.class);
        LibraryView.BookAdapter adapter = Mockito.mock(LibraryView.BookAdapter.class);
        PowerMockito.whenNew(ListView.class).withAnyArguments().thenReturn(listView);
        PowerMockito.whenNew(LibraryView.BookAdapter.class).withAnyArguments().thenReturn(adapter);


        //given
        Library bookList = new Library("Test", new Book("Buch1", "title1", "author1", "publisher1", "releaseDate1", "description1", "imageURL1", 1), new Book("Buch2", "title2", "author2", "publisher2", "releaseDate2", "description2", "imageURL2", 2));
        RelativeLayout parent = Mockito.mock(RelativeLayout.class);

        //When
        libraryView.show(bookList, parent);

        //Then
        ArgumentCaptor<LibraryView.BookAdapter> adapterCaptor = ArgumentCaptor.forClass(LibraryView.BookAdapter.class);
        Mockito.verify(listView).setAdapter(adapterCaptor.capture());
        PowerMockito.verifyNew(LibraryView.BookAdapter.class).withArguments(Matchers.any(Context.class), Matchers.anyInt(), Matchers.eq(bookList));
        Mockito.verify(parent).addView(listView);
    }
}