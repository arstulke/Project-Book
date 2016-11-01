package arstulke.projectbook.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import arstulke.projectbook.utils.serializer.LibraryDeserializer;
import arstulke.projectbook.utils.serializer.LibrarySerializer;

@SuppressWarnings("unused")
@JsonSerialize(using = LibrarySerializer.class)
@JsonDeserialize(using = LibraryDeserializer.class)
public class Library extends ArrayList<Book> {

    private static final Comparator<? super Book> C_TITLE = new Comparator<Book>() {
        @Override
        public int compare(Book o1, Book o2) {
            int res = String.CASE_INSENSITIVE_ORDER.compare(o1.getTitle(), o2.getTitle());
            if (res == 0) {
                res = o1.getTitle().compareTo(o2.getTitle());
            }
            return res;
        }
    };
    private static final Comparator<? super Book> C_AUTHOR = new Comparator<Book>() {
        @Override
        public int compare(Book o1, Book o2) {
            int res = String.CASE_INSENSITIVE_ORDER.compare(o1.getAuthor(), o2.getAuthor());
            if (res == 0) {
                res = o1.getAuthor().compareTo(o2.getAuthor());
            }
            return res;
        }
    };
    private static final Comparator<? super Book> C_PAGES = new Comparator<Book>() {
        @Override
        public int compare(Book o1, Book o2) {
            return Integer.valueOf(o1.getPages()).compareTo(o2.getPages());
        }
    };


    private String name;

    public static Comparator<? super Book> getComparator(sortType sortType) {
        switch (sortType) {
            case AUTHOR:
                return C_AUTHOR;

            case PAGE:
                return C_PAGES;

            case TITLE:
            default:
                return C_TITLE;
        }
    }

    public Book getBookByHashCode(int hashCode) {
        for (Book book : this) {
            if (book.hashCode() == hashCode)
                return book;
        }
        return null;
    }

    public boolean removeBook(int hashCode) {
        int i = 0;
        for (Book book : this) {
            if (book.hashCode() == hashCode) {
                i++;
            }
        }

        if (i == 1) {
            for (Book book : this) {
                if (book.hashCode() == hashCode) {
                    remove(book);
                    return true;
                }
            }
        } else if (i > 1) {
            i = 0;
            for (Book book : this) {
                if (book.hashCode() == hashCode && book.getCover() == null) {
                    remove(i);
                    return true;
                }
                i++;
            }

            i = 0;
            for (Book book : this) {
                if (book.hashCode() == hashCode && book.getCover() != null) {
                    remove(i);
                    return true;
                }
                i++;
            }
        }

        return false;
    }

    public enum sortType {
        TITLE(0), AUTHOR(1), PAGE(2);

        private int value;

        sortType(int i) {
            this.value = i;
        }

        public int getValue() {
            return value;
        }
    }

    public Library(String name) {
        this.name = name;
    }

    public Library(String name, Book... books) {
        this.name = name;

        Collections.addAll(this, books);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
