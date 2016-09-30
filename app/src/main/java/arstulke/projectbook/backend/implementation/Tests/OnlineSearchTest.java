package arstulke.projectbook.backend.implementation.Tests;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Test;

import java.io.IOException;

import arstulke.projectbook.backend.implementation.objects.Book;
import arstulke.projectbook.backend.implementation.search.BookNotFoundException;
import arstulke.projectbook.backend.implementation.search.OnlineSearch;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class OnlineSearchTest{

    @Test
    public void removeAllFromString(){
        //given
        String newString = "susi sagt saure soße";

        //when
        OnlineSearch os = new OnlineSearch();
        String finalString = os.removeAll(newString,"sagt","s");

        //then
        assertThat(finalString, is("ui  aure oße"));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void substringByStringFromString() {
        //given
        String newString = "susi sagt saure soße";

        //when
        OnlineSearch os = new OnlineSearch();
        String finalString = os.substring(newString,"susi sagt "," soße");
        ((ShouldFail) () -> os.substring(newString,"susi sagte einmal "," soße")).test(
                        "Should be: String index out of range: -1",new StringIndexOutOfBoundsException());
        String finalStringExtremeMin = os.substring(newString,"","");

        //then
        assertThat(finalString, is("saure"));
        assertThat(finalStringExtremeMin, is(""));
    }

    @Test
    public void getContentWithoutPost(){
        //given
        String newURL = "http://kam-pixels.de/teamspeak/tsb/result.txt";

        //when
        OnlineSearch os = new OnlineSearch();
        String websiteContent = "";
        try {
            websiteContent = os.getContent(newURL,"");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //then
        assertThat(websiteContent, is("INVALID -- cmd=_notify-validate"));
    }

    @Test
    public void searchBook() throws IOException {
        //given
        String newISBN = "9783933651303";

        //when
        OnlineSearch os = new OnlineSearch();
        Book book = new Book();
        try {
            book = os.searchOnline(newISBN);
        } catch (BookNotFoundException e) {
            e.printStackTrace();
        }

        //then
        assertThat(book.getTitle(),is("Hallo, wir sind Mara und Timo"));
    }
}
