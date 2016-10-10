package arstulke.projectbook.backend.implementation.Tests;


import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Test;

import arstulke.projectbook.backend.implementation.search.SearchBook;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class SearchBookTest {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void refactorISBN() {
        //given
        String ISBN_10 = "1941222294";
        String ISBN_13 = "9781941222294";
        final String ISBN_TO_LONG = "97819412222943";
        final String ISBN_TO_SHORT = "9781941";
        final String ISBN_NO_NUMBER = "9781DasDarfNichtSein941222294";

        //when
        final SearchBook sb = new SearchBook();
        String NEW_ISBN_10 = sb.refactorISBN(ISBN_10);
        String NEW_ISBN_13 = sb.refactorISBN(ISBN_13);
        new ShouldFail(){
            @Override
            public void failCode() {
                sb.refactorISBN(ISBN_TO_LONG);
            }
        }.test("Should fail: IllegalArgumentException",new IllegalArgumentException());
        new ShouldFail(){
            @Override
            public void failCode() {
                sb.refactorISBN(ISBN_NO_NUMBER);
            }
        }.test("Should fail: IllegalArgumentException",new IllegalArgumentException());
        new ShouldFail(){
            @Override
            public void failCode() {
                sb.refactorISBN(ISBN_TO_SHORT);
            }
        }.test("Should fail: IllegalArgumentException",new IllegalArgumentException());

        //then
        assertThat(NEW_ISBN_10, is("9781941222294"));
        assertThat(NEW_ISBN_13, is("9781941222294"));
    }
}
