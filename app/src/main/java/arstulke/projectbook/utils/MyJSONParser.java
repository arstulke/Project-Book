package arstulke.projectbook.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@SuppressWarnings("unused")
public class MyJSONParser {
    public static  String parseObject(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static Object parseJSON(String json, Class c) throws IOException {
        return new ObjectMapper().readValue(json, c);
    }

    public static Object parseJSON(InputStream inputStream, Class c) throws IOException {
        return new ObjectMapper().readValue(inputStream, c);
    }
}
