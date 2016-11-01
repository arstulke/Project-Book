package arstulke.projectbook.utils.serializer;

import android.content.Context;
import android.graphics.Bitmap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import arstulke.projectbook.utils.MyJSONParser;

@SuppressWarnings("unused")
public class BitmapSerializer extends JsonSerializer<Bitmap> {

    public static String url;

    @Override
    public void serialize(Bitmap bitmap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        //region save

        //endregion
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        jsonGenerator.writeBinary(byteArray);
    }
}
