package arstulke.projectbook.utils.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import arstulke.projectbook.model.Book;
import arstulke.projectbook.model.Library;

@SuppressWarnings("unused")
public class LibraryDeserializer extends JsonDeserializer<Library> {

    @Override
    public Library deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        String name = jsonNode.get("name").textValue();
        List<Book> books = new ArrayList<>();

        for(JsonNode object: jsonNode.get("books")){
            Book book = new ObjectMapper().readValue(object.toString(), Book.class);
            books.add(book);
        }



        return new Library(name, books.toArray(new Book[0]));
    }
}
