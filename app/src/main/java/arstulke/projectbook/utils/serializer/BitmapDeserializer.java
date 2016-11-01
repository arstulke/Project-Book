package arstulke.projectbook.utils.serializer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class BitmapDeserializer extends JsonDeserializer<Bitmap> {

    @Override
    public Bitmap deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        byte[] bytes = jsonNode.binaryValue();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
