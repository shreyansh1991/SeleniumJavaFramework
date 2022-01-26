package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import objects.BillingAddress;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JacksonUtils {
    public static <T> T deserializeJson(String filePath,Class<T> T) {
        InputStream inputStream = JacksonUtils.class.getClassLoader().getResourceAsStream(filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(inputStream, T);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Map<String, String> deserializeJson(String filePath, TypeReference<Map<String, String>> T) {
        InputStream inputStream = JacksonUtils.class.getClassLoader().getResourceAsStream(filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(inputStream, T);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
