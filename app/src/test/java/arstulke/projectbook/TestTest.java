package arstulke.projectbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import org.junit.Test;

import java.io.IOException;

import arstulke.projectbook.model.Book;
import arstulke.projectbook.model.BookNotFoundException;
import arstulke.projectbook.model.MyError;

public class TestTest {
    @Test
    public void test() throws IOException, IllegalArgumentException, BookNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        //String jsonInString = "{\"isbn\" : \"randomISBN\",\"title\" : \"randomTitle\",\"author\" : \"randomAuthor\",\"publisher\" : \"randomPublisher\",\"releaseDate\" : \"randomRealeaseDate\",\"description\" : \"randomDescription\",\"imageUrl\" : \"imageURL\",\"pages\" : 100}"; // example
        //String jsonInString = "{\"title\": \"The Cucumber for Java Book\", \"author\": \"Seb Rose; Matt Wynne; Aslak Hellesoy\", \"publisher\": \"O'Reilly UK Ltd.\", \"description\": \"Teams working on the JVM can now say goodbye forever...\", \"imageUrl\": \"http://bilder.buecher.de/produkte/41/41622/41622411z.jpg\", \"pages\": 338, \"isbn\": \"9781941222294\" }"; //von Karl
        //String jsonInString = "{\"error\": \"IOException\"}"; // error
        //String jsonInString = "{\"error\": \"IllegalArgumentException\"}"; // error
        String jsonInString = "{\"error\": \"BookNotFoundException\"}"; // error

        try {
            Book book = mapper.readValue(jsonInString, Book.class);
            System.out.println(book.getISBN());
            System.out.println(book.getTitle());
            System.out.println(book.getAuthor());
            System.out.println(book.getPublisher());
            System.out.println(book.getReleaseDate());
            System.out.println(book.getImageUrl());
            System.out.println(book.getPages());
        } catch (UnrecognizedPropertyException ignore) {
            MyError error = mapper.readValue(jsonInString, MyError.class);
            switch (error.getType()) {
                case "BookNotFoundException":
                    throw new BookNotFoundException();

                case "IllegalArgumentException":
                    throw new IllegalArgumentException("Falsches ISBN Format");

                case "IOException":
                    throw new IOException("Diese Funktion ist mementan nicht verfügbar.");
            }
        }
    }
}
