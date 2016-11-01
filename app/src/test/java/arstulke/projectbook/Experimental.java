package arstulke.projectbook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import arstulke.projectbook.model.Book;

public class Experimental {
    @Test
    public void test() throws IOException {
        URL url = new URL("http://bilder.buecher.de/produkte/41/41622/41622411z.jpg");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        Bitmap cover = BitmapFactory.decodeStream(inputStream);

        Book book = new Book("isbn", "title", "author", "publisher", "relseaseDate", "description", "imageUrl", -1);
        book.setCover(cover);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("book", book);

        String output = jsonObject.toJSONString();
        System.out.println(output);
    }
}
