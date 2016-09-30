package arstulke.projectbook.backend.implementation.objects;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("unused")
public class Library {
    private String name;
    private List<Book> books = new ArrayList<>();

    @SuppressWarnings("WeakerAccess")
    public enum sortType {
        ISBN,TITLE,AUTHOR,PAGE
    }

    public Library(String name){
        this.name = name;
    }

    public Library(String name, List<Book> books){
        this.books = books;
        this.name = name;
    }

    public Object[] toArray(){
        return books.toArray();
    }

    public boolean containsBook(Book book){
        return books.contains(book);
    }

    public void addBook(int index, Book book){
        books.add(index,book);
    }

    public void addBook(Book book){
        books.add(book);
    }

    public void removeBook(Book book){
        books.remove(book);
    }

    public void removeBook(int index){
        books.remove(index);
    }

    public int size(){
        return books.size();
    }

    public void clear(){
        books.clear();
    }

    public boolean isEmpty(){
        return books.isEmpty();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortBy(sortType sort){
        switch (sort){
            case ISBN:
                books.sort((o1, o2) -> o1.getISBN().compareTo(o2.getISBN()));
                break;
            case TITLE:
                books.sort((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
                break;
            case AUTHOR:
                books.sort((o1, o2) -> o1.getAuthor().compareTo(o2.getAuthor()));
                break;
            case PAGE:
                books.sort((o1, o2) -> Integer.valueOf(o1.getPages()).compareTo(o2.getPages()));
                break;
        }
    }

}
