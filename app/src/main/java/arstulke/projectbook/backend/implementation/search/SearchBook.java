package arstulke.projectbook.backend.implementation.search;


import java.io.IOException;

import arstulke.projectbook.backend.implementation.objects.Book;

public class SearchBook extends OnlineSearch {

    public Book byISBN(String isbn) throws IllegalArgumentException, BookNotFoundException, IOException {
        isbn = refactorISBN(isbn);
        return searchOnline(isbn);
    }

    public String refactorISBN(String isbn) {
        String newISBN = isbn.replaceAll(" ", "");
        newISBN = newISBN.replaceAll("-", "");
        if ((newISBN.length() != 13 && newISBN.length() != 10) || !newISBN.matches("[0-9]+"))
            throw new IllegalArgumentException("ISBN incorrect");
        else {
            if (newISBN.length() == 10) {
                return "978" + newISBN;
            } else {
                return newISBN;
            }
        }
    }
}
