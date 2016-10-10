package Steps;

import cucumber.api.DataTable;
import cucumber.api.java.de.*;
import implementation.objects.Book;
import implementation.search.BookNotFoundException;
import implementation.search.SearchBook;
import org.junit.Assert;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookSearchSteps {
    private Book book;
    private String output;
    private String isbn;

    @Wenn("^ich ein Buch mit der ISBN \"([^\"]*)\" suche$")
    public void ichEinBuchMitDerISBNSuche(String isbn) throws IOException {
        SearchBook search = new SearchBook();
        try {
            book = search.byISBN(isbn);
        } catch (BookNotFoundException e) {
            output = "Das Buch konnte nicht gefunden werden oder die ISBN ist inkorrekt.";
            book = null;
        } catch (IllegalArgumentException e) {
            output = "ISBN ist inkorrekt. Bitte überprüfe das Format der ISBN";
            book = null;
        }
    }

    @Dann("^bekomme ich das Buch mit folgenden Merkmalen angezeigt:$")
    public void bekommeIchDasBuchMitFolgendenMerkmalenAngezeigt(DataTable dataTable) {
            //given
            List<List<String>> shouldBe;
            shouldBe = dataTable.raw();
            List<List<String>> isActually = new LinkedList<>();
            List<String> tmp = new LinkedList<>();
        if (book != null) {
            //fill in
            tmp.add("Title");
            tmp.add("Autor");
            tmp.add("Verlag");
            tmp.add("Erscheinungsdatum");
            isActually.add(tmp);
            tmp = new LinkedList<>();
            tmp.add(book.getTitle());
            tmp.add(book.getAuthor());
            tmp.add(book.getPublisher());
            tmp.add(book.getReleaseDate());
            isActually.add(tmp);
        }

            //then
            assertEquals(isActually.toString(), shouldBe.toString());

    }

    @Dann("^bekomme ich folgende Ausgabe \"([^\"]*)\"$")
    public void bekommeIchFolgendeAufforderung(String output) throws Throwable {
        Assert.assertEquals(output, this.output);
    }


    @Dann("^wird die ISBN-10 in die ISBN-13 \"([^\"]*)\" umgewandelt$")
    public void wirdDieISBNInDieISBNUmgewandelt(String isbn) throws Throwable {
        assertEquals(this.isbn, isbn);
    }
}
