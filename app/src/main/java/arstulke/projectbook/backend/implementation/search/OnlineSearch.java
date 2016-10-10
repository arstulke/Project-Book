package arstulke.projectbook.backend.implementation.search;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


import arstulke.projectbook.backend.implementation.objects.Book;

/**
 * BookSearch:
 * * implementation:
 * * * Created by KAABERT on 29.09.2016.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class OnlineSearch {

    public static final String DESKTOP = System.getProperty("user.home") + "/Desktop/config";


    public OnlineSearch() {
    }

    public Book searchOnline(String isbn) throws BookNotFoundException, IOException {
            String url = "http://www.buecher.de/ni/search_search/quick_search/receiver_object/shop_search_quicksearch/";
            String post = "form[q]="+isbn;
            String WebsiteContent = getContent(url,post);

            if(!WebsiteContent.contains("wurden leider keine Artikel gefunden."))
            {
                String title = substring(WebsiteContent, "<meta property=\"og:title\" content=\"", "\"/>");
                String author = substring(WebsiteContent, "<title>" + title + " von ", " - ");
                String description = substring(WebsiteContent, "<meta name=\"description\" content=\"", "\"/>");
                String publisher = substring(WebsiteContent, "rel='nofollow'>", "</a></li><li>Seitenzahl:");
                int pages = Integer.parseInt(substring(WebsiteContent, "<li>Seitenzahl: ", "</li><li>"));
                String release = substring(WebsiteContent, "Seitenzahl: " + pages + "</li><li> ", "</li><li>");
                String imageUrl = substring(WebsiteContent, "<meta property=\"og:image\" content=\"", "\"/>");
                HttpURLConnection connection = (HttpURLConnection)new URL(imageUrl).openConnection();
                connection.setRequestProperty("User-agent","Mozilla/4.0");
                connection.connect();
                InputStream input = connection.getInputStream();

                Bitmap image = BitmapFactory.decodeStream(input);
                return new Book(isbn,title,author,publisher,release,description,imageUrl,image,pages);
            }
            throw new BookNotFoundException();
    }

    public String getContent (String givenUrl, String post) throws IOException {
            URL url = new URL(givenUrl);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            writer.write(post);
            writer.flush();

            String line;
            String content = "";

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                content = content + line;
            }
            writer.close();
            reader.close();

            return content;
    }

    public String substring(String source, String before, String after){
        source = source.substring(source.indexOf(before) + before.length());
        source = source.substring(0, source.indexOf(after));
        return source;
    }

    public String removeAll(String source, String... removeText){
        for(String element: removeText) {
            source = source.replaceAll(element, "");
        }
        return source;
    }
}
