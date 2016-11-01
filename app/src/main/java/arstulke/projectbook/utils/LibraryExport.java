package arstulke.projectbook.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import arstulke.projectbook.model.Book;

/**
 * Useful:
 * * PACKAGE_NAME:
 * * * Created by KAABERT on 01.11.2016.
 */
public class LibraryExport {

    public static void asCSV(FileOutputStream outputStream, ArrayList<Book> books, char separator) throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        for (Book book : books) {
            lines.add(replaceNullValue(book.getIsbn()) + separator +
                    replaceNullValue(book.getTitle()) + separator +
                    replaceNullValue(book.getAuthor()) + separator +
                    replaceNullValue(book.getReleaseDate()) + separator +
                    replaceNullValue(book.getPublisher()) + separator +
                    replaceNullValue(book.getDescription()) + separator +
                    replaceNullValue(book.getBuyLink()));
        }

        String output = "";
        for (String line : lines) {
            output += line + "\n";
        }
        output = output.substring(0, output.length());

        outputStream.write(output.getBytes());
        outputStream.close();
    }

    private static String replaceNullValue(String value) {
        return value == null ? "" : value;
    }
}
