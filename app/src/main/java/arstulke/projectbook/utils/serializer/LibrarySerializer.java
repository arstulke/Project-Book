package arstulke.projectbook.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import arstulke.projectbook.model.Book;
import arstulke.projectbook.model.Library;

public class LibrarySerializer  extends JsonSerializer<Library> {
    @Override
    public void serialize(Library library, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString(library.getName());
        jsonGenerator.writeFieldName("books");
        jsonGenerator.writeStartArray();
        for(Book book : library)
            jsonGenerator.writeObject(book);
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
