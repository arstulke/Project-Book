package arstulke.projectbook.model;

import android.graphics.Bitmap;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import arstulke.projectbook.utils.serializer.BitmapDeserializer;
import arstulke.projectbook.utils.serializer.BitmapSerializer;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Book implements Serializable {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String releaseDate;
    private String description;
    private String imageUrl;
    private int pages;
    private String buyLink;

    @JsonSerialize(using = BitmapSerializer.class)
    @JsonDeserialize(using = BitmapDeserializer.class)
    private Bitmap cover;

    public Book() {
    }

    public Book(String isbn, String title, String author, String publisher, String releaseDate, String description, String imageUrl, int pages) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.description = description;
        this.imageUrl = imageUrl;
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title=\"" + title + "\"" +
                ", isbn=\"" + isbn + "\"" +
                ", author=\"" + author + "\"" +
                ", publisher=\"" + publisher + "\"" +
                ", releaseDate=\"" + releaseDate + "\"" +
                ", description=\"" + description + "\"" +
                ", imageUrl=\"" + imageUrl + "\"" +
                ", cover=" + cover +
                ", pages=" + pages +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int result = isbn.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + pages;
        return result;
    }

    //region GETTER
    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Bitmap getCover() {
        return cover;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public int getPages() {
        return pages;
    }
    //endregion

    //region SETTER
    public void setCover(Bitmap cover) {
        this.cover = cover;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    //endregion

    public boolean matches(String search) {
        return StringUtils.containsIgnoreCase(title, search) || StringUtils.containsIgnoreCase(isbn, search) || StringUtils.containsIgnoreCase(author, search);
    }
}
