package arstulke.projectbook.services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import arstulke.projectbook.ProxyFactory;
import arstulke.projectbook.model.Book;
import arstulke.projectbook.utils.BookParser;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("NewApi")
public class BookServiceTest {
    @Before
    public void setup() {
        ProxyFactory.buildProxy(System.getProperty("user.home") + "/proxySettings.proxy");
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void searchBook() throws IOException, BookService.BookNotFoundException, ParseException {
        //given
        String json = "{\"title\":\"Der kleine Hobbit\",\"author\":\"John R. R. Tolkien\",\"publisher\":\"Dtv\",\"releaseDate\":\"2013\",\"description\":\"Die Vorgeschichte zu 'Herr der Ringe'<br/><br/>Bilbo Beutlin, ein angesehener Hobbit, findet sich eines Morgens in der Gesellschaft von Gandalf, dem Zauberer, wieder - und von dreizehn Zwergen, die einer nach dem anderen unangemeldet in seine Wohnhöhle hereinplatzen. Und damit ist es mit seinem geruhsamen Leben vorbei. Gepackt von einer für Hobbits ungewöhnlichen Abenteuerlust nimmt er den Auftrag an, den Zwergenschatz, den der Drache Smaug einst gestohlen hatte, wieder zurückzuholen. Kein leichtes Unterfangen, denn Smaug sieht es gar nicht gerne, wenn jemand seinem Goldschatz zu nahe kommt.<br />\",\"imageUrl\":\"http://bilder.buecher.de/produkte/38/38064/38064405z.jpg\",\"pages\":397,\"isbn\":\"9783423715669\"}";
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(json);

        //when
        Book actual = BookParser.parseJSONToBook(jsonObject);

        //then
        Book expected = new Book();
        expected.setTitle("Der kleine Hobbit");
        expected.setAuthor("John R. R. Tolkien");
        expected.setPublisher("Dtv");
        expected.setReleaseDate("2013");
        expected.setDescription("Die Vorgeschichte zu 'Herr der Ringe'<br/><br/>Bilbo Beutlin, ein angesehener Hobbit, findet sich eines Morgens in der Gesellschaft von Gandalf, dem Zauberer, wieder - und von dreizehn Zwergen, die einer nach dem anderen unangemeldet in seine Wohnhöhle hereinplatzen. Und damit ist es mit seinem geruhsamen Leben vorbei. Gepackt von einer für Hobbits ungewöhnlichen Abenteuerlust nimmt er den Auftrag an, den Zwergenschatz, den der Drache Smaug einst gestohlen hatte, wieder zurückzuholen. Kein leichtes Unterfangen, denn Smaug sieht es gar nicht gerne, wenn jemand seinem Goldschatz zu nahe kommt.<br />");
        expected.setImageUrl("http://bilder.buecher.de/produkte/38/38064/38064405z.jpg");
        expected.setPages(397);
        expected.setIsbn("9783423715669");

        assertThat(actual, is(expected));
    }
}
