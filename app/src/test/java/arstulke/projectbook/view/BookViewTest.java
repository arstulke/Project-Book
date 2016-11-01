package arstulke.projectbook.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import arstulke.projectbook.R;
import arstulke.projectbook.controller.Settings;
import arstulke.projectbook.model.Book;
import arstulke.projectbook.services.CoverService;

import static arstulke.projectbook.controller.Settings.useEthernet;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LibraryView.class, BookView.class, Settings.class})
public class BookViewTest {

    private Context context;

    @Before
    public void setup() {
        context = Mockito.mock(Context.class);
    }

    @Test
    public void showBook() throws Exception {
        View view = mock(View.class);
        Application application = mock(Application.class);
        Activity activity = mock(Activity.class);
        PowerMockito.mockStatic(Settings.class);
        BDDMockito.given(Settings.useEthernet(any(Application.class))).willReturn(true);

        //region initialize mocks
        TextView title = mock(TextView.class);
        TextView author = mock(TextView.class);
        TextView isbn = mock(TextView.class);
        TextView releaseDate = mock(TextView.class);
        WebView description = mock(WebView.class);
        TextView publisher = mock(TextView.class);
        TextView pages = mock(TextView.class);
        ImageView cover = mock(ImageView.class);
        //endregion
        //region when findViewById then return mock
        when(view.findViewById(R.id.book_title)).thenReturn(title);
        when(view.findViewById(R.id.book_author)).thenReturn(author);
        when(view.findViewById(R.id.book_isbn)).thenReturn(isbn);
        when(view.findViewById(R.id.book_releaseDate)).thenReturn(releaseDate);
        when(view.findViewById(R.id.book_description)).thenReturn(description);
        when(view.findViewById(R.id.book_publisher)).thenReturn(publisher);
        when(view.findViewById(R.id.book_pages)).thenReturn(pages);
        when(view.findViewById(R.id.book_cover)).thenReturn(cover);
        //endregion
        CoverService cs = mock(CoverService.class);
        whenNew(CoverService.class).withNoArguments().thenReturn(cs);

        //given
        Book book = new Book("isbn1", "title1", "author1", "publisher1", "releaseDate1", "description1", "imageURL1", 1);

        //when
        BookView bookView = new BookView(context, activity);
        bookView.showBook(book, true);

        //then
        //region verify findViewById
        //verify(view).findViewById(R.id.book_title);
        verify(view).findViewById(R.id.book_author);
        verify(view).findViewById(R.id.book_isbn);
        verify(view).findViewById(R.id.book_releaseDate);
        verify(view).findViewById(R.id.book_description);
        verify(view).findViewById(R.id.book_publisher);
        verify(view).findViewById(R.id.book_cover);
        verify(view).findViewById(R.id.book_pages);
        //endregion
        //region verify setText()
        verify(title).setText(book.getTitle());
        verify(author).setText(book.getAuthor());
        verify(isbn).setText(book.getIsbn());
        verify(releaseDate).setText(book.getReleaseDate());
        verify(description).loadData(anyString(), anyString(), anyString());
        verify(publisher).setText(book.getPublisher());
        verify(pages).setText("Seitenzahl: " + book.getPages());

        //endregion
        verify(cs).load(useEthernet(application));
    }
}