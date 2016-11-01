package arstulke.projectbook.model;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LibraryManagerTest {
    
    private Library library;
    private LibraryManager libraryManager = new LibraryManager();
    
    @Test
    public void addsElement() {
        //given
        library = new Library("123");

        //when
        libraryManager.add(library);

        //then
        assertEquals(true, libraryManager.contains(library));
    }

    @Test
    public void getsSize() {
        //given
        int size = 0;
        library = new Library("123");

        //when
        libraryManager.add(library);

        //then
        assertEquals(size + 1, libraryManager.size());
    }

    @Test
    public void clearsTheCollection() {
        //given

        library = new Library("123");
        libraryManager.add(library);

        //when
        libraryManager.clear();
        int size = libraryManager.size();

        //then
        assertEquals(0, size);
        assertEquals(true, libraryManager.isEmpty());
    }

    @Test
    public void checksIsEmpty() {
        //given
        library = new Library("123");
        libraryManager.add(library);
        boolean pre = libraryManager.isEmpty();

        //when
        libraryManager.remove(library);

        //then
        assertEquals(true, libraryManager.isEmpty());
        assertEquals(false, pre);
    }

    @Test
    public void convertsToArray() {
        //given
        library = new Library("123");
        libraryManager.add(library);
        Object[] goal = new Object[1];
        goal[0] = library;

        //when
        Object[] cArray = libraryManager.toArray();

        //then
        assertThat(goal, is(cArray));
    }

    @Test
    public void checksCollectionContains() {
        //given
        library = new Library("123");
        libraryManager.add(library);

        //when
        boolean b = libraryManager.contains(library);

        //then
        assertEquals(libraryManager.toArray()[0].equals(library), b);
    }

    @Test
    public void removeElements() {
        //given
        library = new Library("123");
        libraryManager.add(library);

        //when
        libraryManager.remove(library);

        //then
        assertThat(libraryManager.size(), is(0));
        assertThat(libraryManager.isEmpty(), is(true));
    }
}
