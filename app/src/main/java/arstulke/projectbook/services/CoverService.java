package arstulke.projectbook.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import arstulke.projectbook.R;
import arstulke.projectbook.model.Book;

public class CoverService extends AsyncTask<Book, Void, Bitmap> {

    private Exception e = null;
    public Book book = null;
    public ImageView imageView = null;


    public void load(boolean ethernet) {
        if (book.getCover() == null) {
            if (ethernet) {
                execute(book);
            }
        } else {
            imageView.setImageBitmap(book.getCover());
            imageView.setBackgroundResource(R.color.trans);
        }
    }

    @Override
    protected Bitmap doInBackground(Book... params) {
        try {
            return getBitmapFromURL(params[0].getImageUrl());
        } catch (Exception e) {
            this.e = e;
            return null;
        }
    }

    private static Bitmap getBitmapFromURL(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        return BitmapFactory.decodeStream(inputStream);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap == null && e != null) {
            if (e.getClass() != MalformedURLException.class)
                e.printStackTrace();
        } else {
            if (book.getCover() == null) {
                book.setCover(bitmap);
            }

            imageView.setImageBitmap(book.getCover());
            imageView.setBackgroundResource(R.color.trans);

            /*try {
                ObjectMapper objectMapper = new ObjectMapper();
                BitmapSerializer.url = book.getImageUrl().replace("/", "").replace("\\", "");
                String output = objectMapper.writeValueAsString(book);
                System.out.println(output);
                Book deserializeBook = objectMapper.readValue(output, Book.class);
                System.out.println(deserializeBook.equals(book));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
}
