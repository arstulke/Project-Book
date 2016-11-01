package arstulke.projectbook.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import arstulke.projectbook.model.Book;

/**
 * Useful:
 * * PACKAGE_NAME:
 * * * Created by KAABERT on 01.11.2016.
 */
public class LibraryExport {

    public static void asCSV(FileOutputStream outputStream, ArrayList<Book> books, HashMap<String, Boolean> columns, char separator) throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        for (Book book : books) {
            String line = "";
            line += columns.get("isbn") ? replaceNullValue(book.getIsbn()) + separator : "";
            line += columns.get("title") ? replaceNullValue(book.getTitle()) + separator : "";
            line += columns.get("author") ? replaceNullValue(book.getAuthor()) + separator : "";
            line += columns.get("releaseDate") ? replaceNullValue(book.getReleaseDate()) + separator : "";
            line += columns.get("publisher") ? replaceNullValue(book.getPublisher()) + separator : "";
            line += columns.get("description") ? replaceNullValue(book.getDescription()) + separator : "";
            line += columns.get("buyLink") ? replaceNullValue(book.getBuyLink()) : "";

            lines.add(line);
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
