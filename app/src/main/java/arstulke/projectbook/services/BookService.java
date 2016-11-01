package arstulke.projectbook.services;

import android.os.AsyncTask;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@SuppressWarnings("unchecked")
public class BookService extends AsyncTask<String, Void, Void> {

    public BookService(boolean showHTML) {
        if (showHTML)
            urlParams += "html";
    }

    public String status = "RUNNING";
    public JSONObject jsonObject = null;

    private String urlPattern = "http://kam-pixels.de:55555/isbn/";
    private String urlParams = "";


    public void load(String isbn) {
        execute(isbn);
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            String url = prepareURL(params[0]);
            String content = getContentFromURL(new URL(url));
            this.jsonObject = parseJson(content);
        } catch (Exception e) {
            //region exceptionHandling
            if (e.getClass() != FileNotFoundException.class) {
                e.printStackTrace();
            }
            try {
                this.jsonObject = (JSONObject) new JSONParser().parse("{\"error\":\"" + e.getClass().getName() + "\"}");
            } catch (ParseException exception) {
                exception.printStackTrace();
            }
            //endregion
        }

        status = "FINISHED";
        return null;
    }

    //region doInBackgroundUtils
    private String prepareURL(String param) {
        return urlPattern + param + (urlParams.length() > 0 ? ("?" + urlParams) : "");
    }

    private String getContentFromURL(URL url) throws BookNotFoundException, IOException, ParseException {
        URLConnection urlConnection = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String content = "", line;
        while ((line = br.readLine()) != null) {
            content += line;
        }
        br.close();
        return content;
    }

    private JSONObject parseJson(String content) throws ParseException {
        return (JSONObject) new JSONParser().parse(content);
    }
    //endregion

    class BookNotFoundException extends Exception {
        BookNotFoundException(String message) {
            super(message);
        }
    }
}
