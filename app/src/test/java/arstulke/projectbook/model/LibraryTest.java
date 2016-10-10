package arstulke.projectbook.model;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LibraryTest {

    private Library MyLibrary = new Library("MyLibrary");

    @Test
    public void addsElement() {
        //given
        Book book = new Book("123", "", "", "", "", "", "", 0);

        //when
        MyLibrary.addBook(book);

        //then
        assertEquals(true, MyLibrary.containsBook(book));
    }

    @Test
    public void getsSize() {
        //given
        int size = 0;
        Book book = new Book("123", "", "", "", "", "", "", 0);

        //when
        MyLibrary.addBook(book);

        //then
        assertEquals(size + 1, MyLibrary.size());
    }

    @Test
    public void clearsTheCollection() {
        //given

        Book book = new Book("123", "", "", "", "", "", "", 0);
        MyLibrary.addBook(book);

        //when
        MyLibrary.clear();
        int size = MyLibrary.size();

        //then
        assertEquals(0, size);
        assertEquals(true, MyLibrary.isEmpty());
    }

    @Test
    public void checksIsEmpty() {
        //given
        Book book = new Book("123", "", "", "", "", "", "", 0);
        MyLibrary.addBook(book);
        boolean pre = MyLibrary.isEmpty();

        //when
        MyLibrary.removeBook(book);

        //then
        assertEquals(true, MyLibrary.isEmpty());
        assertEquals(false, pre);
    }

    @Test
    public void convertsToArray() {
        //given
        Book book = new Book("123", "", "", "", "", "", "", 0);
        MyLibrary.addBook(book);
        Object[] goal = new Object[1];
        goal[0] = book;

        //when
        Object[] cArray = MyLibrary.toArray();

        //then
        assertThat(goal, is(cArray));
    }

    @Test
    public void checksCollectionContains() {
        //given
        Book book = new Book("123", "", "", "", "", "", "", 0);
        MyLibrary.addBook(book);

        //when
        boolean b = MyLibrary.containsBook(book);

        //then
        assertEquals(MyLibrary.toArray()[0].equals(book), b);
    }

    @Test
    public void removeElements() {
        //given
        Book book = new Book("123", "", "", "", "", "", "", 0);
        MyLibrary.addBook(book);

        //when
        MyLibrary.removeBook(book);

        //then
        assertThat(MyLibrary.size(), is(0));
        assertThat(MyLibrary.isEmpty(), is(true));
    }

}
