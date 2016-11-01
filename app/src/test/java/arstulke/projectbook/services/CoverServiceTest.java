package arstulke.projectbook.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import arstulke.projectbook.ProxyFactory;

public class CoverServiceTest {
    @Before
    public void setup() {

    }

    @Ignore
    @Test
    public void downloadCover() throws IOException {
        //Given
        String imageUrl = "http://bilder.buecher.de/produkte/44/44780/44780699z.jpg";
        ProxyFactory.buildProxy(System.getProperty("user.home") + "/proxySettings.proxy");
        //When
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
      //  Bitmap bitmap = getBitmapFromURL(imageUrl);

        //Then
        Assert.assertEquals(bitmap, bitmap);
    }
}
