package luongdev.switchconfig.common.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MappingUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String[] STRING_ARRAY = new String[0];

    private MappingUtil() {
    }

    public static <T> T convert(Object fromValue, Class<T> toValueType) {
        return OBJECT_MAPPER.convertValue(fromValue, toValueType);
    }

    public static <T> T convert(Object fromValue, TypeReference<T> toValueTypeRef) {
        return OBJECT_MAPPER.convertValue(fromValue, toValueTypeRef);
    }

    public static <T> T readValue(String fromValue, TypeReference<T> toValueTypeRef) {
        try {
            return OBJECT_MAPPER.readValue(fromValue, toValueTypeRef);
        } catch (JsonParseException e) {

        } catch (JsonMappingException e) {

        } catch (IOException e) {

        }

        return null;
    }

    public static <T> T readValue(String fromValue, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(fromValue, valueType);
        } catch (JsonParseException e) {

        } catch (JsonMappingException e) {

        } catch (IOException e) {

        }

        return null;
    }

    public static String writeValueAsString(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {

        }

        return null;
    }


    public static Map<String, Object> jsonToMap(String fromValue) {
        try {
            return OBJECT_MAPPER.readValue(fromValue, new TypeReference<HashMap<String, Object>>() {});
        } catch (IOException e) {

        }

        return null;
    }

    public static String toString(final Object bean) {
        try {
            return OBJECT_MAPPER.writeValueAsString(bean);
        } catch (JsonProcessingException e) {

        }
        return "";
    }

    public static String[] toStringArray(final List<String> list) {
        return (list == null) ? null : list.toArray(STRING_ARRAY);
    }

    public static String[] toStringArray(final String string) {
        return (string == null) ? null : new String[]{
                string
        };
    }

    public static <T> List<T> toList(Iterable<T> source) {
        List<T> target = new ArrayList<>();
        if (source != null) {
            source.forEach(target::add);
        }
        return target;
    }
}