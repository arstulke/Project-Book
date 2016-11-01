package arstulke.projectbook.utils;

import android.app.Activity;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.simple.JSONObject;

import arstulke.projectbook.model.Book;
import arstulke.projectbook.view.BookView;

public class BookParser {
    public static Book parseJSONToBook(JSONObject jsonObject) {
        if (!jsonObject.isEmpty() && !jsonObject.containsKey("error")) {
            Book book = new Book();
            book.setPages(Integer.parseInt(jsonObject.get("pages").toString()));
            book.setTitle((String) jsonObject.get("title"));
            book.setDescription((String) jsonObject.get("description"));
            book.setReleaseDate((String) jsonObject.get("releaseDate"));
            book.setAuthor((String) jsonObject.get("author"));
            book.setPublisher((String) jsonObject.get("publisher"));
            book.setImageUrl((String) jsonObject.get("imageUrl"));
            book.setIsbn((String) jsonObject.get("isbn"));
            book.setBuyLink((String) jsonObject.get("buyLink"));
            //book.setBuyLink("http://bücher.de/");
            return book;
        } else {
            return null;
        }
    }

    public static void saveAndShowBook(Book book, Activity activity) throws JsonProcessingException {
        new BookView(activity, activity).showBook(book, true);
    }
}