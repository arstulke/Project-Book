package arstulke.projectbook.backend.implementation.objects;

import android.graphics.Bitmap;
import android.media.Image;

/**
 * BookSearch:
 * * PACKAGE_NAME:
 * * * Created by KAABERT on 27.09.2016.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Book {
    private String ISBN;
    private String title;
    private String author;
    private String publisher;
    private String releaseDate;
    private String description;
    private String imageUrl;
    private Bitmap cover;
    private int pages;

    public Book() {
    }

    public Book(String ISBN, String title, String author, String publisher, String releaseDate) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
    }

    public Book(String ISBN, String title, String author, String publisher, String releaseDate, String description, String imageUrl, Bitmap cover, int pages) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.description = description;
        this.imageUrl = imageUrl;
        this.cover = cover;
        this.pages = pages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return ISBN.equals(book.ISBN);

    }

    @Override
    public int hashCode() {
        return ISBN.hashCode();
    }

    //region GETTER SETTER
    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }

    public Bitmap getCover() {
        return cover;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
    //endregion
}
